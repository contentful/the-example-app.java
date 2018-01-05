package com.contentful.tea.java.models;

import com.contentful.java.cda.CDAClient;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

@Component
public class Settings {

  public static final String API_CDA = "cda";
  public static final String API_CPA = "cpa";

  private static final String DEFAULT_LOCALE = "en-US";

  private String queryString;
  private String path;
  private String locale = DEFAULT_LOCALE;

  private String api = API_CDA;

  private String spaceId;
  private String deliveryAccessToken;
  private String previewAccessToken;

  CDAClient contentfulDeliveryClient;
  CDAClient contentfulPreviewClient;

  public Settings reset() {
    queryString = null;
    path = null;
    locale = DEFAULT_LOCALE;
    api = API_CDA;
    spaceId = null;
    deliveryAccessToken = null;
    previewAccessToken = null;
    contentfulDeliveryClient = null;
    contentfulPreviewClient = null;
    return this;
  }

  public Settings loadDefaults() {
    Properties properties = new Properties();

    try {
      final InputStream input = Settings.class.getClassLoader().getResourceAsStream("tea.properties");
      properties.load(input);

      setSpaceId(properties.getProperty("spaceId", ""));
      setDeliveryAccessToken(properties.getProperty("deliveryToken", ""));
      setPreviewAccessToken(properties.getProperty("previewToken", ""));

    } catch (IOException exception) {
      throw new IllegalStateException("Could not load default settings.", exception);
    }

    return this;
  }


  public String getLocale() {
    return locale;
  }

  public Settings setLocale(String locale) {
    this.locale = locale;
    return this;
  }

  public String getApi() {
    return api;
  }

  public Settings setApi(String api) {
    if (API_CDA.equals(api) || API_CPA.equals(api)) {
      this.api = api;
    }
    return this;
  }

  public String getQueryString() {
    return queryString;
  }

  public Settings setQueryString(String queryString) {
    this.queryString = queryString;
    return this;
  }

  public String getPath() {
    return path;
  }

  public Settings setPath(String path) {
    this.path = path;
    return this;
  }

  private CDAClient getContentfulDeliveryClient() {
    if (contentfulDeliveryClient == null) {
      contentfulDeliveryClient = CDAClient
          .builder()
          .setSpace(getSpaceId())
          .setToken(getDeliveryAccessToken())
          .build();
    }

    return contentfulDeliveryClient;
  }

  private CDAClient getContentfulPreviewClient() {
    if (contentfulPreviewClient == null) {
      contentfulPreviewClient = CDAClient
          .builder()
          .setSpace(getSpaceId())
          .setToken(getPreviewAccessToken())
          .preview()
          .build();
    }

    return contentfulPreviewClient;
  }

  public CDAClient getCurrentClient() {
    switch (getApi()) {
      case API_CDA:
        return getContentfulDeliveryClient();
      case API_CPA:
        return getContentfulPreviewClient();
      default:
        return null;
    }
  }

  public String getDeliveryAccessToken() {
    return deliveryAccessToken;
  }

  public Settings setDeliveryAccessToken(String deliveryAccessToken) {
    if (deliveryAccessToken != null && deliveryAccessToken.length() > 0) {
      this.deliveryAccessToken = deliveryAccessToken;
    }
    return this;
  }

  public String getPreviewAccessToken() {
    return previewAccessToken;
  }

  public Settings setPreviewAccessToken(String previewAccessToken) {
    if (previewAccessToken != null && previewAccessToken.length() > 0) {
      this.previewAccessToken = previewAccessToken;
    }
    return this;
  }

  public String getSpaceId() {
    return spaceId;
  }

  public Settings setSpaceId(String spaceId) {
    if (spaceId != null && spaceId.length() > 0) {
      this.spaceId = spaceId;
    }
    return this;
  }

  /**
   * @return a human readable string, representing the object.
   */
  @Override public String toString() {
    return "Settings { "
        + "api = " + getApi() + ", "
        + "deliveryAccessToken = " + getDeliveryAccessToken() + ", "
        + "contentfulDeliveryClient = " + contentfulDeliveryClient + ", "
        + "previewAccessToken = " + getPreviewAccessToken() + ", "
        + "contentfulPreviewClient = " + contentfulPreviewClient + ", "
        + "locale = " + getLocale() + ", "
        + "path = " + getPath() + ", "
        + "queryString = " + getQueryString() + ", "
        + "spaceId = " + getSpaceId() + " "
        + "}";
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Settings)) return false;
    final Settings settings = (Settings) o;
    return Objects.equals(getQueryString(), settings.getQueryString()) &&
        Objects.equals(getPath(), settings.getPath()) &&
        Objects.equals(getLocale(), settings.getLocale()) &&
        Objects.equals(getApi(), settings.getApi()) &&
        Objects.equals(getSpaceId(), settings.getSpaceId()) &&
        Objects.equals(getDeliveryAccessToken(), settings.getDeliveryAccessToken()) &&
        Objects.equals(getPreviewAccessToken(), settings.getPreviewAccessToken());
  }

  @Override public int hashCode() {
    return Objects.hash(getQueryString(), getPath(), getLocale(), getApi(), getSpaceId(), getDeliveryAccessToken(), getPreviewAccessToken());
  }
}
