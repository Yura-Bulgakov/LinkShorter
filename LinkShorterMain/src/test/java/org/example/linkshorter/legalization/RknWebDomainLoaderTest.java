package org.example.linkshorter.legalization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RknWebDomainLoaderTest {
    private RestTemplate restTemplate;
    private final String path = "path";
    private RknDomainLoader domainLoader;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        domainLoader = new RknWebDomainLoader(path, restTemplate);
    }


    @Test
    void testMapJson() throws DomainsNotFoundException {
        String mapJson = "{\"0\":\"7-k-official7.site\",\"1\":\"7k-gbo.pics\",\"2\":\"7k-lay.homes\",\"3\":\"7k-vto.pics\",\"4\":\"0-admiralx.icu\",\"5\":\"0-плюс.рф\",\"6\":\"00112.ru\",\"7\":\"007dom.ru\",\"8\":\"0104.info\",\"9\":\"0106.info\",\"10\":\"010c4f.olimpef97.site\"}";

        when(restTemplate.getForObject("path", String.class)).thenReturn(mapJson);

        List<String> domains = domainLoader.loadDomains();

        assertEquals(11, domains.size());
        assertEquals("7-k-official7.site", domains.get(0));
        assertEquals("010c4f.olimpef97.site", domains.get(domains.size() - 1));
    }

    @Test
    void testListJson() throws DomainsNotFoundException {
        String listJson = "[\"0-00.lordfilm0.biz\", \"0-10.lordfilm0.biz\", \"0-100.lordfilm0.biz\", \"0-101.lordfilm0.biz\", \"0-102.lordfilm0.biz\", \"0-103.lordfilm0.biz\"]";

        when(restTemplate.getForObject("path", String.class)).thenReturn(listJson);

        List<String> domains = domainLoader.loadDomains();

        assertEquals(6, domains.size());
        assertEquals("0-00.lordfilm0.biz", domains.get(0));
        assertEquals("0-103.lordfilm0.biz", domains.get(domains.size() - 1));
    }

    @Test
    void testInvalidJson() {
        String invalidJson = "invalid_json";

        when(restTemplate.getForObject("path", String.class)).thenReturn(invalidJson);

        assertThrows(DomainsNotFoundException.class, () -> {
            domainLoader.loadDomains();
        });
    }

    @Test
    void testNullJson() {
        when(restTemplate.getForObject("path", String.class)).thenReturn(null);

        assertThrows(DomainsNotFoundException.class, () -> {
            domainLoader.loadDomains();
        });
    }

    @Test
    void testEmptyJson() {
        String emptyJson = "";

        when(restTemplate.getForObject("path", String.class)).thenReturn(emptyJson);

        assertThrows(DomainsNotFoundException.class, () -> {
            domainLoader.loadDomains();
        });
    }

}