package com.contentful.tea.java.models.courses;

import com.contentful.tea.java.models.mappable.MappableType;

import java.util.Objects;

public class Category extends MappableType {
  private String slug;
  private String title;
  private String cssClass;

  public String getCssClass() {
    return cssClass;
  }

  public Category setCssClass(String cssClass) {
    this.cssClass = cssClass;
    return this;
  }

  public String getSlug() {
    return slug;
  }

  public Category setSlug(String slug) {
    this.slug = slug;
    return this;
  }

  public String getTitle() {
    return title;
  }

  public Category setTitle(String title) {
    this.title = title;
    return this;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Category)) return false;
    final Category category = (Category) o;
    return Objects.equals(getCssClass(), category.getCssClass()) &&
        Objects.equals(getSlug(), category.getSlug()) &&
        Objects.equals(getTitle(), category.getTitle());
  }

  @Override public int hashCode() {
    return Objects.hash(getCssClass(), getSlug(), getTitle());
  }

  @Override public String toString() {
    return "Category { " + super.toString() + " "
        + "cssClass = " + getCssClass() + ", "
        + "slug = " + getSlug() + ", "
        + "title = " + getTitle() + " "
        + "}";
  }
}
