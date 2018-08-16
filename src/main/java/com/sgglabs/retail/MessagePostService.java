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
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.*;

@Service
public class MessagePostService {
    private static final Logger LOG = LoggerFactory.getLogger(MessagePostService.class);

    private static final String MQ_MSG_REST_API_URL = "http://localhost:8161/api/message/SearchResultQ" +
            "?type=queue&clientId=retaildatascan&_class=com.sgglabs.retail.model.dto.ProductSearchResultDTO";
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
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        this.restTemplate = templateBuilder.build();
        restTemplate.setMessageConverters(messageConverters);
    }

    public void sendSearchResultMessage(ProductSearchResultDTO searchResultDTO) throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        String authString = MQ_ADMIN_USERNAME + ":" + MQ_ADMIN_PASSWORD;
        byte[] encodedAuth = Base64.getEncoder().encode(authString.getBytes(Charset.forName("US-ASCII")) );
        String authHeader = "Basic " + new String(encodedAuth);
        httpHeaders.set("Authorization", authHeader);

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
            LOG.debug(response.getStatusCode().toString() + ":::" + response.getStatusCodeValue());
        } catch (JsonProcessingException jpe) {
            LOG.error(jpe.getMessage(), jpe);
            throw jpe;
        }
    }
}