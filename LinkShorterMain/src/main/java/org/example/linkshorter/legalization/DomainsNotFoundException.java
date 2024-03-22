package org.example.linkshorter.legalization;

import java.io.IOException;

public class DomainsNotFoundException extends IOException {
    public DomainsNotFoundException(String message) {
        super(message);
    }
}
