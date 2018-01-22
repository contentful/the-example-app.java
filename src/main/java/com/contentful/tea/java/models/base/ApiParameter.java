package com.contentful.tea.java.models.base;

import com.contentful.tea.java.models.mappable.MappableType;

import java.util.Objects;

public class ApiParameter extends MappableType {
  private String cdaButtonCSSClass;
  private String cpaButtonCSSClass;
  private String currentApiId;

  public String getCdaButtonCSSClass() {
    return cdaButtonCSSClass;
  }

  public ApiParameter setCdaButtonCSSClass(String cdaButtonCSSClass) {
    this.cdaButtonCSSClass = cdaButtonCSSClass;
    return this;
  }

  public String getCpaButtonCSSClass() {
    return cpaButtonCSSClass;
  }

  public ApiParameter setCpaButtonCSSClass(String cpaButtonCSSClass) {
    this.cpaButtonCSSClass = cpaButtonCSSClass;
    return this;
  }

  public String getCurrentApiId() {
    return currentApiId;
  }

  public ApiParameter setCurrentApiId(String currentApiId) {
    this.currentApiId = currentApiId;
    return this;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ApiParameter)) return false;
    final ApiParameter apiParameter = (ApiParameter) o;
    return Objects.equals(getCdaButtonCSSClass(), apiParameter.getCdaButtonCSSClass()) &&
        Objects.equals(getCpaButtonCSSClass(), apiParameter.getCpaButtonCSSClass()) &&
        Objects.equals(getCurrentApiId(), apiParameter.getCurrentApiId());
  }

  @Override public int hashCode() {
    return Objects.hash(
        getCdaButtonCSSClass(),
        getCpaButtonCSSClass(),
        getCurrentApiId());
  }

  @Override public String toString() {
    return "API { "
        + "cdaButtonCSSClass = " + getCdaButtonCSSClass() + ", "
        + "cpaButtonCSSClass = " + getCpaButtonCSSClass() + ", "
        + "currentApiId = " + getCurrentApiId() + " "
        + "}";
  }
}
