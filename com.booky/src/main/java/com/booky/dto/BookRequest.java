package com.booky.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    @NotBlank(message = "The name cannot be empty")
    private String name;

    @NotBlank(message = "The author cannot be empty")
    private String author;

    @NotNull
    @Min(value = 1, message = "The book must have at least 1 page")
    private Integer numPages;
}
