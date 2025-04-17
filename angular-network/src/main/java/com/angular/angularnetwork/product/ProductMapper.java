package com.angular.angularnetwork.product;


import com.angular.angularnetwork.file.FileUtils;
import com.angular.angularnetwork.history.ProductTransactionHistory;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public Product toProduct(ProductRequest request) {
        return Product.builder()
                .id(request.id())
                .title(request.title())
                .description(request.description())
                .archived(false)
                .shareable(request.shareable())
                .build();
    }

    public ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .rate(product.getRate())
                .archived(product.isArchived())
                .shareable(product.isShareable())
                .owner(product.getOwner().fullName())
                .cover(FileUtils.readFileFromLocation(product.getCover()))
                .bought(product.isBought())
                .boughtBy(product.getBoughtBy() != null ? product.getBoughtBy().fullName() : null)
                .build();
    }

    public BoughtProductResponse toBoughtProductResponse(ProductTransactionHistory history) {
        return BoughtProductResponse.builder()
                .id(history.getProduct().getId())
                .title(history.getProduct().getTitle())
                .rate(history.getProduct().getRate())
                .returned(history.isReturned())
                .returnApproved(history.isReturnApproved())
                .build();
    }

}
