package com.example.assessment.librarysystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;


@Data
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Author is mandatory")
    private String author;

    @NotNull(message = "Publication year is required")
    private Integer publicationYear;

    @NotBlank(message = "ISBN is mandatory")
    @Size(min = 10, max = 13, message = "ISBN should be 10 or 13 characters")
    private String isbn;

    // new field to track the number of copies available of a certain book
    @NotNull(message = "Number of copies is required")
    private Integer availableCopies;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("book")
    private List<BorrowingRecord> borrowingRecords;

    public Book() {
    }

    public Book(Long id, String title, String author, Integer publicationYear, String isbn, Integer availableCopies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.availableCopies = availableCopies;
    }
}
