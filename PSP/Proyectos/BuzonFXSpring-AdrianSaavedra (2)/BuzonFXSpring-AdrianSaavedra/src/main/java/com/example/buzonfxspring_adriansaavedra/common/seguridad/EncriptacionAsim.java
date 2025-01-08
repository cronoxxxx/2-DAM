package com.example.buzonfxspring_adriansaavedra.common.seguridad;

import com.example.buzonfxspring_adriansaavedra.common.Constantes;
import com.example.buzonfxspring_adriansaavedra.common.config.Configuracion;
import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorApp;
import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorAppDatosNoValidos;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.*;
import java.security.cert.Certificate;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;
import java.util.Date;

@Log4j2
@Component
public class EncriptacionAsim {
    private final Configuracion configuracion;

    public EncriptacionAsim(Configuracion configuracion) {
        this.configuracion = configuracion;
        Security.addProvider(new BouncyCastleProvider());
    }

    public Either<ErrorApp, String> encriptar(String texto, PublicKey clavePublica) {
        try {
            Cipher cifrador = Cipher.getInstance(Constantes.ECIES, Constantes.BC);
            cifrador.init(Cipher.ENCRYPT_MODE, clavePublica);
            byte[] encoded = cifrador.doFinal(texto.getBytes(StandardCharsets.UTF_8));
            return Either.right(Base64.getUrlEncoder().encodeToString(encoded));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Either.left(new ErrorAppDatosNoValidos(e.getMessage()));
        }
    }

    public Either<ErrorApp, String> desencriptar(String textoCifrado, PrivateKey clavePrivada) {

        byte[] textoCifradoBytes = Base64.getUrlDecoder().decode(textoCifrado);
        try {
            Cipher cifrador = Cipher.getInstance(Constantes.ECIES, Constantes.BC);
            cifrador.init(Cipher.DECRYPT_MODE, clavePrivada);
            byte[] textoDescifradoBytes = cifrador.doFinal(textoCifradoBytes);
            return Either.right(new String(textoDescifradoBytes, StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Either.left(new ErrorAppDatosNoValidos(e.getMessage()));
        }
    }

    public Either<ErrorApp, PublicKey> obtenerClavePublica(String username) {
        try {
            KeyStore keyStore = KeyStore.getInstance(Constantes.JKS);
            char[] keystorePassword = configuracion.getKeyStorePassword().toCharArray();
            FileInputStream fis = new FileInputStream(configuracion.getPathJks());
            keyStore.load(fis, keystorePassword);

            Certificate cert = keyStore.getCertificate(username);
            if (cert == null) {
                return Either.left(new ErrorAppDatosNoValidos(Constantes.NO_SE_ENCONTRO_CERTIFICADO_USUARIO + username));
            }
            return Either.right(cert.getPublicKey());
        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            return Either.left(new ErrorAppDatosNoValidos(Constantes.ERROR_AL_OBTENER_CLAVE_PUBLICA + e.getMessage()));
        }
    }


    public Either<ErrorApp, String> generarClaveSimetricaAleatoria() {
        SecureRandom sr = new SecureRandom();
        byte[] bits = new byte[32];
        sr.nextBytes(bits);
        return Either.right(Base64.getUrlEncoder().encodeToString(bits));
    }

    public Either<ErrorApp, String> firmarMensaje(String mensaje, PrivateKey clavePrivada) {
        try {
            Signature signature = Signature.getInstance(Constantes.SHA256_WITH_ECDSA);
            signature.initSign(clavePrivada);
            signature.update(mensaje.getBytes(StandardCharsets.UTF_8));
            byte[] firma = signature.sign();
            return Either.right(Base64.getEncoder().encodeToString(firma));
        } catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException e) {
            log.error(e.getMessage(), e);
            return Either.left(new ErrorAppDatosNoValidos(Constantes.ERROR_AL_FIRMAR_MENSAJE + e.getMessage()));
        }
    }

    public Either<ErrorApp, Boolean> verificarFirma(String mensaje, String firma, PublicKey clavePublica) {
        try {
            Signature signature = Signature.getInstance(Constantes.SHA256_WITH_ECDSA);
            signature.initVerify(clavePublica);
            signature.update(mensaje.getBytes(StandardCharsets.UTF_8));
            return Either.right(signature.verify(Base64.getDecoder().decode(firma)));
        } catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException e) {
            log.error(e.getMessage(), e);
            return Either.left(new ErrorAppDatosNoValidos(Constantes.ERROR_AL_VERIFICAR_FIRMA + e.getMessage()));
        }
    }

    public Either<ErrorApp, KeyPair> obtenerParClaves(String username, String password) {
        try {
            KeyStore keyStore = KeyStore.getInstance(Constantes.JKS);
            char[] keystorePassword = configuracion.getKeyStorePassword().toCharArray();
            FileInputStream fis = new FileInputStream(configuracion.getPathJks());
            keyStore.load(fis, keystorePassword);
            Key key = keyStore.getKey(username, password.toCharArray());
            Certificate cert = keyStore.getCertificate(username);
            PublicKey publicKey = cert.getPublicKey();
            return Either.right(new KeyPair(publicKey, (PrivateKey) key));
        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            log.error(e.getMessage(), e);
            return Either.left(new ErrorAppDatosNoValidos(Constantes.ERROR_AL_OBTENER_PAR_CLAVES + e.getMessage()));
        }
    }


    public Either<ErrorApp, KeyPair> generarParClaves() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(Constantes.EC, Constantes.BC);
            keyPairGenerator.initialize(new ECGenParameterSpec(Constantes.SECP521R1));
            return Either.right(keyPairGenerator.generateKeyPair());
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | NoSuchProviderException e) {
            log.error(e.getMessage(),e);
            return Either.left(new ErrorAppDatosNoValidos(Constantes.ERROR_AL_GENERAR_PAR_CLAVES + e.getMessage()));
        }
    }

    public Either<ErrorApp, X509Certificate> generarCertificadoUsuario(String username, KeyPair keyPair) {
        try {
            X500Name issuer = new X500Name(Constantes.CN_EQUALS + username);
            X500Name subject = new X500Name(Constantes.CN_EQUALS + username);
            BigInteger serialNumber = BigInteger.valueOf(System.currentTimeMillis());
            Date startDate = new Date();
            Date endDate = new Date(startDate.getTime() + 365 * 24 * 60 * 60 * 1000L);

            SubjectPublicKeyInfo publicKeyInfo = SubjectPublicKeyInfo.getInstance(keyPair.getPublic().getEncoded());
            X509v3CertificateBuilder certBuilder = new X509v3CertificateBuilder(
                    issuer, serialNumber, startDate, endDate, subject, publicKeyInfo);

            ContentSigner contentSigner = new JcaContentSignerBuilder(Constantes.SHA256_WITH_ECDSA).setProvider(Constantes.BC).build(keyPair.getPrivate());
            return Either.right(new JcaX509CertificateConverter().setProvider(Constantes.BC).getCertificate(certBuilder.build(contentSigner)));
        } catch (CertificateException | OperatorCreationException e) {
            log.error(e.getMessage(), e);
            return Either.left(new ErrorAppDatosNoValidos(Constantes.ERROR_AL_GENERAR_CERTIFICADO + e.getMessage()));
        }
    }

    public Either<ErrorApp, Boolean> almacenarClavesUsuario(String username, KeyPair keyPair, X509Certificate certificate, String userPassword) {
        try {
            KeyStore keyStore = KeyStore.getInstance(Constantes.JKS);
            char[] keystorePassword = configuracion.getKeyStorePassword().toCharArray();
            FileInputStream fis = new FileInputStream(configuracion.getPathJks());
            keyStore.load(fis, keystorePassword);

            keyStore.setKeyEntry(username, keyPair.getPrivate(), userPassword.toCharArray(), new Certificate[]{certificate});
            FileOutputStream fos = new FileOutputStream(configuracion.getPathJks());
            keyStore.store(fos, keystorePassword);

            return Either.right(true);
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            log.error(e.getMessage(), e);
            return Either.left(new ErrorAppDatosNoValidos(Constantes.ERROR_AL_ALMACENAR_CLAVES_USUARIO + e.getMessage()));
        }
    }

    public Either<ErrorApp, Boolean> verificarCertificadoUsuario(String username, String userPassword) {
        try {
            KeyStore keyStore = KeyStore.getInstance(Constantes.JKS);
            char[] keystorePassword = configuracion.getKeyStorePassword().toCharArray();
            FileInputStream fis = new FileInputStream(configuracion.getPathJks());
            keyStore.load(fis, keystorePassword);

            if (!keyStore.containsAlias(username)) {
                return Either.left(new ErrorAppDatosNoValidos(Constantes.CERTIFICADO_NO_EXISTE));
            }
            keyStore.getKey(username, userPassword.toCharArray());
            X509Certificate cert = (X509Certificate) keyStore.getCertificate(username);
            cert.checkValidity();
            return Either.right(true);

        } catch (UnrecoverableKeyException | CertificateException | KeyStoreException | IOException |
                 NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            return Either.left(new ErrorAppDatosNoValidos(Constantes.ERROR_AL_VERIFICAR_CERTIFICADO + e.getMessage()));
        }
    }
}
