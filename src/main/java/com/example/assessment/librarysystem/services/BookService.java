package com.example.assessment.librarysystem.services;

import com.example.assessment.librarysystem.entities.Book;
import com.example.assessment.librarysystem.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Cacheable(value = "books", key = "#id")
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @CacheEvict(value = "books", allEntries = true)
    @Transactional
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @CachePut(value = "books", key = "#id")
    @Transactional
    public Book updateBook(Long id, Book updatedBook) {
        Book existingBook = getBookById(id).orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setPublicationYear(updatedBook.getPublicationYear());
        existingBook.setIsbn(updatedBook.getIsbn());
        return bookRepository.save(existingBook);
    }

    @CacheEvict(value = "books", key = "#id")
    @Transactional
    public void deleteBook(Long id) {
        Book existingBook = getBookById(id).orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
        bookRepository.deleteById(existingBook.getId());
    }
}
