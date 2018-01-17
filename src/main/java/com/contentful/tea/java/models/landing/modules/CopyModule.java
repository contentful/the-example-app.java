package com.contentful.tea.java.models.landing.modules;

import com.contentful.tea.java.models.mappable.NullHandler;

import java.util.Map;
import java.util.Objects;

public class CopyModule extends BaseModule {
  private boolean emphasizeStyle;
  private String headline;
  private String copy;
  private String ctaTitle;
  private String ctaLink;

  public CopyModule() {
    super("copy");
  }

  public CopyModule setEmphasizeStyle(boolean emphasizeStyle) {
    this.emphasizeStyle = emphasizeStyle;
    return this;
  }

  public boolean hasEmphasizeStyle() {
    return emphasizeStyle;
  }

  public String getHeadline() {
    return headline;
  }

  public CopyModule setHeadline(String headline) {
    this.headline = headline;
    return this;
  }

  public String getCopy() {
    return copy;
  }

  public CopyModule setCopy(String copy) {
    this.copy = copy;
    return this;
  }

  public String getCtaTitle() {
    return ctaTitle;
  }

  public CopyModule setCtaTitle(String ctaTitle) {
    this.ctaTitle = ctaTitle;
    return this;
  }

  public String getCtaLink() {
    return ctaLink;
  }

  public CopyModule setCtaLink(String ctaLink) {
    this.ctaLink = ctaLink;
    return this;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CopyModule)) return false;
    final CopyModule that = (CopyModule) o;
    return Objects.equals(emphasizeStyle, that.emphasizeStyle) &&
        Objects.equals(getHeadline(), that.getHeadline()) &&
        Objects.equals(getCopy(), that.getCopy()) &&
        Objects.equals(getCtaTitle(), that.getCtaTitle()) &&
        Objects.equals(getCtaLink(), that.getCtaLink());
  }

  @Override public int hashCode() {
    return Objects.hash(hasEmphasizeStyle(), getHeadline(), getCopy(), getCtaTitle(), getCtaLink());
  }

  @Override public String toString() {
    return "ModuleCopy { " + super.toString() + " "
        + "copy = " + getCopy() + ", "
        + "ctaLink = " + getCtaLink() + ", "
        + "ctaTitle = " + getCtaTitle() + ", "
        + "headline = " + getHeadline() + ", "
        + "hasEmphasizeStyle = " + hasEmphasizeStyle() + " "
        + "}";
  }

  @Override public Map<String, Object> toMap(NullHandler nullHandler) {
    final Map<String, Object> map = super.toMap(nullHandler);
    map.put("style", hasEmphasizeStyle() ? "--emphasized" : "");
    return map;
  }
}
