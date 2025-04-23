package com.swiftbeard.library_api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("books")
public class Book {
    @Id
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @NotBlank(message = "ISBN is required")
    @Pattern(regexp = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$",
            message = "Invalid ISBN format")
    @Column("isbn")
    private String isbn;

    @Positive(message = "Publish year must be positive")
    private Integer publishYear;

    private String genre;

    @NotNull(message = "Available status is required")
    @Builder.Default
    private Boolean available = true;
}
