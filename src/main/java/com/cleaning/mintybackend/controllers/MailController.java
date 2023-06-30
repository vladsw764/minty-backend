package com.cleaning.mintybackend.controllers;

import com.cleaning.mintybackend.pojo.MailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mail-send")
public class MailController {

    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String mail;

    @PostMapping
    public ResponseEntity<String> sendMail(@RequestBody MailRequest mailRequest) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail);
        mailMessage.setSubject(mailRequest.getCategory());

        mailMessage.setText(mailRequest.getName() + "\n" + mailRequest.getMobile() + "\n" + mailRequest.getCategory());

        javaMailSender.send(mailMessage);
        return new ResponseEntity<>("Message sent successfully!", HttpStatus.OK);
    }
}
