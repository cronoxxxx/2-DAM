package org.example.loginspring_adriansaavedra.domain.components;

import lombok.extern.log4j.Log4j2;
import org.example.loginspring_adriansaavedra.common.Constantes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class MailComponent {

    @Value(Constantes.INIT_EXP + Constantes.SPRING_MAIL_USERNAME + Constantes.END_EXP)
    private String fromEmail;

    private final JavaMailSender javaMailSender;

    public MailComponent(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendVerificationEmail(String to, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(Constantes.EMAIL_VERIFICATION_SUBJECT);
        message.setText(Constantes.PLEASE_CLICK_ON_THE_LINK_TO_VERIFY_YOUR_EMAIL +
                Constantes.LINK_VERIFY + verificationCode);

        try {
            javaMailSender.send(message);
        } catch (MailException e) {
            log.error(e.getMessage(), e);
        }
    }
}
