package com.contentful.tea.java.services.contentful;

import com.contentful.java.cda.CDAClient;
import com.contentful.tea.java.services.modelcreators.SettingsCreator;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

@Component
public class Contentful {
  private static final String ENVIRONMENT_OVERWRITE_HOST = "CONTENTFUL_DELIVERY_API_HOST";
  private static final String ENVIRONMENT_OVERWRITE_DELIVERY_TOKEN = "CONTENTFUL_DELIVERY_TOKEN";
  private static final String ENVIRONMENT_OVERWRITE_PREVIEW_TOKEN = "CONTENTFUL_PREVIEW_TOKEN";
  private static final String ENVIRONMENT_OVERWRITE_SPACE_ID = "CONTENTFUL_SPACE_ID";

  public static final String API_CDA = "cda";
  public static final String API_CPA = "cpa";

  private String api = API_CDA;

  private String host;
  private String spaceId;
  private String deliveryAccessToken;
  private String previewAccessToken;

  private Boolean doesRunOnProductionCachedValue = null;
  private String customHostCachedValue = null;

  CDAClient contentfulDeliveryClient;
  CDAClient contentfulPreviewClient;

  public Contentful() {
    reset();
  }

  public Contentful reset() {
    api = API_CDA;
    host = "https://cdn.contentful.com";
    spaceId = null;
    deliveryAccessToken = null;
    previewAccessToken = null;
    contentfulDeliveryClient = null;
    contentfulPreviewClient = null;

    return this;
  }

  public String getHost() {
    return host;
  }

  public Contentful setHost(String host) {
    this.host = host;
    return this;
  }

  public String getApi() {
    return api;
  }

  public Contentful setApi(String api) {
    if (API_CDA.equals(api) || API_CPA.equals(api)) {
      this.api = api;
    } else {
      this.api = API_CDA;
    }
    return this;
  }

  private CDAClient getContentfulDeliveryClient() {
    if (contentfulDeliveryClient == null) {
      setHost(getHost().replace("preview", "cdn"));
      contentfulDeliveryClient = createContentfulBuilder()
          .setToken(getDeliveryAccessToken())
          .build();
    }

    return contentfulDeliveryClient;
  }

  private CDAClient getContentfulPreviewClient() {
    if (contentfulPreviewClient == null) {
      setHost(getHost().replace("cdn", "preview"));
      contentfulPreviewClient = createContentfulBuilder()
          .setToken(getPreviewAccessToken())
          .build();
    }

    return contentfulPreviewClient;
  }

  private CDAClient.Builder createContentfulBuilder() {
    final CDAClient.Builder builder = CDAClient
        .builder();

    if (getApi().equals(API_CPA)) {
      builder.preview();
    }

    builder
        .setEndpoint(host)
        .setSpace(getSpaceId())
        .setApplication(getApplicationName(), getVersionString());

    return builder;
  }

  private void overwriteDefaultsWithEnvironment() {
    final String overwriteHost = System.getenv(ENVIRONMENT_OVERWRITE_HOST);
    if (overwriteHost != null && !overwriteHost.isEmpty()) {
      System.out.println("Overwriting host with '" + overwriteHost + "'.");
      setHost(overwriteHost);
    }

    final String spaceId = System.getenv(ENVIRONMENT_OVERWRITE_SPACE_ID);
    if (spaceId != null && !spaceId.isEmpty()) {
      setSpaceId(spaceId);
      System.out.println("Overwriting space id with '" + spaceId + "'.");
    }

    final String overwriteDeliveryToken = System.getenv(ENVIRONMENT_OVERWRITE_DELIVERY_TOKEN);
    if (overwriteDeliveryToken != null && !overwriteDeliveryToken.isEmpty()) {
      setDeliveryAccessToken(overwriteDeliveryToken);
      System.out.println("Overwriting delivery token with '" + overwriteDeliveryToken + "'.");
    }

    final String overwritePreviewToken = System.getenv(ENVIRONMENT_OVERWRITE_PREVIEW_TOKEN);
    if (overwritePreviewToken != null && !overwritePreviewToken.isEmpty()) {
      setPreviewAccessToken(overwritePreviewToken);
      System.out.println("Overwriting preview token with '" + overwritePreviewToken + "'.");
    }
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

  public Contentful setDeliveryAccessToken(String deliveryAccessToken) {
    this.deliveryAccessToken = deliveryAccessToken;
    return this;
  }

  public String getPreviewAccessToken() {
    return previewAccessToken;
  }

  public Contentful setPreviewAccessToken(String previewAccessToken) {
    this.previewAccessToken = previewAccessToken;
    return this;
  }

  public String getSpaceId() {
    return spaceId;
  }

  public Contentful setSpaceId(String spaceId) {
    this.spaceId = spaceId;
    return this;
  }

  @Override
  public String toString() {
    return "Contentful { "
        + "api = " + getApi() + ", "
        + "deliveryAccessToken = " + getDeliveryAccessToken() + ", "
        + "contentfulDeliveryClient = " + contentfulDeliveryClient + ", "
        + "previewAccessToken = " + getPreviewAccessToken() + ", "
        + "contentfulPreviewClient = " + contentfulPreviewClient + ", "
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Contentful)) return false;
    final Contentful other = (Contentful) o;
    return Objects.equals(getApi(), other.getApi()) &&
        Objects.equals(getSpaceId(), other.getSpaceId()) &&
        Objects.equals(getDeliveryAccessToken(), other.getDeliveryAccessToken()) &&
        Objects.equals(getPreviewAccessToken(), other.getPreviewAccessToken());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getApi(), getSpaceId(), getDeliveryAccessToken(), getPreviewAccessToken());
  }

  public Contentful save() {
    return new Contentful()
        .setApi(getApi())
        .setDeliveryAccessToken(getDeliveryAccessToken())
        .setPreviewAccessToken(getPreviewAccessToken())
        .setSpaceId(getSpaceId())
        ;
  }

  public Contentful load(Contentful lastContentful) {
    setApi(lastContentful.getApi());
    setDeliveryAccessToken(lastContentful.getDeliveryAccessToken());
    setPreviewAccessToken(lastContentful.getPreviewAccessToken());
    setSpaceId(lastContentful.getSpaceId());
    return this;
  }

  public Contentful loadFromPreferences() {
    final Properties properties = new Properties();

    try {
      final InputStream input = Contentful.class.getClassLoader().getResourceAsStream("application.properties");
      properties.load(input);

      setSpaceId(properties.getProperty("spaceId", ""));
      setDeliveryAccessToken(properties.getProperty("deliveryToken", ""));
      setPreviewAccessToken(properties.getProperty("previewToken", ""));

    } catch (IOException exception) {
      throw new IllegalStateException("Could not load default settings.", exception);
    }

    overwriteDefaultsWithEnvironment();

    return this;
  }

  public String getVersionString() {
    final Properties properties = new Properties();
    final InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties");
    try {
      properties.load(input);
    } catch (IOException e) {
      return "0.0.0-INVALID";
    }

    return properties.getProperty("version", "0.0.1-INVALID");
  }

  private String getApplicationName() {
    final Properties properties = new Properties();
    final InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties");
    try {
      properties.load(input);
    } catch (IOException e) {
      return "INVALID_APP";
    }

    return properties.getProperty("application", "INVALID_APP");
  }

  public boolean runsOnProduction() {
    if (doesRunOnProductionCachedValue == null) {
      doesRunOnProductionCachedValue = System.getenv("DYNO") != null && getOverwrittenHost().isEmpty();
    }

    return doesRunOnProductionCachedValue;
  }

  private String getOverwrittenHost() {
    if (customHostCachedValue == null) {
      customHostCachedValue = System.getenv(ENVIRONMENT_OVERWRITE_HOST);
    }

    return customHostCachedValue == null ? "" : customHostCachedValue;
  }

  public boolean isUsingCustomCredentials() {
    if (getOverwrittenHost().isEmpty()) {
      try {
        final InputStream input = SettingsCreator.class.getClassLoader().getResourceAsStream("application.properties");
        final Properties properties = new Properties();

        properties.load(input);

        return !Objects.equals(getSpaceId(), properties.getProperty("spaceId", ""))
            || !Objects.equals(getDeliveryAccessToken(), properties.getProperty("deliveryToken", ""))
            || !Objects.equals(getPreviewAccessToken(), properties.getProperty("previewToken", ""));
      } catch (IOException e) {
        return true;
      }
    } else {
      return !usesSameHostExceptForApi()
          || !Objects.equals(getSpaceId(), System.getenv(ENVIRONMENT_OVERWRITE_SPACE_ID))
          || !Objects.equals(getDeliveryAccessToken(), System.getenv(ENVIRONMENT_OVERWRITE_DELIVERY_TOKEN))
          || !Objects.equals(getPreviewAccessToken(), System.getenv(ENVIRONMENT_OVERWRITE_PREVIEW_TOKEN));
    }
  }

  private boolean usesSameHostExceptForApi() {
    String overwrittenHost = getOverwrittenHost();
    String host = getHost();

    overwrittenHost = overwrittenHost.replaceAll("preview", "cdn");
    host = host.replaceAll("preview", "cdn");

    return Objects.equals(overwrittenHost, host);
  }

}
