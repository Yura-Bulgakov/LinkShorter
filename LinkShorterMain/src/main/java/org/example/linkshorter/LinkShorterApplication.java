package org.example.linkshorter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LinkShorterApplication {

    public static void main(String[] args) {
        SpringApplication.run(LinkShorterApplication.class, args);
    }

}
