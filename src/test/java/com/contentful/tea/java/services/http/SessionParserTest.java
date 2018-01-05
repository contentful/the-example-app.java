package com.contentful.tea.java.services.http;

import com.contentful.tea.java.MainController;
import com.contentful.tea.java.models.Settings;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import static com.contentful.tea.java.services.http.Constants.NAME_API;
import static com.contentful.tea.java.services.http.Constants.NAME_DELIVERY_TOKEN;
import static com.contentful.tea.java.services.http.Constants.NAME_LOCALE;
import static com.contentful.tea.java.services.http.Constants.NAME_PREVIEW_TOKEN;
import static com.contentful.tea.java.services.http.Constants.NAME_SPACE_ID;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainController.class)
public class SessionParserTest {

  @Autowired
  @SuppressWarnings("unused")
  private Settings settings;

  @Autowired
  @SuppressWarnings("unused")
  private SessionParser parser;

  @After
  public void teardown() {
    settings.reset();
  }

  @Test
  public void parserLoadsSession() {
    final HttpSession session = new MockHttpSession();

    session.setAttribute(NAME_API, Settings.API_CDA);
    session.setAttribute(NAME_SPACE_ID, "spaceId");
    session.setAttribute(NAME_LOCALE, "locale");
    session.setAttribute(NAME_DELIVERY_TOKEN, "cdaToken");
    session.setAttribute(NAME_PREVIEW_TOKEN, "cpaToken");

    parser.loadSession(session);

    assertThat(settings.getApi()).isEqualTo(Settings.API_CDA);
    assertThat(settings.getSpaceId()).isEqualTo("spaceId");
    assertThat(settings.getLocale()).isEqualTo("locale");
    assertThat(settings.getDeliveryAccessToken()).isEqualTo("cdaToken");
    assertThat(settings.getPreviewAccessToken()).isEqualTo("cpaToken");
  }

  @Test
  public void parserSavesSession() {
    settings
        .setApi(Settings.API_CPA)
        .setSpaceId("spaceId")
        .setLocale("locale")
        .setDeliveryAccessToken("cdaToken")
        .setPreviewAccessToken("cpaToken");

    final HttpSession session = new MockHttpSession();
    parser.saveSession(session);

    assertThat(session.getAttributeNames()).isNotNull();
    Map<String, Object> attributes = extractAttributesIntoMap(session);

    final String[] names = attributes.keySet().toArray(new String[attributes.size()]);
    assertThat(names)
        .containsExactlyInAnyOrder(NAME_API, NAME_DELIVERY_TOKEN, NAME_PREVIEW_TOKEN, NAME_SPACE_ID, NAME_LOCALE);

    final Object[] values = attributes.values().toArray();
    assertThat(values)
        .containsExactlyInAnyOrder("spaceId", "locale", "cdaToken", "cpaToken", Settings.API_CPA);
  }

  private Map<String, Object> extractAttributesIntoMap(HttpSession session) {
    final Map<String, Object> map = new HashMap<>();

    final Enumeration<String> names = session.getAttributeNames();
    for (; names.hasMoreElements(); ) {
      final String name = names.nextElement();
      final Object value = session.getAttribute(name);
      map.put(name, value);
    }

    return map;
  }
}