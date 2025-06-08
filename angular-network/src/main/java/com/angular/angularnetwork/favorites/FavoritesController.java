package com.angular.angularnetwork.favorites;

import com.angular.angularnetwork.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("favorites")
@RequiredArgsConstructor
@Tag(name = "Favorites")
public class FavoritesController {
    private final FavoritesService favoritesService;

    @PostMapping
    public ResponseEntity<Integer> addToFavorites(
            @RequestBody FavoritesRequest request,
            Authentication authentication
    ) {
        Integer id = favoritesService.addToFavorites(request, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping
    public ResponseEntity<PageResponse<FavoritesResponse>> getFavorites(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication authentication) {
        return ResponseEntity.ok(favoritesService.findAllFavorites(page, size, authentication));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeFromFavorites(@PathVariable Integer productId, Authentication authentication) {
        favoritesService.removeFromFavorites(productId, authentication);
        return ResponseEntity.noContent().build();
    }
}
