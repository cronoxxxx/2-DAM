package com.example.buzonfxspring_adriansaavedra.common.seguridad;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;
import java.util.Date;

import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorApp;
import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorAppDatosNoValidos;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.stereotype.Component;
@Log4j2
@Component
public class AsimetricComponent {
    private static final String KEYSTORE_PATH = "buzon.jks";
    private static final String KEYSTORE_PASSWORD = "pepito"; // Debe estar en un archivo de configuración seguro
    private static final String SERVER_ALIAS = "servidor";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private final KeyStore keyStore;

    public AsimetricComponent() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        keyStore = KeyStore.getInstance("PKCS12");
        try (FileInputStream fis = new FileInputStream(KEYSTORE_PATH)) {
            keyStore.load(fis, KEYSTORE_PASSWORD.toCharArray());
        } catch (FileNotFoundException e) {
            keyStore.load(null, null);
        }
    }

    public Either<ErrorApp, KeyPair> generarClaves(String alias, String password) {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "BC");
            keyGen.initialize(2048, new SecureRandom());
            return Either.right(keyGen.generateKeyPair());
        } catch (Exception e) {
            return Either.left(new ErrorAppDatosNoValidos("Error al generar claves: " + e.getMessage()));
        }
    }

    public Either<ErrorApp, PublicKey> obtenerClavePublica(String alias) {
        try {
            Certificate cert = keyStore.getCertificate(alias);
            return cert != null ? Either.right(cert.getPublicKey()) : Either.left(new ErrorAppDatosNoValidos("Certificado no encontrado"));
        } catch (Exception e) {
            return Either.left(new ErrorAppDatosNoValidos("Error al obtener la clave pública: " + e.getMessage()));
        }
    }

    public Either<ErrorApp, PrivateKey> obtenerClavePrivada(String alias, String password) {
        try {
            PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, password.toCharArray());
            return privateKey != null ? Either.right(privateKey) : Either.left(new ErrorAppDatosNoValidos("Clave privada no encontrada"));
        } catch (Exception e) {
            return Either.left(new ErrorAppDatosNoValidos("Error al obtener la clave privada: " + e.getMessage()));
        }
    }

    public Either<ErrorApp, String> obtenerCertificadoBase64(String alias) {
        try {
            Certificate cert = keyStore.getCertificate(alias);
            return cert != null ?
                    Either.right(Base64.getEncoder().encodeToString(cert.getEncoded())) :
                    Either.left(new ErrorAppDatosNoValidos("Certificado no encontrado"));
        } catch (Exception e) {
            return Either.left(new ErrorAppDatosNoValidos("Error al obtener el certificado en Base64: " + e.getMessage()));
        }
    }

    // Métodos privados sin manejo de errores con Either
    public Either<ErrorApp, X509Certificate> generarCertificado(KeyPair keyPair, String alias) {
        try {
            X500Name issuer = new X500Name("CN=" + SERVER_ALIAS);
            X500Name subject = new X500Name("CN=" + alias);
            BigInteger serialNumber = BigInteger.valueOf(System.currentTimeMillis());
            Date startDate = new Date();
            Date endDate = new Date(startDate.getTime() + 365L * 24 * 60 * 60 * 1000);

            SubjectPublicKeyInfo pubKeyInfo = SubjectPublicKeyInfo.getInstance(keyPair.getPublic().getEncoded());
            X509v3CertificateBuilder certBuilder = new X509v3CertificateBuilder(
                    issuer, serialNumber, startDate, endDate, subject, pubKeyInfo);

            ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256withRSA")
                    .setProvider("BC")
                    .build(keyPair.getPrivate());

            X509CertificateHolder certHolder = certBuilder.build(contentSigner);
            return Either.right(new JcaX509CertificateConverter().setProvider("BC").getCertificate(certHolder));
        } catch (Exception e) {
            return Either.left(new ErrorAppDatosNoValidos("Error al generar certificado: " + e.getMessage()));
        }
    }


    public Either<ErrorApp, Boolean> almacenarEnKeyStore(String alias, PrivateKey privateKey, X509Certificate cert, String password) {
        try {
            keyStore.setKeyEntry(alias, privateKey, password.toCharArray(), new Certificate[]{cert});
            try (FileOutputStream fos = new FileOutputStream(KEYSTORE_PATH)) {
                keyStore.store(fos, KEYSTORE_PASSWORD.toCharArray());
            }
            return Either.right(true);
        } catch (Exception e) {
            return Either.left(new ErrorAppDatosNoValidos("Error al almacenar en KeyStore: " + e.getMessage()));
        }
    }

    private PrivateKey obtenerClavePrivadaServidor()  {
        try {
            return (PrivateKey) keyStore.getKey(SERVER_ALIAS, KEYSTORE_PASSWORD.toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }


}