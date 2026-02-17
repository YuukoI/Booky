package com.booky.mapper;

import com.booky.dto.BookRequest;
import com.booky.dto.BookResponse;
import com.booky.dto.FeedbackResponse;
import com.booky.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookMapper {

    public Book toEntity(BookRequest request) {
        return Book.builder()
                .name(request.getName())
                .author(request.getAuthor())
                .numPages(request.getNumPages())
                .build();
    }

    public BookResponse toResponse(Book book) {
        List<FeedbackResponse> feedbackResponses = book.getFeedbacks() != null 
            ? book.getFeedbacks().stream().map(f -> FeedbackResponse.builder()
                    .id(f.getId())
                    .text(f.getText())
                    .analysis(f.getAnalysis())
                    .build()).toList()
            : List.of();

        return BookResponse.builder()
                .id(book.getId())
                .name(book.getName())
                .author(book.getAuthor())
                .numPages(book.getNumPages())
                .feedbacks(feedbackResponses)
                .build();
    }

    public Page<BookResponse> toResponsePage(Page<Book> books) {
        return books.map(this::toResponse);
    }
}
