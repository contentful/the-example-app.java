package com.contentful.tea.java.services.http;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class NonInjectedUrlParameterParserTest {
  private UrlParameterParser parser;

  @Before
  public void setup() {
    parser = new UrlParameterParser();
  }

  @Test
  public void addOneParameterToEmptyQueryString() {
    final String subject = parser.addToQueryString("", "name", "value");

    assertThat(subject).isEqualTo("?name=value");
  }

  @Test
  public void addTwoParameterToEmptyQueryString() {
    final String subject = parser.addToQueryString("?firstName=firstValue", "secondName", "secondValue");

    assertThat(subject).isEqualTo("?firstName=firstValue&secondName=secondValue");
  }

  @Test
  public void updatesUrlValueIfAlreadySet() {
    final String subject = parser.addToQueryString("?name=firstValue", "name", "secondValue");

    assertThat(subject).isEqualTo("?name=secondValue");
  }
}