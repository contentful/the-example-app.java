package com.contentful.tea.java.services.http;

import com.contentful.tea.java.MainController;
import com.contentful.tea.java.services.contentful.Contentful;
import com.contentful.tea.java.services.settings.Settings;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.contentful.tea.java.services.http.Constants.NAME_API;
import static com.contentful.tea.java.services.http.Constants.NAME_DELIVERY_TOKEN;
import static com.contentful.tea.java.services.http.Constants.NAME_EDITORIAL_FEATURES;
import static com.contentful.tea.java.services.http.Constants.NAME_LOCALE;
import static com.contentful.tea.java.services.http.Constants.NAME_PREVIEW_TOKEN;
import static com.contentful.tea.java.services.http.Constants.NAME_SPACE_ID;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainController.class)
public class UrlParserTest {

  @Autowired
  @SuppressWarnings("unused")
  private Contentful contentful;

  @Autowired
  @SuppressWarnings("unused")
  private Settings settings;

  @Autowired
  @SuppressWarnings("unused")
  private UrlParameterParser parser;

  @After
  public void tearDown() {
    contentful.reset();
    settings.reset();
  }

  @Test
  public void parsingNoParameterDoesNotChangeSettings() {
    final String before = contentful.toString();

    parser.urlParameterToApp(Collections.emptyMap());

    final String after = contentful.toString();
    assertThat(before).isEqualTo(after);
  }

  @Test
  public void parsingNullParameterDoesNotChangeSettings() {
    final String before = contentful.toString();

    parser.urlParameterToApp(null);

    final String after = contentful.toString();
    assertThat(before).isEqualTo(after);
  }

  @Test
  public void tokenInUrlChangesTokenInSettings() {
    parser.urlParameterToApp(singletonMap(NAME_DELIVERY_TOKEN, new String[]{"cda_token"}));

    final String after = contentful.getDeliveryAccessToken();
    assertThat(after).isEqualTo("cda_token");
  }

  @Test
  public void allParameterCanGetParsed() {
    final Map<String, String[]> map = new HashMap<>();
    map.put(NAME_API, new String[]{"cpa"});
    map.put(NAME_SPACE_ID, new String[]{"TEST_NAME_SPACE_ID"});
    map.put(NAME_LOCALE, new String[]{"TEST_NAME_LOCALE"});
    map.put(NAME_DELIVERY_TOKEN, new String[]{"TEST_NAME_DELIVERY_TOKEN"});
    map.put(NAME_PREVIEW_TOKEN, new String[]{"TEST_NAME_PREVIEW_TOKEN"});
    map.put(NAME_EDITORIAL_FEATURES, new String[]{"enabled"});

    parser.urlParameterToApp(map);

    assertThat(contentful.getApi()).isEqualTo("cpa");
    assertThat(contentful.getSpaceId()).isEqualTo("TEST_NAME_SPACE_ID");
    assertThat(contentful.getDeliveryAccessToken()).isEqualTo("TEST_NAME_DELIVERY_TOKEN");
    assertThat(contentful.getPreviewAccessToken()).isEqualTo("TEST_NAME_PREVIEW_TOKEN");
    assertThat(settings.getLocale()).isEqualTo("TEST_NAME_LOCALE");
    assertThat(settings.areEditorialFeaturesEnabled()).isEqualTo(true);
  }
}