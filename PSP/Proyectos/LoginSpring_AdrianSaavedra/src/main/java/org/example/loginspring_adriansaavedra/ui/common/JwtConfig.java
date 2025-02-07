package org.example.loginspring_adriansaavedra.ui.common;

import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;

@Log4j2
@Configuration
public class JwtConfig {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Key key() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.update("clave".getBytes(StandardCharsets.UTF_8));
            SecretKeySpec key = new SecretKeySpec(digest.digest(), 0, 64, "AES");
            return Keys.hmacShaKeyFor(key.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("Failed to generate Key", e);
        }
    }
}

