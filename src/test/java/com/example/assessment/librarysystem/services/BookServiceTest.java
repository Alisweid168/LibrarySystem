package com.example.assessment.librarysystem.services;

import com.example.assessment.librarysystem.entities.Book;
import com.example.assessment.librarysystem.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book(1L, "The Great Gatsby", "F. Scott Fitzgerald", 1925, "9780743273565", 5);
    }

    @Test
    void getAllBooks_ShouldReturnListOfBooks() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));

        var books = bookService.getAllBooks();
        assertEquals(1, books.size());
        assertEquals("The Great Gatsby", books.get(0).getTitle());

        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBookById_ShouldReturnBook_WhenBookExists() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        var foundBook = bookService.getBookById(1L);
        assertTrue(foundBook.isPresent());
        assertEquals("The Great Gatsby", foundBook.get().getTitle());

        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void getBookById_ShouldReturnEmpty_WhenBookDoesNotExist() {
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());

        var foundBook = bookService.getBookById(2L);
        assertTrue(foundBook.isEmpty());

        verify(bookRepository, times(1)).findById(2L);
    }

    @Test
    void addBook_ShouldSaveAndReturnBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        var savedBook = bookService.addBook(book);
        assertNotNull(savedBook);
        assertEquals("The Great Gatsby", savedBook.getTitle());

        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void updateBook_ShouldUpdateAndReturnBook_WhenBookExists() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        book.setTitle("Updated Title");
        var updatedBook = bookService.updateBook(1L, book);
        assertNotNull(updatedBook);
        assertEquals("Updated Title", updatedBook.getTitle());

        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void updateBook_ShouldThrowException_WhenBookDoesNotExist() {
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            bookService.updateBook(2L, book);
        });

        assertEquals("Book not found with id: 2", exception.getMessage());

        verify(bookRepository, times(1)).findById(2L);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void deleteBook_ShouldDeleteBook_WhenBookExists() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).deleteById(1L);
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void deleteBook_ShouldThrowException_WhenBookDoesNotExist() {
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            bookService.deleteBook(2L);
        });

        assertEquals("Book not found with id: 2", exception.getMessage());

        verify(bookRepository, times(1)).findById(2L);
        verify(bookRepository, never()).deleteById(any(Long.class));
    }
}
