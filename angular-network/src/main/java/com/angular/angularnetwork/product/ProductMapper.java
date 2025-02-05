package com.angular.angularnetwork.product;


import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public Product toProduct(ProductRequest request) {
        return Product.builder()
                .id(request.id())
                .title(request.title())
                .author(request.authorName())
                .description(request.description())
                .archived(false)
                .shareable(request.shareable())
                .build();
    }

    public ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .authorName(product.getAuthor())
                .ispn(product.getIspn())
                .description(product.getDescription())
                .rate(product.getRate())
                .archived(product.isArchived())
                .shareable(product.isShareable())
                .owner(product.getOwner().fullName())
                // todo implement this later
                // .cover()
                .build();
    }
}
