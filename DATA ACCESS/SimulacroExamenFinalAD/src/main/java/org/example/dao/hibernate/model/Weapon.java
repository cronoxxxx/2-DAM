package org.example.dao.hibernate.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "weapons", schema = "simulacrofinal")
public class Weapon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    @Column(name = "wname", nullable = false, length = 200)
    private String wname;


    @Column(name = "wprice", nullable = false)
    private Double wprice;

}