package org.example.loginspring_adriansaavedra.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.loginspring_adriansaavedra.common.Constantes;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Constantes.CREDENTIALS)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CredentialEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private boolean isVerified;

    private String verificationCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = Constantes.ROLE_ID, nullable = false)
    private RoleEntity role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = Constantes.USER_FAVORITE_PLAYERS,
            joinColumns = @JoinColumn(name = Constantes.USER_ID),
            inverseJoinColumns = @JoinColumn(name = Constantes.PLAYER_ID)
    )
    private Set<PlayerEntity> favoritePlayerEntities = new HashSet<>();
}