package com.rouge.dev.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Cretated By Rohan Rawal on 25-01-2020
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private Configuration configuration;

    public void sendEmailNotification(Map<String,Object> objectMap,String emailId) throws Exception{

        MimeMessage mimeMessage =  sender.createMimeMessage();

        MimeMessageHelper mailMessage = new MimeMessageHelper(mimeMessage, true);

        String htmlFile = "<h1>Username And Password :: user</h1>";

        mailMessage.setText(htmlFile,true);
        mailMessage.setSubject("Test");
        mailMessage.setTo(emailId);
        mailMessage.setFrom("rohanrawal1993@gmail.com");

        sender.send(mimeMessage);
    }
}
