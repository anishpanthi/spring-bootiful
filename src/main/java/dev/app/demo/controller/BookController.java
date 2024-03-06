package dev.app.demo.controller;

import dev.app.demo.entity.Book;
import dev.app.demo.repository.BookRepository;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Log4j2
public class BookController {

  private final BookRepository bookRepository;

  public BookController(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @GetMapping("/books")
  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  @GetMapping("/books/{id}")
  public Book getBookById(@PathVariable Integer id) {
    return bookRepository.findById(id).orElse(null);
  }

  @PostMapping("/books")
  @ResponseStatus(HttpStatus.CREATED)
  public void saveBook(@RequestBody Book book) {
    bookRepository.save(book);
  }

  @PutMapping("/books/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateBook(@RequestBody Book book, @PathVariable Integer id) {
    if (bookRepository.findById(id).isEmpty()) {
      log.info("Book not found with id: {}", id);
      return;
    }
    bookRepository.save(book);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/books/{id}")
  public void deleteBook(@PathVariable Integer id) {
    bookRepository.findById(id).ifPresent(bookRepository::delete);
  }
}
