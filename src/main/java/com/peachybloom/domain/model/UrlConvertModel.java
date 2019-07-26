package com.peachybloom.domain.model;

public class UrlConvertModel {

    private String originUrl;

    private String shorteningUrl;

    private boolean converted = false;

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public String getShorteningUrl() {
        return shorteningUrl;
    }

    public void setShorteningUrl(String shorteningUrl) {
        this.shorteningUrl = shorteningUrl;
    }

    public boolean isConverted() {
        return converted;
    }

    public void setConverted(boolean converted) {
        this.converted = converted;
    }
}
