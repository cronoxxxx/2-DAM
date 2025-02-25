package org.example.dao.hibernate.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "sales", schema = "simulacrofinal")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "units", nullable = false)
    private Integer units;

    @Column(name = "sldate", nullable = false)
    private LocalDate sldate;

}