package com.sgglabs.retail;

import com.sgglabs.retail.model.dto.ProductSearchResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MessagePostService {
    private static final Logger LOG = LoggerFactory.getLogger(MessagePostService.class);

    private static final String ACTIVEMQ_MSG_REST_API_URL =
            "http://localhost:8161/api/message/SearchResultQ?type=queue&clientId=retaildatascan" +
                    "&_class=com.sgglabs.retail.model.dto.ProductSearchResultDTO";
    private static final String ACTIVEMQ_ADMIN_USERNAME = "mqadmin";
    private static final String ACTIVEMQ_ADMIN_PASSWORD = "mqadmin123";

    private RestTemplate restTemplate;

    public MessagePostService(RestTemplateBuilder templateBuilder) {
        templateBuilder.basicAuthorization(ACTIVEMQ_ADMIN_USERNAME, ACTIVEMQ_ADMIN_PASSWORD);
        this.restTemplate = templateBuilder.build();
    }

    public void sendSearchResultMessage(ProductSearchResultDTO searchResultDTO) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
    }
}
