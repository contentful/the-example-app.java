package com.contentful.tea.java.models.exceptions;

public class RedirectException extends RuntimeException {
  private final String whereTo;

  public RedirectException(String whereTo) {
    this.whereTo = whereTo;
  }

  public String getWhereTo() {
    return whereTo;
  }
}
