package com.angular.angularnetwork.product;

import com.angular.angularnetwork.common.BaseEntity;
import com.angular.angularnetwork.feedback.Feedback;
import com.angular.angularnetwork.history.ProductTransactionHistory;
import com.angular.angularnetwork.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product extends BaseEntity {


    private String title;
    private String description;
    private String cover;
    private boolean archived;
    private boolean shareable;
    private float price;
    private boolean bought;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "bought_by_id")
    private User boughtBy;

    @OneToMany(mappedBy = "product")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "product")
    private List<ProductTransactionHistory> histories;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();

    @Transient
    public double getRate(){
        if(feedbacks == null || feedbacks.isEmpty()){
            return 0.0;
        }
        var rate = this.feedbacks.stream()
                .mapToDouble(Feedback::getNote)
                .average()
                .orElse(0.0);
        double roundedRate = Math.round(rate * 10.0) / 10.0;
        return roundedRate;
    }
}
