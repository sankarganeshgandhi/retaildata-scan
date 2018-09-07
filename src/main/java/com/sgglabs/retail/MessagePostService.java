package com.sgglabs.retail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgglabs.retail.model.dto.ProductSearchResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class MessagePostService {
    private static final Logger LOG = LoggerFactory.getLogger(MessagePostService.class);
    private static final String HTTP_BASIC_AUTH_STRING = "Basic";
    private static final String HTTP_HEADER_BASIC_AUTH_KEY = "Authorization";

    private RestTemplate restTemplate;

    private ApplicationConfigProperties props;

    @Autowired
    public MessagePostService(ApplicationConfigProperties properties, RestTemplateBuilder templateBuilder) {
        this.props = properties;
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        this.restTemplate = templateBuilder.build();
        restTemplate.setMessageConverters(messageConverters);
    }

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

        String authString = props.getMqAPIAuthUsername() + ":" + props.getMqAPIAuthPassword();
        byte[] encodedAuth = Base64.getEncoder().encode(authString
                .getBytes(Charset.forName(props.getMqAPIEncodingCharset())));
        String authHeader = HTTP_BASIC_AUTH_STRING + " " + new String(encodedAuth);
        httpHeaders.set(HTTP_HEADER_BASIC_AUTH_KEY, authHeader);

        String jsonOfProductResult;
        try {
            ObjectMapper mapper = new ObjectMapper();
            jsonOfProductResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(searchResultDTO);

            HttpEntity<?> request = new HttpEntity<>(jsonOfProductResult, httpHeaders);
            restTemplate.postForEntity(props.getMqRestAPIURL(), request, String.class);
        } catch (JsonProcessingException jpe) {
            LOG.error(jpe.getMessage(), jpe);
            throw jpe;
        }
    }
}