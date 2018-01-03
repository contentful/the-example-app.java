package com.contentful.tea.java.services.modelconverter;

import com.contentful.tea.java.models.errors.ErrorParameter;
import com.contentful.tea.java.services.StaticContentSetter;
import com.contentful.tea.java.services.localization.Keys;
import com.contentful.tea.java.services.localization.LocalizedStringsProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

@Component
public class ExceptionToErrorParameter implements Converter<Exception, ErrorParameter> {

  @Autowired
  @SuppressWarnings("unused")
  private StaticContentSetter staticContentSetter;

  @Autowired
  @SuppressWarnings("unused")
  private LocalizedStringsProvider localizer;

  @Override
  public ErrorParameter convert(Exception source) {
    final ErrorParameter errorParameter = new ErrorParameter();
    staticContentSetter.applyBaseContent(errorParameter.getBase());

    return errorParameter
        .setContentModelChangedErrorLabel(t(Keys.contentModelChangedErrorLabel))
        .setDraftOrPublishedErrorLabel(t(Keys.draftOrPublishedErrorLabel))
        .setError404Route(t(Keys.error404Route))
        .setErrorLabel(t(Keys.errorLabel))
        .setLocaleContentErrorLabel(t(Keys.localeContentErrorLabel))
        .setSomethingWentWrongLabel(t(Keys.somethingWentWrongLabel))
        .setStackTraceErrorLabel(t(Keys.stackTraceErrorLabel))
        .setStackTraceLabel(t(Keys.stackTraceLabel))
        .setTryLabel(t(Keys.tryLabel))
        .setVerifyCredentialsErrorLabel(t(Keys.verifyCredentialsErrorLabel))
        .setStack(getStackTrace(source))
        .setStatus(exceptionToStatusCode(source))
        .setResponseData(source.getMessage())
        ;
  }

  private int exceptionToStatusCode(Exception source) {
    if (source instanceof FileNotFoundException) {
      return 404;
    }
    if (source instanceof IOException) {
      return 500;
    }

    return 500;
  }

  private String t(Keys key) {
    return localizer.localize(key);
  }

}
