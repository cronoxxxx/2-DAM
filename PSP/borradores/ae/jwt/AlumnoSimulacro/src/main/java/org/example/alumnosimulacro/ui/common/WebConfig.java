package org.example.alumnosimulacro.ui.common;

import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Configuration
@Log4j2
public class WebConfig {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Key key() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.update("your_secret_key".getBytes(StandardCharsets.UTF_8));
            SecretKeySpec key = new SecretKeySpec(digest.digest(), 0, 64, "AES");
            return Keys.hmacShaKeyFor(key.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}