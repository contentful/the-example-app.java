package com.contentful.tea.java.models.landing.modules;

import com.contentful.tea.java.models.courses.Course;

import java.util.Objects;

public class HighlightedCourseModule extends BaseModule {

  public HighlightedCourseModule() {
    super("highlightedCourse");
  }

  private Course course;

  public Course getCourse() {
    return course;
  }

  public HighlightedCourseModule setCourse(Course course) {
    this.course = course;
    return this;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof HighlightedCourseModule)) return false;
    final HighlightedCourseModule that = (HighlightedCourseModule) o;
    return Objects.equals(getCourse(), that.getCourse());
  }

  @Override public int hashCode() {

    return Objects.hash(getCourse());
  }

  @Override public String toString() {
    return "ModuleHighlightedCourse { " + super.toString() + " "
        + "course = " + getCourse() + " "
        + "}";
  }
}
