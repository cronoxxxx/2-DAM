package org.example.dao.hibernate.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "spies", schema = "simulacrofinal")
public class Spy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    @Column(name = "sname", nullable = false, length = 200)
    private String sname;


    @Column(name = "srace", nullable = false, length = 200)
    private String srace;

}