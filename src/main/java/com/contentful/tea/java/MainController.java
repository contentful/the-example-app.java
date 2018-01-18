package com.contentful.tea.java;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAHttpException;
import com.contentful.tea.java.html.JadeHtmlGenerator;
import com.contentful.tea.java.models.courses.CourseParameter;
import com.contentful.tea.java.models.courses.CoursesParameter;
import com.contentful.tea.java.models.errors.ErrorParameter;
import com.contentful.tea.java.models.landing.LandingPageParameter;
import com.contentful.tea.java.models.settings.SettingsParameter;
import com.contentful.tea.java.services.StaticContentSetter;
import com.contentful.tea.java.services.contentful.Contentful;
import com.contentful.tea.java.services.http.SessionParser;
import com.contentful.tea.java.services.http.UrlParameterParser;
import com.contentful.tea.java.services.localization.Keys;
import com.contentful.tea.java.services.localization.Localizer;
import com.contentful.tea.java.services.modelconverter.ArrayToCourses;
import com.contentful.tea.java.services.modelconverter.ArrayToCourses.ArrayAndSelectedCategory;
import com.contentful.tea.java.services.modelconverter.EntryToCourse;
import com.contentful.tea.java.services.modelconverter.EntryToLandingPage;
import com.contentful.tea.java.services.modelconverter.ExceptionToErrorParameter;
import com.contentful.tea.java.services.modelconverter.SettingsToParameter;
import com.contentful.tea.java.services.settings.Settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import static java.lang.String.format;
import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

@ComponentScan
@Controller
@EnableAutoConfiguration
public class MainController implements ErrorController {
  private static final String ERROR_PATH = "/error";

  public static void main(String[] args) {
    SpringApplication.run(MainController.class, args);
  }

  @Autowired
  @SuppressWarnings("unused")
  private Settings settings;

  @Autowired
  @SuppressWarnings("unused")
  private Contentful contentful;

  @Autowired
  @SuppressWarnings("unused")
  private Localizer localizer;

  @Autowired
  @SuppressWarnings("unused")
  private SessionParser sessionParser;

  @Autowired
  @SuppressWarnings("unused")
  private UrlParameterParser urlParameterParser;

  @Autowired
  @SuppressWarnings("unused")
  private StaticContentSetter staticContentSetter;

  @Autowired
  @SuppressWarnings("unused")
  private EntryToLandingPage entryToLandingPage;

  @Autowired
  @SuppressWarnings("unused")
  private ArrayToCourses arrayToCourses;

  @Autowired
  @SuppressWarnings("unused")
  private EntryToCourse entryToCourse;

  @Autowired
  @SuppressWarnings("unused")
  private SettingsToParameter settingsToParameter;

  @Autowired
  @SuppressWarnings("unused")
  private ExceptionToErrorParameter exceptionToError;

  @Autowired
  @SuppressWarnings("unused")
  private JadeHtmlGenerator htmlGenerator;

  @RequestMapping("/")
  @ResponseBody
  @SuppressWarnings("unused")
  public String home(HttpServletRequest request) {
    try {
      setupRoute(request);

      final CDAClient client = contentful.getCurrentClient();
      final CDAEntry cdaLanding = client
          .fetch(CDAEntry.class)
          .include(5)
          .where("locale", settings.getLocale())
          .one("2uNOpLMJioKeoMq8W44uYc");

      final LandingPageParameter parameter = entryToLandingPage.convert(cdaLanding);

      staticContentSetter.applyContent(parameter.getBase());

      return htmlGenerator.generate("landingPage.jade", parameter.toMap());
    } catch (Throwable t) {
      throw new IllegalStateException("Cannot render landing page.", t);
    } finally {
      teardownRoute(request);
    }
  }

  @RequestMapping("/courses")
  @ResponseBody
  @SuppressWarnings("unused")
  public String courses(HttpServletRequest request) {
    try {
      setupRoute(request);

      final CDAClient client = contentful.getCurrentClient();
      final CDAArray courses = client
          .fetch(CDAEntry.class)
          .include(5)
          .withContentType("course")
          .where("locale", settings.getLocale())
          .orderBy("-sys.createdAt")
          .all();

      final String categorySlug = "";
      final String categoryName = "";
      final ArrayAndSelectedCategory compound = new ArrayAndSelectedCategory()
          .setList(courses.items())
          .setCategorySlug(categorySlug);

      final CoursesParameter parameter = arrayToCourses.convert(compound);
      staticContentSetter.applyContent(parameter.getBase());

      return htmlGenerator.generate("courses.jade", parameter.toMap());
    } catch (Throwable t) {
      throw new IllegalStateException("Cannot render courses page.", t);
    } finally {
      teardownRoute(request);
    }
  }

  @RequestMapping("/courses/categories/{slug}")
  @ResponseBody
  @SuppressWarnings("unused")
  public String coursesCategory(HttpServletRequest request,
                                @PathVariable("slug") String slug) {
    try {
      setupRoute(request);

      final CDAClient client = contentful.getCurrentClient();
      final CDAArray courses = client
          .fetch(CDAEntry.class)
          .include(5)
          .withContentType("course")
          .where("locale", settings.getLocale())
          .all();

      final ArrayAndSelectedCategory compound = new ArrayAndSelectedCategory()
          .setList(courses.items())
          .setCategorySlug(slug);

      final CoursesParameter parameter = arrayToCourses.convert(compound);
      staticContentSetter.applyContent(parameter.getBase());

      return htmlGenerator.generate("courses.jade", parameter.toMap());
    } catch (Throwable t) {
      throw new IllegalStateException("Cannot render '" + slug + "' courses category page.", t);
    } finally {
      teardownRoute(request);
    }
  }

  @RequestMapping({"/courses/{coursesSlug}", "/courses/{coursesSlug}/lessons"})
  @ResponseBody
  @SuppressWarnings("unused")
  public String course(
      HttpServletRequest request,
      @SessionAttribute(required = false) Map<String, Set<String>> visitedLessonsByCourseSlug,
      @PathVariable("coursesSlug") String coursesSlug) {
    try {
      setupRoute(request);

      final CDAClient client = contentful.getCurrentClient();
      final CDAEntry course = ((CDAEntry) client
          .fetch(CDAEntry.class)
          .include(5)
          .withContentType("course")
          .where("fields.slug", coursesSlug)
          .where("locale", settings.getLocale())
          .all()
          .items()
          .get(0));

      final Set<String> visitedLessons =
          updateVisitedLessonsInSession(request, visitedLessonsByCourseSlug, coursesSlug, "/");

      final EntryToCourse.Compound compound = new EntryToCourse.Compound()
          .setCourse(course)
          .setCourseSlug(coursesSlug)
          .setVisitedLessons(visitedLessons);

      final CourseParameter parameter = entryToCourse.convert(compound);
      staticContentSetter.applyContent(parameter.getBase());

      return htmlGenerator.generate("course.jade", parameter.toMap());
    } catch (Throwable t) {
      throw new IllegalStateException("Cannot render '" + coursesSlug + "' courses page.", t);
    } finally {
      teardownRoute(request);
    }
  }

  @RequestMapping("/courses/{courseSlug}/lessons/{lessonSlug}")
  @ResponseBody
  @SuppressWarnings("unused")
  public String lesson(
      HttpServletRequest request,
      @SessionAttribute(required = false) Map<String, Set<String>> visitedLessonsByCourseSlug,
      @PathVariable String courseSlug,
      @PathVariable String lessonSlug) {
    try {
      setupRoute(request);

      final CDAClient client = contentful.getCurrentClient();
      final CDAEntry course = ((CDAEntry) client
          .fetch(CDAEntry.class)
          .include(5)
          .withContentType("course")
          .where("fields.slug", courseSlug)
          .where("locale", settings.getLocale())
          .all()
          .items()
          .get(0));

      final Set<String> visitedLessons =
          updateVisitedLessonsInSession(request, visitedLessonsByCourseSlug, courseSlug, lessonSlug);

      final EntryToCourse.Compound compound = new EntryToCourse.Compound()
          .setCourse(course)
          .setCourseSlug(courseSlug)
          .setLessonSlug(lessonSlug)
          .setVisitedLessons(visitedLessons);

      final CourseParameter parameter = entryToCourse.convert(compound);
      staticContentSetter.applyContent(parameter.getBase());

      return htmlGenerator.generate("course.jade", parameter.toMap());
    } catch (Throwable t) {
      throw new IllegalStateException("Cannot render " + courseSlug + "'s lesson '" + lessonSlug + "' page.", t);
    } finally {
      teardownRoute(request);
    }
  }

  @GetMapping(value = "/settings")
  @ResponseBody
  @SuppressWarnings("unused")
  public String settings(HttpServletRequest request) {
    // url contains parameter?
    if (request.getParameterMap().size() > 0) {
      return updateSettings(request);
    }

    try {
      setupRoute(request);

      final SettingsParameter parameter = settingsToParameter.convert(null);
      staticContentSetter.applyContent(parameter.getBase());

      return htmlGenerator.generate("settings.jade", parameter.toMap());
    } catch (Throwable t) {
      throw new IllegalStateException("Cannot render settings page.", t);
    } finally {
      teardownRoute(request);
    }
  }

  @PostMapping(value = "/settings")
  @ResponseBody
  @SuppressWarnings("unused")
  public String updateSettings(HttpServletRequest request) {
    try {
      contentful.reset().loadFromPreferences();
      settings.reset();
      sessionParser.loadSession(request.getSession());
      settings.setBaseUrl(request.getRequestURL().toString());
      settings.setPath(request.getServletPath());

      final Settings lastSettings = settings.save();
      final Contentful lastContentful = contentful.save();
      contentful.reset().loadFromPreferences();
      settings.reset();
      settings.setBaseUrl(request.getRequestURL().toString());
      settings.setPath(request.getServletPath());
      urlParameterParser.parseUrlParameter(request.getParameterMap());

      final SettingsParameter parameter = settingsToParameter.convert(null);

      if (parameter.getErrors().hasErrors()) {
        staticContentSetter.applyErrorContent(parameter.getBase());
        settings.load(lastSettings);
        contentful.load(lastContentful);
      } else {
        if (request.getParameterMap().size() > 0) {
          parameter.setSuccessful(true);
        }
        staticContentSetter.applyContent(parameter.getBase());
      }

      return htmlGenerator.generate("settings.jade", parameter.toMap());
    } catch (Throwable t) {
      throw new IllegalStateException("Cannot render settings page.", t);
    } finally {
      teardownRoute(request);
    }
  }

  @ExceptionHandler(Throwable.class)
  @RequestMapping("/error")
  @ResponseBody
  @SuppressWarnings("unused")
  public String serverError(HttpServletRequest request, Throwable serverException) {
    serverException.printStackTrace(System.err);

    final ErrorParameter errorParameter = exceptionToError.convert(serverException);

    try {
      return htmlGenerator.generate("error.jade", errorParameter.toMap());
    } catch (Throwable nestedException) {
      return format(
          "<h1>Nested exception thrown while handling a server exception</h1><br/>\n\n%s while %s<br/>\n\n<!--\n%s\n\nwhile\n\n%s\n-->",
          nestedException,
          serverException,
          getStackTrace(nestedException),
          getStackTrace(serverException));
    }
  }

  @ExceptionHandler(CDAHttpException.class)
  @RequestMapping("/error/contentful")
  @ResponseBody
  @SuppressWarnings("unused")
  public String contentfulError(HttpServletRequest request, CDAHttpException contentfulException) {
    contentfulException.printStackTrace(System.err);

    settings.setPath(request.getRequestURL().toString());
    final ErrorParameter errorParameter = exceptionToError.convert(contentfulException);
    errorParameter.getBase().getMeta().setTitle(localizer.localize(Keys.errorOccurredTitleLabel));

    try {
      return htmlGenerator.generate("error.jade", errorParameter.toMap());
    } catch (Throwable nestedException) {
      return format(
          "<h1>Nested exception thrown while handling a server exception</h1><br/>\n\n%s while %s<br/>\n\n<!--\n%s\n\nwhile\n\n%s\n-->",
          nestedException,
          contentfulException,
          getStackTrace(nestedException),
          getStackTrace(contentfulException));
    }
  }

  @Override
  public String getErrorPath() {
    return ERROR_PATH;
  }

  private void setupRoute(HttpServletRequest request) {
    contentful.reset().loadFromPreferences();
    sessionParser.loadSession(request.getSession());
    urlParameterParser.parseUrlParameter(request.getParameterMap());
    settings.setBaseUrl(request.getRequestURL().toString());
    settings.setPath(request.getServletPath());
  }

  private void teardownRoute(HttpServletRequest request) {
    sessionParser.saveSession(request.getSession());
  }

  private Set<String> updateVisitedLessonsInSession(HttpServletRequest request, @SessionAttribute(required = false) Map<String, Set<String>> visitedLessonsByCourseSlug, @PathVariable String courseSlug, @PathVariable String lessonSlug) {
    if (visitedLessonsByCourseSlug == null) {
      visitedLessonsByCourseSlug = new HashMap<>();
    }

    Set<String> visitedLessons = visitedLessonsByCourseSlug.computeIfAbsent(courseSlug, k -> new HashSet<>());
    visitedLessons.add(lessonSlug);
    request.getSession().setAttribute("visitedLessonsByCourseSlug", visitedLessonsByCourseSlug);
    return visitedLessons;
  }

}
