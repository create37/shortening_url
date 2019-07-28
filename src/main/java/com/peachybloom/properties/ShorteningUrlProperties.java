package com.peachybloom.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "shortening-url")
public class ShorteningUrlProperties {

    private Long limitKey;
    private int maxLength;
    private char[] digitsValues;
    private String[] prefixUrls;


    public Long getLimitKey() {
        return limitKey;
    }

    public void setLimitKey(Long limitKey) {
        this.limitKey = limitKey;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public char[] getDigitsValues() {
        return digitsValues;
    }

    public void setDigitsValues(char[] digitsValues) {
        this.digitsValues = digitsValues;
    }

    public String[] getPrefixUrls() {
        return prefixUrls;
    }

    public void setPrefixUrls(String[] prefixUrls) {
        this.prefixUrls = prefixUrls;
    }
}
