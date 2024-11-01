package com.example.assessment.librarysystem.controllers;

import com.example.assessment.librarysystem.entities.Book;
import com.example.assessment.librarysystem.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private Book sampleBook;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleBook = new Book();
        sampleBook.setId(1L);
        sampleBook.setTitle("Sample Book");
        sampleBook.setAuthor("Sample Author");
    }

    @Test
    void getAllBooks_ShouldReturnListOfBooks() {
        List<Book> books = Arrays.asList(sampleBook);
        when(bookService.getAllBooks()).thenReturn(books);

        List<Book> result = bookController.getAllBooks();

        assertEquals(1, result.size());
        assertEquals("Sample Book", result.get(0).getTitle());
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void getBookById_ShouldReturnBook_WhenBookExists() {
        when(bookService.getBookById(1L)).thenReturn(Optional.of(sampleBook));

        ResponseEntity<Book> response = bookController.getBookById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleBook, response.getBody());
        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void getBookById_ShouldReturnNotFound_WhenBookDoesNotExist() {
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Book> response = bookController.getBookById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void createBook_ShouldReturnCreatedBook_WhenBookIsValid() {
        when(bookService.addBook(any(Book.class))).thenReturn(sampleBook);

        ResponseEntity<Book> response = bookController.createBook(sampleBook);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(sampleBook, response.getBody());
        verify(bookService, times(1)).addBook(sampleBook);
    }

    @Test
    void updateBook_ShouldReturnUpdatedBook_WhenBookExists() {
        when(bookService.getBookById(1L)).thenReturn(Optional.of(sampleBook));
        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(sampleBook);

        ResponseEntity<Book> response = bookController.updateBook(1L, sampleBook);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleBook, response.getBody());
        verify(bookService, times(1)).getBookById(1L);
        verify(bookService, times(1)).updateBook(eq(1L), any(Book.class));
    }

    @Test
    void updateBook_ShouldReturnNotFound_WhenBookDoesNotExist() {
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Book> response = bookController.updateBook(1L, sampleBook);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(bookService, times(1)).getBookById(1L);
        verify(bookService, times(0)).updateBook(anyLong(), any(Book.class));
    }

    @Test
    void deleteBook_ShouldReturnNoContent_WhenBookExists() {
        when(bookService.getBookById(1L)).thenReturn(Optional.of(sampleBook));
        doNothing().when(bookService).deleteBook(1L);

        ResponseEntity<Void> response = bookController.deleteBook(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(bookService, times(1)).getBookById(1L);
        verify(bookService, times(1)).deleteBook(1L);
    }

    @Test
    void deleteBook_ShouldReturnNotFound_WhenBookDoesNotExist() {
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = bookController.deleteBook(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(bookService, times(1)).getBookById(1L);
        verify(bookService, times(0)).deleteBook(anyLong());
    }
}
