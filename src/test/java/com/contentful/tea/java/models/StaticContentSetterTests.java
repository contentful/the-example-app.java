package com.contentful.tea.java.models;

import com.contentful.tea.java.MainController;
import com.contentful.tea.java.models.base.BaseParameter;
import com.contentful.tea.java.models.courses.CoursesParameter;
import com.contentful.tea.java.models.landing.LandingPageParameter;
import com.contentful.tea.java.services.StaticContentSetter;
import com.contentful.tea.java.services.contentful.Contentful;
import com.contentful.tea.java.services.settings.Settings;
import com.contentful.tea.java.utils.http.EnqueueHttpResponse;
import com.contentful.tea.java.utils.http.EnqueuedHttpResponseTests;

import org.junit.After;
import org.junit.Before;
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
public class StaticContentSetterTests extends EnqueuedHttpResponseTests {

  @Autowired
  @SuppressWarnings("unused")
  private StaticContentSetter setter;

  @Autowired
  @SuppressWarnings("unused")
  private Settings settings;

  @MockBean
  @SuppressWarnings("unused")
  private Contentful contentful;

  @Before
  public void setup() {
    given(this.contentful.getCurrentClient()).willReturn(client);
    given(this.contentful.getApi()).willReturn(Contentful.API_CDA);
  }

  @After
  public void teardown() {
    contentful.reset();
  }

  @After
  public void tearDown() {
    contentful.reset();
    settings.reset();
  }

  @Test
  @EnqueueHttpResponse({"defaults/locales.json", "home/main.json"})
  public void homeRouteSetsStaticContent() {
    settings.setPath("/");
    settings.setQueryString("");

    final LandingPageParameter p = new LandingPageParameter();
    p.getBase().getMeta().setTitle("foo");
    setter.applyContent(p.getBase());

    assertThat(p.getBase().getMeta().getCoursesCSSClass()).isEqualTo("");
    assertThat(p.getBase().getMeta().getCurrentPath()).isEqualTo("/");
    assertThat(p.getBase().getMeta().getHomeCSSClass()).isEqualTo("active");
    assertThat(p.getBase().getMeta().getQueryString()).isEqualTo("");
    assertThat(p.getBase().getMeta().getTitle()).isEqualTo("foo");
    assertThat(p.getBase().getMeta().getUpperMenuCSSClass()).isEqualTo("");

    assertThat(p.getBase().getApi().getCdaButtonCSSClass()).isEqualTo("header__controls_button--active");
    assertThat(p.getBase().getApi().getCpaButtonCSSClass()).isEqualTo("");
    assertThat(p.getBase().getApi().getCurrentApiId()).isEqualTo("cda");

    assertThat(p.getBase().getLocales().getCurrentLocaleCode()).isEqualTo("en-US");
    assertThat(p.getBase().getLocales().getCurrentLocaleName()).isEqualTo("U.S. English");
    assertThat(p.getBase().getLocales().getLocales()).hasSize(2);
    assertThat(p.getBase().getLocales().getLocales().get(0).getCode()).isEqualTo("de-DE");
    assertThat(p.getBase().getLocales().getLocales().get(0).getName()).isEqualTo("German (Germany)");
    assertThat(p.getBase().getLocales().getLocales().get(0).getCssClass()).isEqualTo("");
    assertThat(p.getBase().getLocales().getLocales().get(1).getCode()).isEqualTo("en-US");
    assertThat(p.getBase().getLocales().getLocales().get(1).getName()).isEqualTo("U.S. English");
    assertThat(p.getBase().getLocales().getLocales().get(1).getCssClass()).isEqualTo("header__controls_button--active");
  }

  @Test
  @EnqueueHttpResponse({"defaults/locales.json", "home/main.json"})
  public void nonDefaultSettingsResultInQueryStringForAllApps() {
    given(contentful.isUsingCustomCredentials()).willReturn(true);
    given(contentful.getSpaceId()).willReturn("ThisIsNotASpaceId");

    final BaseParameter base = new BaseParameter();
    setter.applyContent(base);

    assertThat(base.getMeta().getAllPlatformsQueryString()).contains("space_id=ThisIsNotASpaceId");
  }

  @Test
  @EnqueueHttpResponse({"defaults/locales.json", "home/main.json"})
  public void homeRouteSetsStaticContentInGerman() {
    settings.setPath("/");
    settings.setQueryString("");
    settings.setLocale("de-DE");

    final LandingPageParameter p = new LandingPageParameter();
    setter.applyContent(p.getBase());

    assertThat(p.getBase().getMeta().getCoursesCSSClass()).isEqualTo("");
    assertThat(p.getBase().getMeta().getCurrentPath()).isEqualTo("/");
  }

  @Test
  @EnqueueHttpResponse({"defaults/locales.json", "models/courses.json"})
  public void coursesRouteTest() {
    settings.setPath("/courses");
    settings.setQueryString("");

    final CoursesParameter p = new CoursesParameter();
    setter.applyContent(p.getBase());

    assertThat(p.getBase().getMeta().getCoursesCSSClass()).isEqualTo("active");
  }
}
