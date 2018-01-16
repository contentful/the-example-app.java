package com.contentful.tea.java.services.modelconverter;

import com.contentful.tea.java.MainController;
import com.contentful.tea.java.models.errors.ErrorParameter;
import com.contentful.tea.java.services.contentful.Contentful;
import com.contentful.tea.java.services.localization.Keys;
import com.contentful.tea.java.services.localization.LocalizedStringsProvider;

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
  private LocalizedStringsProvider localizer;

  @Autowired
  @SuppressWarnings("unused")
  private Contentful contentful;

  @Before
  public void setup() {
    contentful.loadDefaults();
  }

  @After
  public void tearDown() {
    contentful.reset();
  }

  @Test
  public void convertIoExceptionTo500ErrorParameter() {
    final ErrorParameter subject = converter.convert(new IOException("Could successfully convert exception"));

    verifyStaticErrorLabels(subject);

    assertThat(subject.getResponseData()).isEqualTo("Could successfully convert exception");
    assertThat(subject.getStack()).startsWith("java.io.IOException: Could successfully convert exception");
    assertThat(subject.getStatus()).isEqualTo(500);
  }

  private void verifyStaticErrorLabels(ErrorParameter subject) {
    assertThat(subject.getBase()).isNotNull();
    assertThat(subject.getErrorLabel()).isEqualTo(t(Keys.errorLabel));
    assertThat(subject.getContentModelChangedErrorLabel()).isEqualTo(t(Keys.contentModelChangedErrorLabel));
    assertThat(subject.getDraftOrPublishedErrorLabel()).isEqualTo(t(Keys.draftOrPublishedErrorLabel));
    assertThat(subject.getError404Route()).isEqualTo(t(Keys.error404Route));
    assertThat(subject.getLocaleContentErrorLabel()).isEqualTo(t(Keys.localeContentErrorLabel));
    assertThat(subject.getSomethingWentWrongLabel()).isEqualTo(t(Keys.somethingWentWrongLabel));
    assertThat(subject.getStackTraceErrorLabel()).isEqualTo(t(Keys.stackTraceErrorLabel));
    assertThat(subject.getStackTraceLabel()).isEqualTo(t(Keys.stackTraceLabel));
    assertThat(subject.getVerifyCredentialsErrorLabel()).isEqualTo(t(Keys.verifyCredentialsErrorLabel));
  }

  private String t(Keys errorLabel) {
    return localizer.localize(errorLabel);
  }

}