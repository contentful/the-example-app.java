package com.contentful.tea.java.services.http;

import com.contentful.tea.java.services.contentful.Contentful;
import com.contentful.tea.java.services.settings.Settings;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.contentful.tea.java.services.contentful.Contentful.API_CDA;
import static com.contentful.tea.java.services.contentful.Contentful.API_CPA;
import static com.contentful.tea.java.services.http.Constants.EDITORIAL_FEATURES_ENABLED;
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

  private final Map<String, Manipulator> manipulatorsByNameMap;

  public UrlParameterParser() {
    manipulatorsByNameMap = new HashMap<>();

    manipulatorsByNameMap.put(Constants.NAME_API, new Manipulator() {
      @Override public void fromUrlParameterValueToApp(String value) {
        if (!API_CPA.equals(value) && !API_CPA.equals(value)) {
          value = API_CDA;
        }

        contentful.setApi(value);
        final String queryString = addToQueryString(settings.getQueryString(), Constants.NAME_API, value);
        settings.setQueryString(queryString);
      }

      @Override public String fromAppToUrlParameterValue() {
        return contentful.getApi();
      }
    });
    manipulatorsByNameMap.put(Constants.NAME_LOCALE, new Manipulator() {
      @Override public void fromUrlParameterValueToApp(String value) {
        if (value == null || value.isEmpty()) {
          throw new IllegalStateException("Locale cannot be empty!");
        }
        settings.setLocale(value);
        final String queryString = addToQueryString(settings.getQueryString(), Constants.NAME_LOCALE, value);
        settings.setQueryString(queryString);
      }

      @Override public String fromAppToUrlParameterValue() {
        return settings.getLocale();
      }
    });
    manipulatorsByNameMap.put(Constants.NAME_SPACE_ID, new Manipulator() {
      @Override public void fromUrlParameterValueToApp(String value) {
        if (value == null || value.isEmpty()) {
          throw new IllegalStateException("Spaceid cannot be empty!");
        }
        contentful.setSpaceId(value);
      }

      @Override public String fromAppToUrlParameterValue() {
        return contentful.getSpaceId();
      }
    });
    manipulatorsByNameMap.put(Constants.NAME_EDITORIAL_FEATURES, new Manipulator() {
      @Override public void fromUrlParameterValueToApp(String value) {
        switch (value) {
          case EDITORIAL_FEATURES_ENABLED:
            settings.setEditorialFeaturesEnabled(true);
            break;
          default:
            settings.setEditorialFeaturesEnabled(false);
            break;
        }
      }

      @Override public String fromAppToUrlParameterValue() {
        if (settings.areEditorialFeaturesEnabled()) {
          return EDITORIAL_FEATURES_ENABLED;
        } else {
          return null;
        }
      }
    });
    manipulatorsByNameMap.put(Constants.NAME_DELIVERY_TOKEN, new Manipulator() {
      @Override public void fromUrlParameterValueToApp(String value) {
        if (value == null || value.isEmpty()) {
          throw new IllegalStateException("Delivery token cannot be empty!");
        }
        contentful.setDeliveryAccessToken(value);
      }

      @Override public String fromAppToUrlParameterValue() {
        return contentful.getDeliveryAccessToken();
      }
    });
    manipulatorsByNameMap.put(Constants.NAME_PREVIEW_TOKEN, new Manipulator() {
      @Override public void fromUrlParameterValueToApp(String value) {
        contentful.setPreviewAccessToken(value);
        if (value == null || value.isEmpty()) {
          throw new IllegalStateException("Preview token cannot be empty!");
        }
      }

      @Override public String fromAppToUrlParameterValue() {
        return contentful.getPreviewAccessToken();
      }
    });
  }

  public void urlParameterToApp(Map<String, String[]> urlParameterMap) {
    if (urlParameterMap != null) {
      List<IllegalStateException> exceptions = new ArrayList<>();

      for (final String urlParameterKey : urlParameterMap.keySet()) {
        final String[] values = urlParameterMap.get(urlParameterKey);
        if (manipulatorsByNameMap.containsKey(urlParameterKey)) {
          for (final String value : values) {
            try {
              manipulatorsByNameMap.get(urlParameterKey).fromUrlParameterValueToApp(value);
            } catch (IllegalStateException e) {
              exceptions.add(e);
            }
          }
        } else {
          System.err.println("Undefined parameter found: '" + urlParameterKey + "': '" + Arrays.toString(values) + "'. Ignoring it.");
        }
      }

      if (exceptions.size() > 0) {
        final String exceptionsString = join("\n\t", exceptions.stream().map(ExceptionUtils::getMessage).collect(toList()));
        throw new IllegalStateException("Following errors occurred while parsing the new configuration:\n\t" + exceptionsString);
      }
    }
  }

  public String appToUrlParameter() {
    String result = "";

    for (final Map.Entry<String, Manipulator> e : manipulatorsByNameMap.entrySet()) {
      final String value = e.getValue().fromAppToUrlParameterValue();
      if (value != null && !value.isEmpty() && !"false".equals(value)) {
        result = addToQueryString(result, e.getKey(), value);
      }
    }

    return result;
  }

  String addToQueryString(String query, String name, String value) {
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

    return urlParameters.size() > 0 ? "?" + queryString : "";
  }

  abstract class Manipulator {
    public abstract void fromUrlParameterValueToApp(String value);

    public abstract String fromAppToUrlParameterValue();
  }
}
