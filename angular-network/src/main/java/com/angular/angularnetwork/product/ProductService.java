package com.angular.angularnetwork.product;


import com.angular.angularnetwork.common.PageResponse;
import com.angular.angularnetwork.exception.OperationNotPermittedException;
import com.angular.angularnetwork.file.FileStorageService;
import com.angular.angularnetwork.history.ProductTransactionHistory;
import com.angular.angularnetwork.history.ProductTransactionHistoryRepository;
import com.angular.angularnetwork.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;


import static com.angular.angularnetwork.product.ProductSpecification.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductTransactionHistoryRepository transactionHistoryRepository;
    private final ProductMapper productMapper;
    private final FileStorageService fileStorageService;

    public Integer save(ProductRequest request, Authentication connectedUser) {

        User user = ((User) connectedUser.getPrincipal());
        Product product = productMapper.toProduct(request);
        product.setOwner(user);
        return productRepository.save(product).getId();
    }

    public ProductResponse findById(Integer productId) {
        return productRepository.findById(productId)
                .map(productMapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("No product found with the ID:: "+productId));
    }

    public PageResponse<ProductResponse> findAllProducts(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Product> products = productRepository.findAllDisplayableProducts(pageable, user.getId());
        List<ProductResponse> productsResponse = products.stream()
                .map(productMapper::toProductResponse)
                .toList();
        return new PageResponse<>(
                productsResponse,
                products.getNumber(),
                products.getSize(),
                products.getTotalElements(),
                products.getTotalPages(),
                products.isFirst(),
                products.isLast()
                );
    }

    public PageResponse<ProductResponse> findAllProductsByOwner(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Product> products = productRepository.findAll(withOwnerId(user.getId()), pageable);

        List<ProductResponse> productResponse = products.stream()
                .map(productMapper::toProductResponse)
                .toList();
        return new PageResponse<>(
                productResponse,
                products.getNumber(),
                products.getSize(),
                products.getTotalElements(),
                products.getTotalPages(),
                products.isFirst(),
                products.isLast()
        );
    }

    public PageResponse<BoughtProductResponse> findAllBoughtProducts(int page, int size, Authentication connectedUser) {

        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<ProductTransactionHistory> allBoughtProducts = transactionHistoryRepository.findAllBoughtProducts(pageable, user.getId());
        List<BoughtProductResponse> productResponse = allBoughtProducts.stream()
                .map(productMapper::toBoughtProductResponse)
                .toList();
        return new PageResponse<>(
                productResponse,
                allBoughtProducts.getNumber(),
                allBoughtProducts.getSize(),
                allBoughtProducts.getTotalElements(),
                allBoughtProducts.getTotalPages(),
                allBoughtProducts.isFirst(),
                allBoughtProducts.isLast()
        );
    }


    public PageResponse<BoughtProductResponse> findAllReturnedProducts(int page, int size, Authentication connectedUser) {

        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<ProductTransactionHistory> allBoughtProducts = transactionHistoryRepository.findAllReturnedProducts(pageable, user.getId());
        List<BoughtProductResponse> productResponse = allBoughtProducts.stream()
                .map(productMapper::toBoughtProductResponse)
                .toList();
        return new PageResponse<>(
                productResponse,
                allBoughtProducts.getNumber(),
                allBoughtProducts.getSize(),
                allBoughtProducts.getTotalElements(),
                allBoughtProducts.getTotalPages(),
                allBoughtProducts.isFirst(),
                allBoughtProducts.isLast()
        );
    }

    public Integer updateShareableStatus(Integer productId, Authentication connectedUser) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("No product found with the ID:: "+productId));
        User user = ((User) connectedUser.getPrincipal());
        if(!Objects.equals(product.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You can not update others products shareable status");
        }
        product.setShareable(!product.isShareable());
        productRepository.save(product);
        return productId;
    }

    public Integer updateArchivedStatus(Integer productId, Authentication connectedUser) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("No product found with the ID:: "+productId));
        User user = ((User) connectedUser.getPrincipal());
        if(!Objects.equals(product.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You can not update others products archived status");
        }
        product.setArchived(!product.isArchived());
        productRepository.save(product);
        return productId;
    }

    public Integer purchaseProduct(Integer productId, Authentication connectedUser) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("No product found with the ID:: "+productId));
        if(product.isArchived() || !product.isShareable()){
            throw new OperationNotPermittedException("The requested product can not be purchased since it is archived or not shareable");
        }
        User user = ((User) connectedUser.getPrincipal());
        if(Objects.equals(product.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You can not buy your own product");
        }
        final boolean isAlreadyPurchased = transactionHistoryRepository.isAlreadyPurchasedByUser(productId, user.getId());
        if(isAlreadyPurchased) {
            throw new OperationNotPermittedException("The requested product is already purchased");
        }

        ProductTransactionHistory productTransactionHistory = ProductTransactionHistory.builder()
                .user(user)
                .product(product)
                .returned(false)
                .returnApproved(false)
                .build();
        return transactionHistoryRepository.save(productTransactionHistory).getId();
    }

    public Integer approvePurchaseProduct(Integer productId, Authentication connectedUser) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("No product found with the ID:: "+productId));

        User buyer = ((User) connectedUser.getPrincipal());

        ProductTransactionHistory productTransactionHistory = transactionHistoryRepository.findByProductIdAndUserId(productId,buyer.getId())
                .orElseThrow(() -> new EntityNotFoundException("No product transaction found with the ID:: "+productId));

        if(productTransactionHistory.isPurchaseApproved()){
            throw new OperationNotPermittedException("This purchase is already approved");
        }

        User seller = product.getOwner();

        if(buyer.getBalance() < product.getPrice()){
            throw new OperationNotPermittedException("You have insufficient balance to complete this transaction");
        }

        buyer.setBalance(buyer.getBalance() - product.getPrice());
        seller.setBalance(seller.getBalance() + product.getPrice());

        // Mark the product as bought and set the buyer
        product.setBought(true);
        product.setBoughtBy(buyer);
        productRepository.save(product);

        productTransactionHistory.setPurchaseApproved(true);


        return transactionHistoryRepository.save(productTransactionHistory).getId();
    }
/*  *** İLERİDE BAKILABİLİR ŞİMDİLİK GEREK YOK ***

    public Integer returnPurchasedProduct(Integer productId, Authentication connectedUser) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("No product found with the ID:: "+productId));

        if(product.isArchived() || !product.isShareable()){
            throw new OperationNotPermittedException("The requested product can not be purchased since it is archived or not shareable");
        }

        User user = ((User) connectedUser.getPrincipal());
        if(!Objects.equals(product.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You can not buy or return your own product");
        }

        ProductTransactionHistory productTransactionHistory = transactionHistoryRepository.findByProductIdAndUserId(productId, user.getId())
                .orElseThrow(() -> new OperationNotPermittedException("You did not purchase this book"));
        productTransactionHistory.setReturned(true);

        return transactionHistoryRepository.save(productTransactionHistory).getId();
    }


    public Integer approveReturnPurchasedProduct(Integer productId, Authentication connectedUser) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("No product found with the ID:: "+productId));

        if(product.isArchived() || !product.isShareable()){
            throw new OperationNotPermittedException("The requested product can not be purchased since it is archived or not shareable");
        }

        User user = ((User) connectedUser.getPrincipal());
        if(!Objects.equals(product.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You can not buy or return your own product");
        }

        ProductTransactionHistory productTransactionHistory = transactionHistoryRepository.findByProductIdAndOwnerId(productId, user.getId())
                .orElseThrow(() -> new OperationNotPermittedException("The product is not returned yet. You can not approve its return"));

        productTransactionHistory.setReturnApproved(true);

        return transactionHistoryRepository.save(productTransactionHistory).getId();
    }

*/

    public void uploadProductCoverPicture(MultipartFile file, Authentication connectedUser, Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("No product found with the ID:: "+productId));

        User user = ((User) connectedUser.getPrincipal());
        var productCover = fileStorageService.saveFile(file, user.getId());
        product.setCover(productCover);
        productRepository.save(product);
    }

}
