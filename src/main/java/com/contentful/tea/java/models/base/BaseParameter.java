package com.contentful.tea.java.models.base;

import com.contentful.tea.java.models.mappable.MappableType;

import java.util.Objects;

public class BaseParameter extends MappableType {
  private ApiParameter api = new ApiParameter();
  private LocalesParameter locales = new LocalesParameter();
  private MetaParameter meta = new MetaParameter();
  private BreadcrumbParameter breadcrumb = new BreadcrumbParameter();
  private LabelsParameter labels = new LabelsParameter();

  public ApiParameter getApi() {
    return api;
  }

  public LocalesParameter getLocales() {
    return locales;
  }

  public MetaParameter getMeta() {
    return meta;
  }

  public BreadcrumbParameter getBreadcrumb() {
    return breadcrumb;
  }

  public LabelsParameter getLabels() {
    return labels;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof BaseParameter)) return false;
    final BaseParameter that = (BaseParameter) o;
    return Objects.equals(getApi(), that.getApi()) &&
        Objects.equals(getLocales(), that.getLocales()) &&
        Objects.equals(getBreadcrumb(), that.getBreadcrumb()) &&
        Objects.equals(getLabels(), that.getLabels()) &&
        Objects.equals(getMeta(), that.getMeta());
  }

  @Override public int hashCode() {
    return Objects.hash(getApi(), getLocales(), getLabels(), getBreadcrumb(), getMeta());
  }

  @Override public String toString() {
    return "BaseParameter { " + super.toString() + " "
        + "api = " + getApi() + ", "
        + "breadcrumb = " + getBreadcrumb() + ", "
        + "locales = " + getLocales() + ", "
        + "labels = " + getLabels() + ", "
        + "meta = " + getMeta() + " "
        + "}";
  }
}
