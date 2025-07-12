package com.portfolio.myportfolio.service.implementation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.portfolio.myportfolio.dto.ContactRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {
   private final JavaMailSender mailSender;
    @Value("${portfolio.admin.email}") 
    private String adminEmail;

    public void sendContactMail(ContactRequest dto) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(adminEmail);
        msg.setFrom(dto.getEmail());
        msg.setSubject("New portfolio message from " + dto.getName());
        msg.setText("""
                Name   : %s
                Email  : %s
                
                Message:
                %s
                """.formatted(dto.getName(), dto.getEmail(), dto.getMessage()));
        mailSender.send(msg);
    }
}
