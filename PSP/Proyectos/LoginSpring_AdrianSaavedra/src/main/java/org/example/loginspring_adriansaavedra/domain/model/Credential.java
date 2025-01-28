package org.example.loginspring_adriansaavedra.domain.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Credential {
    private String username;
    private String password;
    private String email;
    private boolean isVerified;
    private String verificationCode;
}
