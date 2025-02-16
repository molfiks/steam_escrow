package com.angular.angularnetwork.feedback;

import com.angular.angularnetwork.common.PageResponse;
import com.angular.angularnetwork.exception.OperationNotPermittedException;
import com.angular.angularnetwork.product.Product;
import com.angular.angularnetwork.product.ProductRepository;
import com.angular.angularnetwork.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final ProductRepository productRepository;
    private final FeedbackMapper feedbackMapper;
    private final FeedbackRepository feedbackRepository;

    public Integer save(FeedbackRequest request, Authentication connectedUser) {
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new EntityNotFoundException("No product found with the ID:: "+ request.productId()));

        if(product.isArchived() || !product.isShareable()){
            throw new OperationNotPermittedException("You can not give a feedback for an archived or not shareable product");
        }

        User user = ((User) connectedUser.getPrincipal());
        if(!Objects.equals(product.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You can not give a feedback for your own product");
        }
        Feedback feedback = feedbackMapper.toFeedback(request);
        return feedbackRepository.save(feedback).getId();
    }

    public PageResponse<FeedbackResponse> findAllFeedbackByProduct(Integer productId, int page, int size, Authentication connectedUser) {
        Pageable pageable = PageRequest.of(page, size);
        User user = ((User) connectedUser.getPrincipal());
        Page<Feedback> feedbacks = feedbackRepository.findAllProductById(productId,pageable);
        List<FeedbackResponse> feedbackResponses = feedbacks.stream()
                .map(f -> feedbackMapper.feedbackResponse(f,user.getId()))
                .toList();
        return new PageResponse<>(
                feedbackResponses,
                feedbacks.getNumber(),
                feedbacks.getSize(),
                feedbacks.getTotalElements(),
                feedbacks.getTotalPages(),
                feedbacks.isFirst(),
                feedbacks.isLast()
        );
    }
}
