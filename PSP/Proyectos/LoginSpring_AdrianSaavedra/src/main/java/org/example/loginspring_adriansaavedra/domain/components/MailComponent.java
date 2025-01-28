package org.example.loginspring_adriansaavedra.domain.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailComponent {

    @Value("${spring.mail.username}")
    private String fromEmail;

    private final JavaMailSender javaMailSender;

    public MailComponent(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendVerificationEmail(String to, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject("Email Verification");
        message.setText("Please click on the link to verify your email: " +
                "http://localhost:8080/verify?code=" + verificationCode);

        try {
            javaMailSender.send(message);
        } catch (MailException e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }
}
