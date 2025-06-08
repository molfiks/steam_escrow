package com.angular.angularnetwork.favorites;

import com.angular.angularnetwork.common.PageResponse;
import com.angular.angularnetwork.exception.OperationNotPermittedException;
import com.angular.angularnetwork.feedback.FeedbackResponse;
import com.angular.angularnetwork.product.Product;
import com.angular.angularnetwork.product.ProductRepository;
import com.angular.angularnetwork.user.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoritesService {
    private final FavoritesRepository favoritesRepository;
    private final FavoritesMapper favoritesMapper;
    private final ProductRepository productRepository;

    public Integer addToFavorites(FavoritesRequest request, Authentication connectedUser) {

        User user = (User) connectedUser.getPrincipal();

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new EntityNotFoundException("No product found with the ID:: "+ request.productId()));

        if(product.isArchived() || !product.isShareable()){
            throw new OperationNotPermittedException("You can not add to favorites an archived or not shareable product");
        }
        if (favoritesRepository.existsByUserAndProductId(user, request.productId())) {
            throw new OperationNotPermittedException("Product is already in favorites");
        }
        if(Objects.equals(product.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You can not add to favorites your own product");
        }

        Favorites favorites = favoritesMapper.toFavorites(request, user);

        return favoritesRepository.save(favorites).getId();
    }

    public PageResponse<FavoritesResponse> findAllFavorites(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Favorites> favorites = favoritesRepository.findFavoritesByUser(user, pageable);
        List<FavoritesResponse> favoritesResponses = favorites.stream()
                .map(favoritesMapper::toFavoritesResponse)
                .toList();
        return new PageResponse<>(
                favoritesResponses,
                favorites.getNumber(),
                favorites.getSize(),
                favorites.getTotalElements(),
                favorites.getTotalPages(),
                favorites.isFirst(),
                favorites.isLast()
        );
    }

    public void removeFromFavorites(Integer productId, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("No product found with the ID:: "+ productId));
        Favorites favorite = favoritesRepository.findByUserAndProduct(user, product)
                .orElseThrow(() -> new EntityNotFoundException("Favorite not found for this user and product"));
        favoritesRepository.delete(favorite);
    }
}
