package com.rouge.dev.controller;

import com.rouge.dev.email.EmailConfiguration;
import com.rouge.dev.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * Cretated By Rohan Rawal on 21-01-2020
 */
@Controller
@Slf4j
public class HomeController  {

    @Autowired
    EmailConfiguration emailConfiguration;

    @Autowired
    EmailService emailService;

    @GetMapping(value = {"","/","index","index.html"})
    public String callHomeView(){
        return "index";
    }

    @GetMapping(value = {"/login","/login.html"})
    public String callLoginView(){
        return "loginpage";
    }

    @GetMapping(value = {"/help","/help.html"})
    public String callingHelpView(){
        return "help";
    }

    @PostMapping(value = "/send/email")
    public String sendEmail(String emailId){

        try {

            Map<String,Object> objectMap = new HashMap<>();

            emailService.sendEmailNotification(objectMap,emailId);

        }catch (Exception e){

            log.debug(e.getMessage());

            return "help";
        }
        return "index";
    }
}
