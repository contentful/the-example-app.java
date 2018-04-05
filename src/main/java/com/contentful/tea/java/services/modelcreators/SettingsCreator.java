package com.contentful.tea.java.services.modelcreators;

import com.contentful.java.cda.CDAHttpException;
import com.contentful.tea.java.markdown.CommonmarkMarkdownParser;
import com.contentful.tea.java.models.settings.SettingsParameter;
import com.contentful.tea.java.services.contentful.Contentful;
import com.contentful.tea.java.services.localization.Keys;
import com.contentful.tea.java.services.localization.Localizer;
import com.contentful.tea.java.services.settings.Settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SettingsCreator {
  private static final String LOCAL_SETTINGS_REFERENCE_IN_TO_GITHUB = "https://github.com/contentful/the-example-app.java/blob/master/src/main/resources/application.properties";
  private static final String LOCAL_SETTINGS_REFERENCE_FILE_NAME = "application.properties";

  @Autowired
  @SuppressWarnings("unused")
  private Localizer localizer;

  @Autowired
  @SuppressWarnings("unused")
  private CommonmarkMarkdownParser markdown;

  @Autowired
  @SuppressWarnings("unused")
  private Contentful contentful;

  @Autowired
  @SuppressWarnings("unused")
  private Settings settings;

  public SettingsParameter create() {

    final SettingsParameter.Errors errors = checkForErrors();

    final SettingsParameter parameter = new SettingsParameter();
    setStaticLabels(parameter)
        .setLoadedFromLocalFileName(LOCAL_SETTINGS_REFERENCE_FILE_NAME)
        .setLoadedFromLocalFileUrl(LOCAL_SETTINGS_REFERENCE_IN_TO_GITHUB)
        .setDeepLinkUrl(createDeepLinkUrl())
        .setApi(contentful.getApi())
        .setDeliveryToken(contentful.getDeliveryAccessToken())
        .setEditorialFeaturesEnabled(settings.areEditorialFeaturesEnabled())
        .setErrors(errors)
        .setLocale(settings.getLocale())
        .setPreviewToken(contentful.getPreviewAccessToken())
        .setSpaceId(contentful.getSpaceId())
        .setSpaceName(fetchSpaceName())
        .setUsesCustomCredentials(contentful.isUsingCustomCredentials())
    ;
    return parameter;
  }

  public SettingsParameter setStaticLabels(SettingsParameter parameter) {
    parameter.getBase().getMeta().setTitle(t(Keys.settingsLabel));

    return parameter
        .setAccessTokenLabel(t(Keys.accessTokenLabel))
        .setApplicationCredentialsLabel(t(Keys.applicationCredentialsLabel))
        .setChangesSavedLabel(t(Keys.changesSavedLabel))
        .setConnectedToSpaceLabel(t(Keys.connectedToSpaceLabel))
        .setContentDeliveryApiHelpText(t(Keys.contentDeliveryApiHelpText))
        .setContentPreviewApiHelpText(t(Keys.contentPreviewApiHelpText))
        .setCopyLinkLabel(t(Keys.copyLinkLabel))
        .setCredentialSourceLabel(t(Keys.credentialSourceLabel))
        .setDeliveryTokenLabel(t(Keys.cdaAccessTokenLabel))
        .setEnableEditorialFeaturesHelpText(t(Keys.enableEditorialFeaturesHelpText))
        .setEnableEditorialFeaturesLabel(t(Keys.enableEditorialFeaturesLabel))
        .setErrorOccurredMessageLabel(t(Keys.errorOccurredMessageLabel))
        .setErrorOccurredTitleLabel(t(Keys.errorOccurredTitleLabel))
        .setLoadedFromLocalFileLabel(t(Keys.loadedFromLocalFileLabel))
        .setOverrideConfigLabel(t(Keys.overrideConfigLabel))
        .setPreviewTokenLabel(t(Keys.cpaAccessTokenLabel))
        .setResetCredentialsLabel(t(Keys.resetCredentialsLabel))
        .setSaveSettingsButtonLabel(t(Keys.saveSettingsButtonLabel))
        .setSettingsIntroLabel(t(Keys.settingsIntroLabel))
        .setSpaceIdHelpText(t(Keys.spaceIdHelpText))
        .setSpaceIdLabel(t(Keys.spaceIdLabel))
        .setTitle(t(Keys.settingsLabel))
        .setUsingServerCredentialsLabel(t(Keys.usingServerCredentialsLabel))
        .setUsingSessionCredentialsLabel(t(Keys.usingSessionCredentialsLabel))
        ;
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
    final SettingsParameter.Errors errors = new SettingsParameter.Errors();

    try {
      contentful.setApi(Contentful.API_CDA);
      try {
        contentful.getCurrentClient().fetchSpace();
      } catch (CDAHttpException e) {
        switch (e.responseCode()) {
          default:
            errors.setDeliveryToken(new SettingsParameter.Errors.Error().setMessage(t(Keys.errorOccurredTitleLabel)));
            break;
          case 401:
            errors.setDeliveryToken(new SettingsParameter.Errors.Error().setMessage(t(Keys.deliveryKeyInvalidLabel)));
            break;
          case 404:
            errors.setSpaceId(new SettingsParameter.Errors.Error().setMessage(t(Keys.spaceOrTokenInvalid)));
            errors.setDeliveryToken(new SettingsParameter.Errors.Error().setMessage(t(Keys.deliveryKeyInvalidLabel)));
            errors.setPreviewToken(new SettingsParameter.Errors.Error().setMessage(t(Keys.previewKeyInvalidLabel)));
            break;
        }
      }

      contentful.setApi(Contentful.API_CPA);
      try {
        contentful.getCurrentClient().fetchSpace();
      } catch (CDAHttpException e) {
        switch (e.responseCode()) {
          default:
          case 401:
            errors.setPreviewToken(new SettingsParameter.Errors.Error().setMessage(t(Keys.previewKeyInvalidLabel)));
            break;
          case 404:
            errors.setSpaceId(new SettingsParameter.Errors.Error().setMessage(t(Keys.spaceOrTokenInvalid)));
            errors.setDeliveryToken(new SettingsParameter.Errors.Error().setMessage(t(Keys.deliveryKeyInvalidLabel)));
            errors.setPreviewToken(new SettingsParameter.Errors.Error().setMessage(t(Keys.previewKeyInvalidLabel)));
            break;
        }
      }

      return errors;
    } catch (Throwable t) {
      errors.setSpaceId(new SettingsParameter.Errors.Error().setMessage(t(Keys.spaceOrTokenInvalid)));
      errors.setDeliveryToken(new SettingsParameter.Errors.Error().setMessage(t(Keys.deliveryKeyInvalidLabel)));
      errors.setPreviewToken(new SettingsParameter.Errors.Error().setMessage(t(Keys.previewKeyInvalidLabel)));
    } finally {
      contentful.setApi(lastApi);
    }

    return errors;
  }

  protected String t(Keys translateKey) {
    return localizer.localize(translateKey);
  }

  protected String m(String raw) {
    return markdown.parse(raw);
  }
}
