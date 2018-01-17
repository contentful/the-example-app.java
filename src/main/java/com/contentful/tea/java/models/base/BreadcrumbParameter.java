package com.contentful.tea.java.models.base;

import com.contentful.tea.java.models.mappable.MappableType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BreadcrumbParameter extends MappableType {

  public static class Breadcrumb {
    private String url;
    private String label;

    public String getUrl() {
      return url;
    }

    public Breadcrumb setUrl(String url) {
      this.url = url;
      return this;
    }

    public String getLabel() {
      return label;
    }

    public Breadcrumb setLabel(String label) {
      this.label = label;
      return this;
    }

    @Override public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Breadcrumb)) return false;
      final Breadcrumb that = (Breadcrumb) o;
      return Objects.equals(getUrl(), that.getUrl()) &&
          Objects.equals(getLabel(), that.getLabel());
    }

    @Override public int hashCode() {
      return Objects.hash(getUrl(), getLabel());
    }


    /**
     * @return a human readable string, representing the object.
     */
    @Override public String toString() {
      return "Breadcrumb { "
          + "label = " + getLabel() + ", "
          + "url = " + getUrl() + " "
          + "}";
    }
  }

  private List<Breadcrumb> breadcrumbs = new ArrayList<>();

  public List<Breadcrumb> getBreadcrumbs() {
    return breadcrumbs;
  }

  public BreadcrumbParameter addBreadcrumb(Breadcrumb... breadcrumbs) {
    this.breadcrumbs.addAll(Arrays.asList(breadcrumbs));
    return this;
  }

  public BreadcrumbParameter setBreadcrumbs(List<Breadcrumb> breadcrumbs) {
    this.breadcrumbs = breadcrumbs;
    return this;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof BreadcrumbParameter)) return false;
    final BreadcrumbParameter that = (BreadcrumbParameter) o;
    return Objects.equals(getBreadcrumbs(), that.getBreadcrumbs());
  }

  @Override public int hashCode() {
    return Objects.hash(getBreadcrumbs());
  }

  @Override public String toString() {
    return "BreadcrumbParameter { " + super.toString() + " "
        + "breadcrumbs = " + getBreadcrumbs() + " "
        + "}";
  }
}
