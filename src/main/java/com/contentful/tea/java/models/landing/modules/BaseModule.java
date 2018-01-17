package com.contentful.tea.java.models.landing.modules;

import com.contentful.tea.java.models.mappable.MappableType;

public class BaseModule extends MappableType {
  private final String type;

  public BaseModule(String type) {
    this.type = type;
  }

  @Override public String toString() {
    return "BaseModule { " + super.toString() + " "
        + "type = " + type + " "
        + "}";
  }
}
