package com.contentful.tea.java.models.base;

import com.contentful.tea.java.models.mappable.MappableType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LocalesParameter extends MappableType {
  private String currentLocaleCode;
  private String currentLocaleName;
  private List<Locale> locales = new ArrayList<>();

  public String getCurrentLocaleCode() {
    return currentLocaleCode;
  }

  public LocalesParameter setCurrentLocaleCode(String currentLocaleCode) {
    this.currentLocaleCode = currentLocaleCode;
    return this;
  }

  public String getCurrentLocaleName() {
    return currentLocaleName;
  }

  public LocalesParameter setCurrentLocaleName(String currentLocaleName) {
    this.currentLocaleName = currentLocaleName;
    return this;
  }

  public List<Locale> getLocales() {
    return locales;
  }

  public LocalesParameter setLocales(List<Locale> locales) {
    this.locales = locales;
    return this;
  }

  public LocalesParameter addLocale(Locale... locales) {
    this.locales.addAll(Arrays.asList(locales));
    return this;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof LocalesParameter)) return false;
    final LocalesParameter localesParameter1 = (LocalesParameter) o;
    return Objects.equals(getCurrentLocaleCode(), localesParameter1.getCurrentLocaleCode()) &&
        Objects.equals(getCurrentLocaleName(), localesParameter1.getCurrentLocaleName()) &&
        Objects.equals(getLocales(), localesParameter1.getLocales());
  }

  @Override public int hashCode() {
    return Objects.hash(
        getCurrentLocaleCode(),
        getCurrentLocaleName(),
        getLocales());
  }

  @Override public String toString() {
    return "Locales { "
        + "currentLocaleCode = " + getCurrentLocaleCode() + ", "
        + "currentLocaleName = " + getCurrentLocaleName() + ", "
        + "locales = " + getLocales() + " "
        + "}";
  }
}
