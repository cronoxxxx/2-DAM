package org.example.loginspring_adriansaavedra.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Credential {
    private int id;
    private String username;
    private String password;
    private List<Player> jugadoresFavoritos;
    private String email;
    private boolean isVerified;
    private String verificationCode;
}
