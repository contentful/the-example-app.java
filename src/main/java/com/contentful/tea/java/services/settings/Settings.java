package com.contentful.tea.java.services.settings;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Settings {

  private static final String DEFAULT_LOCALE = "en-US";

  private String queryString;
  private String path;
  private String locale = DEFAULT_LOCALE;

  private boolean editorialFeaturesEnabled;

  public String getLocale() {
    return locale;
  }

  public Settings setLocale(String locale) {
    this.locale = locale;
    return this;
  }

  public String getQueryString() {
    return queryString;
  }

  public Settings setQueryString(String queryString) {
    this.queryString = queryString;
    return this;
  }

  public String getPath() {
    return path;
  }

  public Settings setPath(String path) {
    this.path = path;
    return this;
  }

  public boolean areEditorialFeaturesEnabled() {
    return editorialFeaturesEnabled;
  }

  public Settings setEditorialFeaturesEnabled(boolean editorialFeaturesEnabled) {
    this.editorialFeaturesEnabled = editorialFeaturesEnabled;
    return this;
  }

  /**
   * @return a human readable string, representing the object.
   */
  @Override public String toString() {
    return "Settings { "
        + "locale = " + getLocale() + ", "
        + "path = " + getPath() + ", "
        + "queryString = " + getQueryString() + ", "
        + "editorialFeaturesEnabled = " + areEditorialFeaturesEnabled() + " "
        + "}";
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Settings)) return false;
    final Settings settings = (Settings) o;
    return Objects.equals(getQueryString(), settings.getQueryString()) &&
        Objects.equals(getPath(), settings.getPath()) &&
        Objects.equals(getLocale(), settings.getLocale()) &&
        Objects.equals(areEditorialFeaturesEnabled(), settings.areEditorialFeaturesEnabled());
  }

  @Override public int hashCode() {
    return Objects.hash(getQueryString(), getPath(), getLocale(), areEditorialFeaturesEnabled());
  }

  public Settings save() {
    return
        new Settings()
            .setEditorialFeaturesEnabled(areEditorialFeaturesEnabled())
            .setLocale(getLocale())
            .setPath(getPath())
            .setQueryString(getQueryString())
        ;
  }

  public void load(Settings lastSettings) {
    setEditorialFeaturesEnabled(lastSettings.areEditorialFeaturesEnabled());
    setLocale(lastSettings.getLocale());
    setPath(lastSettings.getPath());
    setQueryString(lastSettings.getQueryString());
  }

  public Settings reset() {
    setEditorialFeaturesEnabled(false);
    setLocale(DEFAULT_LOCALE);
    setPath("");
    setQueryString("");

    return this;
  }
}
