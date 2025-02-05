package com.angular.angularnetwork.product;


import com.angular.angularnetwork.common.PageResponse;
import com.angular.angularnetwork.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.angular.angularnetwork.product.ProductSpecification.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

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
}
