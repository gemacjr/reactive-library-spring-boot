package com.swiftbeard.library_api.service;


import com.swiftbeard.library_api.exception.BookNotFoundException;
import com.swiftbeard.library_api.model.Book;
import com.swiftbeard.library_api.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;

    public Flux<Book> getAllBooks() {
        return bookRepository.findAll()
                .doOnComplete(() -> log.info("Retrieved all books"))
                .doOnError(e -> log.error("Error retrieving all books", e));
    }

    public Mono<Book> getBookById(Long id) {
        return bookRepository.findById(id)
                .doOnSuccess(book -> {
                    if (book != null) {
                        log.info("Retrieved book with id: {}", id);
                    } else {
                        log.warn("Book with id: {} not found", id);
                    }
                })
                .switchIfEmpty(Mono.error(new BookNotFoundException("Book not found with id: " + id)))
                .doOnError(e -> log.error("Error retrieving book with id: {}", id, e));
    }

    public Flux<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthorIgnoreCase(author)
                .doOnComplete(() -> log.info("Retrieved books by author: {}", author))
                .doOnError(e -> log.error("Error retrieving books by author: {}", author, e));
    }

    public Flux<Book> getBooksByGenre(String genre) {
        return bookRepository.findByGenreIgnoreCase(genre)
                .doOnComplete(() -> log.info("Retrieved books by genre: {}", genre))
                .doOnError(e -> log.error("Error retrieving books by genre: {}", genre, e));
    }

    @Transactional
    public Mono<Book> createBook(Book book) {
        return bookRepository.save(book)
                .doOnSuccess(savedBook -> log.info("Created book with id: {}", savedBook.getId()))
                .doOnError(e -> log.error("Error creating book", e));
    }

    @Transactional
    public Mono<Book> updateBook(Book book) {
        return bookRepository.findById(book.getId())
                .switchIfEmpty(Mono.error(new BookNotFoundException("Book not found with id: " + book.getId())))
                .flatMap(existingBook -> {
                    if (book.getTitle() != null) existingBook.setTitle(book.getTitle());
                    if (book.getAuthor() != null) existingBook.setAuthor(book.getAuthor());
                    if (book.getIsbn() != null) existingBook.setIsbn(book.getIsbn());
                    if (book.getPublishYear() != null) existingBook.setPublishYear(book.getPublishYear());
                    if (book.getGenre() != null) existingBook.setGenre(book.getGenre());
                    if (book.getAvailable() != null) existingBook.setAvailable(book.getAvailable());

                    return bookRepository.save(existingBook);
                })
                .doOnSuccess(updatedBook -> log.info("Updated book with id: {}", updatedBook.getId()))
                .doOnError(e -> log.error("Error updating book with id: {}", book.getId(), e));
    }

    @Transactional
    public Mono<Boolean> deleteBook(Long id) {
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.error(new BookNotFoundException("Book not found with id: " + id)))
                .flatMap(book -> bookRepository.delete(book).thenReturn(true))
                .doOnSuccess(result -> log.info("Deleted book with id: {}", id))
                .doOnError(e -> log.error("Error deleting book with id: {}", id, e));
    }
}

