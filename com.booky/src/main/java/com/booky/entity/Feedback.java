package com.booky.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "feedback")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The feedback cannot be empty")
    @Column(nullable = false)
    private String text;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "analysis_id", nullable = false)
    @JsonIgnoreProperties("feedback")
    private Analysis analysis;

}
