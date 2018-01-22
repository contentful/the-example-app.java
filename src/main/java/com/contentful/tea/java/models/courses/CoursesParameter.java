package com.contentful.tea.java.models.courses;

import com.contentful.tea.java.models.base.BaseParameter;
import com.contentful.tea.java.models.mappable.MappableType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CoursesParameter extends MappableType {
  private BaseParameter base = new BaseParameter();

  private List<Category> categories = new ArrayList<>();
  private List<Course> courses = new ArrayList<>();

  private String title;

  public BaseParameter getBase() {
    return base;
  }

  public List<Course> getCourses() {
    return courses;
  }

  public CoursesParameter setCourses(List<Course> courses) {
    this.courses = courses;
    return this;
  }

  public CoursesParameter addCourse(Course... courses) {
    this.courses.addAll(Arrays.asList(courses));
    return this;
  }

  public List<Category> getCategories() {
    return categories;
  }

  public CoursesParameter setCategories(List<Category> categories) {
    this.categories = categories;
    return this;
  }

  public CoursesParameter addCategory(Category... categories) {
    this.categories.addAll(Arrays.asList(categories));
    return this;
  }

  public String getTitle() {
    return title;
  }

  public CoursesParameter setTitle(String title) {
    this.title = title;
    return this;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CoursesParameter)) return false;
    final CoursesParameter that = (CoursesParameter) o;
    return Objects.equals(getBase(), that.getBase()) &&
        Objects.equals(getCourses(), that.getCourses()) &&
        Objects.equals(getTitle(), that.getTitle()) &&
        Objects.equals(getCategories(), that.getCategories());
  }

  @Override public int hashCode() {
    return Objects.hash(getBase(), getCourses(), getCategories(), getTitle());
  }

  @Override public String toString() {
    return "CoursesParameter { " + super.toString() + " "
        + "base = " + getBase() + ", "
        + "courses = " + getCourses() + ", "
        + "categories = " + getCategories() + " "
        + "title = " + getTitle() + " "
        + "}";
  }
}
