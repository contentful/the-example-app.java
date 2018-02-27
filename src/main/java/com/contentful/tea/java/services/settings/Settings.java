package com.contentful.tea.java.services.settings;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Settings {

  private static final String DEFAULT_LOCALE = "en-US";

  private String queryString;
  private String baseUrl;
  private String path;
  private String locale = DEFAULT_LOCALE;

  private boolean editorialFeaturesEnabled;

  public Settings() {
    reset();
  }

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

  public String getBaseUrl() {
    return baseUrl;
  }

  public Settings setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
    return this;
  }

  @Override public String toString() {
    return "Settings { "
        + "locale = " + getLocale() + ", "
        + "baseUrl = " + getBaseUrl() + ", "
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
        Objects.equals(getLocale(), settings.getLocale()) &&
        Objects.equals(areEditorialFeaturesEnabled(), settings.areEditorialFeaturesEnabled());
  }

  @Override public int hashCode() {
    return Objects.hash(getBaseUrl(), getQueryString(), getPath(), getLocale(), areEditorialFeaturesEnabled());
  }

  public Settings save() {
    return
        new Settings()
            .setEditorialFeaturesEnabled(areEditorialFeaturesEnabled())
            .setBaseUrl(getBaseUrl())
            .setLocale(getLocale())
            .setPath(getPath())
            .setQueryString(getQueryString())
        ;
  }

  public Settings load(Settings lastSettings) {
    setEditorialFeaturesEnabled(lastSettings.areEditorialFeaturesEnabled());
    setLocale(lastSettings.getLocale());
    setBaseUrl(lastSettings.getBaseUrl());
    setPath(lastSettings.getPath());
    setQueryString(lastSettings.getQueryString());
    return this;
  }

  public Settings reset() {
    setEditorialFeaturesEnabled(false);
    setLocale(DEFAULT_LOCALE);
    setBaseUrl(null);
    setPath(null);
    setQueryString(null);

    return this;
  }
}
