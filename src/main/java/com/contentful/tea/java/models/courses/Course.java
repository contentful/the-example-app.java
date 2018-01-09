package com.contentful.tea.java.models.courses;

import com.contentful.tea.java.models.courses.lessons.Lesson;
import com.contentful.tea.java.models.mappable.MappableType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Course extends MappableType {

  private boolean draft;
  private boolean pendingChanges;
  private int duration;
  private List<Category> categories = new ArrayList<>();
  private List<Lesson> lessons = new ArrayList<>();
  private String skillLevel;
  private String cssClass;
  private String imageUrl;
  private String shortDescription;
  private String description;
  private String slug;
  private String title;

  public String getShortDescription() {
    return shortDescription;
  }

  public Course setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public Course setDescription(String description) {
    this.description = description;
    return this;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public Course setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }

  public String getSlug() {
    return slug;
  }

  public Course setSlug(String slug) {
    this.slug = slug;
    return this;
  }

  public String getTitle() {
    return title;
  }

  public Course setTitle(String title) {
    this.title = title;
    return this;
  }

  public boolean isDraft() {
    return draft;
  }

  public Course setDraft(boolean draft) {
    this.draft = draft;
    return this;
  }

  public boolean hasPendingChanges() {
    return pendingChanges;
  }

  public Course setPendingChanges(boolean pendingChanges) {
    this.pendingChanges = pendingChanges;
    return this;
  }

  public int getDuration() {
    return duration;
  }

  public Course setDuration(int duration) {
    this.duration = duration;
    return this;
  }

  public List<Lesson> getLessons() {
    return lessons;
  }

  public Course addLesson(Lesson... lessons) {
    this.lessons.addAll(Arrays.asList(lessons));
    return this;
  }

  public Course setLessons(List<Lesson> lessons) {
    this.lessons = lessons;
    return this;
  }

  public String getSkillLevel() {
    return skillLevel;
  }

  public Course setSkillLevel(String skillLevel) {
    this.skillLevel = skillLevel;
    return this;
  }

  public String getCssClass() {
    return cssClass;
  }

  public Course setCssClass(String cssClass) {
    this.cssClass = cssClass;
    return this;
  }

  public List<Category> getCategories() {
    return categories;
  }

  public Course setCategories(List<Category> categories) {
    this.categories = categories;
    return this;
  }

  public Course addCategory(Category... categories) {
    this.categories.addAll(Arrays.asList(categories));
    return this;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Course)) return false;
    final Course course = (Course) o;
    return isDraft() == course.isDraft() &&
        hasPendingChanges() == course.hasPendingChanges() &&
        Objects.equals(getSlug(), course.getSlug()) &&
        Objects.equals(getTitle(), course.getTitle()) &&
        Objects.equals(getShortDescription(), course.getShortDescription()) &&
        Objects.equals(getDescription(), course.getDescription()) &&
        Objects.equals(getImageUrl(), course.getImageUrl()) &&
        Objects.equals(getCssClass(), course.getCssClass()) &&
        Objects.equals(getLessons(), course.getLessons()) &&
        Objects.equals(getDuration(), course.getDuration()) &&
        Objects.equals(getSkillLevel(), course.getSkillLevel()) &&
        Objects.equals(getCategories(), course.getCategories());
  }

  @Override public int hashCode() {
    return Objects.hash(getSlug(), getTitle(), getDescription(), getShortDescription(), getImageUrl(), isDraft(), hasPendingChanges(), getCategories(), getCssClass(), getLessons(), getDuration());
  }

  /**
   * @return a human readable string, representing the object.
   */
  @Override public String toString() {
    return "Course { " + super.toString() + " "
        + "categories = " + getCategories() + ", "
        + "draft = " + isDraft() + ", "
        + "imageUrl = " + getImageUrl() + ", "
        + "pendingChanges = " + hasPendingChanges() + ", "
        + "description = " + getDescription() + ", "
        + "shortDescription = " + getShortDescription() + ", "
        + "slug = " + getSlug() + ", "
        + "cssClass = " + getCssClass() + ", "
        + "lessons = " + getLessons() + ", "
        + "duration = " + getDuration() + ", "
        + "title = " + getTitle() + " "
        + "}";
  }
}
