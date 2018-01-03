package com.contentful.tea.java.models.base;

import com.contentful.tea.java.models.mappable.MappableType;

import java.util.Objects;

public class ApiParameter extends MappableType {
  private String apiSwitcherHelp;
  private String cdaButtonCSSClass;
  private String contentDeliveryApiHelp;
  private String contentDeliveryApiLabel;
  private String contentPreviewApiHelp;
  private String contentPreviewApiLabel;
  private String cpaButtonCSSClass;
  private String currentApiId;
  private String currentApiLabel;

  public String getApiSwitcherHelp() {
    return apiSwitcherHelp;
  }

  public ApiParameter setApiSwitcherHelp(String apiSwitcherHelp) {
    this.apiSwitcherHelp = apiSwitcherHelp;
    return this;
  }

  public String getCdaButtonCSSClass() {
    return cdaButtonCSSClass;
  }

  public ApiParameter setCdaButtonCSSClass(String cdaButtonCSSClass) {
    this.cdaButtonCSSClass = cdaButtonCSSClass;
    return this;
  }

  public String getContentDeliveryApiHelp() {
    return contentDeliveryApiHelp;
  }

  public ApiParameter setContentDeliveryApiHelp(String contentDeliveryApiHelp) {
    this.contentDeliveryApiHelp = contentDeliveryApiHelp;
    return this;
  }

  public String getContentDeliveryApiLabel() {
    return contentDeliveryApiLabel;
  }

  public ApiParameter setContentDeliveryApiLabel(String contentDeliveryApiLabel) {
    this.contentDeliveryApiLabel = contentDeliveryApiLabel;
    return this;
  }

  public String getContentPreviewApiHelp() {
    return contentPreviewApiHelp;
  }

  public ApiParameter setContentPreviewApiHelp(String contentPreviewApiHelp) {
    this.contentPreviewApiHelp = contentPreviewApiHelp;
    return this;
  }

  public String getContentPreviewApiLabel() {
    return contentPreviewApiLabel;
  }

  public ApiParameter setContentPreviewApiLabel(String contentPreviewApiLabel) {
    this.contentPreviewApiLabel = contentPreviewApiLabel;
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

  public String getCurrentApiLabel() {
    return currentApiLabel;
  }

  public ApiParameter setCurrentApiLabel(String currentApiLabel) {
    this.currentApiLabel = currentApiLabel;
    return this;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ApiParameter)) return false;
    final ApiParameter apiParameter = (ApiParameter) o;
    return Objects.equals(getApiSwitcherHelp(), apiParameter.getApiSwitcherHelp()) &&
        Objects.equals(getCdaButtonCSSClass(), apiParameter.getCdaButtonCSSClass()) &&
        Objects.equals(getContentDeliveryApiHelp(), apiParameter.getContentDeliveryApiHelp()) &&
        Objects.equals(getContentDeliveryApiLabel(), apiParameter.getContentDeliveryApiLabel()) &&
        Objects.equals(getContentPreviewApiHelp(), apiParameter.getContentPreviewApiHelp()) &&
        Objects.equals(getContentPreviewApiLabel(), apiParameter.getContentPreviewApiLabel()) &&
        Objects.equals(getCpaButtonCSSClass(), apiParameter.getCpaButtonCSSClass()) &&
        Objects.equals(getCurrentApiId(), apiParameter.getCurrentApiId()) &&
        Objects.equals(getCurrentApiLabel(), apiParameter.getCurrentApiLabel());
  }

  @Override public int hashCode() {
    return Objects.hash(
        getApiSwitcherHelp(),
        getCdaButtonCSSClass(),
        getContentDeliveryApiHelp(),
        getContentDeliveryApiLabel(),
        getContentPreviewApiHelp(),
        getContentPreviewApiLabel(),
        getCpaButtonCSSClass(),
        getCurrentApiId(),
        getCurrentApiLabel());
  }

  /**
   * @return a human readable string, representing the object.
   */
  @Override public String toString() {
    return "API { "
        + "apiSwitcherHelp = " + getApiSwitcherHelp() + ", "
        + "cdaButtonCSSClass = " + getCdaButtonCSSClass() + ", "
        + "contentDeliveryApiHelp = " + getContentDeliveryApiHelp() + ", "
        + "contentDeliveryApiLabel = " + getContentDeliveryApiLabel() + ", "
        + "contentPreviewApiHelp = " + getContentPreviewApiHelp() + ", "
        + "contentPreviewApiLabel = " + getContentPreviewApiLabel() + ", "
        + "cpaButtonCSSClass = " + getCpaButtonCSSClass() + ", "
        + "currentApiId = " + getCurrentApiId() + ", "
        + "currentApiLabel = " + getCurrentApiLabel() + " "
        + "}";
  }
}
