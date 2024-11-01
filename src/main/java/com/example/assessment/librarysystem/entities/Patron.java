package com.example.assessment.librarysystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Patron {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Contact information is required")
    private String contactInformation;

    @OneToMany(mappedBy = "patron", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("patron")
    private List<BorrowingRecord> borrowingRecords;

    public Patron() {
    }

    public Patron(Long id, String name, String contactInformation) {
        this.id = id;
        this.contactInformation = contactInformation;
        this.name = name;
    }


}
