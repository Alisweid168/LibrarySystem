package com.example.assessment.librarysystem.repository;

import com.example.assessment.librarysystem.entities.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronRepository extends JpaRepository<Patron, Long> {}