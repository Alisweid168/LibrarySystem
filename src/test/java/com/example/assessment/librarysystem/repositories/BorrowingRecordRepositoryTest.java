package com.example.assessment.librarysystem.repositories;


import com.example.assessment.librarysystem.entities.Book;
import com.example.assessment.librarysystem.entities.BorrowingRecord;
import com.example.assessment.librarysystem.entities.Patron;
import com.example.assessment.librarysystem.repository.BorrowingRecordRepository;
import com.example.assessment.librarysystem.repository.BookRepository;
import com.example.assessment.librarysystem.repository.PatronRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BorrowingRecordRepositoryTest {

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PatronRepository patronRepository;

    private Book book;
    private Patron patron;
    private BorrowingRecord borrowingRecord;

    @BeforeEach
    void setUp() {
        // Create and save a Book and a Patron
        book = new Book(null, "The Great Gatsby", "F. Scott Fitzgerald", 1925, "9780743273565", 5);
        bookRepository.save(book);

        patron = new Patron(null, "John Doe", "johndoe@example.com");
        patronRepository.save(patron);

        // Create and save a BorrowingRecord
        borrowingRecord = new BorrowingRecord(null, book, patron, LocalDate.now(), null);
        borrowingRecordRepository.save(borrowingRecord);

    }

    @Test
    void findById_ShouldReturnBorrowingRecord_WhenExists() {
        Optional<BorrowingRecord> foundRecord = borrowingRecordRepository.findById(borrowingRecord.getId());
        assertTrue(foundRecord.isPresent());
        assertEquals(borrowingRecord.getId(), foundRecord.get().getId());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenDoesNotExist() {
        Optional<BorrowingRecord> foundRecord = borrowingRecordRepository.findById(10L);
        assertTrue(foundRecord.isEmpty());
    }

    @Test
    void save_ShouldPersistBorrowingRecord() {
        BorrowingRecord newRecord = new BorrowingRecord(null, book, patron, LocalDate.now(), null);
        BorrowingRecord savedRecord = borrowingRecordRepository.save(newRecord);
        assertNotNull(savedRecord.getId());
        assertEquals(book.getId(), savedRecord.getBook().getId());
        assertEquals(patron.getId(), savedRecord.getPatron().getId());
    }

    @Test
    void deleteById_ShouldDeleteBorrowingRecord_WhenExists() {
        borrowingRecordRepository.deleteById(borrowingRecord.getId());
        Optional<BorrowingRecord> foundRecord = borrowingRecordRepository.findById(borrowingRecord.getId());
        assertTrue(foundRecord.isEmpty());
    }

    @Test
    void deleteById_ShouldDoNothing_WhenDoesNotExist() {
        // Attempt to delete a non-existent borrowing record
        borrowingRecordRepository.deleteById(2L);
        // No exception should be thrown
        assertTrue(true); // Simply verifying no exception occurs
    }

    @Test
    void findByBookIdAndPatronIdAndReturnDateIsNull_ShouldReturnRecord_WhenExists() {
        Optional<BorrowingRecord> foundRecord = borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(book.getId(), patron.getId());
        assertTrue(foundRecord.isPresent());
        assertEquals(borrowingRecord.getId(), foundRecord.get().getId());
    }

    @Test
    void findByBookIdAndPatronIdAndReturnDateIsNull_ShouldReturnEmpty_WhenReturnDateIsNotNull() {
        borrowingRecord.setReturnDate(LocalDate.now());
        borrowingRecordRepository.save(borrowingRecord);

        Optional<BorrowingRecord> foundRecord = borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(book.getId(), patron.getId());
        assertTrue(foundRecord.isEmpty());
    }

    @Test
    void findByBookIdAndPatronIdAndReturnDateIsNull_ShouldReturnEmpty_WhenDoesNotExist() {
        Optional<BorrowingRecord> foundRecord = borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(999L, 888L);
        assertTrue(foundRecord.isEmpty());
    }
}
