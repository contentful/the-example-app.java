package com.contentful.tea.java.services.url;

import com.contentful.tea.java.models.Settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CookieParser {
  @Autowired
  @SuppressWarnings("unused")
  private Settings settings;

  public void loadCookies(String api, String spaceId, String cdaToken, String cpaToken) {
    settings
        .setApi(api)
        .setSpaceId(spaceId)
        .setDeliveryAccessToken(cdaToken)
        .setPreviewAccessToken(cpaToken);
  }

  public void saveCookies() {
    // TODO
  }
}
