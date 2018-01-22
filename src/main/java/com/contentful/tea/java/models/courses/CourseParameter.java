package com.contentful.tea.java.models.courses;

import com.contentful.tea.java.models.base.BaseParameter;
import com.contentful.tea.java.models.mappable.MappableType;

import java.util.Objects;

public class CourseParameter extends MappableType {
  private BaseParameter base = new BaseParameter();

  private String tableOfContentsLabel;
  private String skillLevelLabel;
  private String overviewLabel;
  private String courseOverviewLabel;
  private String courseOverviewCssClass;
  private String durationLabel;
  private String minutesLabel;
  private String startCourseLabel;
  private String nextLessonLabel;
  private Course course;

  public BaseParameter getBase() {
    return base;
  }

  public String getTableOfContentsLabel() {
    return tableOfContentsLabel;
  }

  public CourseParameter setTableOfContentsLabel(String tableOfContentsLabel) {
    this.tableOfContentsLabel = tableOfContentsLabel;
    return this;
  }

  public String getSkillLevelLabel() {
    return skillLevelLabel;
  }

  public CourseParameter setSkillLevelLabel(String skillLevelLabel) {
    this.skillLevelLabel = skillLevelLabel;
    return this;
  }

  public String getOverviewLabel() {
    return overviewLabel;
  }

  public CourseParameter setOverviewLabel(String overviewLabel) {
    this.overviewLabel = overviewLabel;
    return this;
  }

  public String getCourseOverviewLabel() {
    return courseOverviewLabel;
  }

  public CourseParameter setCourseOverviewLabel(String courseOverviewLabel) {
    this.courseOverviewLabel = courseOverviewLabel;
    return this;
  }

  public String getCourseOverviewCssClass() {
    return courseOverviewCssClass;
  }

  public CourseParameter setCourseOverviewCssClass(String courseOverviewCssClass) {
    this.courseOverviewCssClass = courseOverviewCssClass;
    return this;
  }

  public String getDurationLabel() {
    return durationLabel;
  }

  public CourseParameter setDurationLabel(String durationLabel) {
    this.durationLabel = durationLabel;
    return this;
  }

  public String getMinutesLabel() {
    return minutesLabel;
  }

  public CourseParameter setMinutesLabel(String minutesLabel) {
    this.minutesLabel = minutesLabel;
    return this;
  }

  public String getStartCourseLabel() {
    return startCourseLabel;
  }

  public CourseParameter setStartCourseLabel(String startCourseLabel) {
    this.startCourseLabel = startCourseLabel;
    return this;
  }

  public Course getCourse() {
    return course;
  }

  public CourseParameter setCourse(Course course) {
    this.course = course;
    return this;
  }

  public String getNextLessonLabel() {
    return nextLessonLabel;
  }

  public CourseParameter setNextLessonLabel(String nextLessonLabel) {
    this.nextLessonLabel = nextLessonLabel;
    return this;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CourseParameter)) return false;
    final CourseParameter that = (CourseParameter) o;
    return Objects.equals(getTableOfContentsLabel(), that.getTableOfContentsLabel()) &&
        Objects.equals(getSkillLevelLabel(), that.getSkillLevelLabel()) &&
        Objects.equals(getCourseOverviewLabel(), that.getCourseOverviewLabel()) &&
        Objects.equals(getCourseOverviewCssClass(), that.getCourseOverviewCssClass()) &&
        Objects.equals(getOverviewLabel(), that.getOverviewLabel()) &&
        Objects.equals(getDurationLabel(), that.getDurationLabel()) &&
        Objects.equals(getMinutesLabel(), that.getMinutesLabel()) &&
        Objects.equals(getStartCourseLabel(), that.getStartCourseLabel()) &&
        Objects.equals(getNextLessonLabel(), that.getNextLessonLabel()) &&
        Objects.equals(getCourse(), that.getCourse());
  }

  @Override public int hashCode() {
    return Objects.hash(getTableOfContentsLabel(), getSkillLevelLabel(), getCourseOverviewLabel(), getCourseOverviewCssClass(), getOverviewLabel(), getDurationLabel(), getMinutesLabel(), getStartCourseLabel(), getNextLessonLabel(), getCourse());
  }

  @Override public String toString() {
    return "CourseParameter { " + super.toString() + " "
        + "durationLabel = " + getDurationLabel() + ", "
        + "course = " + getCourse() + ", "
        + "minutesLabel = " + getMinutesLabel() + ", "
        + "courseOverViewLabel = " + getCourseOverviewLabel() + ", "
        + "courseOverviewCssClass = " + getCourseOverviewCssClass() + ", "
        + "overviewLabel = " + getOverviewLabel() + ", "
        + "skillLevelLabel = " + getSkillLevelLabel() + ", "
        + "startCourseLabel = " + getStartCourseLabel() + ", "
        + "tableOfContentsLabel = " + getTableOfContentsLabel() + ", "
        + "nextLessonLabel= " + getNextLessonLabel() + " "
        + "}";
  }
}
