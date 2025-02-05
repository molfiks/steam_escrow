package com.angular.angularnetwork.product;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    private Integer id;
    private String title;
    private String authorName;
    private String ispn;
    private String description;
    private String owner;
    private byte[] cover;
    private double rate;
    private boolean archived;
    private boolean shareable;
}
