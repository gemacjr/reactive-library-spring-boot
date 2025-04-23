package com.swiftbeard.library_api.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookTest {

    private Validator validator;
    private Book validBook;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }

        validBook = Book.builder()
                .id(1L)
                .title("Test Book")
                .author("Test Author")
                .isbn("978-0547928227")
                .publishYear(2023)
                .genre("Test")
                .available(true)
                .build();
    }

    @Test
    void whenValidBook_thenNoViolations() {
        Set<ConstraintViolation<Book>> violations = validator.validate(validBook);
        assertFalse(violations.isEmpty());
    }

    @Test
    void whenTitleIsNull_thenViolation() {
        Book book = Book.builder()
                .id(1L)
                .title(null)
                .author("Test Author")
                .isbn("978-0547928227")
                .publishYear(2023)
                .genre("Test")
                .available(true)
                .build();

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertFalse(violations.isEmpty());
        assertEquals("Title is required", violations.iterator().next().getMessage());
    }

    @Test
    void whenAuthorIsNull_thenViolation() {
        Book book = Book.builder()
                .id(1L)
                .title("Test Book")
                .author(null)
                .isbn("978-0547928227")
                .publishYear(2023)
                .genre("Test")
                .available(true)
                .build();

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertFalse(violations.isEmpty());
        assertEquals("Author is required", violations.iterator().next().getMessage());
    }

    @Test
    void whenIsbnIsNull_thenViolation() {
        Book book = Book.builder()
                .id(1L)
                .title("Test Book")
                .author("Test Author")
                .isbn(null)
                .publishYear(2023)
                .genre("Test")
                .available(true)
                .build();

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertFalse(violations.isEmpty());
        assertEquals("ISBN is required", violations.iterator().next().getMessage());
    }

    @Test
    void whenIsbnIsInvalid_thenViolation() {
        Book book = Book.builder()
                .id(1L)
                .title("Test Book")
                .author("Test Author")
                .isbn("invalid-isbn")
                .publishYear(2023)
                .genre("Test")
                .available(true)
                .build();

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertFalse(violations.isEmpty());
        assertEquals("Invalid ISBN format", violations.iterator().next().getMessage());
    }

    @Test
    void whenPublishYearIsNegative_thenViolation() {
        Book book = Book.builder()
                .id(1L)
                .title("Test Book")
                .author("Test Author")
                .isbn("978-0547928227")
                .publishYear(-2023)
                .genre("Test")
                .available(true)
                .build();

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertFalse(violations.isEmpty());
        assertEquals("Publish year must be positive", violations.iterator().next().getMessage());
    }

    @Test
    void whenAvailableIsNull_thenViolation() {
        Book book = Book.builder()
                .id(1L)
                .title("Test Book")
                .author("Test Author")
                .isbn("978-0547928227")
                .publishYear(2023)
                .genre("Test")
                .available(null)
                .build();

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertFalse(violations.isEmpty());
        assertEquals("Available status is required", violations.iterator().next().getMessage());
    }

    @Test
    void whenUsingNoArgsConstructor_thenAvailableDefaultsToTrue() {
        Book book = new Book();
        assertTrue(book.getAvailable());
    }
}
