package org.example.linkshorterbot.util;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class UrlValidator {
    public static boolean isValid(String link) {
        try {
            new URL(link).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }

}
