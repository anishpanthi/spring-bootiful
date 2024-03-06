package dev.app.demo.repository;

import dev.app.demo.entity.Book;
import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

@Repository
@Log4j2
public class BookInMemoryRepository {

  private final List<Book> books = new ArrayList<>();

  @PostConstruct
  private void init() {
    books.add(new Book(1, "Java", "James Gosling", "Java Programming", LocalDate.of(1995, 5, 23)));
    books.add(
        new Book(2, "Python", "Guido van Rossum", "Python Programming", LocalDate.of(2005, 2, 20)));
    books.add(
        new Book(3, "C++", "Bjarne Stroustrup", "C++ Programming", LocalDate.of(2000, 10, 14)));
    log.info("Initial Book Size: {}", books.size());
  }

  public List<Book> findAll() {
    return books;
  }

  public Optional<Book> findById(Integer id) {
    return books.stream().filter(b -> b.id().equals(id)).findFirst();
  }

  public void save(Book book) {
    books.removeIf(b -> b.id().equals(book.id()));
    books.add(book);
    log.info("After save, books size is {}", books.size());
  }

  public void delete(Book book) {
    log.info("Size of books before delete: {}", books.size());
    books.removeIf(b -> b.id().equals(book.id()));
    log.info("Size of books after delete: {}", books.size());
  }
}
