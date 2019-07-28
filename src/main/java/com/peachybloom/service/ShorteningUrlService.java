package com.peachybloom.service;

import com.peachybloom.component.ShorteningUrlMaker;
import com.peachybloom.domain.entity.UrlMap;
import com.peachybloom.domain.model.UrlConvertModel;
import com.peachybloom.repository.UrlMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class ShorteningUrlService {

    @Autowired
    private ShorteningUrlMaker shorteningUrlMaker;

    @Autowired
    private UrlMapRepository urlMapRepository;

    @Transactional
    public UrlConvertModel convert(String originUrl) {
        UrlConvertModel urlConvertModel = new UrlConvertModel();
        UrlMap urlMap = getUrlMap(originUrl, urlConvertModel);
        return makeUrlModel(urlMap, urlConvertModel);
    }

    private UrlMap getUrlMap(String originUrl, UrlConvertModel urlConvertModel) {
        UrlMap urlMap = null;

        String shorteningUrl = shorteningUrlMaker.getShorteningUrl(originUrl);
        if(!StringUtils.isEmpty(shorteningUrl)) {
            Long urlKey = shorteningUrlMaker.makeShorteningUrlKey(shorteningUrl);
            urlMap = urlMapRepository.findById(urlKey)
                    .orElseThrow(() -> new IllegalStateException("Not found ShorteningUrl. url : " + originUrl));
            urlConvertModel.setConverted(true);
        } else {
            urlMap = urlMapRepository.findByOriginUrl(originUrl);
            if(urlMap == null) {
                try {
                    urlMap = new UrlMap();
                    urlMap.setOriginUrl(originUrl);
                    urlMapRepository.save(urlMap);
                } catch (Exception e) {
                    urlMap = urlMapRepository.findByOriginUrl(originUrl);
                    if(urlMap == null) {
                        throw e;
                    }
                }
            }
        }
        return urlMap;
    }

    private UrlConvertModel makeUrlModel(UrlMap urlMap, UrlConvertModel urlConvertModel) {
        String shorteningUrl = shorteningUrlMaker.makeShorteningUrl(urlMap.getId());

        urlConvertModel.setOriginUrl(urlMap.getOriginUrl());
        urlConvertModel.setShorteningUrl(shorteningUrl);
        return urlConvertModel;
    }

}
