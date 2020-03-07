package com.rouge.dev;

import com.rouge.dev.controller.HomeController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Cretated By Rohan Rawal on
 */
@SpringBootApplication
public class SbBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(new Class[]{SbBankApplication.class, HomeController.class}, args);
    }

}
