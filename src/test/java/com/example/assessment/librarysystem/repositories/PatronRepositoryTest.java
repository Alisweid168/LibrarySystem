package com.example.assessment.librarysystem.repositories;


import com.example.assessment.librarysystem.entities.Patron;
import com.example.assessment.librarysystem.repository.PatronRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PatronRepositoryTest {

    @Autowired
    private PatronRepository patronRepository;

    private Patron patron;

    @BeforeEach
    void setUp() {
        patron = new Patron(null, "John Doe", "johndoe@example.com");
        patronRepository.save(patron);
    }

    @Test
    void findAll_ShouldReturnListOfPatrons() {
        var patrons = patronRepository.findAll();
        assertEquals(1, patrons.size());
        assertEquals("John Doe", patrons.get(0).getName());
    }

    @Test
    void findById_ShouldReturnPatron_WhenPatronExists() {
        Optional<Patron> foundPatron = patronRepository.findById(patron.getId());
        assertTrue(foundPatron.isPresent());
        assertEquals("John Doe", foundPatron.get().getName());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenPatronDoesNotExist() {
        Optional<Patron> foundPatron = patronRepository.findById(2L);
        assertTrue(foundPatron.isEmpty());
    }

    @Test
    void save_ShouldPersistPatron() {
        Patron newPatron = new Patron(null, "Jane Smith", "janesmith@example.com");
        Patron savedPatron = patronRepository.save(newPatron);
        assertNotNull(savedPatron.getId());
        assertEquals("Jane Smith", savedPatron.getName());
    }

    @Test
    void deleteById_ShouldDeletePatron_WhenPatronExists() {
        patronRepository.deleteById(patron.getId());
        Optional<Patron> foundPatron = patronRepository.findById(patron.getId());
        assertTrue(foundPatron.isEmpty());
    }

    @Test
    void deleteById_ShouldDoNothing_WhenPatronDoesNotExist() {
        patronRepository.deleteById(2L);
        assertTrue(true);
    }
}
