package com.contentful.tea.java.services.http;

import com.contentful.tea.java.services.contentful.Contentful;
import com.contentful.tea.java.services.settings.Settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import static com.contentful.tea.java.services.http.Constants.EDITORIAL_FEATURES_DISABLED;
import static com.contentful.tea.java.services.http.Constants.EDITORIAL_FEATURES_ENABLED;

@Component
public class SessionParser {
  private static final int TIME_48_HOURS = 48 * 60 * 60;
  @Autowired
  @SuppressWarnings("unused")
  private Contentful contentful;

  @Autowired
  @SuppressWarnings("unused")
  private Settings settings;

  private final Map<String, Manipulator> manipulatorsByNameMap;

  public SessionParser() {
    manipulatorsByNameMap = new HashMap<>();
    manipulatorsByNameMap.put(Constants.NAME_SPACE_ID, new Manipulator<String>() {
      @Override public String get() {
        return contentful.getSpaceId();
      }

      @Override public void set(String value) {
        contentful.setSpaceId(value);
      }
    });
    manipulatorsByNameMap.put(Constants.NAME_DELIVERY_TOKEN, new Manipulator<String>() {
      @Override public String get() {
        return contentful.getDeliveryAccessToken();
      }

      @Override public void set(String value) {
        contentful.setDeliveryAccessToken(value);
      }
    });
    manipulatorsByNameMap.put(Constants.NAME_PREVIEW_TOKEN, new Manipulator<String>() {
      @Override public String get() {
        return contentful.getPreviewAccessToken();
      }

      @Override public void set(String value) {
        contentful.setPreviewAccessToken(value);
      }
    });
    manipulatorsByNameMap.put(Constants.NAME_EDITORIAL_FEATURES, new Manipulator<String>() {
      @Override public String get() {
        return settings.areEditorialFeaturesEnabled() ? EDITORIAL_FEATURES_ENABLED : EDITORIAL_FEATURES_DISABLED;
      }

      @Override public void set(String value) {
        settings.setEditorialFeaturesEnabled(value != null && value.toLowerCase().equals(EDITORIAL_FEATURES_ENABLED));
      }
    });
  }

  public void loadFromSession(HttpSession session) {
    final Enumeration<String> names = session.getAttributeNames();
    for (; names.hasMoreElements(); ) {
      final String name = names.nextElement();
      final Manipulator manipulator = manipulatorsByNameMap.get(name);

      if (manipulator != null) {
        final Object value = session.getAttribute(name);
        manipulator.set(value);
      }
    }

    session.setMaxInactiveInterval(TIME_48_HOURS);
  }

  public void saveToSession(HttpSession session) {
    manipulatorsByNameMap
        .keySet()
        .forEach(name -> session.setAttribute(name, manipulatorsByNameMap.get(name).get()));
  }

  interface Manipulator<T> {
    T get();

    void set(T value);
  }
}
