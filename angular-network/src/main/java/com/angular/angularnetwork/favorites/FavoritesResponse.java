package com.angular.angularnetwork.favorites;

import com.angular.angularnetwork.product.ProductResponse;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoritesResponse {
    private Integer id;
    private ProductResponse productResponse;
    private LocalDateTime addedDate;
}
