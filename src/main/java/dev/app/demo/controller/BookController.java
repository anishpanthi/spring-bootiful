package dev.app.demo.controller;

import dev.app.demo.entity.Book;
import dev.app.demo.repository.BookRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
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
}
