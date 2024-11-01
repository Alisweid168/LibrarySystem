package com.example.assessment.librarysystem.controllers;

import com.example.assessment.librarysystem.entities.BorrowingRecord;
import com.example.assessment.librarysystem.entities.Book;
import com.example.assessment.librarysystem.entities.Patron;
import com.example.assessment.librarysystem.services.BorrowingRecordService;
import com.example.assessment.librarysystem.services.BookService;
import com.example.assessment.librarysystem.services.PatronService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BorrowingRecordControllerTest {

    @Mock
    private BorrowingRecordService borrowingRecordService;

    @Mock
    private BookService bookService;

    @Mock
    private PatronService patronService;

    @InjectMocks
    private BorrowingRecordController borrowingRecordController;

    private BorrowingRecord sampleRecord;
    private Book sampleBook;
    private Patron samplePatron;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleBook = new Book(); // Initialize with necessary fields
        sampleBook.setId(1L);

        samplePatron = new Patron(); // Initialize with necessary fields
        samplePatron.setId(1L);

        sampleRecord = new BorrowingRecord();
        sampleRecord.setId(1L);
        sampleRecord.setBook(sampleBook);
        sampleRecord.setPatron(samplePatron);
        sampleRecord.setBorrowDate(LocalDate.now());
        sampleRecord.setReturnDate(null);
    }

    @Test
    void borrowBook_ShouldReturnBorrowingRecord_WhenBookAndPatronAreValid() {
        when(borrowingRecordService.borrowBook(1L, 1L)).thenReturn(sampleRecord);

        ResponseEntity<BorrowingRecord> response = borrowingRecordController.borrowBook(1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleRecord, response.getBody());
        verify(borrowingRecordService, times(1)).borrowBook(1L, 1L);
    }

    @Test
    void returnBook_ShouldReturnBorrowingRecord_WhenBookAndPatronAreValid() {
        when(borrowingRecordService.returnBook(1L, 1L)).thenReturn(sampleRecord);

        ResponseEntity<BorrowingRecord> response = borrowingRecordController.returnBook(1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleRecord, response.getBody());
        verify(borrowingRecordService, times(1)).returnBook(1L, 1L);
    }
}
