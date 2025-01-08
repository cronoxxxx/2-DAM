package com.example.buzonfxspring_adriansaavedra.common.seguridad;

import com.example.buzonfxspring_adriansaavedra.common.Constantes;
import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorApp;
import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorAppDatosNoValidos;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import com.google.common.primitives.Bytes;
@Log4j2
@Component
public class EncriptacionAES {

    public Either<ErrorApp, String> encriptar(String strToEncrypt, String secret) {
        try {
            byte[] iv = new byte[12];
            byte[] salt = new byte[16];
            SecureRandom sr = new SecureRandom();
            sr.nextBytes(iv);
            sr.nextBytes(salt);

            SecretKey secretKey = generateSecretKey(secret, salt);

            Cipher cipher = Cipher.getInstance(Constantes.AES_GCM_NO_PADDING);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

            byte[] encryptedData = cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8));
            byte[] combined = Bytes.concat(iv, salt, encryptedData);

            return Either.right(Base64.getUrlEncoder().encodeToString(combined));
        } catch (Exception e) {
            log.error(Constantes.ERROR_ENCRYPT, e);
            return Either.left(new ErrorAppDatosNoValidos(Constantes.ERROR_ENCRYPT));
        }
    }

    public Either<ErrorApp, String> desencriptar(String strToDecrypt, String secret) {
        try {
            byte[] decoded = Base64.getUrlDecoder().decode(strToDecrypt);

            byte[] iv = Arrays.copyOfRange(decoded, 0, 12);
            byte[] salt = Arrays.copyOfRange(decoded, 12, 28);
            byte[] encryptedData = Arrays.copyOfRange(decoded, 28, decoded.length);

            SecretKey secretKey = generateSecretKey(secret, salt);

            Cipher cipher = Cipher.getInstance(Constantes.AES_GCM_NO_PADDING);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);

            byte[] decryptedData = cipher.doFinal(encryptedData);
            return Either.right(new String(decryptedData, StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error(Constantes.ERROR_DECRYPT, e);
            return Either.left(new ErrorAppDatosNoValidos(Constantes.ERROR_DECRYPT));
        }
    }

    private SecretKey generateSecretKey(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(Constantes.PBKDF_2_WITH_HMAC_SHA_256);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), Constantes.AES);
    }
}