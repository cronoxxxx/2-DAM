package org.example.loginspring_adriansaavedra.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.loginspring_adriansaavedra.common.Constantes;

@Entity
@Table(name = Constantes.ROLES)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String rol;
}