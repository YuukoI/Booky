package com.booky.mapper;

import com.booky.dto.FeedbackRequest;
import com.booky.dto.FeedbackResponse;
import com.booky.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class FeedbackMapper {

    public Feedback toEntity(FeedbackRequest request) {
        return Feedback.builder()
                .text(request.getText())
                .build();
    }

    public FeedbackResponse toResponse(Feedback feedback) {
        return FeedbackResponse.builder()
                .id(feedback.getId())
                .text(feedback.getText())
                .analysis(feedback.getAnalysis())
                .build();
    }

    public Page<FeedbackResponse> toResponsePage(Page<Feedback> feedbacks) {
        return feedbacks.map(this::toResponse);
    }
}
