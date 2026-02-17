package com.booky.service;

import com.booky.entity.Analysis;
import com.booky.entity.Feedback;
import com.booky.exception.BookyException;
import com.booky.repository.BookRepository;
import com.booky.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedbackServiceImp implements FeedbackService {

    private final BookRepository bookRepository;
    private final FeedbackRepository feedbackRepository;
    private final AnalysisService analysisService;

    @Override
    public Feedback createFeedback(Long bookId, Feedback feedback) {
        log.info("Creating feedback for bookId: {}", bookId);
        log.info("Feedback text: {}", feedback.getText());

        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookyException("Book not found"));
        
        log.info("Book found: {}", book.getName());

        log.info("Starting AI analysis...");
        Analysis analysis = analysisService.analyzeText(feedback.getText());
        log.info("Analysis completed: {}", analysis);

        if(!analysis.isSuitable()){
            log.warn("Feedback is not suitable");
            throw new BookyException("Feedback is not suitable");
        }

        Feedback feedbackBook = Feedback.builder()
                .text(feedback.getText())
                .book(book)
                .analysis(analysis)
                .build();

        log.info("Saving feedback...");
        Feedback savedFeedback = feedbackRepository.save(feedbackBook);
        log.info("Feedback saved with ID: {}", savedFeedback.getId());

        return savedFeedback;
    }

    @Override
    public Feedback updateFeedback(Long id, Feedback feedback) {
        Feedback feedbackUpdate = feedbackRepository.findById(id).orElseThrow(() -> new BookyException("Feedback not found"));

        if(feedback.getText() != null){
            feedbackUpdate.setText(feedback.getText());
        }

        return feedbackRepository.save(feedbackUpdate);
    }

    @Override
    public void deleteFeedback(Long id) {
        if(feedbackRepository.findById(id).isPresent()){
            feedbackRepository.deleteById(id);
        }else{
            throw new BookyException("Feedback not found");
        }
    }

    @Override
    public Feedback getFeedback(Long id) {
        return feedbackRepository.findById(id).orElseThrow(() -> new BookyException("Feedback not found"));
    }

    @Override
    public Page<Feedback> getAllFeedbacksPaged(Pageable pageable) {
        return feedbackRepository.findAll(pageable);
    }

    @Override
    public Page<Feedback> findByKeywordPaged(String keyword, Pageable pageable) {
        return feedbackRepository.findByKeyword(keyword, pageable);
    }
}
