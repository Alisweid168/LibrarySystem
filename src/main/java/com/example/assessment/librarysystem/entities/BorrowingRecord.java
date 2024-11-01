package com.example.assessment.librarysystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class BorrowingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    @NotNull(message = "Book must be specified")
    @JsonIgnore
    private Book book;

    @ManyToOne
    @JoinColumn(name = "patron_id", nullable = false)
    @NotNull(message = "Patron must be specified")
    @JsonIgnore
    private Patron patron;

    @NotNull(message = "Borrow date is required")
    private LocalDate borrowDate;

    private LocalDate returnDate;

    public BorrowingRecord() {
    }

    public BorrowingRecord(Long id, Book book, Patron patron, LocalDate borrowDate, LocalDate returnDate) {
        this.id = id;
        this.book = book;
        this.patron = patron;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }


}
