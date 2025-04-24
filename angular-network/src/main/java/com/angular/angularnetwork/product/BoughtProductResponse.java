package com.angular.angularnetwork.product;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoughtProductResponse {
// burada bought productta purchase approve eklenecek controllerler kontrol edilecek service düzenlenecek
    private Integer id;
    private String title;
    private String sellerName;
    private String buyerName;
    private float price;
    private double rate;
    private boolean purchaseApproved;
    private boolean returned;
    private boolean returnApproved;
}
