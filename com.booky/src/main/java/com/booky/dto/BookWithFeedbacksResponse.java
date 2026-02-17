package com.booky.dto;

import com.booky.entity.Book;
import com.booky.entity.Feedback;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookWithFeedbacksResponse {
    private Long id;
    private String name;
    private String author;
    private Integer numPages;
    private List<FeedbackResponse> feedbacks;
}
