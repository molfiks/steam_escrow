package com.angular.angularnetwork.favorites;

import com.angular.angularnetwork.product.Product;
import com.angular.angularnetwork.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.angular.angularnetwork.product.ProductMapper;

@Service
@RequiredArgsConstructor
public class FavoritesMapper {

    private final ProductMapper productMapper;

    public Favorites toFavorites(FavoritesRequest request, User user) {
        Authentication connectedUser = SecurityContextHolder.getContext().getAuthentication();
        return Favorites.builder()
                .user(user)
                .product(Product.builder()
                        .id(request.productId())
                        .build()
                )
                .build();
    }

    public FavoritesResponse toFavoritesResponse(Favorites favorites) {
        return FavoritesResponse.builder()
                .id(favorites.getId())
                .productResponse(productMapper.toProductResponse(favorites.getProduct()))
                .addedDate(favorites.getCreatedDate())
                .build();
    }
}
