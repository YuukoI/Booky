package com.booky.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "book")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The name cannot be empty")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "The author cannot be empty")
    @Column(nullable = false)
    private String author;

    @NotNull
    @Min(value = 1, message = "The book must have at least 1 page")
    @Column(name = "num_pages", nullable = false)
    private Integer numPages;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("feedbacks.book")
    private List<Feedback> feedbacks = new ArrayList<>();
}
