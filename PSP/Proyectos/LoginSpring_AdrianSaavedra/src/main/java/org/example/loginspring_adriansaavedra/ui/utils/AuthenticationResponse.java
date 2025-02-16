package org.example.loginspring_adriansaavedra.ui.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private Integer userId;
    private String accessToken;
    private String refreshToken;
}
