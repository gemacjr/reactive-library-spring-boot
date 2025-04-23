package com.swiftbeard.library_api.controller;

import com.swiftbeard.library_api.model.Book;
import com.swiftbeard.library_api.model.BookInput;
import com.swiftbeard.library_api.model.BookUpdateInput;
import com.swiftbeard.library_api.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private Book book;
    private BookInput bookInput;
    private BookUpdateInput bookUpdateInput;

    @BeforeEach
    void setUp() {
        book = Book.builder()
                .id(1L)
                .title("Test Book")
                .author("Test Author")
                .isbn("978-1234567890")
                .publishYear(2023)
                .genre("Test")
                .available(true)
                .build();

        bookInput = new BookInput(
                "Test Book",
                "Test Author",
                "978-1234567890",
                2023,
                "Test",
                true
        );

        bookUpdateInput = new BookUpdateInput(
                1L,
                "Updated Book",
                "Test Author",
                "978-1234567890",
                2023,
                "Test",
                true
        );
    }

    @Test
    void books_ShouldReturnAllBooks() {
        when(bookService.getAllBooks()).thenReturn(Flux.just(book));

        StepVerifier.create(bookController.books())
                .expectNext(book)
                .verifyComplete();
    }

    @Test
    void bookById_ShouldReturnBookById() {
        when(bookService.getBookById(anyLong())).thenReturn(Mono.just(book));

        StepVerifier.create(bookController.bookById(1L))
                .expectNext(book)
                .verifyComplete();
    }

    @Test
    void booksByAuthor_ShouldReturnBooksByAuthor() {
        when(bookService.getBooksByAuthor(anyString())).thenReturn(Flux.just(book));

        StepVerifier.create(bookController.booksByAuthor("Test Author"))
                .expectNext(book)
                .verifyComplete();
    }

    @Test
    void booksByGenre_ShouldReturnBooksByGenre() {
        when(bookService.getBooksByGenre(anyString())).thenReturn(Flux.just(book));

        StepVerifier.create(bookController.booksByGenre("Test"))
                .expectNext(book)
                .verifyComplete();
    }

    @Test
    void createBook_ShouldCreateAndReturnBook() {
        when(bookService.createBook(any(Book.class))).thenReturn(Mono.just(book));

        StepVerifier.create(bookController.createBook(bookInput))
                .expectNext(book)
                .verifyComplete();
    }

    @Test
    void updateBook_ShouldUpdateAndReturnBook() {
        when(bookService.updateBook(any(Book.class))).thenReturn(Mono.just(book));

        StepVerifier.create(bookController.updateBook(bookUpdateInput))
                .expectNext(book)
                .verifyComplete();
    }

    @Test
    void deleteBook_ShouldReturnTrue() {
        when(bookService.deleteBook(anyLong())).thenReturn(Mono.just(true));

        StepVerifier.create(bookController.deleteBook(1L))
                .expectNext(true)
                .verifyComplete();
    }
}
