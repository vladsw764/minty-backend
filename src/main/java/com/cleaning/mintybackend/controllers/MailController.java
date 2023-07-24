package com.cleaning.mintybackend.controllers;

import com.cleaning.mintybackend.pojo.MailRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/v1/mail-send")
public class MailController {

    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String mail;


    @PostMapping
    public ResponseEntity<String> sendMail(@RequestBody MailRequest mailRequest) {
        String subject = mailRequest.getCategory();
        String content = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "    <title>Деталі замовлення</title>\n"
                + "</head>\n"
                + "<body style=\"font-family: Arial, sans-serif; line-height: 1.6; margin: 0; padding: 0; text-align: left; background-color: white; color: #0066cc;\">\n" // Set white background and blue text color
                + "    <div style=\"max-width: 600px; margin: 0 auto; padding: 20px; background-color: white;\">\n"
                + "        <h1 style=\"font-size: 30px;\">Деталі замовлення</h1>\n" // Removed color to inherit blue from the body
                + "        <p style=\"font-size: 24px;\">Ім'я: " + mailRequest.getName() + "</p>\n"
                + "        <p style=\"font-size: 24px;\">Номер телефону: " + mailRequest.getMobile() + "</p>\n"
                + "        <p style=\"font-size: 24px;\">Категорія замовлення: " + mailRequest.getCategory() + "</p>\n"
                + "    </div>\n"
                + "</body>\n"
                + "</html>";


        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "utf-8");

        try {
            messageHelper.setTo(mail);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true); // Set the second argument to 'true' for HTML content
            javaMailSender.send(mimeMessage);
            return new ResponseEntity<>("Message sent successfully!", HttpStatus.OK);
        } catch (MessagingException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to send email.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
