package org.example.dao.hibernate.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Visitors")
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Visitor_ID")
    private Integer visitorId;

    @Column(name = "Name")
    private String name;

    @Column(name = "Email")
    private String email;

    @Column(name = "Tickets")
    private Integer tickets;
}