package com.sgglabs.retail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class ApplicationConfigProperties {
    @Value("${app.mq.rest.api.url}")
    private String mqRestAPIURL;

    @Value("${app.mq.rest.api.auth.username}")
    private String mqAPIAuthUsername;

    @Value("${app.mq.rest.api.auth.password}")
    private String mqAPIAuthPassword;

    @Value("${app.const.base64.encode.charset}")
    private String mqAPIEncodingCharset;

    public String getMqRestAPIURL() {
        return mqRestAPIURL;
    }

    public void setMqRestAPIURL(String mqRestAPIURL) {
        this.mqRestAPIURL = mqRestAPIURL;
    }

    public String getMqAPIAuthUsername() {
        return mqAPIAuthUsername;
    }

    public void setMqAPIAuthUsername(String mqAPIAuthUsername) {
        this.mqAPIAuthUsername = mqAPIAuthUsername;
    }

    public String getMqAPIAuthPassword() {
        return mqAPIAuthPassword;
    }

    public void setMqAPIAuthPassword(String mqAPIAuthPassword) {
        this.mqAPIAuthPassword = mqAPIAuthPassword;
    }

    public String getMqAPIEncodingCharset() {
        return mqAPIEncodingCharset;
    }

    public void setMqAPIEncodingCharset(String mqAPIEncodingCharset) {
        this.mqAPIEncodingCharset = mqAPIEncodingCharset;
    }
}
