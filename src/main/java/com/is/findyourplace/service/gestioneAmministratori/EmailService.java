package com.is.findyourplace.service.gestioneAmministratori;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    /**
     * JavaMailSender per mandare delle email automaticamente.
     */
    @Autowired
    private final JavaMailSender javaMailSender;

    /**
     * Metodo usato per mandare le email.
     * @param toEmail Email destinatario
     * @param subject Soggetto dell' email
     * @param message Messaggio dell' email
     */
    @Async
    public void sendEmail(
            final String toEmail,
            final String subject,
            final String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailMessage.setFrom("findyourplace.fyp@gmail.com");
        javaMailSender.send(mailMessage);
    }
}
