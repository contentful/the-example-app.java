package com.contentful.tea.java.services.modelconverter;

import com.contentful.java.cda.CDAHttpException;
import com.contentful.tea.java.models.settings.SettingsParameter;
import com.contentful.tea.java.services.contentful.Contentful;
import com.contentful.tea.java.services.localization.Keys;
import com.contentful.tea.java.services.settings.Settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
public class SettingsToParameter extends ContentfulModelToMappableTypeConverter<Void, SettingsParameter> {

  private static final String LOCAL_SETTINGS_REFERENCE_IN_TO_GITHUB = "https://github.com/contentful/the-example-app.java/blob/master/src/main/resources/application.properties";
  private static final String LOCAL_SETTINGS_REFERENCE_FILE_NAME = "application.properties";
  @Autowired
  @SuppressWarnings("unused")
  private Contentful contentful;

  @Autowired
  @SuppressWarnings("unused")
  private Settings settings;

  @Override public SettingsParameter convert(Void v) {

    final SettingsParameter.Errors errors = checkForErrors();

    final SettingsParameter parameter = new SettingsParameter()
        .setAccessTokenLabel(t(Keys.accessTokenLabel))
        .setApi(contentful.getApi())
        .setApplicationCredentialsLabel(t(Keys.applicationCredentialsLabel))
        .setChangesSavedLabel(t(Keys.changesSavedLabel))
        .setConnectedToSpaceLabel(t(Keys.connectedToSpaceLabel))
        .setContentDeliveryApiHelpText(t(Keys.contentDeliveryApiHelpText))
        .setContentPreviewApiHelpText(t(Keys.contentPreviewApiHelpText))
        .setCopyLinkLabel(t(Keys.copyLinkLabel))
        .setCredentialSourceLabel(t(Keys.credentialSourceLabel))
        .setDeepLinkUrl(createDeepLinkUrl())
        .setDeliveryToken(contentful.getDeliveryAccessToken())
        .setDeliveryTokenLabel(t(Keys.cdaAccessTokenLabel))
        .setEditorialFeaturesEnabled(settings.areEditorialFeaturesEnabled())
        .setEnableEditorialFeaturesHelpText(t(Keys.enableEditorialFeaturesHelpText))
        .setEnableEditorialFeaturesLabel(t(Keys.enableEditorialFeaturesLabel))
        .setErrorOccurredMessageLabel(t(Keys.errorOccurredMessageLabel))
        .setErrorOccurredTitleLabel(t(Keys.errorOccurredTitleLabel))
        .setErrors(errors)
        .setLoadedFromLocalFileLabel(t(Keys.loadedFromLocalFileLabel))
        .setLoadedFromLocalFileName(LOCAL_SETTINGS_REFERENCE_FILE_NAME)
        .setLoadedFromLocalFileUrl(LOCAL_SETTINGS_REFERENCE_IN_TO_GITHUB)
        .setLocale(settings.getLocale())
        .setOverrideConfigLabel(t(Keys.overrideConfigLabel))
        .setPreviewToken(contentful.getPreviewAccessToken())
        .setPreviewTokenLabel(t(Keys.cpaAccessTokenLabel))
        .setResetCredentialsLabel(t(Keys.resetCredentialsLabel))
        .setSaveSettingsButtonLabel(t(Keys.saveSettingsButtonLabel))
        .setSettingsIntroLabel(t(Keys.settingsIntroLabel))
        .setSpaceId(contentful.getSpaceId())
        .setSpaceIdHelpText(t(Keys.spaceIdHelpText))
        .setSpaceIdLabel(t(Keys.spaceIdLabel))
        .setSpaceName(fetchSpaceName())
        .setTitle(t(Keys.settingsLabel))
        .setUsesCustomCredentials(isUsingCustomCredentials())
        .setUsingServerCredentialsLabel(t(Keys.usingServerCredentialsLabel))
        .setUsingSessionCredentialsLabel(t(Keys.usingSessionCredentialsLabel));

    parameter.getBase().getMeta().setTitle(t(Keys.settingsLabel));

    return parameter;
  }

  private boolean isUsingCustomCredentials() {
    final InputStream input = SettingsToParameter.class.getClassLoader().getResourceAsStream("application.properties");
    final Properties properties = new Properties();
    try {
      properties.load(input);

      return !contentful.getSpaceId().equals(properties.getProperty("spaceId", ""))
          || !contentful.getDeliveryAccessToken().equals(properties.getProperty("deliveryToken", ""))
          || !contentful.getPreviewAccessToken().equals(properties.getProperty("previewToken", ""));
    } catch (IOException e) {
      return true;
    }
  }

  private String createDeepLinkUrl() {
    return settings.getBaseUrl()
        + "?space_id=" + contentful.getSpaceId()
        + "&preview_token=" + contentful.getPreviewAccessToken()
        + "&delivery_token=" + contentful.getDeliveryAccessToken()
        + "&api=" + contentful.getApi()
        + "&enable_editorial_features=" + settings.areEditorialFeaturesEnabled();
  }

  private String fetchSpaceName() {
    try {
      return contentful.getCurrentClient().fetchSpace().name();
    } catch (CDAHttpException e) {
      return "";
    }
  }

  private SettingsParameter.Errors checkForErrors() {
    final String lastApi = contentful.getApi();

    try {
      final SettingsParameter.Errors errors = new SettingsParameter.Errors();

      contentful.setApi(Contentful.API_CDA);
      try {
        contentful.getCurrentClient().fetchSpace();
      } catch (CDAHttpException e) {
        switch (e.responseCode()) {
          default:
          case 401:
            errors.setDeliveryToken(new SettingsParameter.Errors.Error().setMessage(t(Keys.spaceOrTokenInvalid)));
            return errors;
          case 404:
            errors.setSpaceId(new SettingsParameter.Errors.Error().setMessage(t(Keys.spaceOrTokenInvalid)));
            return errors;
        }
      }

      contentful.setApi(Contentful.API_CPA);
      try {
        contentful.getCurrentClient().fetchSpace();
      } catch (CDAHttpException e) {
        switch (e.responseCode()) {
          default:
          case 401:
            errors.setPreviewToken(new SettingsParameter.Errors.Error().setMessage(t(Keys.spaceOrTokenInvalid)));
            return errors;
          case 404:
            errors.setSpaceId(new SettingsParameter.Errors.Error().setMessage(t(Keys.spaceOrTokenInvalid)));
            return errors;
        }
      }

      return errors;
    } finally {
      contentful.setApi(lastApi);
    }
  }
}
