package org.example.linkshorter.legalization;

import java.util.List;

public interface RknDomainLoader {
    List<String> loadDomains() throws DomainsNotFoundException;
}
