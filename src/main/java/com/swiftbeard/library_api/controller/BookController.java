package com.swiftbeard.library_api.controller;

import com.swiftbeard.library_api.model.Book;
import com.swiftbeard.library_api.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @QueryMapping
    public Flux<Book> books() {
        return bookService.getAllBooks();
    }

    @QueryMapping
    public Mono<Book> bookById(@Argument Long id) {
        return bookService.getBookById(id);
    }

    @QueryMapping
    public Flux<Book> booksByAuthor(@Argument String author) {
        return bookService.getBooksByAuthor(author);
    }

    @QueryMapping
    public Flux<Book> booksByGenre(@Argument String genre) {
        return bookService.getBooksByGenre(genre);
    }

    @MutationMapping
    public Mono<Book> createBook(@Argument("book") Book input) {
        Book book = Book.builder()
                .title(input.getTitle())
                .author(input.getAuthor())
                .isbn(input.getIsbn())
                .publishYear(input.getPublishYear())
                .genre(input.getGenre())
                .available(input.getAvailable() != null ? input.getAvailable() : true)
                .build();

        return bookService.createBook(book);
    }

    @MutationMapping
    public Mono<Book> updateBook(@Argument("book") Book input) {
        Book book = Book.builder()
                .id(input.getId())
                .title(input.getTitle())
                .author(input.getAuthor())
                .isbn(input.getIsbn())
                .publishYear(input.getPublishYear())
                .genre(input.getGenre())
                .available(input.getAvailable())
                .build();

        return bookService.updateBook(book);
    }

    @MutationMapping
    public Mono<Boolean> deleteBook(@Argument Long id) {
        return bookService.deleteBook(id);
    }

    // Input classes for GraphQL mutations
    record BookInput(String title, String author, String isbn, Integer publishYear, String genre, Boolean available) {}

    record BookUpdateInput(Long id, String title, String author, String isbn, Integer publishYear, String genre, Boolean available) {}
}

