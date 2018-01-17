package com.contentful.tea.java.models.base;

import com.contentful.tea.java.models.mappable.MappableType;

import java.util.Objects;

public class Locale extends MappableType {
  public static final String CSS_CLASS_ACTIVE = "header__controls_button--active";

  private String code;
  private String cssClass;
  private String name;

  public String getCode() {
    return code;
  }

  public Locale setCode(String code) {
    this.code = code;
    return this;
  }

  public String getCssClass() {
    return cssClass;
  }

  public Locale setCssClass(String cssClass) {
    this.cssClass = cssClass;
    return this;
  }

  public String getName() {
    return name;
  }

  public Locale setName(String name) {
    this.name = name;
    return this;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Locale)) return false;
    final Locale locale = (Locale) o;
    return Objects.equals(getCode(), locale.getCode()) &&
        Objects.equals(getCssClass(), locale.getCssClass()) &&
        Objects.equals(getName(), locale.getName());
  }

  @Override public int hashCode() {
    return Objects.hash(getCode(), getCssClass(), getName());
  }

  @Override public String toString() {
    return "Locale { " + super.toString() + " "
        + "code = " + getCode() + ", "
        + "cssClass = " + getCssClass() + ", "
        + "name = " + getName() + " "
        + "}";
  }
}
