package com.sgglabs.retail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgglabs.retail.model.dto.ProductSearchResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class MessagePostService {
    private static final Logger LOG = LoggerFactory.getLogger(MessagePostService.class);

    private static final String MQ_MSG_REST_API_URL = "http://localhost:8161/api/message/SearchResultQ";
    private static final String MQ_REST_REQ_PARAM_VAR_TYPE = "type";
    private static final String MQ_REST_REQ_PARAM_VAL_TYPE = "queue";

    private static final String MQ_REST_REQ_PARAM_VAR_CLIENTID = "clientId";
    private static final String MQ_REST_REQ_PARAM_VAL_CLIENTID = "retaildatascan";

    private static final String MQ_REST_REQ_PARAM_VAR_CLASS = "_class";
    private static final String MQ_REST_REQ_PARAM_VAL_CLASS = "com.sgglabs.retail.model.dto.ProductSearchResultDTO";

    private static final String MQ_ADMIN_USERNAME = "mqadmin";
    private static final String MQ_ADMIN_PASSWORD = "mqadmin123";

    private RestTemplate restTemplate;

    public MessagePostService(RestTemplateBuilder templateBuilder) {
        templateBuilder.basicAuthorization(MQ_ADMIN_USERNAME, MQ_ADMIN_PASSWORD);
        this.restTemplate = templateBuilder.build();
    }

    public void sendSearchResultMessage(ProductSearchResultDTO searchResultDTO) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_PLAIN);

        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put(MQ_REST_REQ_PARAM_VAR_TYPE, MQ_REST_REQ_PARAM_VAL_TYPE);
        paramsMap.put(MQ_REST_REQ_PARAM_VAR_CLIENTID, MQ_REST_REQ_PARAM_VAL_CLIENTID);
        paramsMap.put(MQ_REST_REQ_PARAM_VAR_CLIENTID, MQ_REST_REQ_PARAM_VAL_CLASS);

        String jsonOfProductResult = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            jsonOfProductResult = mapper.writeValueAsString(searchResultDTO);

            MultiValueMap<String, String> requestBodyMap = new LinkedMultiValueMap<String, String>();
            requestBodyMap.add("productSearchResult", jsonOfProductResult);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBodyMap, httpHeaders);
            ResponseEntity<String> response = restTemplate.postForEntity(MQ_MSG_REST_API_URL,
                    request, String.class, paramsMap);
        } catch (JsonProcessingException jpe) {
            LOG.error(jpe.getMessage(), jpe);
        }
    }
}