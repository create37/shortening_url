package com.peachybloom.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ShorteningUrlUtilsTest {

    @Test
    public void makeShorteningUrlTest() {
        Long key = 218340105584895L;
        String shorteningUrl = ShorteningUrlUtils.makeShorteningUrl(key);

        assertThat(shorteningUrl).isEqualTo(ShorteningUrlUtils.getPrefixUrls()[0] + "ZZZZZZZZ");
    }

    @Test(expected = IllegalStateException.class)
    public void makeShorteningUrlFailedTest() {
        Long key = 218340105584896L;
        String shorteningUrl = ShorteningUrlUtils.makeShorteningUrl(key);

        assertThat(shorteningUrl).isEqualTo(ShorteningUrlUtils.getPrefixUrls()[0] + "100000000");
    }

    @Test
    public void makeShorteningUrlKeyTest() {
        String shorteningUrl = "ZZZZZZZZ";
        Long key = ShorteningUrlUtils.makeShorteningUrlKey(shorteningUrl);

        assertThat(key).isEqualTo(218340105584895L);
    }

    @Test
    public void getShorteningUrlTest() {
        String shorteningFullUrl = "http://localhost:8080/ZZZZZZZZ";
        String shorteningUrl = ShorteningUrlUtils.getShorteningUrl(shorteningFullUrl);

        assertThat(shorteningUrl).isEqualTo("ZZZZZZZZ");
    }

    @Test(expected = IllegalStateException.class)
    public void containsNotDecodeCharFailedTest() {
        String shorteningUrl = "{123}";
        Long key = ShorteningUrlUtils.makeShorteningUrlKey(shorteningUrl);
    }
}
