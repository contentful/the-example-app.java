package com.contentful.tea.java.models.courses;

import com.contentful.tea.java.models.courses.lessons.LessonParameter;
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
  private List<LessonParameter> lessons = new ArrayList<>();
  private LessonParameter currentLesson;
  private String nextLessonSlug;
  private String skillLevel;
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

  public List<LessonParameter> getLessons() {
    return lessons;
  }

  public Course addLesson(LessonParameter... lessons) {
    this.lessons.addAll(Arrays.asList(lessons));
    return this;
  }

  public Course setLessons(List<LessonParameter> lessons) {
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

  public LessonParameter getCurrentLesson() {
    return currentLesson;
  }

  public Course setCurrentLesson(LessonParameter currentLesson) {
    this.currentLesson = currentLesson;
    return this;
  }

  public String getNextLessonSlug() {
    return nextLessonSlug;
  }

  public Course setNextLessonSlug(String nextLessonSlug) {
    this.nextLessonSlug = nextLessonSlug;
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
        Objects.equals(getLessons(), course.getLessons()) &&
        Objects.equals(getDuration(), course.getDuration()) &&
        Objects.equals(getSkillLevel(), course.getSkillLevel()) &&
        Objects.equals(getCurrentLesson(), course.getCurrentLesson()) &&
        Objects.equals(getNextLessonSlug(), course.getNextLessonSlug()) &&
        Objects.equals(getCategories(), course.getCategories());
  }

  @Override public int hashCode() {
    return Objects.hash(getSlug(), getTitle(), getDescription(), getShortDescription(), getImageUrl(), isDraft(), hasPendingChanges(), getCategories(), getLessons(), getDuration(), getCurrentLesson(), getNextLessonSlug());
  }

  @Override public String toString() {
    return "Course { " + super.toString() + " "
        + "categories = " + getCategories() + ", "
        + "draft = " + isDraft() + ", "
        + "imageUrl = " + getImageUrl() + ", "
        + "pendingChanges = " + hasPendingChanges() + ", "
        + "description = " + getDescription() + ", "
        + "shortDescription = " + getShortDescription() + ", "
        + "slug = " + getSlug() + ", "
        + "lessons = " + getLessons() + ", "
        + "duration = " + getDuration() + ", "
        + "title = " + getTitle() + ", "
        + "nextSessionSlug = " + getNextLessonSlug() + ", "
        + "currentLesson = " + getCurrentLesson() + " "
        + "}";
  }
}
