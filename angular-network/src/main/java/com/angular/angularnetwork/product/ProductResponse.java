package com.angular.angularnetwork.product;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    private Integer id;
    private String title;
    private String description;
    private String owner;
    private float price;
    private byte[] cover;
    private double rate;
    private boolean archived;
    private boolean shareable;
    private boolean bought;
    private String boughtBy;
    private List<byte[]> imagePaths;
}
