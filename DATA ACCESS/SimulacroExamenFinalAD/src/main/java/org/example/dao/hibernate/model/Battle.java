package org.example.dao.hibernate.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "battles", schema = "simulacrofinal")
public class Battle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    @Column(name = "bname", nullable = false, length = 200)
    private String bname;


    @Column(name = "bplace", nullable = false, length = 200)
    private String bplace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="faction_one", nullable = false)
    private Faction factionOne;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="faction_two", nullable = false)
    private Faction factionTwo;


    @Column(name = "bdate", nullable = false)
    private LocalDate bdate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_spy",nullable = false)
    private Spy spy;

}