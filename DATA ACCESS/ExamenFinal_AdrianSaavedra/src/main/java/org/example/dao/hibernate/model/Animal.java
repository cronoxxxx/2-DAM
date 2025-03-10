package org.example.dao.hibernate.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Animals")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Animal_ID")
    private Integer animalId;

    @Column(name = "Name")
    private String name;

    @Column(name = "Species")
    private String species;

    @Column(name = "Age")
    private Integer age;

    @ManyToOne
    @JoinColumn(name = "Habitat_ID")
    private Habitat habitat;
}