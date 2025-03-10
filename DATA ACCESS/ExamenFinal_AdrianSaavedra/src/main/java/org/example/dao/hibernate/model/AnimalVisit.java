package org.example.dao.hibernate.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "Animal_Visits")
public class AnimalVisit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Animal_ID")
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "Visitor_ID")
    private Visitor visitor;

    @Column(name = "Visit_Date")
    private LocalDate visitDate;
}