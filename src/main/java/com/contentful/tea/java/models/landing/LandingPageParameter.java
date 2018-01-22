package com.contentful.tea.java.models.landing;

import com.contentful.tea.java.models.mappable.MappableType;
import com.contentful.tea.java.models.base.BaseParameter;
import com.contentful.tea.java.models.landing.modules.BaseModule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LandingPageParameter extends MappableType {

  private BaseParameter base = new BaseParameter();

  private List<BaseModule> modules = new ArrayList<>();

  public BaseParameter getBase() {
    return base;
  }

  public List<BaseModule> getModules() {
    return modules;
  }

  public LandingPageParameter setModules(List<BaseModule> modules) {
    this.modules = modules;
    return this;
  }

  public LandingPageParameter addModule(BaseModule... baseModules) {
    if (baseModules == null) {
      this.modules = new ArrayList<>();
    }
    this.modules.addAll(Arrays.asList(baseModules));
    return this;
  }

  @Override public String toString() {
    return "LandingPageParameter { " + super.toString() + " "
        + "base = " + getBase() + ", "
        + "modules = " + getModules() + " "
        + "}";
  }
}
