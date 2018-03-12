package com.contentful.tea.java.services.modelconverter;

import com.contentful.java.cda.CDAAsset;
import com.contentful.java.cda.CDAEntry;
import com.contentful.tea.java.models.landing.LandingPageParameter;
import com.contentful.tea.java.models.landing.modules.BaseModule;
import com.contentful.tea.java.models.landing.modules.CopyModule;
import com.contentful.tea.java.models.landing.modules.HeroImageModule;
import com.contentful.tea.java.models.landing.modules.HighlightedCourseModule;
import com.contentful.tea.java.services.localization.Keys;
import com.contentful.tea.java.services.modelenhancers.EditorialFeaturesEnhancer;

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
  private EditorialFeaturesEnhancer enhancer;

  @Override
  public LandingPageParameter convert(CDAEntry entry, int editorialFeaturesDepth) {
    final LandingPageParameter parameter = new LandingPageParameter();
    parameter.getBase().getMeta().setTitle(t(Keys.homeLabel));
    parameter.getBase().getLabels().setErrorDoesNotExistLabel(t(Keys.errorHighlightedCourse));

    addModules(parameter, entry, editorialFeaturesDepth);

    if (editorialFeaturesDepth > 0) {
      enhancer.enhance(entry, parameter.getBase());
    }
    return parameter;
  }

  private void addModules(LandingPageParameter parameter, CDAEntry entry, int editorialFeaturesDepth) {
    final List<CDAEntry> contentModules = entry.getField("contentModules");
    if (contentModules == null) {
      return;
    }

    for (final CDAEntry module : contentModules) {
      final BaseModule moduleParameter = createNewModuleParameter(module, editorialFeaturesDepth);

      if (moduleParameter != null) {
        parameter.addModule(moduleParameter);
      }

      if (editorialFeaturesDepth > 0) {
        if (enhancer.isDraft(module)) {
          parameter.getBase().getMeta().setDraft(true);
        }

        if (enhancer.isPending(module)) {
          parameter.getBase().getMeta().setPendingChanges(true);
        }
      }
    }
  }

  private BaseModule createNewModuleParameter(CDAEntry module, int editorialFeaturesDepth) {
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
        final CDAEntry course = module.getField("course");
        if (course != null) {
          return new HighlightedCourseModule()
              .setCourse(
                  courseConverter.convert(
                      new EntryToCourse.Compound()
                          .setCourse(course),
                      editorialFeaturesDepth - 1
                  ).getCourse());
        } else {
          return null;
        }
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
