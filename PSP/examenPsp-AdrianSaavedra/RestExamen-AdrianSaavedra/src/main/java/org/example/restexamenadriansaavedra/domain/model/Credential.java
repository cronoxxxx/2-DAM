package org.example.restexamenadriansaavedra.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Credential {
    private String name;
    private String password;
    private String role;
}
