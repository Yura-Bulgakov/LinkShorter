package org.example.linkshorterbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class LinkShorterBotApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(LinkShorterBotApplication.class, args);
    }

}
