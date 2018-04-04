package com.contentful.tea.java.services.http;

import com.contentful.tea.java.MainController;
import com.contentful.tea.java.services.contentful.Contentful;
import com.contentful.tea.java.services.settings.Settings;

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

import static com.contentful.tea.java.services.http.Constants.NAME_DELIVERY_TOKEN;
import static com.contentful.tea.java.services.http.Constants.NAME_EDITORIAL_FEATURES;
import static com.contentful.tea.java.services.http.Constants.NAME_PREVIEW_TOKEN;
import static com.contentful.tea.java.services.http.Constants.NAME_SPACE_ID;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainController.class)
public class SessionParserTest {

  @Autowired
  @SuppressWarnings("unused")
  private Contentful contentful;

  @Autowired
  @SuppressWarnings("unused")
  private Settings settings;

  @Autowired
  @SuppressWarnings("unused")
  private SessionParser parser;

  @After
  public void teardown() {
    contentful.reset();
    settings.reset();
  }

  @Test
  public void parserLoadsSession() {
    final HttpSession session = new MockHttpSession();

    session.setAttribute(NAME_SPACE_ID, "spaceId");
    session.setAttribute(NAME_DELIVERY_TOKEN, "cdaToken");
    session.setAttribute(NAME_PREVIEW_TOKEN, "cpaToken");

    parser.loadFromSession(session);

    assertThat(contentful.getSpaceId()).isEqualTo("spaceId");
    assertThat(contentful.getDeliveryAccessToken()).isEqualTo("cdaToken");
    assertThat(contentful.getPreviewAccessToken()).isEqualTo("cpaToken");
  }

  @Test
  public void parserSavesSession() {
    contentful
        .setSpaceId("spaceId")
        .setDeliveryAccessToken("cdaToken")
        .setPreviewAccessToken("cpaToken");

    final HttpSession session = new MockHttpSession();
    parser.saveToSession(session);

    assertThat(session.getAttributeNames()).isNotNull();
    Map<String, Object> attributes = extractAttributesIntoMap(session);

    final String[] names = attributes.keySet().toArray(new String[attributes.size()]);
    assertThat(names)
        .containsExactlyInAnyOrder(NAME_DELIVERY_TOKEN, NAME_PREVIEW_TOKEN, NAME_SPACE_ID, NAME_EDITORIAL_FEATURES);

    final Object[] values = attributes.values().toArray();
    assertThat(values)
        .containsExactlyInAnyOrder("spaceId", "cdaToken", "cpaToken", "disabled");
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