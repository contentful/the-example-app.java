package com.contentful.tea.java.models.base;

import com.contentful.tea.java.models.mappable.MappableType;

import java.util.Objects;

public class BaseParameter extends MappableType {
  private ApiParameter api = new ApiParameter();
  private LocalesParameter locales = new LocalesParameter();
  private MetaParameter meta = new MetaParameter();
  private BreadcrumbParameter breadcrumb = new BreadcrumbParameter();

  public ApiParameter getApi() {
    return api;
  }

  public BaseParameter setApi(ApiParameter api) {
    this.api = api;
    return this;
  }

  public LocalesParameter getLocales() {
    return locales;
  }

  public BaseParameter setLocales(LocalesParameter locales) {
    this.locales = locales;
    return this;
  }

  public MetaParameter getMeta() {
    return meta;
  }

  public BaseParameter setMeta(MetaParameter meta) {
    this.meta = meta;
    return this;
  }

  public BreadcrumbParameter getBreadcrumb() {
    return breadcrumb;
  }

  public void setBreadcrumb(BreadcrumbParameter breadcrumb) {
    this.breadcrumb = breadcrumb;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof BaseParameter)) return false;
    final BaseParameter that = (BaseParameter) o;
    return Objects.equals(getApi(), that.getApi()) &&
        Objects.equals(getLocales(), that.getLocales()) &&
        Objects.equals(getBreadcrumb(), that.getBreadcrumb()) &&
        Objects.equals(getMeta(), that.getMeta());
  }

  @Override public int hashCode() {
    return Objects.hash(getApi(), getLocales(), getBreadcrumb(), getMeta());
  }

  /**
   * @return a human readable string, representing the object.
   */
  @Override public String toString() {
    return "BaseParameter { " + super.toString() + " "
        + "api = " + getApi() + ", "
        + "breadcrumb = " + getBreadcrumb() + ", "
        + "locales = " + getLocales() + ", "
        + "meta = " + getMeta() + " "
        + "}";
  }
}
