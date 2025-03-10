package org.example.dao.hibernate.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "Colony", schema = "laultima")
public class Colony {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "population", nullable = false)
    private Integer population;

    @Column(name = "founded", nullable = false)
    private LocalDate founded;

}