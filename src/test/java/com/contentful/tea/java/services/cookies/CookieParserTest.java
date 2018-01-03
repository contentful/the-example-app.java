package com.contentful.tea.java.services.cookies;

import com.contentful.tea.java.MainController;
import com.contentful.tea.java.models.Settings;
import com.contentful.tea.java.services.url.CookieParser;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainController.class)
public class CookieParserTest {

  @Autowired
  @SuppressWarnings("unused")
  private Settings settings;

  @Autowired
  @SuppressWarnings("unused")
  private CookieParser parser;

  @After
  public void teardown() {
    settings.reset();
  }

  @Test
  public void parserSetsTokens() {
    parser.loadCookies("CDA", "spaceId", "cdaToken", "cpaToken");

    assertThat(settings.getApi()).isEqualTo(Settings.API_CDA);
    assertThat(settings.getSpaceId()).isEqualTo("spaceId");
    assertThat(settings.getDeliveryAccessToken()).isEqualTo("cdaToken");
    assertThat(settings.getPreviewAccessToken()).isEqualTo("cpaToken");
  }

  @Test
  public void parserRetrievesTokens() {
    settings
        .setApi(Settings.API_CDA)
        .setSpaceId("spaceId")
        .setDeliveryAccessToken("cdaToken")
        .setPreviewAccessToken("cpaToken");

    parser.saveCookies();/// todo
  }
}