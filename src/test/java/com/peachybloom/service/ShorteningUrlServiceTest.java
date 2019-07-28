package com.peachybloom.service;

import com.peachybloom.component.ShorteningUrlMaker;
import com.peachybloom.domain.model.UrlConvertModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ShorteningUrlServiceTest {

    @Autowired
    private ShorteningUrlService shorteningUrlService;

    @Autowired
    private ShorteningUrlMaker shorteningUrlMaker;

    @Test
    public void convertTest() {
        String originUrl = "http://www.google.com";
        UrlConvertModel urlConvertModel = shorteningUrlService.convert(originUrl);

        assertThat(urlConvertModel.getOriginUrl()).isEqualTo(originUrl);
        assertThat(urlConvertModel.getShorteningUrl()).isNotEmpty();
    }

    @Test
    public void sameUrlConvertTest() {
        String originUrl = "http://www.google.com";

        UrlConvertModel firstUrlConvertModel = shorteningUrlService.convert(originUrl);
        UrlConvertModel secondUrlConvertModel = shorteningUrlService.convert(originUrl);

        assertThat(firstUrlConvertModel.getShorteningUrl()).isEqualTo(secondUrlConvertModel.getShorteningUrl());
    }

    @Test(expected = IllegalStateException.class)
    public void convertFailedTest() {
        String originUrl = shorteningUrlMaker.getPrefixUrls()[0]+ "zzzz";
        UrlConvertModel urlConvertModel = shorteningUrlService.convert(originUrl);

        assertThat(urlConvertModel.getOriginUrl()).isEqualTo(originUrl);
    }
}
