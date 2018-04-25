package com.contentful.tea.java.models.base;

import com.contentful.tea.java.MainController;
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
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainController.class)
public class LocalesParameterTest extends EnqueuedHttpResponseTests {

  @Autowired
  @SuppressWarnings("unused")
  private Contentful contentful;

  @Autowired
  @SuppressWarnings("unused")
  private Settings settings;

  @Autowired
  @SuppressWarnings("unused")
  private StaticContentSetter setter;


  @Before
  public void setup() {
    contentful.loadFromPreferences();
    settings.setPath("/").setQueryString("");
  }

  @After
  public void tearDown() {
    contentful.reset();
    settings.reset();
  }

  @Test
  @EnqueueHttpResponse({"defaults/locales.json", "home/main.json"})
  public void staticTextCanBeTranslated() {
    settings.setLocale("de-DE");

    final LandingPageParameter p = new LandingPageParameter();
    p.getBase().getMeta().setTitle("foo");
    setter.applyContent(p.getBase());

    assertThat(p.getBase().getLabels().getContactUsLabel()).isEqualTo("Kontakt");
    assertThat(p.getBase().getLabels().getCoursesLabel()).isEqualTo("Kurse");
    assertThat(p.getBase().getLabels().getDescription()).isEqualTo("Dies ist die Beispielanwendung, eine Anwendung die Ihnen hilft Ihre eigene Anwendung mit Contentful zu bauen.");
    assertThat(p.getBase().getLabels().getDraftLabel()).isEqualTo("Entwurf");
    assertThat(p.getBase().getLabels().getFooterDisclaimer()).isEqualTo("Powered by Contentful. Diese Website und deren Materialien existieren nur für Demonstrationszwecken. Sie können diese benutzen, um den Inhalt ihres Contentful Kontos anzusehen.");
  }

  @Test
  public void staticTextIsSet() {
    final LandingPageParameter p = new LandingPageParameter();
    p.getBase().getMeta().setTitle("foo");
    setter.applyContent(p.getBase());

    assertThat(p.getBase().getLabels().getContactUsLabel()).isEqualTo("Contact us");
    assertThat(p.getBase().getLabels().getCoursesLabel()).isEqualTo("Courses");
    assertThat(p.getBase().getLabels().getDescription()).isEqualTo("This is \"The Example App\", a reference for building your own applications using Contentful.");
    assertThat(p.getBase().getLabels().getDraftLabel()).isEqualTo("draft");
    assertThat(p.getBase().getLabels().getFooterDisclaimer()).isEqualTo("Powered by Contentful. This website and the materials found on it are for demo purposes. You can use this to preview the content created on your Contentful account.");
    assertThat(p.getBase().getLabels().getHomeLabel()).isEqualTo("Home");
    assertThat(p.getBase().getLabels().getImageAlt()).isEqualTo("This is \"The Example App\", a reference for building your own applications using Contentful.");
    assertThat(p.getBase().getLabels().getImageDescription()).isEqualTo("This is \"The Example App\", a reference for building your own applications using Contentful.");
    assertThat(p.getBase().getLabels().getImprintLabel()).isEqualTo("Imprint");
    assertThat(p.getBase().getLabels().getLogoAlt()).isEqualTo("Contentful Example App");
    assertThat(p.getBase().getLabels().getModalCTALabel()).isEqualTo("Ok, got it.");
    assertThat(p.getBase().getLabels().getModalIntro()).isEqualTo("This is \"The Java Example App\". While building your own apps with Contentful, you can reference this app's code, found on");
    assertThat(p.getBase().getLabels().getModalSpaceIntro()).isEqualTo("You can also edit the content in the app by cloning the Contentful space to your own Contentful account by following the instructions");
    assertThat(p.getBase().getLabels().getModalSpaceLinkLabel()).isEqualTo("here");
    assertThat(p.getBase().getLabels().getModalTitle()).isEqualTo("A reference for Java developers using Contentful");
    assertThat(p.getBase().getLabels().getModalPlatforms()).isEqualTo("This app is also available in the following platforms and languages:");
    assertThat(p.getBase().getLabels().getPendingChangesLabel()).isEqualTo("pending changes");
    assertThat(p.getBase().getLabels().getSettingsLabel()).isEqualTo("Settings");
    assertThat(p.getBase().getLabels().getTwitterCard()).isEqualTo("This is \"The Example App\", a reference for building your own applications using Contentful.");
    assertThat(p.getBase().getLabels().getViewOnGitHub()).isEqualTo("View on GitHub");
    assertThat(p.getBase().getLabels().getWhatIsThisApp()).isEqualTo("Help");
    assertThat(p.getBase().getLabels().getApiSwitcherHelp()).isEqualTo("View the published or draft content by simply switching between the Deliver and Preview APIs.");
    assertThat(p.getBase().getLabels().getContentDeliveryApiHelp()).isEqualTo("This API fetches published content from the Content Delivery API");
    assertThat(p.getBase().getLabels().getContentDeliveryApiLabel()).isEqualTo("Content Delivery API");
    assertThat(p.getBase().getLabels().getContentPreviewApiHelp()).isEqualTo("This API fetches un-published content from the Content Preview API");
    assertThat(p.getBase().getLabels().getContentPreviewApiLabel()).isEqualTo("Content Preview API");
    assertThat(p.getBase().getLabels().getCurrentApiLabel()).isEqualTo("Content Delivery API");
    assertThat(p.getBase().getLabels().getLocaleQuestion()).isEqualTo("Working with multiple languages? You can query the Content Delivery API for a specific locale.");
  }

}