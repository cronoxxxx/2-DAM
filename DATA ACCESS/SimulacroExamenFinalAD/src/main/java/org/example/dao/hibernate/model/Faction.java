package org.example.dao.hibernate.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@Table(name = "faction", schema = "simulacrofinal")
public class Faction {
    @Id
    @Column(name = "fname", nullable = false, length = 200)
    private String fname;

    @Column(name = "contact", nullable = false, length = 200)
    private String contact;


    @Column(name = "planet", nullable = false, length = 200)
    private String planet;

    @Column(name = "number_controlled_systems", nullable = false)
    private Integer numberControlledSystems;

    @Column(name = "date_last_purchase")
    private LocalDate dateLastPurchase;

}