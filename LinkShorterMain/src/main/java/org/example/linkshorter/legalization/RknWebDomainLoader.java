package org.example.linkshorter.legalization;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class RknWebDomainLoader implements RknDomainLoader {
    private final String rknWebApi;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RestTemplate restTemplate;

    @Autowired
    public RknWebDomainLoader(@Value("${rkn.web.api}") String rknWebApi) {
        this(rknWebApi, new RestTemplate());
    }

    protected RknWebDomainLoader(String rknWebApi, RestTemplate restTemplate) {
        this.rknWebApi = rknWebApi;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<String> loadDomains() throws DomainsNotFoundException {
        String rknJson = restTemplate.getForObject(rknWebApi, String.class);
        return mapDomainsResponse(rknJson);
    }

    private List<String> mapDomainsResponse(String str) throws DomainsNotFoundException {
        List<String> domainList = new ArrayList<>();
        try {
            if (StringUtils.isEmpty(str)) {
                throw new Exception("Получена пустая строка");
            }
            JsonNode jsonNode = objectMapper.readTree(str);
            for (JsonNode node : jsonNode) {
                if (node != null) {
                    domainList.add(node.asText());
                }
            }
            return domainList;
        } catch (Exception e) {
            logger.error("Произошло исключение '{}' при попытке получить список заблокированных доменов: {}",
                    e.getClass().getSimpleName(), e.getMessage());
            throw new DomainsNotFoundException("Список заблокированных доменов не найден");
        }
    }

}
