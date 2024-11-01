package com.example.assessment.librarysystem.bootStrap;

import com.example.assessment.librarysystem.entities.Book;
import com.example.assessment.librarysystem.entities.BorrowingRecord;
import com.example.assessment.librarysystem.entities.Patron;
import com.example.assessment.librarysystem.repository.BookRepository;
import com.example.assessment.librarysystem.repository.BorrowingRecordRepository;
import com.example.assessment.librarysystem.repository.PatronRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Component
public class BootStrapData implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(BootStrapData.class);

    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;
    private final BorrowingRecordRepository borrowingRecordRepository;

    public BootStrapData(BookRepository bookRepository, PatronRepository patronRepository, BorrowingRecordRepository borrowingRecordRepository) {
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
        this.borrowingRecordRepository = borrowingRecordRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Book book1 = new Book();
        book1.setTitle("The Great Gatsby");
        book1.setAuthor("F. Scott Fitzgerald");
        book1.setPublicationYear(1925);
        book1.setIsbn("9780743273565");
        book1.setAvailableCopies(5);

        Book book2 = new Book();
        book2.setTitle("1984");
        book2.setAuthor("George Orwell");
        book2.setPublicationYear(1949);
        book2.setIsbn("9780451524935");
        book2.setAvailableCopies(3);


        bookRepository.save(book1);
        bookRepository.save(book2);


        Patron patron1 = new Patron();
        patron1.setName("John Doe");
        patron1.setContactInformation("john.doe@example.com");

        Patron patron2 = new Patron();
        patron2.setName("Jane Smith");
        patron2.setContactInformation("jane.smith@example.com");


        patronRepository.save(patron1);
        patronRepository.save(patron2);


        BorrowingRecord record1 = new BorrowingRecord();
        record1.setBook(book1);
        record1.setPatron(patron1);
        record1.setBorrowDate(LocalDate.of(2024, 10, 1));
        record1.setReturnDate(null);

        BorrowingRecord record2 = new BorrowingRecord();
        record2.setBook(book2);
        record2.setPatron(patron2);
        record2.setBorrowDate(LocalDate.of(2024, 10, 5));
        record2.setReturnDate(LocalDate.of(2024, 10, 12));  // Returned


        borrowingRecordRepository.save(record1);
        borrowingRecordRepository.save(record2);

        logger.info("Books, patrons, and borrowing records added to the database successfully!");


    }

}
