package com.contentful.tea.java.services;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDALocale;
import com.contentful.java.cda.CDASpace;
import com.contentful.java.cda.QueryOperation;
import com.contentful.tea.java.markdown.MarkdownParser;
import com.contentful.tea.java.models.base.BaseParameter;
import com.contentful.tea.java.models.base.BreadcrumbParameter;
import com.contentful.tea.java.models.base.Locale;
import com.contentful.tea.java.models.base.LocalesParameter;
import com.contentful.tea.java.services.contentful.Contentful;
import com.contentful.tea.java.services.localization.Keys;
import com.contentful.tea.java.services.localization.LocalizedStringsProvider;
import com.contentful.tea.java.services.settings.Settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static com.contentful.tea.java.models.base.ApiParameter.CSS_CLASS_ACTIVE_BUTTON;
import static com.contentful.tea.java.services.contentful.Contentful.API_CDA;
import static java.lang.String.format;

@Component
public class StaticContentSetter {
  private static final String idRegex = "[-_a-zA-Z0-9]+";

  @Autowired
  @SuppressWarnings("unused")
  private LocalizedStringsProvider localizer;

  @Autowired
  @SuppressWarnings("unused")
  private MarkdownParser markdown;

  @Autowired
  @SuppressWarnings("unused")
  private Contentful contentful;

  @Autowired
  @SuppressWarnings("unused")
  private Settings settings;

  public void applyContent(BaseParameter base) {
    setStaticContent(base);
    updateBreadcrumbs(base);
    updateApis(base);
    updateTitle(base);
    updateLocales(base);
  }

  public void applyErrorContent(BaseParameter base) {
    setStaticContent(base);
    updateApis(base);
    updateTitle(base);
  }

  private void setStaticContent(BaseParameter base) {
    base.getApi()
        .setApiSwitcherHelp(t(Keys.apiSwitcherHelp))
        .setContentDeliveryApiHelp(t(Keys.contentDeliveryApiHelp))
        .setContentDeliveryApiLabel(t(Keys.contentDeliveryApiLabel))
        .setContentPreviewApiHelp(t(Keys.contentPreviewApiHelp))
        .setContentPreviewApiLabel(t(Keys.contentPreviewApiLabel))
    ;

    base.getMeta()
        .setAllCoursesLabel(t(Keys.allCoursesLabel))
        .setCategoriesLabel(t(Keys.categoriesLabel))
        .setContactUsLabel(t(Keys.contactUsLabel))
        .setCoursesLabel(t(Keys.coursesLabel))
        .setDescription(t(Keys.metaDescription))
        .setDraftLabel(t(Keys.draftLabel))
        .setFooterDisclaimer(t(Keys.footerDisclaimer))
        .setHomeLabel(t(Keys.homeLabel))
        .setImageAlt(t(Keys.metaImageAlt))
        .setImageDescription(t(Keys.metaImageDescription))
        .setImprintLabel(t(Keys.imprintLabel))
        .setLogoAlt(t(Keys.logoAlt))
        .setModalCTALabel(t(Keys.modalCTALabel))
        .setModalIntro(t(Keys.modalIntroJava))
        .setModalSpaceIntro(t(Keys.modalSpaceIntro))
        .setModalSpaceLinkLabel(t(Keys.modalSpaceLinkLabel))
        .setModalTitle(t(Keys.modalTitleJava))
        .setModalPlatforms(t(Keys.modalPlatforms))
        .setPendingChangesLabel(t(Keys.pendingChangesLabel))
        .setSettingsLabel(t(Keys.settingsLabel))
        .setTwitterCard(t(Keys.metaTwitterCard))
        .setViewOnGitHub(t(Keys.viewOnGithub))
        .setWhatIsThisApp(t(Keys.whatIsThisApp))
        .setComingSoonLabel(t(Keys.comingSoonLabel))
        .setHostedLabel(t(Keys.hostedLabel))
        .setViewCourseLabel(t(Keys.viewCourseLabel));
    ;
  }

  private void updateApis(BaseParameter base) {
    if (API_CDA.equals(contentful.getApi())) {
      base.getApi()
          .setCurrentApiLabel(t(Keys.contentDeliveryApiLabel))
          .setCurrentApiId(contentful.getApi())
          .setCpaButtonCSSClass("")
          .setCdaButtonCSSClass(CSS_CLASS_ACTIVE_BUTTON)
      ;
    } else {
      base.getApi()
          .setCurrentApiLabel(t(Keys.contentPreviewApiLabel))
          .setCurrentApiId(contentful.getApi())
          .setCpaButtonCSSClass(CSS_CLASS_ACTIVE_BUTTON)
          .setCdaButtonCSSClass("")
      ;
    }
  }

  private void updateTitle(BaseParameter base) {
    base.getMeta()
        .setQueryString(settings.getQueryString())
        .setCurrentPath(settings.getPath());

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

  private void updateLocales(BaseParameter base) {
    final CDAClient client = contentful.getCurrentClient();
    final CDASpace space = client.fetchSpace();
    final List<CDALocale> locales = space.locales();
    Collections.reverse(locales);

    final LocalesParameter localesParameter = base.getLocales();
    localesParameter
        .setLocaleQuestion(t(Keys.localeQuestion))
        .setLocaleLabel(t(Keys.locale))
        .setCurrentLocaleCode(settings.getLocale());

    for (final CDALocale locale : locales) {
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
    if (path.matches("/courses/" + idRegex + "/lessons/" + idRegex)) {
      final String[] split = path.split("/");

      final String courseSlug = split[2];
      breadcrumb.addBreadcrumb(
          new BreadcrumbParameter.Breadcrumb()
              .setLabel(slugToTitle(courseSlug))
              .setUrl(format("/courses/%s", courseSlug)),
          new BreadcrumbParameter.Breadcrumb()
              .setLabel(t(Keys.lessonsLabel))
              .setUrl(format("/courses/%s/lessons", courseSlug)),
          new BreadcrumbParameter.Breadcrumb()
              .setLabel(base.getMeta().getTitle())
              .setUrl(path)
      );
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
    final String localized = localizer.localize(key);
    return localized;
  }

  private String m(String s) {
    final String parsed = markdown.parse(s);
    return parsed;
  }


}
