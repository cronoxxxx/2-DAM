package org.example.dao.hibernate.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Astronaut", schema = "laultima")
public class Astronaut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "rank_name", nullable = false, length = 50)
    private String rankName;

    @Column(name = "specialization", nullable = false, length = 100)
    private String specialization;

}