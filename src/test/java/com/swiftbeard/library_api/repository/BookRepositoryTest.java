package com.swiftbeard.library_api.repository;

import com.swiftbeard.library_api.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataR2dbcTest
class BookRepositoryTest {

    @InjectMocks
    private BookRepository bookRepository;

    @Autowired
    private DatabaseClient databaseClient;

    @BeforeEach
    void setUp() {
        // Create table if not exists
        Mono<Void> createTable = databaseClient.sql(
                "CREATE TABLE IF NOT EXISTS books (" +
                        "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                        "title VARCHAR(255) NOT NULL," +
                        "author VARCHAR(255) NOT NULL," +
                        "isbn VARCHAR(20) NOT NULL," +
                        "publish_year INT," +
                        "genre VARCHAR(100)," +
                        "available BOOLEAN NOT NULL DEFAULT TRUE)"
        ).then();

        // Clean up existing data
        Mono<Void> deleteAll = bookRepository.deleteAll();

        // Insert test data
        Mono<Book> insertBook1 = bookRepository.save(
                Book.builder()
                        .title("The Hobbit")
                        .author("J.R.R. Tolkien")
                        .isbn("978-0547928227")
                        .publishYear(1937)
                        .genre("Fantasy")
                        .available(true)
                        .build()
        );

        Mono<Book> insertBook2 = bookRepository.save(
                Book.builder()
                        .title("1984")
                        .author("George Orwell")
                        .isbn("978-0451524935")
                        .publishYear(1949)
                        .genre("Dystopian")
                        .available(true)
                        .build()
        );

        // Execute setup
        StepVerifier.create(createTable
                        .then(deleteAll)
                        .then(insertBook1)
                        .then(insertBook2))
                .verifyComplete();
    }

    @Test
    void findAll_ShouldReturnAllBooks() {
        Flux<Book> allBooks = bookRepository.findAll();

        StepVerifier.create(allBooks.collectList())
                .expectNextMatches(books -> books.size() == 2)
                .verifyComplete();
    }

    @Test
    void findById_ShouldReturnBook() {
        Mono<Book> savedBook = bookRepository.findAll().next();

        StepVerifier.create(savedBook.flatMap(book -> bookRepository.findById(book.getId())))
                .expectNextMatches(foundBook -> foundBook.getTitle().equals("The Hobbit") || foundBook.getTitle().equals("1984"))
                .verifyComplete();
    }

    @Test
    void findByAuthorIgnoreCase_ShouldReturnBooksByAuthor() {
        Flux<Book> booksByAuthor = bookRepository.findByAuthorIgnoreCase("j.r.r. tolkien");

        StepVerifier.create(booksByAuthor)
                .expectNextMatches(book -> book.getTitle().equals("The Hobbit"))
                .verifyComplete();
    }

    @Test
    void findByGenreIgnoreCase_ShouldReturnBooksByGenre() {
        Flux<Book> booksByGenre = bookRepository.findByGenreIgnoreCase("dystopian");

        StepVerifier.create(booksByGenre)
                .expectNextMatches(book -> book.getTitle().equals("1984"))
                .verifyComplete();
    }

    @Test
    void searchByTitle_ShouldReturnBooksByTitleKeyword() {
        Flux<Book> booksByTitle = bookRepository.searchByTitle("hobbit");

        StepVerifier.create(booksByTitle)
                .expectNextMatches(book -> book.getTitle().equals("The Hobbit"))
                .verifyComplete();
    }

    @Test
    void save_ShouldCreateNewBook() {
        Book newBook = Book.builder()
                .title("Test Book")
                .author("Test Author")
                .isbn("978-1234567890")
                .publishYear(2023)
                .genre("Test")
                .available(true)
                .build();

        Mono<Book> savedBook = bookRepository.save(newBook);

        StepVerifier.create(savedBook)
                .expectNextMatches(book ->
                        book.getId() != null &&
                                book.getTitle().equals("Test Book") &&
                                book.getAuthor().equals("Test Author"))
                .verifyComplete();
    }

    @Test
    void delete_ShouldRemoveBook() {
        Mono<Book> bookToDelete = bookRepository.findAll().next();

        StepVerifier.create(
                        bookToDelete.flatMap(book ->
                                bookRepository.delete(book)
                                        .then(bookRepository.findById(book.getId()))))
                .verifyComplete(); // findById should return empty Mono
    }
}
