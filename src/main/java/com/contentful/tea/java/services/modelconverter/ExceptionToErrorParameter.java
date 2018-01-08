package com.contentful.tea.java.services.modelconverter;

import com.contentful.java.cda.CDAHttpException;
import com.contentful.tea.java.models.base.BaseParameter;
import com.contentful.tea.java.models.base.Locale;
import com.contentful.tea.java.models.base.LocalesParameter;
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
public class ExceptionToErrorParameter implements Converter<Throwable, ErrorParameter> {

  @Autowired
  @SuppressWarnings("unused")
  private StaticContentSetter staticContentSetter;

  @Autowired
  @SuppressWarnings("unused")
  private LocalizedStringsProvider localizer;

  @Override
  public ErrorParameter convert(Throwable source) {
    final ErrorParameter errorParameter = new ErrorParameter();
    final BaseParameter base = errorParameter.getBase();
    staticContentSetter.applyErrorContent(base);
    base.setLocales(
        new LocalesParameter()
            .setLocaleLabel(t(Keys.locale))
            .setLocaleQuestion(t(Keys.localeQuestion))
            .setCurrentLocaleName("U.S. English")
            .setCurrentLocaleCode("en-US")
            .addLocale(
                new Locale()
                    .setCode("en-US")
                    .setName("EN-US")
                    .setCssClass(Locale.CSS_CLASS_ACTIVE))
    );


    return errorParameter
        .setContentModelChangedErrorLabel(t(Keys.contentModelChangedErrorLabel))
        .setDraftOrPublishedErrorLabel(t(Keys.draftOrPublishedErrorLabel))
        .setError404Route(t(Keys.error404Route))
        .setErrorLabel(t(Keys.errorLabel))
        .setLocaleContentErrorLabel(t(Keys.localeContentErrorLabel))
        .setResponseData(source.getMessage())
        .setSomethingWentWrongLabel(t(Keys.somethingWentWrongLabel))
        .setStack(getStackTrace(source))
        .setStackTraceErrorLabel(t(Keys.stackTraceErrorLabel))
        .setStackTraceLabel(t(Keys.stackTraceLabel))
        .setStatus(exceptionToStatusCode(source))
        .setTryLabel(t(Keys.tryLabel))
        .setVerifyCredentialsErrorLabel(t(Keys.verifyCredentialsErrorLabel))
        ;
  }

  private int exceptionToStatusCode(Throwable source) {
    if (source instanceof FileNotFoundException) {
      return 404;
    }

    if (source instanceof CDAHttpException) {
      final CDAHttpException cdaException = (CDAHttpException) source;
      return cdaException.responseCode();
    }

    if (source instanceof IOException) {
      return 500;
    }

    if (source.getMessage() == null) {
      return 404;
    }

    return 500;
  }

  private String t(Keys key) {
    return localizer.localize(key);
  }

}
