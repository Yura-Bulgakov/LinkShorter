package org.example.linkshorter.util;

import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Component
public class LinkValidator {

    public boolean isValid(String link) {
        try {
            new URL(link).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }

    public String getDomain(String link) {
        try {
            URL url = new URL(link);
            return url.getHost();
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
