import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.security.spec.ECGenParameterSpec;
import java.util.Date;
import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class maincutre {
    static {
        // Registrar BouncyCastle como proveedor de seguridad
        Security.addProvider(new BouncyCastleProvider());
    }

    public static final String PEPITO = "pepito"; // Considera cambiar esta contraseña por una más segura

    public static void main(String[] args) {
        try {
            // Generar par de claves usando curva elíptica
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
            keyPairGenerator.initialize(new ECGenParameterSpec("secp521r1")); // Usar secp521r1 para mayor seguridad
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            // Crear certificado autofirmado
            X500Principal subject = new X500Principal("CN=Servidor");
            Date startDate = new Date();
            Date expiryDate = new Date(startDate.getTime() + 365 * 24 * 60 * 60 * 1000L); // 1 año
            BigInteger serialNumber = BigInteger.valueOf(System.currentTimeMillis());

            SubjectPublicKeyInfo publicKeyInfo = SubjectPublicKeyInfo.getInstance(keyPair.getPublic().getEncoded());
            X509v3CertificateBuilder certBuilder = new X509v3CertificateBuilder(
                    new X500Name("CN=Servidor"), serialNumber, startDate, expiryDate,
                    new X500Name("CN=Servidor"), publicKeyInfo);

            ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256withECDSA").build(keyPair.getPrivate());
            X509Certificate cert = new JcaX509CertificateConverter().setProvider("BC").getCertificate(certBuilder.build(contentSigner));

            // Crear KeyStore y almacenar claves
            KeyStore keyStore = KeyStore.getInstance("JKS");
            char[] password = PEPITO.toCharArray(); // Cambia esto por una contraseña más segura
            keyStore.load(null, null);
            keyStore.setKeyEntry("servidor", keyPair.getPrivate(), password, new Certificate[]{cert});

            // Guardar KeyStore en un archivo
            try (FileOutputStream fos = new FileOutputStream("buzon.jks")) {
                keyStore.store(fos, password);
            }

            System.out.println("Claves del servidor generadas y almacenadas en buzon.jks");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}