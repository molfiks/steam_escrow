package com.angular.angularnetwork.product;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ProductRequest(
        Integer id,
        @NotNull(message = "100")
        @NotEmpty(message = "100")
        String title,
        @NotNull(message = "101")
        @NotEmpty(message = "101")
        String authorName,
        @NotNull(message = "102")
        @NotEmpty(message = "102")
        String ispn,
        @NotNull(message = "103")
        @NotEmpty(message = "103")
        String description,
        boolean shareable
) {
}
