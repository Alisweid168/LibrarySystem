package com.example.assessment.librarysystem.services;

import com.example.assessment.librarysystem.entities.Patron;
import com.example.assessment.librarysystem.repository.PatronRepository;
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
class PatronServiceTest {

    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private PatronService patronService;

    private Patron patron;

    @BeforeEach
    void setUp() {
        patron = new Patron(1L, "John Doe", "johndoe@example.com");
    }

    @Test
    void getAllPatrons_ShouldReturnListOfPatrons() {
        when(patronRepository.findAll()).thenReturn(Arrays.asList(patron));

        var patrons = patronService.getAllPatrons();
        assertEquals(1, patrons.size());
        assertEquals("John Doe", patrons.get(0).getName());

        verify(patronRepository, times(1)).findAll();
    }

    @Test
    void getPatronById_ShouldReturnPatron_WhenPatronExists() {
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));

        var foundPatron = patronService.getPatronById(1L);
        assertTrue(foundPatron.isPresent());
        assertEquals("John Doe", foundPatron.get().getName());

        verify(patronRepository, times(1)).findById(1L);
    }

    @Test
    void getPatronById_ShouldReturnEmpty_WhenPatronDoesNotExist() {
        when(patronRepository.findById(2L)).thenReturn(Optional.empty());

        var foundPatron = patronService.getPatronById(2L);
        assertTrue(foundPatron.isEmpty());

        verify(patronRepository, times(1)).findById(2L);
    }

    @Test
    void addPatron_ShouldSaveAndReturnPatron() {
        when(patronRepository.save(any(Patron.class))).thenReturn(patron);

        var savedPatron = patronService.addPatron(patron);
        assertNotNull(savedPatron);
        assertEquals("John Doe", savedPatron.getName());

        verify(patronRepository, times(1)).save(patron);
    }

    @Test
    void updatePatron_ShouldUpdateAndReturnPatron_WhenPatronExists() {
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));
        when(patronRepository.save(any(Patron.class))).thenReturn(patron);

        patron.setName("Updated Name");
        var updatedPatron = patronService.updatePatron(1L, patron);
        assertNotNull(updatedPatron);
        assertEquals("Updated Name", updatedPatron.getName());

        verify(patronRepository, times(1)).findById(1L);
        verify(patronRepository, times(1)).save(patron);
    }

    @Test
    void updatePatron_ShouldThrowException_WhenPatronDoesNotExist() {
        when(patronRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            patronService.updatePatron(2L, patron);
        });

        assertEquals("Patron not found with ID: 2", exception.getMessage());

        verify(patronRepository, times(1)).findById(2L);
        verify(patronRepository, never()).save(any(Patron.class));
    }

    @Test
    void deletePatron_ShouldDeletePatron_WhenPatronExists() {
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));

        patronService.deleteById(1L);

        verify(patronRepository, times(1)).deleteById(1L);
        verify(patronRepository, times(1)).findById(1L);
    }

    @Test
    void deletePatron_ShouldThrowException_WhenPatronDoesNotExist() {
        when(patronRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            patronService.deleteById(2L);
        });

        assertEquals("Patron not found with id: 2", exception.getMessage());

        verify(patronRepository, times(1)).findById(2L);
        verify(patronRepository, never()).deleteById(any(Long.class));
    }
}
