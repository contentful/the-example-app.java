package com.contentful.tea.java.models.courses.lessons.modules;

import java.util.Objects;

public class CodeModule extends Module {
  private String curl;
  private String dotNet;
  private String javascript;
  private String java;
  private String javaAndroid;
  private String php;
  private String python;
  private String ruby;
  private String swift;

  public CodeModule() {
    super("code");
  }

  public String getCurl() {
    return curl;
  }

  public CodeModule setCurl(String curl) {
    this.curl = curl;
    return this;
  }

  public String getDotNet() {
    return dotNet;
  }

  public CodeModule setDotNet(String dotNet) {
    this.dotNet = dotNet;
    return this;
  }

  public String getJavascript() {
    return javascript;
  }

  public CodeModule setJavascript(String javascript) {
    this.javascript = javascript;
    return this;
  }

  public String getJava() {
    return java;
  }

  public CodeModule setJava(String java) {
    this.java = java;
    return this;
  }

  public String getJavaAndroid() {
    return javaAndroid;
  }

  public CodeModule setJavaAndroid(String javaAndroid) {
    this.javaAndroid = javaAndroid;
    return this;
  }

  public String getPhp() {
    return php;
  }

  public CodeModule setPhp(String php) {
    this.php = php;
    return this;
  }

  public String getPython() {
    return python;
  }

  public CodeModule setPython(String python) {
    this.python = python;
    return this;
  }

  public String getRuby() {
    return ruby;
  }

  public CodeModule setRuby(String ruby) {
    this.ruby = ruby;
    return this;
  }

  public String getSwift() {
    return swift;
  }

  public CodeModule setSwift(String swift) {
    this.swift = swift;
    return this;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CodeModule)) return false;
    if (!super.equals(o)) return false;
    final CodeModule that = (CodeModule) o;
    return Objects.equals(getCurl(), that.getCurl()) &&
        Objects.equals(getDotNet(), that.getDotNet()) &&
        Objects.equals(getJavascript(), that.getJavascript()) &&
        Objects.equals(getJava(), that.getJava()) &&
        Objects.equals(getJavaAndroid(), that.getJavaAndroid()) &&
        Objects.equals(getPhp(), that.getPhp()) &&
        Objects.equals(getPython(), that.getPython()) &&
        Objects.equals(getRuby(), that.getRuby()) &&
        Objects.equals(getSwift(), that.getSwift());
  }

  @Override public int hashCode() {
    return Objects.hash(super.hashCode(), getCurl(), getDotNet(), getJavascript(), getJava(), getJavaAndroid(), getPhp(), getPython(), getRuby(), getSwift());
  }

  @Override public String toString() {
    return "CodeModule { " + super.toString() + " "
        + "curl = " + getCurl() + ", "
        + "dotNet = " + getDotNet() + ", "
        + "java = " + getJava() + ", "
        + "javaAndroid = " + getJavaAndroid() + ", "
        + "javascript = " + getJavascript() + ", "
        + "php = " + getPhp() + ", "
        + "python = " + getPython() + ", "
        + "ruby = " + getRuby() + ", "
        + "swift = " + getSwift() + " "
        + "}";
  }
}
