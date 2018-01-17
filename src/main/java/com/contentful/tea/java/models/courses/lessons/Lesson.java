package com.contentful.tea.java.models.courses.lessons;

import com.contentful.tea.java.models.courses.lessons.modules.Module;
import com.contentful.tea.java.models.mappable.MappableType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Lesson extends MappableType {

  private String slug;
  private String title;
  private List<Module> modules = new ArrayList<>();
  private String cssClass;

  public String getSlug() {
    return slug;
  }

  public Lesson setSlug(String slug) {
    this.slug = slug;
    return this;
  }

  public String getCssClass() {
    return cssClass;
  }

  public Lesson setCssClass(String cssClass) {
    this.cssClass = cssClass;
    return this;
  }

  public String getTitle() {
    return title;
  }

  public Lesson setTitle(String title) {
    this.title = title;
    return this;
  }

  public List<Module> getModules() {
    return modules;
  }

  public Lesson addModule(Module... modules) {
    this.modules.addAll(Arrays.asList(modules));
    return this;
  }

  public Lesson setModules(List<Module> modules) {
    this.modules = modules;
    return this;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Lesson)) return false;
    final Lesson lesson = (Lesson) o;
    return Objects.equals(getSlug(), lesson.getSlug()) &&
        Objects.equals(getTitle(), lesson.getTitle()) &&
        Objects.equals(getModules(), lesson.getModules()) &&
        Objects.equals(getCssClass(), lesson.getCssClass());
  }

  @Override public int hashCode() {

    return Objects.hash(getSlug(), getTitle(), getModules(), getCssClass());
  }

  @Override public String toString() {
    return "Lesson { " + super.toString() + " "
        + "cssClass = " + getCssClass() + ", "
        + "modules = " + getModules() + ", "
        + "slug = " + getSlug() + ", "
        + "title = " + getTitle() + " "
        + "}";
  }
}
