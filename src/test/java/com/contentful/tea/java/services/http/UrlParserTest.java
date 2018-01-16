package com.contentful.tea.java.services.http;

import com.contentful.tea.java.MainController;
import com.contentful.tea.java.services.contentful.Contentful;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainController.class)
public class UrlParserTest {

  @Autowired
  @SuppressWarnings("unused")
  private Contentful contentful;

  @Autowired
  @SuppressWarnings("unused")
  private UrlParameterParser parser;

  @After
  public void tearDown() {
    contentful.reset();
  }

  @Test
  public void parsingNoParameterDoesNotChangeSettings() {
    final String before = contentful.toString();

    parser.parseUrlParameter(Collections.emptyMap());

    final String after = contentful.toString();
    assertThat(before).isEqualTo(after);
  }

  @Test
  public void parsingNullParameterDoesNotChangeSettings() {
    final String before = contentful.toString();

    parser.parseUrlParameter(null);

    final String after = contentful.toString();
    assertThat(before).isEqualTo(after);
  }

  @Test
  public void tokenInUrlChangesTokenInSettings() {
    final String before = contentful.getDeliveryAccessToken();

    parser.parseUrlParameter(Collections.singletonMap(Constants.NAME_DELIVERY_TOKEN, new String[]{"cda_token"}));

    final String after = contentful.getDeliveryAccessToken();
    assertThat(before).isNotEqualTo(after);
  }
}