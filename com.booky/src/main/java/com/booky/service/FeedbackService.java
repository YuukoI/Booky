package com.booky.service;

import com.booky.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedbackService {

    Feedback createFeedback(Long bookId, Feedback feedback);

    Feedback updateFeedback(Long id, Feedback feedback);

    void deleteFeedback(Long id);

    Feedback getFeedback(Long id);

    Page<Feedback> getAllFeedbacksPaged(Pageable pageable);

    Page<Feedback> findByKeywordPaged(String keyword, Pageable pageable);
}
