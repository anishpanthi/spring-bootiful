package dev.app.demo.entity;

import java.time.LocalDate;

public record Book(
    Integer id, String title, String author, String description, LocalDate publishedDate) {}
