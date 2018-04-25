package com.contentful.tea.java.services.localization;

import com.contentful.tea.java.services.settings.Settings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Locale.getDefault;
import static org.apache.commons.io.FileUtils.readFileToString;

@Component
public class Localizer {

  @Autowired
  public Settings settings;

  private final Map<String, Map<String, String>> localizations = new HashMap<>();

  public Localizer() {
    final Gson gson = new GsonBuilder().create();
    populateLocalization(gson, "en-US");
    populateLocalization(gson, "de-DE");
  }

  public String localize(Keys key) {
    return localize(settings.getLocale(), key);
  }

  String localize(String locale, Keys key) {
    final Map<String, String> localized = localizations.get(locale);
    if (localized != null) {
      return localized.get(key.name());
    } else {
      return localizations.get("en-US").get(key.name());
    }
  }

  @SuppressWarnings("unchecked")
  private void populateLocalization(Gson gson, String locale) {
    final String path = format(getDefault(), "src/main/resources/locales/%s.json", locale);
    try {
      final String json = readFileToString(new File(path));
      final Map fromJson = gson.fromJson(json, Map.class);

      localizations.put(locale, fromJson);
    } catch (IOException e) {
      throw new IllegalStateException(format("Cannot find locale '%s' in '%s'.", locale, path), e);
    }
  }
}
