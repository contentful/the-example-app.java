package com.contentful.tea.java.models.errors;

import com.contentful.tea.java.models.base.BaseParameter;
import com.contentful.tea.java.models.mappable.MappableType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ErrorParameter extends MappableType {
  private BaseParameter base = new BaseParameter();

  private String responseData;
  private String responseDataLabel;
  private String stack;
  private String somethingWentWrongLabel;
  private String stackTraceLabel;
  private String tryLabel;
  private boolean useCustomCredentials;
  private String resetCredentialsLabel;
  private int status;
  private List<String> hints = new ArrayList<>();

  public BaseParameter getBase() {
    return base;
  }

  public String getResponseData() {
    return responseData;
  }

  public ErrorParameter setResponseData(String responseData) {
    this.responseData = responseData;
    return this;
  }

  public String getResponseDataLabel() {
    return responseDataLabel;
  }

  public ErrorParameter setResponseDataLabel(String responseDataLabel) {
    this.responseDataLabel = responseDataLabel;
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

  public List<String> getHints() {
    return hints;
  }

  public ErrorParameter setHints(List<String> hints) {
    this.hints.clear();
    this.hints.addAll(hints);

    return this;
  }

  public ErrorParameter addHint(String hint) {
    this.hints.add(hint);
    return this;
  }

  public boolean usesCustomCredentials() {
    return useCustomCredentials;
  }

  public ErrorParameter setUseCustomCredentials(boolean useCustomCredentials) {
    this.useCustomCredentials = useCustomCredentials;
    return this;
  }

  public String getResetCredentialsLabel() {
    return resetCredentialsLabel;
  }

  public ErrorParameter setResetCredentialsLabel(String resetCredentialsLabel) {
    this.resetCredentialsLabel = resetCredentialsLabel;
    return this;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ErrorParameter)) return false;
    final ErrorParameter that = (ErrorParameter) o;
    return getStatus() == that.getStatus() &&
        Objects.equals(getBase(), that.getBase()) &&
        Objects.equals(getResponseData(), that.getResponseData()) &&
        Objects.equals(getResponseDataLabel(), that.getResponseDataLabel()) &&
        Objects.equals(getStack(), that.getStack()) &&
        Objects.equals(getSomethingWentWrongLabel(), that.getSomethingWentWrongLabel()) &&
        Objects.equals(getTryLabel(), that.getTryLabel()) &&
        Objects.equals(getStackTraceLabel(), that.getStackTraceLabel()) &&
        Objects.equals(getResetCredentialsLabel(), that.getResetCredentialsLabel()) &&
        Objects.equals(usesCustomCredentials(), that.usesCustomCredentials()) &&
        Objects.equals(getHints(), that.getHints());
  }

  @Override public int hashCode() {
    return Objects.hash(getBase(), getResponseData(), getStack(), getStackTraceLabel(), getSomethingWentWrongLabel(), getStackTraceLabel(), getStatus(), getTryLabel());
  }

  @Override public String toString() {
    return "ErrorParameter { " + super.toString() + " "
        + "base = " + getBase() + ", "
        + "responseData = " + getResponseData() + ", "
        + "stack = " + getStack() + ", "
        + "somethingWentWrongLabel = " + getSomethingWentWrongLabel() + ", "
        + "stackTraceLabel = " + getStackTraceLabel() + ", "
        + "status = " + getStatus() + ", "
        + "hints = " + String.join(",", hints) + ", "
        + "tryLabel= " + getTryLabel() + " "
        + "}";
  }
}
