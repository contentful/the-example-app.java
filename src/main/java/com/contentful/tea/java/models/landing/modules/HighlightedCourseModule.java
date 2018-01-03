package com.contentful.tea.java.models.landing.modules;

import com.contentful.tea.java.models.courses.Course;

import java.util.Objects;

public class HighlightedCourseModule extends BaseModule {

  public HighlightedCourseModule() {
    super("highlightedCourse");
  }

  private Course course;
  private String viewCourseLabel;

  public Course getCourse() {
    return course;
  }

  public HighlightedCourseModule setCourse(Course course) {
    this.course = course;
    return this;
  }

  public String getViewCourseLabel() {
    return viewCourseLabel;
  }

  public HighlightedCourseModule setViewCourseLabel(String viewCourseLabel) {
    this.viewCourseLabel = viewCourseLabel;
    return this;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof HighlightedCourseModule)) return false;
    final HighlightedCourseModule that = (HighlightedCourseModule) o;
    return Objects.equals(getCourse(), that.getCourse()) &&
        Objects.equals(getViewCourseLabel(), that.getViewCourseLabel());
  }

  @Override public int hashCode() {

    return Objects.hash(getCourse(), getViewCourseLabel());
  }

  /**
   * @return a human readable string, representing the object.
   */
  @Override public String toString() {
    return "ModuleHighlightedCourse { " + super.toString() + " "
        + "course = " + getCourse() + ", "
        + "viewCourseLabel = " + getViewCourseLabel() + " "
        + "}";
  }
}
