package com.peachybloom.component;

import com.peachybloom.properties.ShorteningUrlProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class ShorteningUrlMaker {

    private static Long LIMIT_KEY;

    private static int SHORTENING_URL_MAX_LENGTH ;

    private static String[] PREFIX_URLS;

    private static int DIGITS;

    private static char[] DIGITS_VALUES;

    private static Map<Character, Integer> DECODE_MAP = new HashMap<>();

    @Autowired
    private ShorteningUrlProperties shorteningUrlProperties;

    @PostConstruct
    public void init() {
        this.LIMIT_KEY = shorteningUrlProperties.getLimitKey();
        this.SHORTENING_URL_MAX_LENGTH = shorteningUrlProperties.getMaxLength();
        this.DIGITS = shorteningUrlProperties.getDigitsValues().length;
        this.DIGITS_VALUES = shorteningUrlProperties.getDigitsValues();
        this.PREFIX_URLS = shorteningUrlProperties.getPrefixUrls();

        for(int i=0; i<DIGITS_VALUES.length; i++) {
            DECODE_MAP.put(DIGITS_VALUES[i], i);
        }
    }

    public String makeShorteningUrl(Long key) {

        if(key > LIMIT_KEY) {
            throw new IllegalStateException("Out of Key. key : " + key + ", limit key : " + LIMIT_KEY);
        }

        StringBuilder sb = new StringBuilder();

        Long quotient = key;
        do {
            Long index = quotient%DIGITS;
            sb.append(DIGITS_VALUES[index.intValue()]);

            quotient = quotient/DIGITS;
        } while(quotient != 0);

        return PREFIX_URLS[0] + sb.reverse().toString();
    }

    public Long makeShorteningUrlKey(String shorteningUrl) {
        char[] shorteningUrlArr = shorteningUrl.toCharArray();

        Long key = 0L;
        int pow = shorteningUrl.length();
        for(int i=0; i<shorteningUrlArr.length; i++) {
            Integer decodeValue = DECODE_MAP.get(shorteningUrlArr[i]);
            if(decodeValue == null) {
                throw new IllegalStateException(
                        "Contains characters that can not be converted. ShorteningUrl : " + shorteningUrl);
            }
            key += (long) Math.pow(DIGITS, --pow) * decodeValue;
        }
        return key;
    }

    public String getShorteningUrl(String url) {
        String shorteningUrl = "";
        for(String prefixUrl : PREFIX_URLS) {
            if(url.startsWith(prefixUrl)) {
                String tempShorteningUrl = url.substring(prefixUrl.length());
                if(tempShorteningUrl.length() <= SHORTENING_URL_MAX_LENGTH) {
                    shorteningUrl = tempShorteningUrl;
                    break;
                }
            }
        }
        return shorteningUrl;
    }

    public String[] getPrefixUrls() {
        return PREFIX_URLS;
    }
}
