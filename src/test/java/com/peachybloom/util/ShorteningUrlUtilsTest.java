package com.peachybloom.util;

import com.peachybloom.component.ShorteningUrlMaker;
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
public class ShorteningUrlUtilsTest {

    @Autowired
    private ShorteningUrlMaker shorteningUrlMaker;

    @Test
    public void makeShorteningUrlTest() {
        Long key = 218340105584895L;
        String shorteningUrl = shorteningUrlMaker.makeShorteningUrl(key);

        assertThat(shorteningUrl).isEqualTo(shorteningUrlMaker.getPrefixUrls()[0] + "ZZZZZZZZ");
    }

    @Test(expected = IllegalStateException.class)
    public void makeShorteningUrlFailedTest() {
        Long key = 218340105584896L;
        String shorteningUrl = shorteningUrlMaker.makeShorteningUrl(key);

        assertThat(shorteningUrl).isEqualTo(shorteningUrlMaker.getPrefixUrls()[0] + "100000000");
    }

    @Test
    public void makeShorteningUrlKeyTest() {
        String shorteningUrl = "ZZZZZZZZ";
        Long key = shorteningUrlMaker.makeShorteningUrlKey(shorteningUrl);

        assertThat(key).isEqualTo(218340105584895L);
    }

    @Test
    public void getShorteningUrlTest() {
        String shorteningFullUrl = "http://localhost:8080/ZZZZZZZZ";
        String shorteningUrl = shorteningUrlMaker.getShorteningUrl(shorteningFullUrl);

        assertThat(shorteningUrl).isEqualTo("ZZZZZZZZ");
    }

    @Test(expected = IllegalStateException.class)
    public void containsNotDecodeCharFailedTest() {
        String shorteningUrl = "{123}";
        Long key = shorteningUrlMaker.makeShorteningUrlKey(shorteningUrl);
    }
}
