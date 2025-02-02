package com.example.hospitalcrud.dao.model;
import com.example.hospitalcrud.common.UserType;
import org.hibernate.annotations.NamedQuery;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_login")
@NamedQuery(name = "Credential.findByUserName", query = "SELECT c FROM Credential c WHERE c.userName = :username")
public class Credential {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private int id;

    @Column(name = "username", nullable = false, length = 50, unique = true)
    private String userName;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "doctor_id")
    private Integer doctorId;

    public Credential(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public UserType getUserType() {
        if (patient != null) {
            return UserType.PATIENT;
        } else if (doctorId != null) {
            return UserType.DOCTOR;
        } else {
            return UserType.ADMIN;
        }
    }
}
