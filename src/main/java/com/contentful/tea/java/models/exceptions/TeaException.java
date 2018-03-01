package com.contentful.tea.java.models.exceptions;

import com.contentful.tea.java.services.localization.Keys;

import java.util.List;

import static java.util.Arrays.asList;

public abstract class TeaException extends IllegalStateException {

  public TeaException(String message, Throwable cause) {
    super(message, cause);
  }

  public static class HomeLayoutNotFoundException extends TeaException {
    public HomeLayoutNotFoundException(Throwable cause) {
      super("Cannot render landing page.", cause);
    }

    @Override public List<Keys> createHints() {
      return asList(
          Keys.notFoundErrorHint,
          Keys.errorMessage404Route,
          Keys.errorHighlightedCourse,
          Keys.stackTraceErrorHint
          );
    }
  }

  public static class CoursesNotFoundException extends TeaException {
    public CoursesNotFoundException(Throwable cause) {
      super("Cannot render courses page.", cause);
    }

    @Override public List<Keys> createHints() {
      return asList(
          Keys.notFoundErrorHint,
          Keys.errorMessage404Course,
          Keys.stackTraceErrorHint
      );
    }
  }

  public static class CourseNotFoundException extends TeaException {
    public CourseNotFoundException(String slug, Throwable cause) {
      super("Cannot render '" + slug + "' courses page.", cause);
    }

    @Override public List<Keys> createHints() {
      return asList(
          Keys.notFoundErrorHint,
          Keys.errorMessage404Course,
          Keys.errorMessage404Lesson,
          Keys.stackTraceErrorHint
      );
    }
  }

  public static class CoursesForCategoryNotFoundException extends TeaException {
    public CoursesForCategoryNotFoundException(String slug, Throwable cause) {
      super("Cannot render '" + slug + "' courses category page.", cause);
    }

    @Override public List<Keys> createHints() {
      return asList(
          Keys.notFoundErrorHint,
          Keys.errorMessage404Course,
          Keys.errorMessage404Category,
          Keys.errorMessage404Lesson,
          Keys.stackTraceErrorHint
      );
    }
  }

  public static class LessonFromCourseNotFoundException extends TeaException {
    public LessonFromCourseNotFoundException(String courseSlug, String lessonSlug, Throwable cause) {
      super("Cannot render " + courseSlug + "'s lesson '" + lessonSlug + "' page.", cause);
    }

    @Override public List<Keys> createHints() {
      return asList(
          Keys.notFoundErrorHint,
          Keys.errorMessage404Course,
          Keys.errorMessage404Lesson,
          Keys.stackTraceErrorHint
      );
    }
  }

  public static class ImprintRenderingException extends TeaException {
    public ImprintRenderingException(Throwable cause) {
      super("Cannot render imprint page.", cause);
    }

    @Override public List<Keys> createHints() {
      return asList(
          Keys.notFoundErrorHint,
          Keys.stackTraceErrorHint,
          Keys.localeContentErrorHint,
          Keys.contentModelChangedErrorHint,
          Keys.editorialFeaturesHint,
          Keys.draftOrPublishedErrorHint,
          Keys.verifyCredentialsErrorHint
      );
    }
  }

  public static class SettingsRenderingException extends TeaException {
    public SettingsRenderingException(Throwable cause) {
      super("Cannot render settings page.", cause);
    }

    @Override public List<Keys> createHints() {
      return asList(
          Keys.notFoundErrorHint,
          Keys.stackTraceErrorHint,
          Keys.localeContentErrorHint,
          Keys.contentModelChangedErrorHint,
          Keys.editorialFeaturesHint,
          Keys.draftOrPublishedErrorHint,
          Keys.verifyCredentialsErrorHint
      );
    }
  }

  public abstract List<Keys> createHints();
}
