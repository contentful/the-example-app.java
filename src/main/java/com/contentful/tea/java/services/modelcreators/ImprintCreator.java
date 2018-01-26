package com.contentful.tea.java.services.modelcreators;

import com.contentful.tea.java.markdown.CommonmarkMarkdownParser;
import com.contentful.tea.java.models.imprint.ImprintParameter;
import com.contentful.tea.java.services.localization.Keys;
import com.contentful.tea.java.services.localization.Localizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImprintCreator {
  @Autowired
  @SuppressWarnings("unused")
  private Localizer localizer;

  @Autowired
  @SuppressWarnings("unused")
  private CommonmarkMarkdownParser markdown;

  public ImprintParameter create() {
    final ImprintParameter imprintParameter = new ImprintParameter();
    imprintParameter.getBase().getMeta().setTitle(t(Keys.imprintLabel));

    return imprintParameter
        .setCompanyLabel(t(Keys.companyLabel))
        .setGermanyLabel(t(Keys.germanyLabel))
        .setManagingDirectorLabel(t(Keys.managingDirectorLabel))
        .setOfficeLabel(t(Keys.officeLabel))
        .setRegistrationCourtLabel(t(Keys.registrationCourtLabel))
        .setVatNumberLabel(t(Keys.vatNumberLabel))
        ;
  }

  private String t(Keys translateKey) {
    return localizer.localize(translateKey);
  }
}
