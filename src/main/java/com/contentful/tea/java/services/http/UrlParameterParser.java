package com.contentful.tea.java.services.http;

import com.contentful.tea.java.services.contentful.Contentful;
import com.contentful.tea.java.services.settings.Settings;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.contentful.tea.java.services.contentful.Contentful.API_CDA;
import static com.contentful.tea.java.services.contentful.Contentful.API_CPA;
import static java.lang.String.join;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
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
        switch (value) {
          case API_CPA:
          case API_CDA:
            contentful.setApi(value);
            addToQueryString(Constants.NAME_API, value);
            break;
          default:
            throw new IllegalStateException("API cannot be of value '" + value + "'. Only '" + API_CDA + "' and '" + API_CPA + "' are allowed.");
        }
      }
    });
    parsersByNameMap.put(Constants.NAME_SPACE_ID, new Parser() {
      @Override public void parse(String value) {
        contentful.setSpaceId(value);
        if (value == null || value.isEmpty()) {
          throw new IllegalStateException("Spaceid cannot be empty!");
        }
      }
    });
    parsersByNameMap.put(Constants.NAME_LOCALE, new Parser() {
      @Override public void parse(String value) {
        settings.setLocale(value);
        addToQueryString(Constants.NAME_LOCALE, value);
        if (value == null || value.isEmpty()) {
          throw new IllegalStateException("Locale cannot be empty!");
        }
      }
    });
    parsersByNameMap.put(Constants.NAME_EDITORIAL_FEATURES, new Parser() {
      @Override public void parse(String value) {
        switch (value) {
          case "true":
          case "false":
            settings.setEditorialFeaturesEnabled(Boolean.valueOf(value));
            break;
          default:
            throw new IllegalStateException("Editorial features cannot set to '" + value + "'. Only 'true' and 'false' are allowed.");
        }
      }
    });
    parsersByNameMap.put(Constants.NAME_DELIVERY_TOKEN, new Parser() {
      @Override public void parse(String value) {
        contentful.setDeliveryAccessToken(value);
        if (value == null || value.isEmpty()) {
          throw new IllegalStateException("Delivery token cannot be empty!");
        }
      }
    });
    parsersByNameMap.put(Constants.NAME_PREVIEW_TOKEN, new Parser() {
      @Override public void parse(String value) {
        contentful.setPreviewAccessToken(value);
        if (value == null || value.isEmpty()) {
          throw new IllegalStateException("Preview token cannot be empty!");
        }
      }
    });
  }

  public void parseUrlParameter(Map<String, String[]> urlParameterMap) {
    if (urlParameterMap != null) {
      List<IllegalStateException> exceptions = new ArrayList<>();

      for (final String urlParameterKey : urlParameterMap.keySet()) {
        if (parsersByNameMap.containsKey(urlParameterKey)) {
          final String[] values = urlParameterMap.get(urlParameterKey);
          for (final String value : values) {
            try {
              parsersByNameMap.get(urlParameterKey).parse(value);
            } catch (IllegalStateException e) {
              exceptions.add(e);
            }
          }
        }
      }

      if (exceptions.size() > 0) {
        final String exceptionsString = join("\n\t", exceptions.stream().map(ExceptionUtils::getMessage).collect(toList()));
        throw new IllegalStateException("Following errors occurred while parsing the new configuration:\n\t" + exceptionsString);
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
        .collect(toList())
        .stream()
        .reduce((result, element) -> result += "&" + element)
        .get();

    settings.setQueryString(urlParameters.size() > 0 ? "?" + queryString : "");
  }

  abstract class Parser {
    public abstract void parse(String value);
  }
}
