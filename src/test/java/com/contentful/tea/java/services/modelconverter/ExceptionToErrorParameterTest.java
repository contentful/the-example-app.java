package com.contentful.tea.java.services.modelconverter;

import com.contentful.tea.java.MainController;
import com.contentful.tea.java.models.errors.ErrorParameter;
import com.contentful.tea.java.services.contentful.Contentful;
import com.contentful.tea.java.services.localization.Keys;
import com.contentful.tea.java.services.localization.Localizer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainController.class)
public class ExceptionToErrorParameterTest {

  @Autowired
  @SuppressWarnings("unused")
  private ExceptionToErrorParameter converter;

  @Autowired
  @SuppressWarnings("unused")
  private Localizer localizer;

  @Autowired
  @SuppressWarnings("unused")
  private Contentful contentful;

  @Before
  public void setup() {
    contentful.loadFromPreferences();
  }

  @After
  public void tearDown() {
    contentful.reset();
  }

  @Test
  public void convertIoExceptionTo500ErrorParameter() {
    final ErrorParameter subject = converter.convert(new IOException("Could successfully convert exception"));

    verifyStaticErrorLabels(subject);

    assertThat(subject.getStack()).contains("Could successfully convert exception");
    assertThat(subject.getStatus()).isEqualTo(404);
  }

  private void verifyStaticErrorLabels(ErrorParameter subject) {
    assertThat(subject.getBase()).isNotNull();
    assertThat(subject.getSomethingWentWrongLabel()).isEqualTo(t(Keys.somethingWentWrongLabel));
  }

  private String t(Keys errorLabel) {
    return localizer.localize(errorLabel);
  }

}