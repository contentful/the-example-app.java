package com.contentful.tea.java.services.modelconverter;

import com.contentful.java.cda.CDAHttpException;
import com.contentful.tea.java.models.base.BaseParameter;
import com.contentful.tea.java.models.base.Locale;
import com.contentful.tea.java.models.errors.ErrorParameter;
import com.contentful.tea.java.models.exceptions.TeaException;
import com.contentful.tea.java.services.StaticContentSetter;
import com.contentful.tea.java.services.contentful.Contentful;
import com.contentful.tea.java.services.localization.Keys;
import com.contentful.tea.java.services.localization.Localizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

@Component
public class ExceptionToErrorParameter implements Converter<Throwable, ErrorParameter> {

  @Autowired
  @SuppressWarnings("unused")
  StaticContentSetter staticContentSetter;

  @Autowired
  @SuppressWarnings("unused")
  Localizer localizer;

  @Autowired
  @SuppressWarnings("unused")
  Contentful contentful;

  @Override
  public ErrorParameter convert(Throwable source) {
    final ErrorParameter errorParameter = new ErrorParameter();
    final BaseParameter base = errorParameter.getBase();
    staticContentSetter.applyErrorContent(base);
    base.getLocales()
        .setCurrentLocaleName("U.S. English")
        .setCurrentLocaleCode("en-US")
        .addLocale(
            new Locale()
                .setCode("en-US")
                .setName("U.S. English")
                .setCssClass(Locale.CSS_CLASS_ACTIVE),
            new Locale()
                .setCode("de-DE")
                .setName("Germany (Germany)")
                .setCssClass("")
        )
    ;

    base.getMeta().setTitle(t(Keys.errorOccurredTitleLabel));

    return errorParameter
        .setHints(hintKeysToHints(source))
        .setResponseDataLabel(t(Keys.errorLabel))
        .setSomethingWentWrongLabel(t(Keys.somethingWentWrongLabel))
        .setResponseData(getResponseData(source))
        .setStatus(404)
        .setTryLabel(t(Keys.hintsLabel))
        .setStackTraceLabel(t(Keys.stackTraceLabel))
        .setStack(getStackTrace(source))
        .setUseCustomCredentials(contentful.isUsingCustomCredentials())
        .setResetCredentialsLabel(t(Keys.resetCredentialsLabel))
        ;
  }

  private List<String> hintKeysToHints(Throwable source) {
    final List<Keys> keys = source instanceof TeaException ? ((TeaException) source).createHints() : Collections.singletonList(Keys.notFoundErrorHint);
    return keys
        .stream()
        .filter(Objects::nonNull)
        .map(this::t)
        .filter(not(String::isEmpty))
        .collect(toList());
  }

  private static <T> Predicate<T> not(Predicate<T> t) {
    return t.negate();
  }

  private String getResponseData(Throwable source) {
    final Throwable cause = source.getCause();
    return cause instanceof CDAHttpException ? cause.toString() : null;
  }

  public String t(Keys key) {
    return localizer.localize(key);
  }

}
