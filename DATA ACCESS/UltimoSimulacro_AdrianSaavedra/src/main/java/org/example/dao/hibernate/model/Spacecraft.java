package org.example.dao.hibernate.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "Spacecraft", schema = "laultima")
public class Spacecraft {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "fuel_efficiency", nullable = false, precision = 4, scale = 2)
    private BigDecimal fuelEfficiency;

    @Column(name = "in_service", nullable = false)
    private Boolean inService = false;

}