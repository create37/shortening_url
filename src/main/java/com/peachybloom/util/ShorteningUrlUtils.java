package com.peachybloom.util;

import java.util.HashMap;
import java.util.Map;

public class ShorteningUrlUtils {

    private static final Long LIMIT_KEY = 218340105584895L;

    private static final int SHORTENING_URL_MAX_LENGTH = 8;

    private static final int DIGITS = 62;

    private static final String[] PREFIX_URLS = {"http://localhost:8080/"};

    private static final char[] BASE62 = {
            '0','1','2','3','4','5','6','7','8','9',
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
    };

    private static Map<Character, Integer> BASE62_DECODE_MAP = new HashMap<>();

    static {
        for(int i=0; i<BASE62.length; i++) {
            BASE62_DECODE_MAP.put(BASE62[i], i);
        }
    }

    public static String makeShorteningUrl(Long key) {

        if(key > LIMIT_KEY) {
            throw new IllegalStateException("Out of Key. key : " + key + ", limit key : " + LIMIT_KEY);
        }

        StringBuilder sb = new StringBuilder();

        Long quotient = key;
        do {
            Long index = quotient%DIGITS;
            sb.append(BASE62[index.intValue()]);

            quotient = quotient/DIGITS;
        } while(quotient != 0);

        return PREFIX_URLS[0] + sb.reverse().toString();
    }

    public static Long makeShorteningUrlKey(String shorteningUrl) {
        char[] shorteningUrlArr = shorteningUrl.toCharArray();

        Long key = 0L;
        int pow = shorteningUrl.length();
        for(int i=0; i<shorteningUrlArr.length; i++) {
            Integer decodeValue = BASE62_DECODE_MAP.get(shorteningUrlArr[i]);
            if(decodeValue == null) {
                throw new IllegalStateException(
                        "Contains characters that can not be converted. ShorteningUrl : " + shorteningUrl);
            }
            key += (long) Math.pow(DIGITS, --pow) * decodeValue;
        }
        return key;
    }

    public static String getShorteningUrl(String url) {
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

    public static String[] getPrefixUrls() {
        return PREFIX_URLS;
    }
}
