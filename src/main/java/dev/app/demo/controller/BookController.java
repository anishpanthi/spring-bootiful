package dev.app.demo.controller;

import dev.app.demo.entity.Book;
import dev.app.demo.repository.BookJdbcRepository;
import io.micrometer.observation.annotation.Observed;
import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
@Log4j2
public class BookController {

  private final BookJdbcRepository bookJdbcRepository;

  public BookController(BookJdbcRepository bookJdbcRepository) {
    this.bookJdbcRepository = bookJdbcRepository;
  }

  @Observed(name = "book-api", contextualName = "get-all-books")
  @GetMapping("/books")
  public List<Book> getAllBooks() {
    return bookJdbcRepository.findAll();
  }

  @Observed(name = "book-api", contextualName = "get-book-by-id")
  @GetMapping("/books/{id}")
  public Book getBookById(@PathVariable Integer id) {
    return bookJdbcRepository
        .findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
  }

  @Observed(name = "book-api", contextualName = "post-book")
  @PostMapping("/books")
  @ResponseStatus(HttpStatus.CREATED)
  public void saveBook(@RequestBody @Valid Book book) {
    bookJdbcRepository.save(book);
  }

  @Observed(name = "book-api", contextualName = "put-book")
  @PutMapping("/books/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateBook(@RequestBody @Valid Book book, @PathVariable Integer id) {
    if (bookJdbcRepository.findById(id).isEmpty()) {
      log.info("Book not found with id: {}", id);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
    }
    bookJdbcRepository.update(book, id);
  }

  @Observed(name = "book-api", contextualName = "delete-book")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/books/{id}")
  public void deleteBook(@PathVariable Integer id) {
    bookJdbcRepository
        .findById(id)
        .ifPresentOrElse(
            bookJdbcRepository::delete,
            () -> {
              log.info("Book not found with id: {}", id);
              throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
            });
  }
}
