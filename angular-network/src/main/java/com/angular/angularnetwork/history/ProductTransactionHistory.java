package com.angular.angularnetwork.history;

import com.angular.angularnetwork.common.BaseEntity;
import com.angular.angularnetwork.product.Product;
import com.angular.angularnetwork.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class ProductTransactionHistory extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private boolean returned; // muhtemelen silinecek
    private boolean returnApproved;  // muhtemelen silinecek
    private boolean purchaseApproved;
}
