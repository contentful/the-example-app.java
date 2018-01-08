package com.contentful.tea.java.models.base;

import com.contentful.tea.java.models.mappable.MappableType;

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
}
