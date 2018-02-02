package com.contentful.tea.java.utils;

import com.contentful.tea.java.models.base.AnalyticsParameter;
import com.contentful.tea.java.models.base.BaseParameter;
import com.contentful.tea.java.models.base.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtils {
  public static void createBaseParameter(BaseParameter base) {
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
        .setDeeplinkToContentful("TEST-setDeeplinkToContentful")
        .setAllPlatformsQueryString("TEST-allPlatformsQueryString")
        .setAnalytics(new AnalyticsParameter().setSpaceId("TEST-spaceID"))
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

    base
        .getLabels()
        .setAllCoursesLabel("TEST-setAllCoursesLabel")
        .setApiSwitcherHelp("TEST-setApiSwitcherHelp")
        .setCategoriesLabel("TEST-setCategoriesLabel")
        .setComingSoonLabel("TEST-setComingSoonLabel")
        .setContactUsLabel("TEST-setContactUsLabel")
        .setContentDeliveryApiHelp("TEST-setContentDeliveryApiHelp")
        .setContentDeliveryApiLabel("TEST-setContentDeliveryApiLabel")
        .setContentPreviewApiHelp("TEST-setContentPreviewApiHelp")
        .setContentPreviewApiLabel("TEST-setContentPreviewApiLabel")
        .setCoursesLabel("TEST-setCoursesLabel")
        .setCurrentApiLabel("TEST-setCurrentApiLabel")
        .setDescription("TEST-setDescription")
        .setDraftLabel("TEST-setDraftLabel")
        .setEditInWebAppLabel("TEST-setEditInWebAppLabel")
        .setEditorialFeaturesHint("TEST-setEditorialFeaturesHint")
        .setFooterDisclaimer("TEST-setFooterDisclaimer")
        .setHomeLabel("TEST-setHomeLabel")
        .setHostedLabel("TEST-setHostedLabel")
        .setImageAlt("TEST-setImageAlt")
        .setImageDescription("TEST-setImageDescription")
        .setImprintLabel("TEST-setImprintLabel")
        .setLocaleLabel("TEST-setLocaleLabel")
        .setLocaleQuestion("TEST-setLocaleQuestion")
        .setLogoAlt("TEST-setLogoAlt")
        .setModalCTALabel("TEST-setModalCTALabel")
        .setModalIntro("TEST-setModalIntro")
        .setModalPlatforms("TEST-setModalPlatforms")
        .setModalSpaceIntro("TEST-setModalSpaceIntro")
        .setModalSpaceLinkLabel("TEST-setModalSpaceLinkLabel")
        .setModalTitle("TEST-setModalTitle")
        .setPendingChangesLabel("TEST-setPendingChangesLabel")
        .setSettingsLabel("TEST-setSettingsLabel")
        .setTwitterCard("TEST-setTwitterCard")
        .setViewCourseLabel("TEST-setViewCourseLabel")
        .setViewOnGitHub("TEST-setViewOnGitHub")
        .setWhatIsThisApp("TEST-setWhatIsThisApp")
    ;
  }

  public static void assertBaseParameterInHtml(String generatedHtml) {
    assertThat(generatedHtml)
        .doesNotContain("\uD83D\uDE31")
        .doesNotContain("!{")
        .doesNotContain("#{")
        .contains("<meta property=\"og:url\" content=\"http://contentful-example-app-java.contentful.com/TEST-setCurrentPath\">")
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
        .contains("TEST-spaceID")
    ;
  }
}
