package com.contentful.tea.java.services.modelconverter;

import com.contentful.java.cda.CDAAsset;
import com.contentful.java.cda.CDAEntry;
import com.contentful.tea.java.models.courses.lessons.LessonParameter;
import com.contentful.tea.java.models.courses.lessons.modules.CodeModule;
import com.contentful.tea.java.models.courses.lessons.modules.CopyModule;
import com.contentful.tea.java.models.courses.lessons.modules.ImageModule;
import com.contentful.tea.java.models.courses.lessons.modules.Module;
import com.contentful.tea.java.services.localization.Keys;
import com.contentful.tea.java.services.modelenhancers.EditorialFeaturesEnhancer;
import com.contentful.tea.java.services.settings.Settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.contentful.java.cda.image.ImageOption.https;

@Component
public class EntryToLesson extends ContentfulModelToMappableTypeConverter<CDAEntry, LessonParameter> {

  @Autowired
  @SuppressWarnings("unused")
  private Settings settings;

  @Autowired
  @SuppressWarnings("unused")
  private EditorialFeaturesEnhancer enhancer;

  @Override
  public LessonParameter convert(CDAEntry cdaLesson, int editorialFeaturesDepth) {
    final LessonParameter result = new LessonParameter()
        .setSlug(cdaLesson.getField("slug"))
        .setTitle(cdaLesson.getField("title"));

    final List<CDAEntry> cdaModules = cdaLesson.getField("modules");
    if (cdaModules != null) {
      for (final CDAEntry cdaModule : cdaModules) {
        final Module module = createModule(cdaModule);
        if (module != null) {
          result.addModule(module);
        }

        if (editorialFeaturesDepth > 0) {
          if (enhancer.isPending(cdaModule)) {
            result.getBase().getMeta().setPendingChanges(true);
          }
          if (enhancer.isDraft(cdaModule)) {
            result.getBase().getMeta().setDraft(true);
          }
        }
      }
    }

    if (editorialFeaturesDepth > 0) {
      enhancer.enhance(cdaLesson, result.getBase());
    }

    return result;
  }

  private Module createModule(CDAEntry cdaModule) {
    final String title = m(cdaModule.getField("title"));
    switch (cdaModule.contentType().id()) {
      case "lessonCopy":
        return new CopyModule()
            .<CopyModule>setTitle(title)
            .setCopy(m(cdaModule.getField("copy")))
            ;
      case "lessonImage":
        return new ImageModule()
            .<ImageModule>setTitle(title)
            .setCaption(m(cdaModule.getField("caption")))
            .setMissingImageLabel(t(Keys.imageErrorTitle))
            .setImageUrl(((CDAAsset) cdaModule.getField("image")).urlForImageWith(https()))
            ;
      case "lessonCodeSnippets":
        return new CodeModule()
            .<CodeModule>setTitle(title)
            .setCurl(cdaModule.getField("curl"))
            .setDotNet(cdaModule.getField("dotNet"))
            .setJava(cdaModule.getField("java"))
            .setJavaAndroid(cdaModule.getField("javaAndroid"))
            .setJavascript(cdaModule.getField("javascript"))
            .setPhp(cdaModule.getField("php"))
            .setPython(cdaModule.getField("python"))
            .setRuby(cdaModule.getField("ruby"))
            .setSwift(cdaModule.getField("swift"))
            ;
      default:
        return null;
    }
  }

}
