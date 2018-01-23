package com.contentful.tea.java.services.http;

import com.contentful.tea.java.MainController;
import com.contentful.tea.java.services.contentful.Contentful;
import com.contentful.tea.java.services.settings.Settings;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainController.class)
public class UrlParameterParserTest {
  @MockBean
  @SuppressWarnings("unused")
  private Settings settings;

  @MockBean
  @SuppressWarnings("unused")
  private Contentful contentful;

  @Autowired
  @SuppressWarnings("unused")
  private UrlParameterParser parser;

  @Test
  public void addOneParameterToEmptyQueryString() {
    given(settings.getQueryString()).willReturn("");

    parser.addToQueryString("name", "value");

    then(settings).should().setQueryString("?name=value");
  }

  @Test
  public void addTwoParameterToEmptyQueryString() {
    given(settings.getQueryString()).willReturn("?firstName=firstValue");

    parser.addToQueryString("secondName", "secondValue");

    then(settings).should().setQueryString("?firstName=firstValue&secondName=secondValue");
  }

  @Test
  public void updatesUrlValueIfAlreadySet() {
    given(settings.getQueryString()).willReturn("?name=firstValue");

    parser.addToQueryString("name", "secondValue");

    then(settings).should().setQueryString("?name=secondValue");
  }

}