package com.example.manualproject.service;

import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
public class ChangeEmail extends EmailConfirm {
    public void send(String userName, String email) {
        try {
            message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailAddress));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Registration Confirm");
            message.setText("Hi, " + userName + "\n\n For email confirmation please follow the link\n"
                    + "http://localhost:8080/changeemail?&u=" + userName + "&h=" + hash + "&e=" + email);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
