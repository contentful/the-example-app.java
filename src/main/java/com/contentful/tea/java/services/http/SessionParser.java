package com.contentful.tea.java.services.http;

import com.contentful.tea.java.models.Settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

@Component
public class SessionParser {
  @Autowired
  @SuppressWarnings("unused")
  private Settings settings;

  private final Map<String, Manipulator> manipulatorsByNameMap;

  public SessionParser() {
    manipulatorsByNameMap = new HashMap<>();

    manipulatorsByNameMap.put(Constants.NAME_API, new Manipulator() {
      @Override public String get() {
        return settings.getApi();
      }

      @Override public void set(String value) {
        settings.setApi(value);
      }
    });
    manipulatorsByNameMap.put(Constants.NAME_SPACE_ID, new Manipulator() {
      @Override public String get() {
        return settings.getSpaceId();
      }

      @Override public void set(String value) {
        settings.setSpaceId(value);
      }
    });
    manipulatorsByNameMap.put(Constants.NAME_DELIVERY_TOKEN, new Manipulator() {
      @Override public String get() {
        return settings.getDeliveryAccessToken();
      }

      @Override public void set(String value) {
        settings.setDeliveryAccessToken(value);
      }
    });
    manipulatorsByNameMap.put(Constants.NAME_PREVIEW_TOKEN, new Manipulator() {
      @Override public String get() {
        return settings.getPreviewAccessToken();
      }

      @Override public void set(String value) {
        settings.setPreviewAccessToken(value);
      }
    });
    manipulatorsByNameMap.put(Constants.NAME_LOCALE, new Manipulator() {
      @Override public String get() {
        return settings.getLocale();
      }

      @Override public void set(String value) {
        settings.setLocale(value);
      }
    });
  }

  public void loadSession(HttpSession session) {
    final Enumeration<String> names = session.getAttributeNames();
    for (; names.hasMoreElements(); ) {
      final String name = names.nextElement();
      final Manipulator manipulator = manipulatorsByNameMap.get(name);

      if (manipulator != null) {
        final String value = (String) session.getAttribute(name);
        manipulator.set(value);
      }
    }
  }

  public void saveSession(HttpSession session) {
    manipulatorsByNameMap
        .keySet()
        .forEach(name -> session.setAttribute(name, manipulatorsByNameMap.get(name).get()));
  }

  interface Manipulator {
    String get();

    void set(String value);
  }
}
