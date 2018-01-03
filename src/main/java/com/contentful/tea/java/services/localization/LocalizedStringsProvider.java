package com.contentful.tea.java.services.localization;

public interface LocalizedStringsProvider {
  String localize(Keys key);
  String localize(String locale, Keys key);
}
