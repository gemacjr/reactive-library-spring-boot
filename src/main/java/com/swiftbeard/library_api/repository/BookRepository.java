package com.swiftbeard.library_api.repository;


import com.swiftbeard.library_api.model.Book;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface BookRepository extends ReactiveCrudRepository<Book, Long> {

    Flux<Book> findByAuthorIgnoreCase(String author);

    Flux<Book> findByGenreIgnoreCase(String genre);

    @Query("SELECT * FROM books WHERE LOWER(title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Flux<Book> searchByTitle(String keyword);
}

