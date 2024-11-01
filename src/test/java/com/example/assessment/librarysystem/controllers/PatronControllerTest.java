package com.example.assessment.librarysystem.controllers;


import com.example.assessment.librarysystem.entities.Patron;
import com.example.assessment.librarysystem.services.PatronService;
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

class PatronControllerTest {

    @Mock
    private PatronService patronService;

    @InjectMocks
    private PatronController patronController;

    private Patron samplePatron;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        samplePatron = new Patron();
        samplePatron.setId(1L);
        samplePatron.setName("Sample Patron");
        samplePatron.setContactInformation("Sample Contact Info");
    }

    @Test
    void getAllPatrons_ShouldReturnListOfPatrons() {
        List<Patron> patrons = Arrays.asList(samplePatron);
        when(patronService.getAllPatrons()).thenReturn(patrons);

        List<Patron> result = patronController.getAllPatrons();

        assertEquals(1, result.size());
        assertEquals("Sample Patron", result.get(0).getName());
        verify(patronService, times(1)).getAllPatrons();
    }

    @Test
    void getPatronById_ShouldReturnPatron_WhenPatronExists() {
        when(patronService.getPatronById(1L)).thenReturn(Optional.of(samplePatron));

        ResponseEntity<Patron> response = patronController.getPatronById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(samplePatron, response.getBody());
        verify(patronService, times(1)).getPatronById(1L);
    }

    @Test
    void getPatronById_ShouldReturnNotFound_WhenPatronDoesNotExist() {
        when(patronService.getPatronById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Patron> response = patronController.getPatronById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(patronService, times(1)).getPatronById(1L);
    }

    @Test
    void createPatron_ShouldReturnCreatedPatron_WhenPatronIsValid() {
        when(patronService.addPatron(any(Patron.class))).thenReturn(samplePatron);

        ResponseEntity<Patron> response = patronController.createPatron(samplePatron);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(samplePatron, response.getBody());
        verify(patronService, times(1)).addPatron(samplePatron);
    }

    @Test
    void updatePatron_ShouldReturnUpdatedPatron_WhenPatronExists() {
        when(patronService.getPatronById(1L)).thenReturn(Optional.of(samplePatron));
        when(patronService.updatePatron(eq(1L), any(Patron.class))).thenReturn(samplePatron);

        ResponseEntity<Patron> response = patronController.updatePatron(1L, samplePatron);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(samplePatron, response.getBody());
        verify(patronService, times(1)).getPatronById(1L);
        verify(patronService, times(1)).updatePatron(eq(1L), any(Patron.class));
    }

    @Test
    void updatePatron_ShouldReturnNotFound_WhenPatronDoesNotExist() {
        when(patronService.getPatronById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Patron> response = patronController.updatePatron(1L, samplePatron);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(patronService, times(1)).getPatronById(1L);
        verify(patronService, times(0)).updatePatron(anyLong(), any(Patron.class));
    }

    @Test
    void deletePatron_ShouldReturnNoContent_WhenPatronExists() {
        when(patronService.getPatronById(1L)).thenReturn(Optional.of(samplePatron));
        doNothing().when(patronService).deleteById(1L);

        ResponseEntity<Void> response = patronController.deletePatron(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(patronService, times(1)).getPatronById(1L);
        verify(patronService, times(1)).deleteById(1L);
    }

    @Test
    void deletePatron_ShouldReturnNotFound_WhenPatronDoesNotExist() {
        when(patronService.getPatronById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = patronController.deletePatron(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(patronService, times(1)).getPatronById(1L);
        verify(patronService, times(0)).deleteById(anyLong());
    }
}
