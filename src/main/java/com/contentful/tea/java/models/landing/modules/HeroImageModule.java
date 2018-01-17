package com.contentful.tea.java.models.landing.modules;

import java.util.Objects;

public class HeroImageModule extends BaseModule {
  String headline;
  String backgroundImageUrl;
  String backgroundImageTitle;

  public HeroImageModule() {
    super("heroImage");
  }

  public String getHeadline() {
    return headline;
  }

  public HeroImageModule setHeadline(String headline) {
    this.headline = headline;
    return this;
  }

  public String getBackgroundImageUrl() {
    return backgroundImageUrl;
  }

  public HeroImageModule setBackgroundImageUrl(String backgroundImageUrl) {
    this.backgroundImageUrl = backgroundImageUrl;
    return this;
  }

  public String getBackgroundImageTitle() {
    return backgroundImageTitle;
  }

  public HeroImageModule setBackgroundImageTitle(String backgroundImageTitle) {
    this.backgroundImageTitle = backgroundImageTitle;
    return this;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof HeroImageModule)) return false;
    final HeroImageModule that = (HeroImageModule) o;
    return Objects.equals(getHeadline(), that.getHeadline()) &&
        Objects.equals(getBackgroundImageUrl(), that.getBackgroundImageUrl()) &&
        Objects.equals(getBackgroundImageTitle(), that.getBackgroundImageTitle());
  }

  @Override public int hashCode() {
    return Objects.hash(getHeadline(), getBackgroundImageUrl(), getBackgroundImageTitle());
  }

  @Override public String toString() {
    return "ModuleHeroImage { " + super.toString() + " "
        + "backgroundImageTitle = " + getBackgroundImageTitle() + ", "
        + "backgroundImageUrl = " + getBackgroundImageUrl() + ", "
        + "headline = " + getHeadline() + " "
        + "}";
  }
}
