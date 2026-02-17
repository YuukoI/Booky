package com.booky.controller;

import com.booky.dto.FeedbackRequest;
import com.booky.dto.FeedbackResponse;
import com.booky.entity.Feedback;
import com.booky.mapper.FeedbackMapper;
import com.booky.service.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final FeedbackMapper feedbackMapper;

    @PostMapping("/{bookId}")
    public ResponseEntity<FeedbackResponse> createFeedback(@PathVariable Long bookId, @Valid @RequestBody FeedbackRequest feedbackRequest){
        try {
            Feedback feedback = feedbackMapper.toEntity(feedbackRequest);
            Feedback feedbackSaved = feedbackService.createFeedback(bookId, feedback);
            FeedbackResponse response = feedbackMapper.toResponse(feedbackSaved);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<FeedbackResponse>> getAllFeedbacksPaged(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "15") int size,
                                                                       @RequestParam(defaultValue = "id") String sortBy){

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Feedback> feedbacks = feedbackService.getAllFeedbacksPaged(pageable);
        Page<FeedbackResponse> response = feedbackMapper.toResponsePage(feedbacks);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<FeedbackResponse> getFeedback(@PathVariable Long id){
        Feedback feedback = feedbackService.getFeedback(id);
        FeedbackResponse response = feedbackMapper.toResponse(feedback);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id){
        feedbackService.deleteFeedback(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeedbackResponse> updateFeedback(@PathVariable Long id, @Valid @RequestBody FeedbackRequest feedbackRequest){
        Feedback feedback = feedbackMapper.toEntity(feedbackRequest);
        Feedback updatedFeedback = feedbackService.updateFeedback(id, feedback);
        FeedbackResponse response = feedbackMapper.toResponse(updatedFeedback);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<FeedbackResponse>> findByKeywordPaged(@RequestParam String keyword,
                                                                     @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "15") int size,
                                                                     @RequestParam(defaultValue = "id") String sortBy){

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Feedback> feedbacks = feedbackService.findByKeywordPaged(keyword, pageable);
        Page<FeedbackResponse> response = feedbackMapper.toResponsePage(feedbacks);

        return ResponseEntity.ok(response);
    }

}
