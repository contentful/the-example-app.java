package com.contentful.tea.java.models.settings;

import com.contentful.tea.java.models.base.BaseParameter;
import com.contentful.tea.java.models.mappable.MappableType;

import java.util.Objects;

public class SettingsParameter extends MappableType {
  public static class Errors extends MappableType {
    public static class Error extends MappableType {
      private String message;

      public String getMessage() {
        return message;
      }

      public Error setMessage(String message) {
        this.message = message;
        return this;
      }

      @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Error)) return false;
        final Error error = (Error) o;
        return Objects.equals(getMessage(), error.getMessage());
      }

      @Override public int hashCode() {
        return Objects.hash(getMessage());
      }

      /**
       * @return a human readable string, representing the object.
       */
      @Override public String toString() {
        return "Error { " + super.toString() + " "
            + "message = " + getMessage() + " "
            + "}";
      }
    }

    private Error spaceId;
    private Error deliveryToken;
    private Error previewToken;

    private boolean hasErrors = false;

    public boolean hasErrors() {
      return hasErrors;
    }

    public Error getSpaceId() {
      return spaceId;
    }

    public Errors setSpaceId(Error spaceId) {
      this.spaceId = spaceId;
      if (spaceId != null) {
        hasErrors = true;
      }
      return this;
    }

    public Error getDeliveryToken() {
      return deliveryToken;
    }

    public Errors setDeliveryToken(Error deliveryToken) {
      this.deliveryToken = deliveryToken;
      if (deliveryToken != null) {
        hasErrors = true;
      }
      return this;
    }

    public Error getPreviewToken() {
      return previewToken;
    }

    public Errors setPreviewToken(Error previewToken) {
      this.previewToken = previewToken;
      if (previewToken != null) {
        hasErrors = true;
      }
      return this;
    }

    @Override public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Errors)) return false;
      final Errors errors = (Errors) o;
      return Objects.equals(getSpaceId(), errors.getSpaceId()) &&
          Objects.equals(getDeliveryToken(), errors.getDeliveryToken()) &&
          Objects.equals(getPreviewToken(), errors.getPreviewToken());
    }

    @Override public int hashCode() {
      return Objects.hash(getSpaceId(), getDeliveryToken(), getPreviewToken());
    }

    /**
     * @return a human readable string, representing the object.
     */
    @Override public String toString() {
      return "Errors { " + super.toString() + " "
          + "deliveryToken = " + getDeliveryToken() + ", "
          + "previewToken = " + getPreviewToken() + ", "
          + "spaceId = " + getSpaceId() + " "
          + "}";
    }
  }

  private BaseParameter base = new BaseParameter();

  private boolean editorialFeaturesEnabled;
  private boolean successful;
  private boolean usesCustomCredentials;

  private Errors errors = new Errors();

  private String accessTokenLabel;
  private String api;
  private String applicationCredentialsLabel;
  private String changesSavedLabel;
  private String connectedToSpaceLabel;
  private String contentDeliveryApiHelpText;
  private String contentPreviewApiHelpText;
  private String copyLinkLabel;
  private String credentialSourceLabel;
  private String deepLinkUrl;
  private String deliveryToken;
  private String deliveryTokenLabel;
  private String enableEditorialFeaturesHelpText;
  private String enableEditorialFeaturesLabel;
  private String errorOccurredMessageLabel;
  private String errorOccurredTitleLabel;
  private String loadedFromLocalFileLabel;
  private String loadedFromLocalFileName;
  private String loadedFromLocalFileUrl;
  private String locale;
  private String overrideConfigLabel;
  private String previewToken;
  private String previewTokenLabel;
  private String resetCredentialsLabel;
  private String saveSettingsButtonLabel;
  private String settingsIntroLabel;
  private String spaceId;
  private String spaceIdHelpText;
  private String spaceIdLabel;
  private String spaceName;
  private String title;
  private String usingServerCredentialsLabel;
  private String usingSessionCredentialsLabel;

  public BaseParameter getBase() {
    return base;
  }

  public SettingsParameter setBase(BaseParameter base) {
    this.base = base;
    return this;
  }

  public String getAccessTokenLabel() {
    return accessTokenLabel;
  }

  public SettingsParameter setAccessTokenLabel(String accessTokenLabel) {
    this.accessTokenLabel = accessTokenLabel;
    return this;
  }

  public String getChangesSavedLabel() {
    return changesSavedLabel;
  }

  public SettingsParameter setChangesSavedLabel(String changesSavedLabel) {
    this.changesSavedLabel = changesSavedLabel;
    return this;
  }

  public String getConnectedToSpaceLabel() {
    return connectedToSpaceLabel;
  }

  public SettingsParameter setConnectedToSpaceLabel(String connectedToSpaceLabel) {
    this.connectedToSpaceLabel = connectedToSpaceLabel;
    return this;
  }

  public String getContentDeliveryApiHelpText() {
    return contentDeliveryApiHelpText;
  }

  public SettingsParameter setContentDeliveryApiHelpText(String contentDeliveryApiHelpText) {
    this.contentDeliveryApiHelpText = contentDeliveryApiHelpText;
    return this;
  }

  public String getContentPreviewApiHelpText() {
    return contentPreviewApiHelpText;
  }

  public SettingsParameter setContentPreviewApiHelpText(String contentPreviewApiHelpText) {
    this.contentPreviewApiHelpText = contentPreviewApiHelpText;
    return this;
  }

  public String getDeliveryToken() {
    return deliveryToken;
  }

  public SettingsParameter setDeliveryToken(String deliveryToken) {
    this.deliveryToken = deliveryToken;
    return this;
  }

  public boolean areEditorialFeaturesEnabled() {
    return editorialFeaturesEnabled;
  }

  public SettingsParameter setEditorialFeaturesEnabled(boolean editorialFeaturesEnabled) {
    this.editorialFeaturesEnabled = editorialFeaturesEnabled;
    return this;
  }

  public String getEnableEditorialFeaturesHelpText() {
    return enableEditorialFeaturesHelpText;
  }

  public SettingsParameter setEnableEditorialFeaturesHelpText(String enableEditorialFeaturesHelpText) {
    this.enableEditorialFeaturesHelpText = enableEditorialFeaturesHelpText;
    return this;
  }

  public String getEnableEditorialFeaturesLabel() {
    return enableEditorialFeaturesLabel;
  }

  public SettingsParameter setEnableEditorialFeaturesLabel(String enableEditorialFeaturesLabel) {
    this.enableEditorialFeaturesLabel = enableEditorialFeaturesLabel;
    return this;
  }

  public String getErrorOccurredMessageLabel() {
    return errorOccurredMessageLabel;
  }

  public SettingsParameter setErrorOccurredMessageLabel(String errorOccurredMessageLabel) {
    this.errorOccurredMessageLabel = errorOccurredMessageLabel;
    return this;
  }

  public String getErrorOccurredTitleLabel() {
    return errorOccurredTitleLabel;
  }

  public SettingsParameter setErrorOccurredTitleLabel(String errorOccurredTitleLabel) {
    this.errorOccurredTitleLabel = errorOccurredTitleLabel;
    return this;
  }

  public String getPreviewToken() {
    return previewToken;
  }

  public SettingsParameter setPreviewToken(String previewToken) {
    this.previewToken = previewToken;
    return this;
  }

  public String getSaveSettingsButtonLabel() {
    return saveSettingsButtonLabel;
  }

  public SettingsParameter setSaveSettingsButtonLabel(String saveSettingsButtonLabel) {
    this.saveSettingsButtonLabel = saveSettingsButtonLabel;
    return this;
  }

  public String getSettingsIntroLabel() {
    return settingsIntroLabel;
  }

  public SettingsParameter setSettingsIntroLabel(String settingsIntroLabel) {
    this.settingsIntroLabel = settingsIntroLabel;
    return this;
  }

  public String getSpaceId() {
    return spaceId;
  }

  public SettingsParameter setSpaceId(String spaceId) {
    this.spaceId = spaceId;
    return this;
  }

  public String getSpaceIdHelpText() {
    return spaceIdHelpText;
  }

  public SettingsParameter setSpaceIdHelpText(String spaceIdHelpText) {
    this.spaceIdHelpText = spaceIdHelpText;
    return this;
  }

  public String getSpaceIdLabel() {
    return spaceIdLabel;
  }

  public SettingsParameter setSpaceIdLabel(String spaceIdLabel) {
    this.spaceIdLabel = spaceIdLabel;
    return this;
  }

  public String getSpaceName() {
    return spaceName;
  }

  public SettingsParameter setSpaceName(String spaceName) {
    this.spaceName = spaceName;
    return this;
  }

  public boolean isSuccessful() {
    return successful;
  }

  public SettingsParameter setSuccessful(boolean successful) {
    this.successful = successful;
    return this;
  }

  public String getTitle() {
    return title;
  }

  public SettingsParameter setTitle(String title) {
    this.title = title;
    return this;
  }

  public Errors getErrors() {
    return errors;
  }

  public SettingsParameter setErrors(Errors errors) {
    this.errors = errors;
    return this;
  }

  public String getApplicationCredentialsLabel() {
    return applicationCredentialsLabel;
  }

  public SettingsParameter setApplicationCredentialsLabel(String applicationCredentialsLabel) {
    this.applicationCredentialsLabel = applicationCredentialsLabel;
    return this;
  }

  public String getCredentialSourceLabel() {
    return credentialSourceLabel;
  }

  public SettingsParameter setCredentialSourceLabel(String credentialSourceLabel) {
    this.credentialSourceLabel = credentialSourceLabel;
    return this;
  }

  public String getLoadedFromLocalFileLabel() {
    return loadedFromLocalFileLabel;
  }

  public SettingsParameter setLoadedFromLocalFileLabel(String loadedFromLocalFileLabel) {
    this.loadedFromLocalFileLabel = loadedFromLocalFileLabel;
    return this;
  }

  public String getOverrideConfigLabel() {
    return overrideConfigLabel;
  }

  public SettingsParameter setOverrideConfigLabel(String overrideConfigLabel) {
    this.overrideConfigLabel = overrideConfigLabel;
    return this;
  }

  public String getResetCredentialsLabel() {
    return resetCredentialsLabel;
  }

  public SettingsParameter setResetCredentialsLabel(String resetCredentialsLabel) {
    this.resetCredentialsLabel = resetCredentialsLabel;
    return this;
  }

  public String getUsingServerCredentialsLabel() {
    return usingServerCredentialsLabel;
  }

  public SettingsParameter setUsingServerCredentialsLabel(String usingServerCredentialsLabel) {
    this.usingServerCredentialsLabel = usingServerCredentialsLabel;
    return this;
  }

  public String getUsingSessionCredentialsLabel() {
    return usingSessionCredentialsLabel;
  }

  public SettingsParameter setUsingSessionCredentialsLabel(String usingSessionCredentialsLabel) {
    this.usingSessionCredentialsLabel = usingSessionCredentialsLabel;
    return this;
  }

  public String getDeepLinkUrl() {
    return deepLinkUrl;
  }

  public SettingsParameter setDeepLinkUrl(String deepLinkUrl) {
    this.deepLinkUrl = deepLinkUrl;
    return this;
  }

  public boolean isUsingCustomCredentials() {
    return usesCustomCredentials;
  }

  public SettingsParameter setUsesCustomCredentials(boolean usesCustomCredentials) {
    this.usesCustomCredentials = usesCustomCredentials;
    return this;
  }

  public boolean isEditorialFeaturesEnabled() {
    return editorialFeaturesEnabled;
  }

  public String getCopyLinkLabel() {
    return copyLinkLabel;
  }

  public SettingsParameter setCopyLinkLabel(String copyLinkLabel) {
    this.copyLinkLabel = copyLinkLabel;
    return this;
  }

  public String getLoadedFromLocalFileUrl() {
    return loadedFromLocalFileUrl;
  }

  public SettingsParameter setLoadedFromLocalFileUrl(String loadedFromLocalFileUrl) {
    this.loadedFromLocalFileUrl = loadedFromLocalFileUrl;
    return this;
  }

  public String getLoadedFromLocalFileName() {
    return loadedFromLocalFileName;
  }

  public SettingsParameter setLoadedFromLocalFileName(String loadedFromLocalFileName) {
    this.loadedFromLocalFileName = loadedFromLocalFileName;
    return this;
  }

  public String getApi() {
    return api;
  }

  public SettingsParameter setApi(String api) {
    this.api = api;
    return this;
  }

  public String getLocale() {
    return locale;
  }

  public SettingsParameter setLocale(String locale) {
    this.locale = locale;
    return this;
  }

  public String getDeliveryTokenLabel() {
    return deliveryTokenLabel;
  }

  public SettingsParameter setDeliveryTokenLabel(String deliveryTokenLabel) {
    this.deliveryTokenLabel = deliveryTokenLabel;
    return this;
  }

  public String getPreviewTokenLabel() {
    return previewTokenLabel;
  }

  public SettingsParameter setPreviewTokenLabel(String previewTokenLabel) {
    this.previewTokenLabel = previewTokenLabel;
    return this;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof SettingsParameter)) return false;
    final SettingsParameter that = (SettingsParameter) o;
    return Objects.equals(getBase(), that.getBase()) &&
        Objects.equals(areEditorialFeaturesEnabled(), that.areEditorialFeaturesEnabled()) &&
        Objects.equals(getApi(), that.getApi()) &&
        Objects.equals(getAccessTokenLabel(), that.getAccessTokenLabel()) &&
        Objects.equals(getApplicationCredentialsLabel(), that.getApplicationCredentialsLabel()) &&
        Objects.equals(getChangesSavedLabel(), that.getChangesSavedLabel()) &&
        Objects.equals(getConnectedToSpaceLabel(), that.getConnectedToSpaceLabel()) &&
        Objects.equals(getContentDeliveryApiHelpText(), that.getContentDeliveryApiHelpText()) &&
        Objects.equals(getContentPreviewApiHelpText(), that.getContentPreviewApiHelpText()) &&
        Objects.equals(getCopyLinkLabel(), that.getCopyLinkLabel()) &&
        Objects.equals(getCredentialSourceLabel(), that.getCredentialSourceLabel()) &&
        Objects.equals(getDeepLinkUrl(), that.getDeepLinkUrl()) &&
        Objects.equals(getDeliveryToken(), that.getDeliveryToken()) &&
        Objects.equals(getDeliveryTokenLabel(), that.getDeliveryTokenLabel()) &&
        Objects.equals(getEnableEditorialFeaturesHelpText(), that.getEnableEditorialFeaturesHelpText()) &&
        Objects.equals(getEnableEditorialFeaturesLabel(), that.getEnableEditorialFeaturesLabel()) &&
        Objects.equals(getErrorOccurredMessageLabel(), that.getErrorOccurredMessageLabel()) &&
        Objects.equals(getErrorOccurredTitleLabel(), that.getErrorOccurredTitleLabel()) &&
        Objects.equals(getErrors(), that.getErrors()) &&
        Objects.equals(getLoadedFromLocalFileLabel(), that.getLoadedFromLocalFileLabel()) &&
        Objects.equals(getLoadedFromLocalFileName(), that.getLoadedFromLocalFileName()) &&
        Objects.equals(getLoadedFromLocalFileUrl(), that.getLoadedFromLocalFileUrl()) &&
        Objects.equals(getLocale(), that.getLocale()) &&
        Objects.equals(getOverrideConfigLabel(), that.getOverrideConfigLabel()) &&
        Objects.equals(getPreviewToken(), that.getPreviewToken()) &&
        Objects.equals(getPreviewTokenLabel(), that.getPreviewTokenLabel()) &&
        Objects.equals(getResetCredentialsLabel(), that.getResetCredentialsLabel()) &&
        Objects.equals(getSaveSettingsButtonLabel(), that.getSaveSettingsButtonLabel()) &&
        Objects.equals(getSettingsIntroLabel(), that.getSettingsIntroLabel()) &&
        Objects.equals(getSpaceId(), that.getSpaceId()) &&
        Objects.equals(getSpaceIdHelpText(), that.getSpaceIdHelpText()) &&
        Objects.equals(getSpaceIdLabel(), that.getSpaceIdLabel()) &&
        Objects.equals(getSpaceName(), that.getSpaceName()) &&
        Objects.equals(getTitle(), that.getTitle()) &&
        Objects.equals(getUsingServerCredentialsLabel(), that.getUsingServerCredentialsLabel()) &&
        Objects.equals(getUsingSessionCredentialsLabel(), that.getUsingSessionCredentialsLabel()) &&
        Objects.equals(isSuccessful(), that.isSuccessful()) &&
        Objects.equals(isUsingCustomCredentials(), that.isUsingCustomCredentials());
  }

  @Override public int hashCode() {
    return Objects.hash(getBase(), getApi(), getLoadedFromLocalFileUrl(), getLoadedFromLocalFileName(), getAccessTokenLabel(), getChangesSavedLabel(), getConnectedToSpaceLabel(), getContentDeliveryApiHelpText(), getContentPreviewApiHelpText(), getDeliveryToken(), getDeliveryTokenLabel(), areEditorialFeaturesEnabled(), getEnableEditorialFeaturesHelpText(), getEnableEditorialFeaturesLabel(), getErrorOccurredMessageLabel(), getErrorOccurredTitleLabel(), getPreviewToken(), getPreviewTokenLabel(), getSaveSettingsButtonLabel(), getSettingsIntroLabel(), getSpaceId(), getSpaceIdHelpText(), getSpaceIdLabel(), getSpaceName(), isSuccessful(), getTitle(), getErrors(), getApplicationCredentialsLabel(), getCredentialSourceLabel(), getLoadedFromLocalFileLabel(), getLocale(), getOverrideConfigLabel(), getResetCredentialsLabel(), getUsingServerCredentialsLabel(), getUsingSessionCredentialsLabel(), getDeepLinkUrl(), isUsingCustomCredentials(), getCopyLinkLabel());
  }

  @Override public String toString() {
    return "SettingsParameter { " + super.toString() + " "
        + "accessTokenLabel = " + getAccessTokenLabel() + ", "
        + "api = " + getApi() + ", "
        + "applicationCredentialsLabel = " + getApplicationCredentialsLabel() + ", "
        + "areEditorialFeaturesEnabled = " + areEditorialFeaturesEnabled() + ", "
        + "base = " + getBase() + ", "
        + "changesSavedLabel = " + getChangesSavedLabel() + ", "
        + "connectedToSpaceLabel = " + getConnectedToSpaceLabel() + ", "
        + "contentDeliveryApiHelpText = " + getContentDeliveryApiHelpText() + ", "
        + "contentPreviewApiHelpText = " + getContentPreviewApiHelpText() + ", "
        + "copyLinkLabel = " + getCopyLinkLabel() + ", "
        + "credentialSourceLabel = " + getCredentialSourceLabel() + ", "
        + "deepLinkUrl = " + getDeepLinkUrl() + ", "
        + "deliveryToken = " + getDeliveryToken() + ", "
        + "deliveryTokenLabel = " + getDeliveryTokenLabel() + ", "
        + "editorialFeaturesEnabled = " + isEditorialFeaturesEnabled() + ", "
        + "enableEditorialFeaturesHelpText = " + getEnableEditorialFeaturesHelpText() + ", "
        + "enableEditorialFeaturesLabel = " + getEnableEditorialFeaturesLabel() + ", "
        + "errorOccurredMessageLabel = " + getErrorOccurredMessageLabel() + ", "
        + "errorOccurredTitleLabel = " + getErrorOccurredTitleLabel() + ", "
        + "errors = " + getErrors() + ", "
        + "loadedFromLocalFileLabel = " + getLoadedFromLocalFileLabel() + ", "
        + "loadedFromLocalFileName = " + getLoadedFromLocalFileName() + ", "
        + "loadedFromLocalFileUrl = " + getLoadedFromLocalFileUrl() + ", "
        + "locale = " + getLocale() + ", "
        + "overrideConfigLabel = " + getOverrideConfigLabel() + ", "
        + "previewToken = " + getPreviewToken() + ", "
        + "previewTokenLabel = " + getPreviewTokenLabel() + ", "
        + "resetCredentialsLabel = " + getResetCredentialsLabel() + ", "
        + "saveSettingsButtonLabel = " + getSaveSettingsButtonLabel() + ", "
        + "settingsIntroLabel = " + getSettingsIntroLabel() + ", "
        + "spaceId = " + getSpaceId() + ", "
        + "spaceIdHelpText = " + getSpaceIdHelpText() + ", "
        + "spaceIdLabel = " + getSpaceIdLabel() + ", "
        + "spaceName = " + getSpaceName() + ", "
        + "successful = " + isSuccessful() + ", "
        + "title = " + getTitle() + ", "
        + "usesCustomCredentials = " + isUsingCustomCredentials() + ", "
        + "usingServerCredentialsLabel = " + getUsingServerCredentialsLabel() + ", "
        + "usingSessionCredentialsLabel = " + getUsingSessionCredentialsLabel() + " "
        + "}";
  }
}
