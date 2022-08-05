package com.aysevarlik.satisfactionsurveyproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication

@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SatisfactionSurveyProjectApplication{

    public static void main(String[] args) {

        System.setProperty("spring.devtools.restart.enabled","true");
        System.setProperty("java.awt.headless", "false");

        SpringApplication.run(SatisfactionSurveyProjectApplication.class, args);
    }

}
