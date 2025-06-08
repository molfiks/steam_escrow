package com.angular.angularnetwork.favorites;


import jakarta.validation.constraints.NotNull;

public record FavoritesRequest(

        @NotNull
        Integer productId

) {
}
