package com.contentful.tea.java.services.url;

import com.contentful.tea.java.models.Settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UrlParameterParser {

  public static final String NAME_DELIVERY_TOKEN = "CDA_ID";

  @Autowired
  @SuppressWarnings("unused")
  private Settings settings;

  private final Map<String, ParameterParser> parsersByName;

  public UrlParameterParser() {
    parsersByName = new HashMap<>();

    // TODO: Add more parsers!
    parsersByName.put(NAME_DELIVERY_TOKEN, new ParameterParser() {
      @Override public void parse(String value) {
        settings.setDeliveryAccessToken(value);
      }
    });
  }


  public void parseUrlParameter(Map<String, String> urlParameterMap) {
    if (urlParameterMap != null) {
      for (final String urlParameterKey : urlParameterMap.keySet()) {
        if (parsersByName.containsKey(urlParameterKey)) {
          final String value = urlParameterMap.get(urlParameterKey);
          parsersByName.get(urlParameterKey).parse(value);
        }
      }
    }
  }


  private static abstract class ParameterParser {
    public abstract void parse(String value);
  }
}
