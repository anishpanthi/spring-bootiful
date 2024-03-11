package dev.app.demo.repository;

import dev.app.demo.entity.Book;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Log4j2
public class BookJdbcRepository {

  private final JdbcClient jdbcClient;

  public BookJdbcRepository(JdbcClient jdbcClient) {
    this.jdbcClient = jdbcClient;
  }

  @Observed(name = "book-repository", contextualName = "find-all-books")
  public List<Book> findAll() {
    return jdbcClient.sql("SELECT * FROM books").query(Book.class).stream().toList();
  }

  @Observed(name = "book-repository", contextualName = "find-book-by-id")
  public Optional<Book> findById(Integer id) {
    return jdbcClient
        .sql("SELECT * FROM books WHERE id = :id")
        .param("id", id)
        .query(Book.class)
        .stream()
        .findFirst();
  }

  @Observed(name = "book-repository", contextualName = "save-book")
  public void save(Book book) {
    jdbcClient
        .sql("INSERT INTO books (title, author, description, published_date) VALUES (?, ?, ?, ?)")
        .params(List.of(book.title(), book.author(), book.description(), book.publishedDate()))
        .update();
  }

  @Observed(name = "book-repository", contextualName = "update-book")
  public void update(Book book, Integer id) {
    jdbcClient
        .sql(
            "UPDATE books SET title = ?, author = ?, description = ?, published_date = ? WHERE id = ?")
        .params(List.of(book.title(), book.author(), book.description(), book.publishedDate(), id))
        .update();
  }

  @Observed(name = "book-repository", contextualName = "delete-book")
  public void delete(Book book) {
    jdbcClient.sql("DELETE FROM books WHERE id = :id").param("id", book.id()).update();
  }
}
