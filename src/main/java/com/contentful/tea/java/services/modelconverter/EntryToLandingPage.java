package com.contentful.tea.java.services.modelconverter;

import com.contentful.java.cda.CDAAsset;
import com.contentful.java.cda.CDAEntry;
import com.contentful.tea.java.models.landing.LandingPageParameter;
import com.contentful.tea.java.models.landing.modules.BaseModule;
import com.contentful.tea.java.models.landing.modules.CopyModule;
import com.contentful.tea.java.models.landing.modules.HeroImageModule;
import com.contentful.tea.java.models.landing.modules.HighlightedCourseModule;
import com.contentful.tea.java.services.localization.Keys;
import com.contentful.tea.java.services.modelenhancers.AddEditorialFeaturesEnhancer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EntryToLandingPage extends ContentfulModelToMappableTypeConverter<CDAEntry, LandingPageParameter> {

  @Autowired
  @SuppressWarnings("unused")
  private EntryToCourse courseConverter;

  @Autowired
  @SuppressWarnings("unused")
  private AddEditorialFeaturesEnhancer enhancer;

  @Override
  public LandingPageParameter convert(CDAEntry entry) {
    final LandingPageParameter parameter = new LandingPageParameter();
    parameter.getBase().getMeta().setTitle(t(Keys.homeLabel));

    addModules(parameter, entry);

    enhancer.enhance(entry, parameter.getBase());

    return parameter;
  }

  private void addModules(LandingPageParameter parameter, CDAEntry entry) {
    final List<CDAEntry> contentModules = entry.getField("contentModules");
    for (final CDAEntry module : contentModules) {
      final BaseModule moduleParameter = createNewModuleParameter(module);
      parameter.addModule(moduleParameter);
    }
  }

  private BaseModule createNewModuleParameter(CDAEntry module) {
    switch (module.contentType().id()) {
      case "layoutCopy":
        return new CopyModule()
            .setHeadline(module.getField("headline"))
            .setCopy(module.getField("copy"))
            .setCtaLink(module.getField("ctaLink"))
            .setCtaTitle(module.getField("ctaTitle"))
            .setEmphasizeStyle("Emphasized".equals(module.getField("visualStyle")))
            ;
      case "layoutHighlightedCourse":
        return new HighlightedCourseModule()
            .setCourse(
                courseConverter.convert(
                    new EntryToCourse.Compound()
                        .setCourse(((CDAEntry) module.getField("course")))
                ).getCourse());
      case "layoutHeroImage":
        return new HeroImageModule()
            .setHeadline(module.getField("headline"))
            .setBackgroundImageTitle(module.getField("backgroundImageTitle"))
            .setBackgroundImageUrl(backgroundImageUrlFromModule(module))
            ;
      default:
        return null;
    }
  }

  private String backgroundImageUrlFromModule(CDAEntry module) {
    final CDAAsset asset = module.getField("backgroundImage");
    return asset.url();
  }
}
