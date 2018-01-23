package com.contentful.tea.java.services.http;

import com.contentful.tea.java.services.contentful.Contentful;
import com.contentful.tea.java.services.settings.Settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toMap;

@Component
public class UrlParameterParser {

  @Autowired
  @SuppressWarnings("unused")
  private Contentful contentful;

  @Autowired
  @SuppressWarnings("unused")
  private Settings settings;

  private final Map<String, Parser> parsersByNameMap;

  public UrlParameterParser() {
    parsersByNameMap = new HashMap<>();

    parsersByNameMap.put(Constants.NAME_API, new Parser() {
      @Override public void parse(String value) {
        contentful.setApi(value);
        addToQueryString(Constants.NAME_API, value);
      }
    });
    parsersByNameMap.put(Constants.NAME_SPACE_ID, new Parser() {
      @Override public void parse(String value) {
        contentful.setSpaceId(value);
      }
    });
    parsersByNameMap.put(Constants.NAME_LOCALE, new Parser() {
      @Override public void parse(String value) {
        settings.setLocale(value);
        addToQueryString(Constants.NAME_LOCALE, value);
      }
    });
    parsersByNameMap.put(Constants.NAME_EDITORIAL_FEATURES, new Parser() {
      @Override public void parse(String value) {
        settings.setEditorialFeaturesEnabled(Boolean.valueOf(value));
      }
    });
    parsersByNameMap.put(Constants.NAME_DELIVERY_TOKEN, new Parser() {
      @Override public void parse(String value) {
        contentful.setDeliveryAccessToken(value);
      }
    });
    parsersByNameMap.put(Constants.NAME_PREVIEW_TOKEN, new Parser() {
      @Override public void parse(String value) {
        contentful.setPreviewAccessToken(value);
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

  void addToQueryString(String name, String value) {
    String query = settings.getQueryString();
    if (query == null) {
      query = "";
    }

    if (query.length() > 0 && query.charAt(0) == '?') {
      query = query.replaceFirst("\\?", "");
    }

    final List<String> urlParameters = new ArrayList<>(asList(query.split("&")));
    urlParameters.add(name + "=" + value);

    final String queryString = urlParameters
        .stream()
        .filter(parameter -> parameter != null && !parameter.isEmpty())
        .map(parameter -> parameter.split("="))
        .collect(
            toMap(
                split -> split[0], // key
                split -> split[1], // value
                (oldValue, newValue) -> newValue) // overwrite old value on key collision
        )
        .entrySet()
        .stream()
        .map(e -> e.getKey() + "=" + e.getValue())
        .collect(Collectors.toList())
        .stream()
        .reduce((result, element) -> result += "&" + element)
        .get();

    settings.setQueryString(urlParameters.size() > 0 ? "?" + queryString : "");
  }

  abstract class Parser {
    public abstract void parse(String value);
  }
}
