package com.contentful.tea.java.services.localization;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class LocalizerTests {

  private Localizer localizer;

  @Before
  public void setup() {
    localizer = new Localizer();
  }

  @Test
  public void basicTranslationWorks() {
    assertThat(localizer.localize("en-US", Keys.whatIsThisApp)).isEqualTo("Help");
    assertThat(localizer.localize("de-DE", Keys.whatIsThisApp)).isEqualTo("Hilfe");
  }

  @Test
  public void notSupportedLocaleReturnsEnglish() {
    assertThat(localizer.localize("non existing locale", Keys.whatIsThisApp)).isEqualTo("Help");
  }
}
