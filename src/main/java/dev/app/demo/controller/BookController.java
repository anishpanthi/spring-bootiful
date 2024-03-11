package dev.app.demo.controller;

import dev.app.demo.entity.Book;
import dev.app.demo.repository.BookJdbcRepository;
import io.micrometer.observation.annotation.Observed;
import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/books")
@Log4j2
public class BookController {

  private final BookJdbcRepository bookJdbcRepository;

  public BookController(BookJdbcRepository bookJdbcRepository) {
    this.bookJdbcRepository = bookJdbcRepository;
  }

  @GetMapping
  @Observed(name = "book-api", contextualName = "get-all-books")
  public List<Book> getAllBooks() {
    return bookJdbcRepository.findAll();
  }

  @GetMapping("/{id}")
  @Observed(name = "book-api", contextualName = "get-book-by-id")
  public Book getBookById(@PathVariable Integer id) {
    return bookJdbcRepository
        .findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
  }

  @PostMapping
  @Observed(name = "book-api", contextualName = "post-book")
  @ResponseStatus(HttpStatus.CREATED)
  public void saveBook(@RequestBody @Valid Book book) {
    bookJdbcRepository.save(book);
  }

  @PutMapping("/{id}")
  @Observed(name = "book-api", contextualName = "put-book")
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
  @DeleteMapping("/{id}")
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
