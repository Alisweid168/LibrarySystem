package com.example.assessment.librarysystem.repository;

import com.example.assessment.librarysystem.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
