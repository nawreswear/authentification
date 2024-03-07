package com.springjwt.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendVerificationEmail(String to, String verificationCode) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(to);
            helper.setSubject("Confirm Your Email Address");
            helper.setText("Dear User,\n\n"
                    + "Thank you for signing up for our service, Ikalas.\n\n"
                    + "Please enter the following verification code in the verification page to confirm your email address.\n\n"
                    + verificationCode + "\n\n"
                    + "Or use this link to verify your account.\n\n"
                    + "https://ikalas.com/auth/signup-confirmation-link?emailAddress=" + to + "&verificationCode=" + verificationCode + "\n\n"
                    + "Thank you for choosing our service!\n\n"
                    + "Best regards,\nIkalas Support Team");
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
