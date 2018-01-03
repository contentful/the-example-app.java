package com.contentful.tea.java.models.errors;

import com.contentful.tea.java.models.base.BaseParameter;
import com.contentful.tea.java.models.mappable.MappableType;

import java.util.Objects;

public class ErrorParameter extends MappableType {
  private BaseParameter base = new BaseParameter();

  private String contentModelChangedErrorLabel;
  private String draftOrPublishedErrorLabel;
  private String error404Route;
  private String errorLabel;
  private String localeContentErrorLabel;
  private String responseData;
  private String stack;
  private String somethingWentWrongLabel;
  private String stackTraceErrorLabel;
  private String stackTraceLabel;
  private String tryLabel;
  private int status;
  private String verifyCredentialsErrorLabel;

  public BaseParameter getBase() {
    return base;
  }

  public ErrorParameter setBase(BaseParameter base) {
    this.base = base;
    return this;
  }

  public String getContentModelChangedErrorLabel() {
    return contentModelChangedErrorLabel;
  }

  public ErrorParameter setContentModelChangedErrorLabel(String contentModelChangedErrorLabel) {
    this.contentModelChangedErrorLabel = contentModelChangedErrorLabel;
    return this;
  }

  public String getDraftOrPublishedErrorLabel() {
    return draftOrPublishedErrorLabel;
  }

  public ErrorParameter setDraftOrPublishedErrorLabel(String draftOrPublishedErrorLabel) {
    this.draftOrPublishedErrorLabel = draftOrPublishedErrorLabel;
    return this;
  }

  public String getError404Route() {
    return error404Route;
  }

  public ErrorParameter setError404Route(String error404Route) {
    this.error404Route = error404Route;
    return this;
  }

  public String getErrorLabel() {
    return errorLabel;
  }

  public ErrorParameter setErrorLabel(String errorLabel) {
    this.errorLabel = errorLabel;
    return this;
  }

  public String getLocaleContentErrorLabel() {
    return localeContentErrorLabel;
  }

  public ErrorParameter setLocaleContentErrorLabel(String localeContentErrorLabel) {
    this.localeContentErrorLabel = localeContentErrorLabel;
    return this;
  }

  public String getResponseData() {
    return responseData;
  }

  public ErrorParameter setResponseData(String responseData) {
    this.responseData = responseData;
    return this;
  }

  public String getStack() {
    return stack;
  }

  public ErrorParameter setStack(String stack) {
    this.stack = stack;
    return this;
  }

  public String getSomethingWentWrongLabel() {
    return somethingWentWrongLabel;
  }

  public ErrorParameter setSomethingWentWrongLabel(String somethingWentWrongLabel) {
    this.somethingWentWrongLabel = somethingWentWrongLabel;
    return this;
  }

  public String getStackTraceErrorLabel() {
    return stackTraceErrorLabel;
  }

  public ErrorParameter setStackTraceErrorLabel(String stackTraceErrorLabel) {
    this.stackTraceErrorLabel = stackTraceErrorLabel;
    return this;
  }

  public String getStackTraceLabel() {
    return stackTraceLabel;
  }

  public ErrorParameter setStackTraceLabel(String stackTraceLabel) {
    this.stackTraceLabel = stackTraceLabel;
    return this;
  }

  public int getStatus() {
    return status;
  }

  public ErrorParameter setStatus(int status) {
    this.status = status;
    return this;
  }

  public String getTryLabel() {
    return tryLabel;
  }

  public ErrorParameter setTryLabel(String tryLabel) {
    this.tryLabel = tryLabel;
    return this;
  }

  public String getVerifyCredentialsErrorLabel() {
    return verifyCredentialsErrorLabel;
  }

  public ErrorParameter setVerifyCredentialsErrorLabel(String verifyCredentialsErrorLabel) {
    this.verifyCredentialsErrorLabel = verifyCredentialsErrorLabel;
    return this;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ErrorParameter)) return false;
    final ErrorParameter that = (ErrorParameter) o;
    return getStatus() == that.getStatus() &&
        Objects.equals(getBase(), that.getBase()) &&
        Objects.equals(getContentModelChangedErrorLabel(), that.getContentModelChangedErrorLabel()) &&
        Objects.equals(getDraftOrPublishedErrorLabel(), that.getDraftOrPublishedErrorLabel()) &&
        Objects.equals(getError404Route(), that.getError404Route()) &&
        Objects.equals(getErrorLabel(), that.getErrorLabel()) &&
        Objects.equals(getLocaleContentErrorLabel(), that.getLocaleContentErrorLabel()) &&
        Objects.equals(getResponseData(), that.getResponseData()) &&
        Objects.equals(getStack(), that.getStack()) &&
        Objects.equals(getSomethingWentWrongLabel(), that.getSomethingWentWrongLabel()) &&
        Objects.equals(getStackTraceErrorLabel(), that.getStackTraceErrorLabel()) &&
        Objects.equals(getStackTraceLabel(), that.getStackTraceLabel()) &&
        Objects.equals(getTryLabel(), that.getTryLabel()) &&
        Objects.equals(getVerifyCredentialsErrorLabel(), that.getVerifyCredentialsErrorLabel());
  }

  @Override public int hashCode() {
    return Objects.hash(getBase(), getContentModelChangedErrorLabel(), getDraftOrPublishedErrorLabel(), getError404Route(), getErrorLabel(), getLocaleContentErrorLabel(), getResponseData(), getStack(), getSomethingWentWrongLabel(), getStackTraceErrorLabel(), getStackTraceLabel(), getStatus(), getTryLabel(), getVerifyCredentialsErrorLabel());
  }

  /**
   * @return a human readable string, representing the object.
   */
  @Override public String toString() {
    return "ErrorParameter { " + super.toString() + " "
        + "base = " + getBase() + ", "
        + "contentModelChangedErrorLabel = " + getContentModelChangedErrorLabel() + ", "
        + "draftOrPublishedErrorLabel = " + getDraftOrPublishedErrorLabel() + ", "
        + "error404Route = " + getError404Route() + ", "
        + "errorLabel = " + getErrorLabel() + ", "
        + "localeContentErrorLabel = " + getLocaleContentErrorLabel() + ", "
        + "responseData = " + getResponseData() + ", "
        + "somethingWentWrongLabel = " + getSomethingWentWrongLabel() + ", "
        + "stack = " + getStack() + ", "
        + "stackTraceErrorLabel = " + getStackTraceErrorLabel() + ", "
        + "stackTraceLabel = " + getStackTraceLabel() + ", "
        + "status = " + getStatus() + ", "
        + "tryLabel= " + getTryLabel() + ", "
        + "verifyCredentialsErrorLabel = " + getVerifyCredentialsErrorLabel() + " "
        + "}";
  }
}
