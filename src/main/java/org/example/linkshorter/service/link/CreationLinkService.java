package org.example.linkshorter.service.link;

import org.example.linkshorter.entity.ShortLink;

public interface CreationLinkService {
    ShortLink addLink(String longLink);

    ShortLink addLink(String longLink, String token);
}
