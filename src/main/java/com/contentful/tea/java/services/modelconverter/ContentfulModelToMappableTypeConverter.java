package com.contentful.tea.java.services.modelconverter;

import com.contentful.java.cda.CDAEntry;
import com.contentful.tea.java.markdown.CommonmarkMarkdownParser;
import com.contentful.tea.java.services.settings.Settings;
import com.contentful.tea.java.models.mappable.MappableType;
import com.contentful.tea.java.services.localization.Keys;
import com.contentful.tea.java.services.localization.LocalizedStringsProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public abstract class ContentfulModelToMappableTypeConverter<ContentfulModel, ViewModel extends MappableType> implements Converter<ContentfulModel, ViewModel> {

  @Autowired
  @SuppressWarnings("unused")
  protected Settings settings;

  @Autowired
  @SuppressWarnings("unused")
  protected LocalizedStringsProvider localizer;

  @Autowired
  @SuppressWarnings("unused")
  protected CommonmarkMarkdownParser markdown;

  protected boolean isDraft(CDAEntry module) {
    return false;
  }

  protected boolean hasPendingChanges(CDAEntry module) {
    return false;
  }

  protected String t(Keys translateKey) {
    return localizer.localize(translateKey);
  }

  protected String m(String raw) {
    return markdown.parse(raw);
  }
}
