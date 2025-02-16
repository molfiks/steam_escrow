package com.angular.angularnetwork.feedback;


import com.angular.angularnetwork.common.BaseEntity;
import com.angular.angularnetwork.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Feedback extends BaseEntity {


    private Double note; // 1-5 stars
    private String comment;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
