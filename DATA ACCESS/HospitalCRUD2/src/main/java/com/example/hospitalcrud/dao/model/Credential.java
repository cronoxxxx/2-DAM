package com.example.hospitalcrud.dao.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_login")
public class Credential {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private int user_id;
    @Column(name = "username", nullable = false, length = 50, unique = true)
    private String userName;
    @Column(name = "password", nullable = false, length = 100)
    private String password;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @Column(name = "doctor_id")
    private Integer doctor_id;


    public Credential(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }


}
