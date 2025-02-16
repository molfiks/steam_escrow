package com.angular.angularnetwork.feedback;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    @Query("""
            SELECT feedback
            FROM Feedback feedback
            WHERE feedback.product.id = :productId
            """)
    Page<Feedback> findAllProductById(Integer productId, Pageable pageable);
}
