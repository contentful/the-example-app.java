package com.contentful.tea.java.models;

import com.contentful.tea.java.MainController;
import com.contentful.tea.java.models.courses.CoursesParameter;
import com.contentful.tea.java.models.landing.LandingPageParameter;
import com.contentful.tea.java.services.StaticContentSetter;
import com.contentful.tea.java.utils.http.EnqueueHttpResponse;
import com.contentful.tea.java.utils.http.EnqueuedHttpResponseTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainController.class)
public class StaticContentSetterTests extends EnqueuedHttpResponseTests {

  @Autowired
  @SuppressWarnings("unused")
  private StaticContentSetter setter;

  @Autowired
  @SuppressWarnings("unused")
  private Settings settings;

  @Before
  public void setup() {
    settings.setSpaceId("jnzexv31feqf");
    settings.setDeliveryAccessToken("<DELIVERY_TOKEN>");

    settings.contentfulDeliveryClient = client;
  }

  @After
  public void teardown() {
    settings.reset();
  }

  @Test
  @EnqueueHttpResponse({"home/main.json", "defaults/space.json"})
  public void homeRouteSetsStaticContent() {
    settings.setPath("/");
    settings.setQueryString("");

    final LandingPageParameter p = new LandingPageParameter();
    p.getBase().getMeta().setTitle("foo");
    setter.applyContent(p.getBase());

    assertThat(p.getBase().getMeta().getContactUsLabel()).isEqualTo("Contact us");
    assertThat(p.getBase().getMeta().getCoursesCSSClass()).isEqualTo("");
    assertThat(p.getBase().getMeta().getCoursesLabel()).isEqualTo("Courses");
    assertThat(p.getBase().getMeta().getCurrentPath()).isEqualTo("/");
    assertThat(p.getBase().getMeta().getDescription()).isEqualTo("This is \"The Example App\", a reference for building your own applications using Contentful.");
    assertThat(p.getBase().getMeta().getDraftLabel()).isEqualTo("draft");
    assertThat(p.getBase().getMeta().getFooterDisclaimer()).isEqualTo("Powered by Contentful. This website and the materials found on it are for demo purposes. You can use this to preview the content created on your Contentful account.");
    assertThat(p.getBase().getMeta().getHomeCSSClass()).isEqualTo("active");
    assertThat(p.getBase().getMeta().getHomeLabel()).isEqualTo("Home");
    assertThat(p.getBase().getMeta().getImageAlt()).isEqualTo("This is \"The Example App\", a reference for building your own applications using Contentful.");
    assertThat(p.getBase().getMeta().getImageDescription()).isEqualTo("This is \"The Example App\", a reference for building your own applications using Contentful.");
    assertThat(p.getBase().getMeta().getImprintLabel()).isEqualTo("Imprint");
    assertThat(p.getBase().getMeta().getLogoAlt()).isEqualTo("Contentful Example App");
    assertThat(p.getBase().getMeta().getModalCTALabel()).isEqualTo("Ok, got it.");
    assertThat(p.getBase().getMeta().getModalIntro()).isEqualTo("This is \"The Node.js Example App\". While building your own apps with Contentful, you can reference this app's code, found on");
    assertThat(p.getBase().getMeta().getModalSpaceIntro()).isEqualTo("You can also edit the content in the app by cloning the Contentful space to your own Contentful account by following the instructions");
    assertThat(p.getBase().getMeta().getModalSpaceLinkLabel()).isEqualTo("here");
    assertThat(p.getBase().getMeta().getModalTitle()).isEqualTo("A reference for Node.js developers using Contentful");
    assertThat(p.getBase().getMeta().getPendingChangesLabel()).isEqualTo("pending changes");
    assertThat(p.getBase().getMeta().getQueryString()).isEqualTo("");
    assertThat(p.getBase().getMeta().getSettingsLabel()).isEqualTo("Settings");
    assertThat(p.getBase().getMeta().getTitle()).isEqualTo("foo");
    assertThat(p.getBase().getMeta().getTwitterCard()).isEqualTo("This is \"The Example App\", a reference for building your own applications using Contentful.");
    assertThat(p.getBase().getMeta().getUpperMenuCSSClass()).isEqualTo("");
    assertThat(p.getBase().getMeta().getViewOnGitHub()).isEqualTo("View on GitHub");
    assertThat(p.getBase().getMeta().getWhatIsThisApp()).isEqualTo("Help");

    assertThat(p.getBase().getApi().getApiSwitcherHelp()).isEqualTo("View the published or draft content by simply switching between the Deliver and Preview APIs.");
    assertThat(p.getBase().getApi().getCdaButtonCSSClass()).isEqualTo("header__controls_button--active");
    assertThat(p.getBase().getApi().getContentDeliveryApiHelp()).isEqualTo("This API fetches published content from the Content Delivery API");
    assertThat(p.getBase().getApi().getContentDeliveryApiLabel()).isEqualTo("Content Delivery API");
    assertThat(p.getBase().getApi().getContentPreviewApiHelp()).isEqualTo("This API fetches un-published content from the Content Preview API");
    assertThat(p.getBase().getApi().getContentPreviewApiLabel()).isEqualTo("Content Preview API");
    assertThat(p.getBase().getApi().getCpaButtonCSSClass()).isEqualTo("");
    assertThat(p.getBase().getApi().getCurrentApiId()).isEqualTo("cda");
    assertThat(p.getBase().getApi().getCurrentApiLabel()).isEqualTo("Content Delivery API access token");

    assertThat(p.getBase().getLocales().getCurrentLocaleCode()).isEqualTo("en-US");
    assertThat(p.getBase().getLocales().getCurrentLocaleName()).isEqualTo("U.S. English");
    assertThat(p.getBase().getLocales().getLocaleQuestion()).isEqualTo("Working with multiple languages? You can query the Content Delivery API for a specific locale.");
    assertThat(p.getBase().getLocales().getLocales()).hasSize(2);
    assertThat(p.getBase().getLocales().getLocales().get(0).getCode()).isEqualTo("en-US");
    assertThat(p.getBase().getLocales().getLocales().get(0).getName()).isEqualTo("U.S. English");
    assertThat(p.getBase().getLocales().getLocales().get(0).getCssClass()).isEqualTo("header__controls_button--active");
    assertThat(p.getBase().getLocales().getLocales().get(1).getCode()).isEqualTo("de-DE");
    assertThat(p.getBase().getLocales().getLocales().get(1).getName()).isEqualTo("German (Germany)");
    assertThat(p.getBase().getLocales().getLocales().get(1).getCssClass()).isEqualTo("");
  }

  @Test
  @EnqueueHttpResponse({"home/main.json", "defaults/space.json"})
  public void homeRouteSetsStaticContentInGerman() {
    settings.setPath("/");
    settings.setQueryString("");
    settings.setLocale("de-DE");

    final LandingPageParameter p = new LandingPageParameter();
    setter.applyContent(p.getBase());

    assertThat(p.getBase().getMeta().getContactUsLabel()).isEqualTo("Kontakt");
    assertThat(p.getBase().getMeta().getCoursesCSSClass()).isEqualTo("");
    assertThat(p.getBase().getMeta().getCoursesLabel()).isEqualTo("Kurse");
    assertThat(p.getBase().getMeta().getCurrentPath()).isEqualTo("/");
    assertThat(p.getBase().getMeta().getDescription()).isEqualTo("Dies ist die Beispielanwendung, eine Anwendung die Ihnen hilft Ihre eigene Anwendung mit Contentful zu bauen.");
    assertThat(p.getBase().getMeta().getDraftLabel()).isEqualTo("Entwurf");
    assertThat(p.getBase().getMeta().getFooterDisclaimer()).isEqualTo("Powered by Contentful. Diese Website und deren Materialien existieren nur für Demonstrationszwecken. Sie können diese benutzen, um den Inhalt ihres Contentful Kontos anzusehen.");
  }

  @Test
  @EnqueueHttpResponse({"models/courses.json", "defaults/space.json"})
  public void coursesRouteTest() {
    settings.setPath("/courses");
    settings.setQueryString("");

    final CoursesParameter p = new CoursesParameter();
    setter.applyContent(p.getBase());

    assertThat(p.getBase().getMeta().getCoursesLabel()).isEqualTo("Courses");
    assertThat(p.getBase().getMeta().getCoursesCSSClass()).isEqualTo("active");
    assertThat(p.getBase().getMeta().getCategoriesLabel()).isEqualTo("Categories");
  }
}
