package com.contentful.tea.java;

import com.contentful.tea.java.services.localization.Keys;
import com.contentful.tea.java.services.localization.LocalizedStringsFromResourcesProvider;
import com.contentful.tea.java.services.localization.LocalizedStringsProvider;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class LocalizerTests {

  private LocalizedStringsProvider localizer;

  @Before
  public void setup() {
    localizer = new LocalizedStringsFromResourcesProvider();
  }

  @Test
  public void basicTranslationWorks() {
    assertThat(localizer.localize("en-US", Keys.whatIsThisApp)).isEqualTo("Help");
    assertThat(localizer.localize("de-DE", Keys.whatIsThisApp)).isEqualTo("Hilfe");
    assertThat(localizer.localize("DoesNotExist", Keys.defaultTitle)).isNull();
  }
}
