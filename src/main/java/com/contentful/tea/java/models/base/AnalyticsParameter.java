package com.contentful.tea.java.models.base;

import com.contentful.tea.java.models.mappable.MappableType;

import java.util.Objects;

public class AnalyticsParameter extends MappableType {
  private String spaceId;

  public String getSpaceId() {
    return spaceId;
  }

  public AnalyticsParameter setSpaceId(String spaceId) {
    this.spaceId = spaceId;
    return this;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AnalyticsParameter)) return false;
    final AnalyticsParameter that = (AnalyticsParameter) o;
    return Objects.equals(getSpaceId(), that.getSpaceId());
  }

  @Override public int hashCode() {
    return Objects.hash(getSpaceId());
  }

  @Override public String toString() {
    return "AnalyticsParameter { " + super.toString() + " "
        + "spaceId = " + getSpaceId() + " "
        + "}";
  }
}
