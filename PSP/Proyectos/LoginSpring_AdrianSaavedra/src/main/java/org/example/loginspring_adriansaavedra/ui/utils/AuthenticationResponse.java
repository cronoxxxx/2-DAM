package org.example.loginspring_adriansaavedra.ui.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.loginspring_adriansaavedra.common.Constantes;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    @JsonProperty(Constantes.ACCESS_TOKEN)
    private String accessToken;
    @JsonProperty(Constantes.REFRESH_TOKEN)
    private String refreshToken;
}
