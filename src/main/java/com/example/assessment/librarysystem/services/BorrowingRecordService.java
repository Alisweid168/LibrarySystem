package com.example.assessment.librarysystem.services;


import com.example.assessment.librarysystem.entities.Book;
import com.example.assessment.librarysystem.entities.BorrowingRecord;
import com.example.assessment.librarysystem.entities.Patron;
import com.example.assessment.librarysystem.repository.BorrowingRecordRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowingRecordService {

    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookService bookService;
    private final PatronService patronService;

    public BorrowingRecordService(BorrowingRecordRepository borrowingRecordRepository, BookService bookService, PatronService patronService) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.bookService = bookService;
        this.patronService = patronService;
    }

    @CacheEvict(value = "availableBooks", key = "#bookId")
    @Transactional
    public BorrowingRecord borrowBook(Long bookId, Long patronId) {
        Book book = bookService.getBookById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));
        Patron patron = patronService.getPatronById(patronId).orElseThrow(() -> new RuntimeException("Patron not found with id: " + patronId));

        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("The book is currently checked out and not available.");
        }

        Optional<BorrowingRecord> existingRecord = borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId);
        if (existingRecord.isPresent()) {
            throw new RuntimeException("This book is already borrowed by the patron and has not been returned.");
        }

        if (book != null && patron != null) {
            BorrowingRecord record = new BorrowingRecord();
            record.setBook(book);
            record.setPatron(patron);
            record.setBorrowDate(LocalDate.now());
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            borrowingRecordRepository.save(record);
            return record;
        }

        throw new RuntimeException("Book or Patron not found");
    }

    @CacheEvict(value = "availableBooks", key = "#bookId")
    @Transactional
    public BorrowingRecord returnBook(Long bookId, Long patronId) {
        Optional<BorrowingRecord> optionalRecord = borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId);

        if (optionalRecord.isPresent()) {
            BorrowingRecord record = optionalRecord.get();
            record.setReturnDate(LocalDate.now());
            borrowingRecordRepository.save(record);
            return optionalRecord.get();
        } else {
            throw new RuntimeException("No active borrowing record found for this book and patron");
        }
    }

    @Cacheable(value = "availableBooks", key = "#bookId")
    public Book getBookAvailability(Long bookId) {
        return bookService.getBookById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));
    }
}
