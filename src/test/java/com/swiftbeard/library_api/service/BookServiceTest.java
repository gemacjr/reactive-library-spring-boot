package com.swiftbeard.library_api.service;

import com.swiftbeard.library_api.exception.BookNotFoundException;
import com.swiftbeard.library_api.model.Book;
import com.swiftbeard.library_api.repository.BookRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;

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
    }

    @Test
    void getAllBooks_ShouldReturnAllBooks() {
        when(bookRepository.findAll()).thenReturn(Flux.just(book));

        StepVerifier.create(bookService.getAllBooks())
                .expectNext(book)
                .verifyComplete();
    }

    @Test
    void getBookById_WhenBookExists_ShouldReturnBook() {
        when(bookRepository.findById(anyLong())).thenReturn(Mono.just(book));

        StepVerifier.create(bookService.getBookById(1L))
                .expectNext(book)
                .verifyComplete();
    }

    @Test
    void getBookById_WhenBookDoesNotExist_ShouldThrowException() {
        when(bookRepository.findById(anyLong())).thenReturn(Mono.empty());

        StepVerifier.create(bookService.getBookById(1L))
                .expectError(BookNotFoundException.class)
                .verify();
    }

    @Test
    void getBooksByAuthor_ShouldReturnBooksByAuthor() {
        when(bookRepository.findByAuthorIgnoreCase(anyString())).thenReturn(Flux.just(book));

        StepVerifier.create(bookService.getBooksByAuthor("Test Author"))
                .expectNext(book)
                .verifyComplete();
    }

    @Test
    void getBooksByGenre_ShouldReturnBooksByGenre() {
        when(bookRepository.findByGenreIgnoreCase(anyString())).thenReturn(Flux.just(book));

        StepVerifier.create(bookService.getBooksByGenre("Test"))
                .expectNext(book)
                .verifyComplete();
    }

    @Test
    void createBook_ShouldCreateAndReturnBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(Mono.just(book));

        StepVerifier.create(bookService.createBook(book))
                .expectNext(book)
                .verifyComplete();
    }

    @Test
    void updateBook_WhenBookExists_ShouldUpdateAndReturnBook() {
        Book updatedBook = Book.builder()
                .id(1L)
                .title("Updated Book")
                .author("Test Author")
                .isbn("978-1234567890")
                .publishYear(2023)
                .genre("Test")
                .available(true)
                .build();

        when(bookRepository.findById(anyLong())).thenReturn(Mono.just(book));
        when(bookRepository.save(any(Book.class))).thenReturn(Mono.just(updatedBook));

        StepVerifier.create(bookService.updateBook(updatedBook))
                .expectNext(updatedBook)
                .verifyComplete();
    }

    @Test
    void updateBook_WhenBookDoesNotExist_ShouldThrowException() {
        when(bookRepository.findById(anyLong())).thenReturn(Mono.empty());

        StepVerifier.create(bookService.updateBook(book))
                .expectError(BookNotFoundException.class)
                .verify();
    }

    @Test
    void deleteBook_WhenBookExists_ShouldReturnTrue() {
        when(bookRepository.findById(anyLong())).thenReturn(Mono.just(book));
        when(bookRepository.delete(any(Book.class))).thenReturn(Mono.empty());

        StepVerifier.create(bookService.deleteBook(1L))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void deleteBook_WhenBookDoesNotExist_ShouldThrowException() {
        when(bookRepository.findById(anyLong())).thenReturn(Mono.empty());

        StepVerifier.create(bookService.deleteBook(1L))
                .expectError(BookNotFoundException.class)
                .verify();
    }
}
