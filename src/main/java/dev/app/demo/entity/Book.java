package dev.app.demo.entity;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

public record Book(
    Integer id,
    @NotEmpty
    String title,

    String author, String description, LocalDate publishedDate) {}
