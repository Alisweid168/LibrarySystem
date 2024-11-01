package com.example.assessment.librarysystem.services;

import com.example.assessment.librarysystem.entities.Book;
import com.example.assessment.librarysystem.entities.BorrowingRecord;
import com.example.assessment.librarysystem.entities.Patron;
import com.example.assessment.librarysystem.repository.BorrowingRecordRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BorrowingRecordServiceTest {

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @Mock
    private BookService bookService;

    @Mock
    private PatronService patronService;

    @InjectMocks
    private BorrowingRecordService borrowingRecordService;

    private Book book;
    private Patron patron;
    private BorrowingRecord borrowingRecord;

    @BeforeEach
    void setUp() {
        book = new Book(null, "The Great Gatsby", "F. Scott Fitzgerald", 1925, "9780743273565", 5);
        patron = new Patron(null, "John Doe", "johndoe@example.com");
        borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDate.now());
        borrowingRecord.setReturnDate(null);

    }

    @Test
    void borrowBook_ShouldBorrowBook_WhenValid() {
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));
        when(patronService.getPatronById(1L)).thenReturn(Optional.of(patron));
        when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(1L, 1L)).thenReturn(Optional.empty());
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);

        var result = borrowingRecordService.borrowBook(1L, 1L);

        assertNotNull(result);
        assertEquals(book, result.getBook());
        assertEquals(patron, result.getPatron());
        verify(borrowingRecordRepository, times(1)).save(any(BorrowingRecord.class));
    }

    @Test
    void borrowBook_ShouldThrowException_WhenBookNotAvailable() {
        book.setAvailableCopies(0);
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));
        when(patronService.getPatronById(1L)).thenReturn(Optional.of(patron));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            borrowingRecordService.borrowBook(1L, 1L);
        });

        assertEquals("The book is currently checked out and not available.", exception.getMessage());
    }

    @Test
    void borrowBook_ShouldThrowException_WhenBookAlreadyBorrowed() {
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));
        when(patronService.getPatronById(1L)).thenReturn(Optional.of(patron));
        when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(1L, 1L))
                .thenReturn(Optional.of(borrowingRecord));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            borrowingRecordService.borrowBook(1L, 1L);
        });

        assertEquals("This book is already borrowed by the patron and has not been returned.", exception.getMessage());
    }

    @Test
    void returnBook_ShouldReturnBook_WhenValid() {
        when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(1L, 1L))
                .thenReturn(Optional.of(borrowingRecord));

        BorrowingRecord  result = borrowingRecordService.returnBook(1L, 1L);
        assertNotNull(result);
        assertEquals(borrowingRecord.getReturnDate(), result.getReturnDate());
        assertNotNull(result.getReturnDate());
        verify(borrowingRecordRepository, times(1)).save(borrowingRecord);
    }

    @Test
    void returnBook_ShouldThrowException_WhenNoActiveRecord() {
        when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(1L, 1L))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            borrowingRecordService.returnBook(1L, 1L);
        });

        assertEquals("No active borrowing record found for this book and patron", exception.getMessage());
    }

    @Test
    void getBookAvailability_ShouldReturnBook_WhenExists() {
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));

        var result = borrowingRecordService.getBookAvailability(1L);
        assertNotNull(result);
        assertEquals("The Great Gatsby", result.getTitle());
    }

    @Test
    void getBookAvailability_ShouldThrowException_WhenBookDoesNotExist() {
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            borrowingRecordService.getBookAvailability(1L);
        });

        assertEquals("Book not found with id: 1", exception.getMessage());
    }
}
