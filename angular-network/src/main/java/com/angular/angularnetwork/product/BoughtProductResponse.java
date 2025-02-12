package com.angular.angularnetwork.product;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoughtProductResponse {

    private Integer id;
    private String title;
    private String authorName;
    private String ispn;
    private double rate;
    private boolean returned;
    private boolean returnApproved;
}
