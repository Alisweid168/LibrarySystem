package com.example.assessment.librarysystem.repositories;

import com.example.assessment.librarysystem.entities.Book;
import com.example.assessment.librarysystem.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book(null, "The Great Gatsby", "F. Scott Fitzgerald", 1925, "9780743273565", 5);
        bookRepository.save(book);
    }

    @Test
    void findAll_ShouldReturnListOfBooks() {
        var books = bookRepository.findAll();
        assertEquals(1, books.size());
        assertEquals("The Great Gatsby", books.get(0).getTitle());
    }

    @Test
    void findById_ShouldReturnBook_WhenBookExists() {
        Optional<Book> foundBook = bookRepository.findById(book.getId());
        assertTrue(foundBook.isPresent());
        assertEquals("The Great Gatsby", foundBook.get().getTitle());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenBookDoesNotExist() {
        Optional<Book> foundBook = bookRepository.findById(2L);
        assertTrue(foundBook.isEmpty());
    }

    @Test
    void save_ShouldPersistBook() {
        Book newBook = new Book(null, "1984", "George Orwell", 1949, "9780451524935", 3);
        Book savedBook = bookRepository.save(newBook);
        assertNotNull(savedBook.getId());
        assertEquals("1984", savedBook.getTitle());
    }

    @Test
    void deleteById_ShouldDeleteBook_WhenBookExists() {
        bookRepository.deleteById(1L);
        Optional<Book> foundBook = bookRepository.findById(1L);
        assertTrue(foundBook.isEmpty());
    }

    @Test
    void deleteById_ShouldDoNothing_WhenBookDoesNotExist() {
        bookRepository.deleteById(2L);
        assertTrue(true);
    }
}