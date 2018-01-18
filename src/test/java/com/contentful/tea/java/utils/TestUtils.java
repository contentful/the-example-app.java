package com.contentful.tea.java.utils;

import com.contentful.tea.java.models.base.BaseParameter;
import com.contentful.tea.java.models.base.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtils {
  public static BaseParameter createBaseParameter() {
    final BaseParameter base = new BaseParameter();
    base.getApi()
        .setCdaButtonCSSClass("TEST-setCdaButtonCSSClass")
        .setCpaButtonCSSClass("TEST-setCpaButtonCSSClass")
        .setCurrentApiId("TEST-setCurrentApiId")
    ;

    base.getMeta()
        .setAllCoursesCssClass("TEST-setAllCoursesCSSClass")
        .setCoursesCSSClass("TEST-setCoursesCSSClass")
        .setCurrentPath("/TEST-setCurrentPath")
        .setHomeCSSClass("TEST-setHomeCSSClass")
        .setQueryString("TEST-setQueryString")
        .setTitle("TEST-setTitle")
        .setUpperMenuCSSClass("TEST-setUpperMenuCSSClass")
    ;

    base.getLocales()
        .setCurrentLocaleCode("TEST-setCurrentLocaleCode")
        .setCurrentLocaleName("TEST-setCurrentLocaleName")
        .addLocale(
            new Locale()
                .setCode("en-US")
                .setName("\uD83C\uDDFA\uD83C\uDDF8")
                .setCssClass("inactive")
        )
        .addLocale(
            new Locale()
                .setCode("de-DE")
                .setName("\uD83C\uDDE9\uD83C\uDDEA")
                .setCssClass("active")
        );
    return base;
  }

  public static void assertBaseParameterInHtml(String generatedHtml) {
    assertThat(generatedHtml)
        .doesNotContain("\uD83D\uDE31")
        .doesNotContain("!{")
        .doesNotContain("#{")
        .contains("<meta property=\"og:url\" content=\"http://contentful-example-app-java.herokuapp.com/TEST-setCurrentPath\">")
        .contains("\uD83C\uDDE9\uD83C\uDDEA")
        .contains("\uD83C\uDDFA\uD83C\uDDF8")
        .contains("active")
        .contains("de-DE")
        .contains("inactive")
        .contains("TEST-setCdaButtonCSSClass")
        .contains("TEST-setCoursesCSSClass")
        .contains("TEST-setCpaButtonCSSClass")
        .contains("TEST-setCurrentApiId")
        .contains("TEST-setCurrentLocaleCode")
        .contains("TEST-setCurrentLocaleName")
        .contains("/TEST-setCurrentPath")
        .contains("TEST-setHomeCSSClass")
        .contains("TEST-setQueryString")
        .contains("TEST-setUpperMenuCSSClass")
    ;
  }
}
