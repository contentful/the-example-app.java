package com.contentful.tea.java.models.courses.lessons.modules;

import java.util.Objects;

public class CopyModule extends Module {
  private String copy;

  public CopyModule() {
    super("copy");
  }

  public String getCopy() {
    return copy;
  }

  public CopyModule setCopy(String copy) {
    this.copy = copy;
    return this;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CopyModule)) return false;
    if (!super.equals(o)) return false;
    final CopyModule that = (CopyModule) o;
    return Objects.equals(getCopy(), that.getCopy());
  }

  @Override public int hashCode() {
    return Objects.hash(super.hashCode(), getCopy());
  }

  @Override public String toString() {
    return "CopyModule { " + super.toString() + " "
        + "copy = " + getCopy() + " "
        + "}";
  }
}
