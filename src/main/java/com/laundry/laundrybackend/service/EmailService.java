package com.laundry.laundrybackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPasswordEmail(String toEmail, String name, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Laundry Registration - Your Login Credentials");

        String emailBody = String.format(
                "Dear %s,\n\n" +
                        "Thank you for registering your laundry service with us!\n\n" +
                        "Your account has been created successfully. Here are your login credentials:\n\n" +
                        "Email: %s\n" +
                        "Password: %s\n\n" +
                        "Please keep this information secure and do not share it with anyone.\n" +
                        "Your registration is currently under review and will be approved shortly.\n\n" +
                        "Best regards,\n" +
                        "Laundry Management Team",
                name, toEmail, password
        );

        message.setText(emailBody);
        message.setFrom("elharidioumaima@gmail.com");

        mailSender.send(message);
    }
}