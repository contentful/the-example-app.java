package com.contentful.tea.java.models;

import com.contentful.tea.java.models.base.ApiParameter;
import com.contentful.tea.java.models.base.BaseParameter;
import com.contentful.tea.java.models.base.BreadcrumbParameter;
import com.contentful.tea.java.models.base.Locale;
import com.contentful.tea.java.models.base.LocalesParameter;
import com.contentful.tea.java.models.base.MetaParameter;
import com.contentful.tea.java.models.courses.Category;
import com.contentful.tea.java.models.courses.Course;
import com.contentful.tea.java.models.courses.CourseParameter;
import com.contentful.tea.java.models.courses.CoursesParameter;
import com.contentful.tea.java.models.courses.lessons.LessonParameter;
import com.contentful.tea.java.models.courses.lessons.modules.CodeModule;
import com.contentful.tea.java.models.courses.lessons.modules.ImageModule;
import com.contentful.tea.java.models.errors.ErrorParameter;
import com.contentful.tea.java.models.landing.LandingPageParameter;
import com.contentful.tea.java.models.landing.modules.HeroImageModule;
import com.contentful.tea.java.models.landing.modules.HighlightedCourseModule;
import com.contentful.tea.java.models.settings.SettingsParameter;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class SimpleModelMethodTests {
  @Test public void testSettingsParameterToString() {
    final SettingsParameter object = new SettingsParameter().setAccessTokenLabel("TEST-SettingsParameter");
    assertThat(object.toString()).contains("TEST-SettingsParameter");
    assertThat(object).isNotEqualTo(new SettingsParameter());
    assertThat(object.hashCode()).isNotEqualTo(new SettingsParameter());
    assertThat(new SettingsParameter().toString()).isEqualTo(new SettingsParameter().toString());
  }

  @Test public void testLandingPageParameterToString() {
    final LandingPageParameter object = new LandingPageParameter()
        .addModule(
            new HeroImageModule()
                .setHeadline("TEST-LandingPageParameter")
        );
    assertThat(object.toString()).contains("TEST-LandingPageParameter");
    assertThat(object).isNotEqualTo(new LandingPageParameter());
    assertThat(object.hashCode()).isNotEqualTo(new LandingPageParameter());
  }

  @Test public void testCoursesParameterToString() {
    final CoursesParameter object = new CoursesParameter().setTitle("TEST-CoursesParameter");
    assertThat(object.toString()).contains("TEST-CoursesParameter");
    assertThat(object).isNotEqualTo(new CoursesParameter());
    assertThat(object.hashCode()).isNotEqualTo(new CoursesParameter());
  }

  @Test public void testCourseParameterToString() {
    final CourseParameter object = new CourseParameter().setDurationLabel("TEST-CourseParameter");
    assertThat(object.toString()).contains("TEST-CourseParameter");
    assertThat(object).isNotEqualTo(new CourseParameter());
    assertThat(object.hashCode()).isNotEqualTo(new CourseParameter());
  }

  @Test public void testCourseToString() {
    final Course object = new Course().setImageUrl("TEST-Course");
    assertThat(object.toString()).contains("TEST-Course");
    assertThat(object).isNotEqualTo(new Course());
    assertThat(object.hashCode()).isNotEqualTo(new Course());
  }

  @Test public void testCategoryToString() {
    final Category object = new Category().setSlug("TEST-Category");
    assertThat(object.toString()).contains("TEST-Category");
    assertThat(object).isNotEqualTo(new Category());
    assertThat(object.hashCode()).isNotEqualTo(new Category());
  }

  @Test public void testMetaParameterToString() {
    final MetaParameter object = new MetaParameter().setHomeCSSClass("TEST-MetaParameter");
    assertThat(object.toString()).contains("TEST-MetaParameter");
    assertThat(object).isNotEqualTo(new MetaParameter());
    assertThat(object.hashCode()).isNotEqualTo(new MetaParameter());
  }

  @Test public void testLocalesParameterToString() {
    final LocalesParameter object = new LocalesParameter().setCurrentLocaleCode("TEST-LocalesParameter");
    assertThat(object.toString()).contains("TEST-LocalesParameter");
    assertThat(object).isNotEqualTo(new LocalesParameter());
    assertThat(object.hashCode()).isNotEqualTo(new LocalesParameter());
  }

  @Test public void testLocaleToString() {
    final Locale object = new Locale().setCssClass("TEST-Locale");
    assertThat(object.toString()).contains("TEST-Locale");
    assertThat(object).isNotEqualTo(new Locale());
    assertThat(object.hashCode()).isNotEqualTo(new Locale());
  }

  @Test public void testBreadcrumbParameterToString() {
    final BreadcrumbParameter object = new BreadcrumbParameter()
        .addBreadcrumb(
            new BreadcrumbParameter
                .Breadcrumb()
                .setLabel("TEST-BreadcrumbParameter")
        );
    assertThat(object.toString()).contains("TEST-BreadcrumbParameter");
    assertThat(object).isNotEqualTo(new BreadcrumbParameter());
    assertThat(object.hashCode()).isNotEqualTo(new BreadcrumbParameter());
  }

  @Test public void testBaseParameterToString() {
    final BaseParameter object = new BaseParameter();
    object
        .getLocales()
        .setCurrentLocaleName("TEST-BaseParameter");
    assertThat(object.toString()).contains("TEST-BaseParameter");
    assertThat(object).isNotEqualTo(new BaseParameter());
    assertThat(object.hashCode()).isNotEqualTo(new BaseParameter());
  }

  @Test public void testApiParameterToString() {
    final ApiParameter object = new ApiParameter().setCpaButtonCSSClass("TEST-ApiParameter");
    assertThat(object.toString()).contains("TEST-ApiParameter");
    assertThat(object).isNotEqualTo(new ApiParameter());
    assertThat(object.hashCode()).isNotEqualTo(new ApiParameter());
  }

  @Test public void testHeroImageModuleToString() {
    final HeroImageModule object = new HeroImageModule().setBackgroundImageUrl("TEST-HeroImageModule");
    assertThat(object.toString()).contains("TEST-HeroImageModule");
    assertThat(object).isNotEqualTo(new HeroImageModule());
    assertThat(object.hashCode()).isNotEqualTo(new HeroImageModule());
  }

  @Test public void testHighlightedCourseModuleToString() {
    final HighlightedCourseModule object = new HighlightedCourseModule()
        .setCourse(
            new Course()
                .setNextLessonSlug("TEST-HighlightedCourseModule")
        );
    assertThat(object.toString()).contains("TEST-HighlightedCourseModule");
    assertThat(object).isNotEqualTo(new HighlightedCourseModule());
    assertThat(object.hashCode()).isNotEqualTo(new HighlightedCourseModule());
  }

  @Test public void testLessonToString() {
    final LessonParameter object = new LessonParameter().setCssClass("TEST-Lesson");
    assertThat(object.toString()).contains("TEST-Lesson");
    assertThat(object).isNotEqualTo(new LessonParameter());
    assertThat(object.hashCode()).isNotEqualTo(new LessonParameter());
  }

  @Test public void testImageModuleToString() {
    final ImageModule object = new ImageModule().setImageUrl("TEST-ImageModule");
    assertThat(object.toString()).contains("TEST-ImageModule");
    assertThat(object).isNotEqualTo(new ImageModule());
    assertThat(object.hashCode()).isNotEqualTo(new ImageModule());
  }

  @Test public void testCodeModuleToString() {
    final CodeModule object = new CodeModule().setJava("TEST-CodeModule");
    assertThat(object.toString()).contains("TEST-CodeModule");
    assertThat(object).isNotEqualTo(new CodeModule());
    assertThat(object.hashCode()).isNotEqualTo(new CodeModule());
  }
}
