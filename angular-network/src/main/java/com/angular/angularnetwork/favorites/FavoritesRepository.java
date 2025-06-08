package com.angular.angularnetwork.favorites;

import com.angular.angularnetwork.product.Product;
import com.angular.angularnetwork.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface FavoritesRepository extends JpaRepository<Favorites, Integer> {

    @Query("""
            SELECT favorites
            FROM Favorites favorites
            WHERE favorites.user= :user
            """)
    Page<Favorites> findFavoritesByUser(User user, Pageable pageable);

    Optional<Favorites> findByUserAndProduct(User user, Product product);

    boolean existsByUserAndProductId(User user, Integer productId);


}
