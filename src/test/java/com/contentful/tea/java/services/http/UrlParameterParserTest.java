package com.contentful.tea.java.services.http;

import com.contentful.tea.java.MainController;
import com.contentful.tea.java.services.contentful.Contentful;
import com.contentful.tea.java.services.settings.Settings;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainController.class)
public class UrlParameterParserTest {
  @MockBean
  @SuppressWarnings("unused")
  private Settings settings;

  @MockBean
  @SuppressWarnings("unused")
  private Contentful contentful;

  @Autowired
  @SuppressWarnings("unused")
  private UrlParameterParser parser;

  @Test
  public void klingonLocaleGetsSavedAsUrlParameter() {
    given(settings.getLocale()).willReturn("tlh");

    final String subject = parser.appToUrlParameter();

    assertThat(subject).isEqualTo("?locale=tlh");
  }

  @Test
  public void twoParametersGetSavedAsUrl() {
    given(settings.getLocale()).willReturn("tlh");
    given(contentful.getApi()).willReturn("rest");

    final String subject = parser.appToUrlParameter();

    assertThat(subject).isEqualTo("?api=rest&locale=tlh");
  }

  @Test
  public void allParametersGetSavedAndNotMore() {
    given(settings.getLocale()).willReturn("tlh");
    given(settings.areEditorialFeaturesEnabled()).willReturn(true);
    given(contentful.getApi()).willReturn("rest");
    given(contentful.getDeliveryAccessToken()).willReturn("delivery");
    given(contentful.getPreviewAccessToken()).willReturn("preview");
    given(contentful.getSpaceId()).willReturn("space");

    final String subject = parser.appToUrlParameter();

    assertThat(subject).isEqualTo("?preview_token=preview&delivery_token=delivery&editorial_features=enabled&api=rest&locale=tlh&space_id=space");
  }

  @Test
  public void editorialFeaturesCanGetDisabled() {
    given(settings.areEditorialFeaturesEnabled()).willReturn(false);

    final String subject = parser.appToUrlParameter();

    assertThat(subject).isEqualTo("");
  }

  @Test
  public void editorialFeaturesCanGetEnabled() {
    given(settings.areEditorialFeaturesEnabled()).willReturn(true);

    final String subject = parser.appToUrlParameter();

    assertThat(subject).isEqualTo("?editorial_features=enabled");
  }
}