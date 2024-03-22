package org.example.linkshorter.legalization;

public interface DomainCheckService {
    void checkLongLinkDomains() throws DomainsNotFoundException;
}
