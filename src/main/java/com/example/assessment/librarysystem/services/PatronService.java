package com.example.assessment.librarysystem.services;


import com.example.assessment.librarysystem.entities.Patron;
import com.example.assessment.librarysystem.repository.PatronRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PatronService {

    @Autowired
    private PatronRepository patronRepository;


    @Transactional(readOnly = true)
    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    @Cacheable(value = "patrons", key = "#id")
    @Transactional(readOnly = true)
    public Optional<Patron> getPatronById(Long id) {
        return patronRepository.findById(id);
    }

    @CacheEvict(value = "patrons", allEntries = true)
    @Transactional
    public Patron addPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    @CachePut(value = "patrons", key = "#id")
    @Transactional
    public Patron updatePatron(Long id, Patron updatedPatron) {
        Patron existingPatron = patronRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patron not found with ID: " + id));
        existingPatron.setName(updatedPatron.getName());
        existingPatron.setContactInformation(updatedPatron.getContactInformation());
        return patronRepository.save(existingPatron);
    }

    @CacheEvict(value = "patrons", key = "#id")
    @Transactional
    public void deleteById(Long id) {
        Patron existingPatron = getPatronById(id).orElseThrow(() -> new EntityNotFoundException("Patron not found with id: " + id));
        patronRepository.deleteById(existingPatron.getId());
    }
}
