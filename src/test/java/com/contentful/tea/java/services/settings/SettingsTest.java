package com.contentful.tea.java.services.settings;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class SettingsTest {

  @Test
  public void canResetSettings() {
    final Settings freshSettings = new Settings();

    final Settings subject = new Settings()
        .setPath("SomePath")
        .setQueryString("SomeQueryString")
        .setLocale("SomeLocale")
        .setBaseUrl("SomeBaseUrl")
        .setEditorialFeaturesEnabled(true);

    assertThat(freshSettings.toString()).isEqualTo(subject.reset().toString());
  }

  @Test
  public void canSaveSettings() {
    final Settings subject = new Settings()
        .setPath("SomePath")
        .setQueryString("SomeQueryString")
        .setLocale("SomeLocale")
        .setBaseUrl("SomeBaseUrl")
        .setEditorialFeaturesEnabled(true);

    final Settings save = subject.save();
    subject.reset();

    assertThat(save.toString()).isNotEqualTo(subject.toString());
    assertThat(save.getPath()).isEqualTo("SomePath");
    assertThat(save.getQueryString()).isEqualTo("SomeQueryString");
    assertThat(save.getLocale()).isEqualTo("SomeLocale");
    assertThat(save.getBaseUrl()).isEqualTo("SomeBaseUrl");
    assertThat(save.areEditorialFeaturesEnabled()).isTrue();
  }

  @Test
  public void canLoadSettings() {
    final Settings subject = new Settings()
        .setPath("SomePath")
        .setQueryString("SomeQueryString")
        .setLocale("SomeLocale")
        .setBaseUrl("SomeBaseUrl")
        .setEditorialFeaturesEnabled(true);

    final Settings load = new Settings().load(subject);

    assertThat(load.getPath()).isEqualTo("SomePath");
    assertThat(load.getQueryString()).isEqualTo("SomeQueryString");
    assertThat(load.getLocale()).isEqualTo("SomeLocale");
    assertThat(load.getBaseUrl()).isEqualTo("SomeBaseUrl");
    assertThat(load.areEditorialFeaturesEnabled()).isTrue();
  }

}