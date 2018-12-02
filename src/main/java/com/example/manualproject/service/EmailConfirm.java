package com.example.manualproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
public class EmailConfirm {
    @Autowired
    protected PasswordEncoder passwordEncoder;

    private Properties smtpProperties;
    Session session;
    Message message;
    protected String mailAddress;
    private String mailPassword;
    protected String hash;


    public void sendMail(String userName, String email) {
        hash = passwordEncoder.encode(userName);
        loadEmailProperty();
        setSmtpProperties();
        authenticateSender();
        send(userName, email);
    }

    public void loadEmailProperty() {
        InputStream fis;
        Properties emailProperty = new Properties();
        try {
            fis = this.getClass().getClassLoader().getResourceAsStream("email.properties");
            emailProperty.load(fis);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        mailAddress = emailProperty.getProperty("email");
        mailPassword = emailProperty.getProperty("password");
    }

    public void setSmtpProperties() {
        smtpProperties = new Properties();
        smtpProperties.put("mail.smtp.auth", "true");
        smtpProperties.put("mail.smtp.starttls.enable", "true");
        smtpProperties.put("mail.smtp.host", "smtp.gmail.com");
        smtpProperties.put("mail.smtp.port", "587");
    }

    public void authenticateSender() {
        session = Session.getInstance(smtpProperties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailAddress, mailPassword);
            }
        });
    }

    public void send(String userName, String email) { // todo del
        try {
            message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailAddress));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Registration Confirm");
            message.setText("Hi, " + userName + "\n\n For email confirmation please follow the link\n"
                    + "http://localhost:8080/registrationconfirm?u=" + userName + "&h=" + hash);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
