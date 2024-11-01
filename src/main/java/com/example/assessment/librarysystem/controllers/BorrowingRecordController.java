package com.example.assessment.librarysystem.controllers;


import com.example.assessment.librarysystem.entities.BorrowingRecord;
import com.example.assessment.librarysystem.services.BookService;
import com.example.assessment.librarysystem.services.BorrowingRecordService;
import com.example.assessment.librarysystem.services.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BorrowingRecordController {

    @Autowired
    private BorrowingRecordService borrowingRecordService;

    @Autowired
    private BookService bookService;

    @Autowired
    private PatronService patronService;

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        BorrowingRecord record = borrowingRecordService.borrowBook(bookId, patronId);
        return ResponseEntity.ok(record);
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        BorrowingRecord record = borrowingRecordService.returnBook(bookId, patronId);
        return ResponseEntity.ok(record);
    }
}
