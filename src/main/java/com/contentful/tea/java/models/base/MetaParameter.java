package com.contentful.tea.java.models.base;

import com.contentful.tea.java.models.mappable.MappableType;

import java.util.Objects;

public class MetaParameter extends MappableType {
  private AnalyticsParameter analytics;
  private String allCoursesCssClass;
  private String allPlatformsQueryString;
  private String coursesCSSClass;
  private String currentPath;
  private boolean draft;
  private boolean editorialFeatures;
  private String homeCSSClass;
  private boolean pendingChanges;
  private String queryString;
  private String title;
  private String upperMenuCSSClass;
  private String deeplinkToContentful;

  public AnalyticsParameter getAnalytics() {
    return analytics;
  }

  public MetaParameter setAnalytics(AnalyticsParameter analytics) {
    this.analytics = analytics;
    return this;
  }

  public String getAllCoursesCssClass() {
    return allCoursesCssClass;
  }

  public MetaParameter setAllCoursesCssClass(String allCoursesCssClass) {
    this.allCoursesCssClass = allCoursesCssClass;
    return this;
  }

  public String getCoursesCSSClass() {
    return coursesCSSClass;
  }

  public MetaParameter setCoursesCSSClass(String coursesCSSClass) {
    this.coursesCSSClass = coursesCSSClass;
    return this;
  }

  public String getCurrentPath() {
    return currentPath;
  }

  public MetaParameter setCurrentPath(String currentPath) {
    this.currentPath = currentPath;
    return this;
  }

  public String getHomeCSSClass() {
    return homeCSSClass;
  }

  public MetaParameter setHomeCSSClass(String homeCSSClass) {
    this.homeCSSClass = homeCSSClass;
    return this;
  }

  public String getQueryString() {
    return queryString;
  }

  public MetaParameter setQueryString(String queryString) {
    this.queryString = queryString;
    return this;
  }

  public String getTitle() {
    return title;
  }

  public MetaParameter setTitle(String title) {
    this.title = title;
    return this;
  }

  public String getUpperMenuCSSClass() {
    return upperMenuCSSClass;
  }

  public MetaParameter setUpperMenuCSSClass(String upperMenuCSSClass) {
    this.upperMenuCSSClass = upperMenuCSSClass;
    return this;
  }

  public boolean isDraft() {
    return draft;
  }

  public MetaParameter setDraft(boolean draft) {
    this.draft = draft;
    return this;
  }

  public boolean hasEditorialFeatures() {
    return editorialFeatures;
  }

  public MetaParameter setEditorialFeatures(boolean editorialFeatures) {
    this.editorialFeatures = editorialFeatures;
    return this;
  }

  public boolean hasPendingChanges() {
    return pendingChanges;
  }

  public MetaParameter setPendingChanges(boolean pendingChanges) {
    this.pendingChanges = pendingChanges;
    return this;
  }

  public String getDeeplinkToContentful() {
    return deeplinkToContentful;
  }

  public MetaParameter setDeeplinkToContentful(String deeplinkToContentful) {
    this.deeplinkToContentful = deeplinkToContentful;
    return this;
  }

  public String getAllPlatformsQueryString() {
    return allPlatformsQueryString;
  }

  public MetaParameter setAllPlatformsQueryString(String allPlatformsQueryString) {
    this.allPlatformsQueryString = allPlatformsQueryString;
    return this;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof MetaParameter)) return false;
    final MetaParameter metaParameter = (MetaParameter) o;
    return Objects.equals(getAnalytics(), metaParameter.getAnalytics()) &&
        Objects.equals(getAllCoursesCssClass(), metaParameter.getAllCoursesCssClass()) &&
        Objects.equals(getAllPlatformsQueryString(), metaParameter.getAllPlatformsQueryString()) &&
        Objects.equals(getCoursesCSSClass(), metaParameter.getCoursesCSSClass()) &&
        Objects.equals(getCurrentPath(), metaParameter.getCurrentPath()) &&
        Objects.equals(getHomeCSSClass(), metaParameter.getHomeCSSClass()) &&
        Objects.equals(getQueryString(), metaParameter.getQueryString()) &&
        Objects.equals(getTitle(), metaParameter.getTitle()) &&
        Objects.equals(hasEditorialFeatures(), metaParameter.hasEditorialFeatures()) &&
        Objects.equals(hasPendingChanges(), metaParameter.hasPendingChanges()) &&
        Objects.equals(isDraft(), metaParameter.isDraft()) &&
        Objects.equals(getDeeplinkToContentful(), metaParameter.getDeeplinkToContentful()) &&
        Objects.equals(getUpperMenuCSSClass(), metaParameter.getUpperMenuCSSClass());
  }

  @Override public int hashCode() {
    return Objects.hash(
        getAnalytics(),
        getAllCoursesCssClass(),
        getAllPlatformsQueryString(),
        getCoursesCSSClass(),
        getCurrentPath(),
        getHomeCSSClass(),
        getQueryString(),
        getTitle(),
        getDeeplinkToContentful(),
        getUpperMenuCSSClass());
  }

  @Override public String toString() {
    return "Meta { "
        + "analytics = " + getAnalytics() + ", "
        + "allCoursesCssClass = " + getAllCoursesCssClass() + ", "
        + "allPlatformsQueryString = " + getAllPlatformsQueryString() + ", "
        + "coursesCSSClass = " + getCoursesCSSClass() + ", "
        + "currentPath = " + getCurrentPath() + ", "
        + "homeCSSClass = " + getHomeCSSClass() + ", "
        + "queryString = " + getQueryString() + ", "
        + "title = " + getTitle() + ", "
        + "deeplinkToContentful = " + getDeeplinkToContentful() + ", "
        + "upperMenuCSSClass = " + getUpperMenuCSSClass() + " "
        + "}";
  }
}
