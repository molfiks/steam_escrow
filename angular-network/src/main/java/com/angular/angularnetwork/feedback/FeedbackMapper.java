package com.angular.angularnetwork.feedback;

import com.angular.angularnetwork.product.Product;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FeedbackMapper {
    public Feedback toFeedback(FeedbackRequest request) {
        return Feedback.builder()
                .note(request.note())
                .comment(request.comment())
                .product(Product.builder()
                        .id(request.productId())
                        .build()
                )
                .build();
    }

    public FeedbackResponse feedbackResponse(Feedback feedback, Integer id) {
        return FeedbackResponse.builder()
                .note(feedback.getNote())
                .comment(feedback.getComment())
                .ownFeedback(Objects.equals(feedback.getCreatedBy(),id))
                .build();
    }
}
