package com.contentful.tea.java.services;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDALocale;
import com.contentful.java.cda.CDAResource;
import com.contentful.java.cda.QueryOperation;
import com.contentful.tea.java.markdown.MarkdownParser;
import com.contentful.tea.java.models.base.AnalyticsParameter;
import com.contentful.tea.java.models.base.BaseParameter;
import com.contentful.tea.java.models.base.BreadcrumbParameter;
import com.contentful.tea.java.models.base.Locale;
import com.contentful.tea.java.models.base.LocalesParameter;
import com.contentful.tea.java.services.contentful.Contentful;
import com.contentful.tea.java.services.http.UrlParameterParser;
import com.contentful.tea.java.services.localization.Keys;
import com.contentful.tea.java.services.localization.Localizer;
import com.contentful.tea.java.services.settings.Settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.contentful.tea.java.services.contentful.Contentful.API_CDA;
import static com.contentful.tea.java.services.contentful.Contentful.API_CPA;
import static java.lang.String.format;

@Component
public class StaticContentSetter {
  private static final String idRegex = "[-_a-zA-Z0-9]+";
  private static final String CSS_CLASS_ACTIVE_BUTTON = "header__controls_button--active";

  @Autowired
  @SuppressWarnings("unused")
  private Localizer localizer;

  @Autowired
  @SuppressWarnings("unused")
  private MarkdownParser markdown;

  @Autowired
  @SuppressWarnings("unused")
  private Contentful contentful;

  @Autowired
  @SuppressWarnings("unused")
  private Settings settings;

  @Autowired
  @SuppressWarnings("unused")
  private UrlParameterParser urlParameterParser;

  public void applyContent(BaseParameter base) {
    updateLabels(base);
    updateBreadcrumbs(base);
    updateApis(base);
    updateHeadersAndTitle(base);
    updateLocales(base);
  }

  public void applyErrorContent(BaseParameter base) {
    updateLabels(base);
    updateApis(base);
    updateHeadersAndTitle(base);
  }

  private void updateLabels(BaseParameter base) {
    base.getLabels()
        .setAllCoursesLabel(t(Keys.allCoursesLabel))
        .setApiSwitcherHelp(t(Keys.apiSwitcherHelp))
        .setCategoriesLabel(t(Keys.categoriesLabel))
        .setComingSoonLabel(t(Keys.comingSoonLabel))
        .setContactUsLabel(t(Keys.contactUsLabel))
        .setContentDeliveryApiHelp(t(Keys.contentDeliveryApiHelp))
        .setContentDeliveryApiLabel(t(Keys.contentDeliveryApiLabel))
        .setContentPreviewApiHelp(t(Keys.contentPreviewApiHelp))
        .setContentPreviewApiLabel(t(Keys.contentPreviewApiLabel))
        .setCoursesLabel(t(Keys.coursesLabel))
        .setCurrentApiLabel(t(cpaOrCdaApiLabel()))
        .setDescription(t(Keys.metaDescription))
        .setDraftLabel(t(Keys.draftLabel))
        .setEditInWebAppLabel(t(Keys.editInTheWebAppLabel))
        .setEditorialFeaturesHint(t(Keys.editorialFeaturesHint))
        .setFooterDisclaimer(t(Keys.footerDisclaimer))
        .setHomeLabel(t(Keys.homeLabel))
        .setHostedLabel(t(Keys.hostedLabel))
        .setImageAlt(t(Keys.metaImageAlt))
        .setImageDescription(t(Keys.metaImageDescription))
        .setImprintLabel(t(Keys.imprintLabel))
        .setLocaleLabel(t(Keys.locale))
        .setLocaleQuestion(t(Keys.localeQuestion))
        .setLogoAlt(t(Keys.logoAlt))
        .setModalCTALabel(t(Keys.modalCTALabel))
        .setModalIntro(t(Keys.modalIntroJava))
        .setModalPlatforms(t(Keys.modalPlatforms))
        .setModalSpaceIntro(t(Keys.modalSpaceIntro))
        .setModalSpaceLinkLabel(t(Keys.modalSpaceLinkLabel))
        .setModalTitle(t(Keys.modalTitleJava))
        .setPendingChangesLabel(t(Keys.pendingChangesLabel))
        .setSettingsLabel(t(Keys.settingsLabel))
        .setTwitterCard(t(Keys.metaTwitterCard))
        .setViewCourseLabel(t(Keys.viewCourseLabel))
        .setViewOnGitHub(t(Keys.viewOnGithub))
        .setWhatIsThisApp(t(Keys.whatIsThisApp))
        .setNoContentLabel(t(Keys.noContentLabel))
    ;
  }

  private Keys cpaOrCdaApiLabel() {
    return Objects.equals(contentful.getApi(), API_CPA) ? Keys.contentPreviewApiLabel : Keys.contentDeliveryApiLabel;
  }

  private void updateApis(BaseParameter base) {
    if (API_CDA.equals(contentful.getApi())) {
      base.getApi()
          .setCurrentApiId(contentful.getApi())
          .setCpaButtonCSSClass("")
          .setCdaButtonCSSClass(CSS_CLASS_ACTIVE_BUTTON)
      ;
    } else {
      base.getApi()
          .setCurrentApiId(contentful.getApi())
          .setCpaButtonCSSClass(CSS_CLASS_ACTIVE_BUTTON)
          .setCdaButtonCSSClass("")
      ;
    }
  }

  private void updateHeadersAndTitle(BaseParameter base) {
    base.getMeta()
        .setQueryString(settings.getQueryString())
        .setCurrentPath(settings.getPath())
        .setAllPlatformsQueryString(getAllPlatformsQueryString())
        .setAnalytics(hasAnalytics() ? new AnalyticsParameter().setSpaceId(contentful.getSpaceId()) : null)
    ;

    if (settings.getPath() != null && settings.getPath().length() > 0) {
      base.getMeta()
          .setUpperMenuCSSClass(settings.getPath().startsWith("/settings") ? "active" : "")
          .setCoursesCSSClass(settings.getPath().startsWith("/courses") ? "active" : "")
          .setHomeCSSClass(settings.getPath().equals("/") ? "active" : "");
    } else {
      base
          .getMeta()
          .setHomeCSSClass("active");
    }
  }

  private String getAllPlatformsQueryString() {
    if (contentful.isUsingCustomCredentials()) {
      return urlParameterParser.appToUrlParameter();
    } else {
      return "";
    }
  }

  private boolean hasAnalytics() {
    return contentful.runsOnProduction();
  }

  private void updateLocales(BaseParameter base) {
    final CDAClient client = contentful.getCurrentClient();
    final CDAArray locales = client.fetch(CDALocale.class).all();

    final LocalesParameter localesParameter = base.getLocales();
    localesParameter
        .setCurrentLocaleCode(settings.getLocale());

    for (final CDAResource resource : locales.items()) {
      final CDALocale locale = (CDALocale) resource;
      final Locale localeParameter = new Locale();
      localeParameter
          .setCode(locale.code())
          .setName(locale.name());

      if (locale.code().equals(settings.getLocale())) {
        localeParameter.setCssClass(Locale.CSS_CLASS_ACTIVE);
        localesParameter.setCurrentLocaleName(locale.name());
      } else {
        localeParameter.setCssClass("");
      }

      localesParameter.addLocale(localeParameter);
    }
  }

  private void updateBreadcrumbs(BaseParameter base) {
    final String path = settings.getPath();
    if (path == null || path.isEmpty() || path.equals("/")) {
      return;
    }

    final BreadcrumbParameter breadcrumb = base.getBreadcrumb();
    breadcrumb.getBreadcrumbs().clear();
    breadcrumb.addBreadcrumb(new BreadcrumbParameter.Breadcrumb()
        .setUrl("/")
        .setLabel(t(Keys.homeLabel))
    );

    if (path.startsWith("/courses")) {
      addCoursesBreadcrumbs(base, path, breadcrumb);
    } else if (path.startsWith("/settings")) {
      addSettingsBreadCrumbs(breadcrumb);
    } else if (path.startsWith("/imprint")) {
      andAddImprintBreadCrumbs(breadcrumb);
    }
  }

  private void addCoursesBreadcrumbs(BaseParameter base, String path, BreadcrumbParameter breadcrumb) {
    checkAndAddCoursesBreadcrumb(path, breadcrumb);
    checkAndAddSpecificCourseBreadcrumb(base, path, breadcrumb);
    checkAndAddSpecificLesson(base, path, breadcrumb);
    checkAndAddCategoriesList(path, breadcrumb);
    checkAndAddSpecificCategory(base, path, breadcrumb);
  }

  private void addSettingsBreadCrumbs(BreadcrumbParameter breadcrumb) {
    breadcrumb.addBreadcrumb(
        new BreadcrumbParameter.Breadcrumb()
            .setLabel(t(Keys.settingsLabel))
            .setUrl("/settings")
    );
  }

  private void andAddImprintBreadCrumbs(BreadcrumbParameter breadcrumb) {
    breadcrumb.addBreadcrumb(
        new BreadcrumbParameter.Breadcrumb()
            .setLabel(t(Keys.imprintLabel))
            .setUrl("/imprint")
    );
  }


  private void checkAndAddSpecificCategory(BaseParameter base, String path, BreadcrumbParameter breadcrumb) {
    if (path.matches("/courses/categories/" + idRegex)) {
      breadcrumb.addBreadcrumb(
          new BreadcrumbParameter.Breadcrumb()
              .setUrl(path)
              .setLabel(base.getMeta().getTitle().replaceAll(" \\([0-9]+\\)", ""))
      );
    }
  }

  private void checkAndAddCategoriesList(String path, BreadcrumbParameter breadcrumb) {
    if (path.startsWith("/courses/categories")) {
      breadcrumb.addBreadcrumb(
          new BreadcrumbParameter.Breadcrumb()
              .setUrl("/courses/categories")
              .setLabel(t(Keys.categoriesLabel))
      );
    }
  }

  private void checkAndAddSpecificLesson(BaseParameter base, String path, BreadcrumbParameter breadcrumb) {
    if (path.matches("/courses/" + idRegex + "/lessons(/" + idRegex + ")?")) {
      final String[] split = path.split("/");

      final String courseSlug = split[2];
      breadcrumb.addBreadcrumb(
          new BreadcrumbParameter.Breadcrumb()
              .setLabel(slugToTitle(courseSlug))
              .setUrl(format("/courses/%s", courseSlug)),
          new BreadcrumbParameter.Breadcrumb()
              .setLabel(t(Keys.lessonsLabel))
              .setUrl(format("/courses/%s/lessons", courseSlug))
      );

      if (split.length == 5) {
        breadcrumb.addBreadcrumb(
            new BreadcrumbParameter.Breadcrumb()
                .setLabel(base.getMeta().getTitle())
                .setUrl(path)
        );
      }
    }
  }

  private void checkAndAddSpecificCourseBreadcrumb(BaseParameter base, String path, BreadcrumbParameter breadcrumb) {
    if (path.matches("/courses/" + idRegex)
        && !path.startsWith("/courses/categories")) {
      breadcrumb.addBreadcrumb(
          new BreadcrumbParameter.Breadcrumb()
              .setUrl(path)
              .setLabel(base.getMeta().getTitle())
      );
    }
  }

  private void checkAndAddCoursesBreadcrumb(String path, BreadcrumbParameter breadcrumb) {
    if (path.startsWith("/courses")) {
      breadcrumb.addBreadcrumb(
          new BreadcrumbParameter.Breadcrumb()
              .setUrl("/courses")
              .setLabel(t(Keys.coursesLabel))
      );
    }
  }

  private String slugToTitle(String courseSlug) {
    final CDAArray courses = contentful.getCurrentClient()
        .fetch(CDAEntry.class)
        .withContentType("course")
        .where("fields.slug", QueryOperation.IsEqualTo, courseSlug)
        .where("locale", settings.getLocale())
        .all();

    if (courses.total() == 1) {
      CDAEntry course = (CDAEntry) courses.items().get(0);
      return course.getField("title");
    } else {
      if (courses.total() == 0) {
        throw new IllegalStateException(format("Course with slug '%s' not found.", courseSlug));
      } else {
        throw new IllegalStateException(format("More then one course with the slug of '%s' found …", courseSlug));
      }
    }
  }

  private String t(Keys key) {
    return localizer.localize(key);
  }

  private String m(String s) {
    return markdown.parse(s);
  }
}
