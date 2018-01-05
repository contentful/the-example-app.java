package com.contentful.tea.java.services.http;

import com.contentful.tea.java.models.Settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UrlParameterParser {

  @Autowired
  @SuppressWarnings("unused")
  private Settings settings;

  private final Map<String, Parser> parsersByNameMap;

  public UrlParameterParser() {
    parsersByNameMap = new HashMap<>();

    parsersByNameMap.put(Constants.NAME_API, new Parser() {
      @Override public void parse(String value) {
        settings.setApi(value);
      }
    });
    parsersByNameMap.put(Constants.NAME_SPACE_ID, new Parser() {
      @Override public void parse(String value) {
        settings.setSpaceId(value);
      }
    });
    parsersByNameMap.put(Constants.NAME_LOCALE, new Parser() {
      @Override public void parse(String value) {
        settings.setLocale(value);
      }
    });
    parsersByNameMap.put(Constants.NAME_DELIVERY_TOKEN, new Parser() {
      @Override public void parse(String value) {
        settings.setDeliveryAccessToken(value);
      }
    });
    parsersByNameMap.put(Constants.NAME_PREVIEW_TOKEN, new Parser() {
      @Override public void parse(String value) {
        settings.setPreviewAccessToken(value);
      }
    });
  }

  public void parseUrlParameter(Map<String, String[]> urlParameterMap) {
    if (urlParameterMap != null) {
      for (final String urlParameterKey : urlParameterMap.keySet()) {
        if (parsersByNameMap.containsKey(urlParameterKey)) {
          final String[] values = urlParameterMap.get(urlParameterKey);
          for (final String value : values) {
            parsersByNameMap.get(urlParameterKey).parse(value);
          }
        }
      }
    }
  }

  abstract class Parser {
    public abstract void parse(String value);
  }
}
