package com.contentful.tea.java;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAHttpException;
import com.contentful.tea.java.html.JadeHtmlGenerator;
import com.contentful.tea.java.models.courses.CourseParameter;
import com.contentful.tea.java.models.courses.CoursesParameter;
import com.contentful.tea.java.models.errors.ErrorParameter;
import com.contentful.tea.java.models.exceptions.RedirectException;
import com.contentful.tea.java.models.exceptions.TeaException;
import com.contentful.tea.java.models.imprint.ImprintParameter;
import com.contentful.tea.java.models.landing.LandingPageParameter;
import com.contentful.tea.java.models.settings.SettingsParameter;
import com.contentful.tea.java.models.settings.SettingsParameter.Errors;
import com.contentful.tea.java.services.StaticContentSetter;
import com.contentful.tea.java.services.contentful.Contentful;
import com.contentful.tea.java.services.http.Constants;
import com.contentful.tea.java.services.http.SessionParser;
import com.contentful.tea.java.services.http.UrlParameterParser;
import com.contentful.tea.java.services.localization.Keys;
import com.contentful.tea.java.services.localization.Localizer;
import com.contentful.tea.java.services.modelconverter.ArrayToCourses;
import com.contentful.tea.java.services.modelconverter.ArrayToCourses.ArrayAndSelectedCategory;
import com.contentful.tea.java.services.modelconverter.EntryToCourse;
import com.contentful.tea.java.services.modelconverter.EntryToLandingPage;
import com.contentful.tea.java.services.modelconverter.ExceptionToErrorParameter;
import com.contentful.tea.java.services.modelcreators.ImprintCreator;
import com.contentful.tea.java.services.modelcreators.SettingsCreator;
import com.contentful.tea.java.services.settings.Settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@ResponseBody
@EnableAutoConfiguration
public class MainController implements ErrorController {
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
  private SettingsCreator settingsCreator;

  @Autowired
  @SuppressWarnings("unused")
  private ImprintCreator imprintCreator;

  @Autowired
  @SuppressWarnings("unused")
  private ExceptionToErrorParameter exceptionToError;

  @Autowired
  @SuppressWarnings("unused")
  private JadeHtmlGenerator htmlGenerator;

  @RequestMapping(value = "/", produces = "text/html")
  @SuppressWarnings("unused")
  public String home(HttpServletRequest request) {
    setupRoute(request);

    try {
      final CDAClient client = contentful.getCurrentClient();
      final CDAEntry cdaLanding = client
          .fetch(CDAEntry.class)
          .include(5)
          .where("locale", settings.getLocale())
          .one("2uNOpLMJioKeoMq8W44uYc");

      final LandingPageParameter parameter = entryToLandingPage.convert(cdaLanding, 2);

      staticContentSetter.applyContent(parameter.getBase());

      return htmlGenerator.generate("landingPage.jade", parameter.toMap());
    } catch (Throwable t) {
      throw new TeaException.HomeLayoutNotFoundException(t);
    } finally {
      teardownRoute(request);
    }
  }

  @RequestMapping(value = "/courses", produces = "text/html")
  @SuppressWarnings("unused")
  public String courses(HttpServletRequest request) {
    setupRoute(request);

    try {
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

      final CoursesParameter parameter = arrayToCourses.convert(compound, 1);
      staticContentSetter.applyContent(parameter.getBase());

      return htmlGenerator.generate("courses.jade", parameter.toMap());
    } catch (Throwable t) {
      throw new TeaException.CoursesNotFoundException(t);
    } finally {
      teardownRoute(request);
    }
  }

  @RequestMapping(value = {"/courses/categories/{slug}", "/courses/categories"}, produces = "text/html")
  @SuppressWarnings("unused")
  public String coursesCategory(HttpServletRequest request,
                                @PathVariable(value = "slug", required = false) String slug) {
    setupRoute(request);

    try {
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

      final CoursesParameter parameter = arrayToCourses.convert(compound, 1);
      staticContentSetter.applyContent(parameter.getBase());

      return htmlGenerator.generate("courses.jade", parameter.toMap());
    } catch (Throwable t) {
      throw new TeaException.CoursesForCategoryNotFoundException(slug, t);
    } finally {
      teardownRoute(request);
    }
  }

  @RequestMapping(value = {"/courses/{coursesSlug}", "/courses/{coursesSlug}/lessons"}, produces = "text/html")
  @SuppressWarnings("unused")
  public String course(
      HttpServletRequest request,
      @SessionAttribute(required = false) Map<String, Set<String>> visitedLessonsByCourseSlug,
      @PathVariable("coursesSlug") String coursesSlug) {
    setupRoute(request);

    try {
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

      final CourseParameter parameter = entryToCourse.convert(compound, 1);
      staticContentSetter.applyContent(parameter.getBase());

      return htmlGenerator.generate("course.jade", parameter.toMap());
    } catch (Throwable t) {
      throw new TeaException.CourseNotFoundException(coursesSlug, t);
    } finally {
      teardownRoute(request);
    }
  }

  @RequestMapping(value = "/courses/{courseSlug}/lessons/{lessonSlug}", produces = "text/html")
  @SuppressWarnings("unused")
  public String lesson(
      HttpServletRequest request,
      @SessionAttribute(required = false) Map<String, Set<String>> visitedLessonsByCourseSlug,
      @PathVariable String courseSlug,
      @PathVariable String lessonSlug) {
    setupRoute(request);

    try {
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

      final CourseParameter parameter = entryToCourse.convert(compound, 2);
      staticContentSetter.applyContent(parameter.getBase());

      return htmlGenerator.generate("course.jade", parameter.toMap());
    } catch (Throwable t) {
      throw new TeaException.LessonFromCourseNotFoundException(courseSlug, lessonSlug, t);
    } finally {
      teardownRoute(request);
    }
  }

  @RequestMapping(value = "/courses/**/lessons/**", produces = "text/html")
  @SuppressWarnings("unused")
  public ResponseEntity<String> errorLessonsRoute(HttpServletRequest request) {
    return teaError(request, new TeaException.LessonRouteNotFoundException());
  }

  @RequestMapping(value = "/courses/**", produces = "text/html")
  @SuppressWarnings("unused")
  public ResponseEntity<String> errorCoursesRoute(HttpServletRequest request) {
    return teaError(request, new TeaException.CoursesRouteNotFoundException());
  }

  @RequestMapping(value = "/imprint", produces = "text/html")
  @SuppressWarnings("unused")
  public String imprint(HttpServletRequest request) {
    setupRoute(request);

    try {
      final ImprintParameter parameter = imprintCreator.create();
      staticContentSetter.applyContent(parameter.getBase());

      return htmlGenerator.generate("imprint.jade", parameter.toMap());
    } catch (Throwable t) {
      throw new TeaException.ImprintRenderingException(t);
    } finally {
      teardownRoute(request);
    }
  }

  @GetMapping(value = "/settings", produces = "text/html")
  @SuppressWarnings("unused")
  public String settings(HttpServletRequest request) {
    setupRoute(request);

    try {
      final SettingsParameter parameter = settingsCreator.create();
      staticContentSetter.applyContent(parameter.getBase());

      return htmlGenerator.generate("settings.jade", parameter.toMap());
    } catch (Throwable t) {
      throw new TeaException.SettingsRenderingException(t);
    } finally {
      teardownRoute(request);
    }
  }

  @PostMapping(value = "/settings", produces = "text/html")
  @SuppressWarnings("unused")
  public String updateSettings(HttpServletRequest request) {
    if (shouldReset(request)) {
      contentful.reset().loadFromPreferences();
      settings.reset();
      sessionParser.saveToSession(request.getSession());
      return settings(request);
    } else {

      try {

        final Settings lastSettings = settings.save();
        final Contentful lastContentful = contentful.save();
        settings.setBaseUrl(request.getRequestURL().toString());
        settings.setPath(request.getServletPath());

        SettingsParameter parameter = new SettingsParameter();
        try {
          contentful.reset().loadFromPreferences();
          urlParameterParser.urlParameterToApp(request.getParameterMap());
          parameter = settingsCreator.create();
        } catch (Throwable t) {
          parameter
              .setErrors(
                  parameter.getErrors()
                      .setSpaceId(
                          new Errors.Error()
                              .setMessage(
                                  localizer.localize(Keys.spaceOrTokenInvalid)
                              )
                      )
              );
        }

        if (parameter.getErrors().hasErrors()) {
          outputError(parameter.getErrors().getDeliveryToken());
          outputError(parameter.getErrors().getPreviewToken());
          outputError(parameter.getErrors().getSpaceId());

          staticContentSetter.applyErrorContent(parameter.getBase());
          settingsCreator.setStaticLabels(parameter);

          parameter
              .setDeliveryToken(contentful.getDeliveryAccessToken())
              .setPreviewToken(contentful.getPreviewAccessToken())
              .setSpaceId(contentful.getSpaceId());

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
        throw new TeaException.SettingsRenderingException(t);
      } finally {
        teardownRoute(request);
      }
    }
  }

  private boolean shouldReset(HttpServletRequest request) {
    boolean reset = false;

    if (contentful.isUsingCustomCredentials()) {
      final String[] resets = request.getParameterMap().get("reset");
      if (resets != null && resets.length > 0) {
        reset = Boolean.parseBoolean(resets[0]);
      }
    }

    return reset;
  }

  private void outputError(SettingsParameter.Errors.Error error) {
    if (error != null) {
      System.err.println("Following error occurred: '" + error.toString() + "'.");
    }
  }

  private boolean configurationIsDifferentToLastTime(Contentful lastContentful, Settings lastSettings) {
    return !(lastContentful.equals(contentful) && lastSettings.equals(settings));
  }

  @ExceptionHandler(TeaException.class)
  @RequestMapping(value = "/error/tea", produces = "text/html")
  @SuppressWarnings("unused")
  public ResponseEntity<String> teaError(HttpServletRequest request, TeaException teaException) {
    teaException.printStackTrace(System.err);

    settings.setLocale(request.getParameter(Constants.NAME_LOCALE));

    final ErrorParameter errorParameter = exceptionToError.convert(teaException);

    try {
      return new ResponseEntity<>(htmlGenerator.generate("error.jade", errorParameter.toMap()),
          HttpStatus.NOT_FOUND);
    } catch (Throwable nestedException) {
      return new ResponseEntity<>(
          format(
              "<h1>Nested exception thrown while handling a server exception</h1><br/>\n\n%s while %s<br/>\n\n<!--\n%s\n\nwhile\n\n%s\n-->",
              nestedException,
              teaException,
              getStackTrace(nestedException),
              getStackTrace(teaException)),
          HttpStatus.NOT_FOUND);
    }
  }


  @ExceptionHandler(CDAHttpException.class)
  @RequestMapping(value = "/error/contentful", produces = "text/html")
  @SuppressWarnings("unused")
  public ResponseEntity<String> contentfulError(HttpServletRequest request, CDAHttpException contentfulException) {
    contentfulException.printStackTrace(System.err);

    settings.setPath(request.getRequestURL().toString());
    final ErrorParameter errorParameter = exceptionToError.convert(contentfulException);
    errorParameter.getBase().getMeta().setTitle(localizer.localize(Keys.errorOccurredTitleLabel));

    try {
      return new ResponseEntity<>(htmlGenerator.generate("error.jade", errorParameter.toMap()),
          HttpStatus.NOT_FOUND);
    } catch (Throwable nestedException) {
      return new ResponseEntity<>(
          format(
              "<h1>Nested exception thrown while handling a server exception</h1><br/>\n\n%s while %s<br/>\n\n<!--\n%s\n\nwhile\n\n%s\n-->",
              nestedException,
              contentfulException,
              getStackTrace(nestedException),
              getStackTrace(contentfulException)),
          HttpStatus.NOT_FOUND);
    }
  }

  @ExceptionHandler(Throwable.class)
  @RequestMapping(path = "/error", produces = "text/html")
  @SuppressWarnings("unused")
  public ResponseEntity<String> generalError(HttpServletRequest request, Throwable serverException) {
    return teaError(request, new TeaException.RouteNotFoundException(request.getRequestURI()));
  }

  @ExceptionHandler(RedirectException.class)
  @RequestMapping(path = "/error/redirect", produces = "text/html")
  @SuppressWarnings("unused")
  public String redirectException(HttpServletRequest request, RedirectException redirect) {
    if ("/settings".equals(redirect.getWhereTo())) {
      return updateSettings(request);
    } else {
      throw new TeaException.RouteNotFoundException("redirect:/" + redirect.getWhereTo());
    }
  }

  private void setupRoute(HttpServletRequest request) {
    contentful.reset().loadFromPreferences();
    settings.reset();
    sessionParser.loadFromSession(request.getSession());
    urlParameterParser.urlParameterToApp(request.getParameterMap());
    settings.setBaseUrl(request.getRequestURL().toString());
    settings.setPath(request.getServletPath());

    if (!areCredentialsValid()) {
      throw new RedirectException("/settings");
    }
  }

  private boolean areCredentialsValid() {
    try {
      contentful.getCurrentClient().fetchSpace();
      return true;
    } catch (CDAHttpException e) {
      return false;
    }
  }

  private void teardownRoute(HttpServletRequest request) {
    sessionParser.saveToSession(request.getSession());
  }

  private Set<String> updateVisitedLessonsInSession(
      HttpServletRequest request,
      @SessionAttribute(required = false) Map<String, Set<String>> visitedLessonsByCourseSlug,
      @PathVariable String courseSlug,
      @PathVariable String lessonSlug) {
    if (visitedLessonsByCourseSlug == null) {
      visitedLessonsByCourseSlug = new HashMap<>();
    }

    Set<String> visitedLessons = visitedLessonsByCourseSlug.computeIfAbsent(courseSlug, k -> new HashSet<>());
    visitedLessons.add(lessonSlug);
    request.getSession().setAttribute("visitedLessonsByCourseSlug", visitedLessonsByCourseSlug);
    return visitedLessons;
  }

  @Override public String getErrorPath() {
    return "/";
  }
}
