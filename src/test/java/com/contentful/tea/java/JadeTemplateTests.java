package com.contentful.tea.java;

import com.contentful.tea.java.html.JadeHtmlGenerator;
import com.contentful.tea.java.models.base.BaseParameter;
import com.contentful.tea.java.models.courses.Category;
import com.contentful.tea.java.models.courses.Course;
import com.contentful.tea.java.models.courses.CourseParameter;
import com.contentful.tea.java.models.courses.CoursesParameter;
import com.contentful.tea.java.models.courses.lessons.LessonParameter;
import com.contentful.tea.java.models.landing.LandingPageParameter;
import com.contentful.tea.java.models.landing.modules.CopyModule;
import com.contentful.tea.java.models.landing.modules.HeroImageModule;
import com.contentful.tea.java.models.landing.modules.HighlightedCourseModule;
import com.contentful.tea.java.models.mappable.NullHandler;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.contentful.tea.java.utils.TestUtils.assertBaseParameterInHtml;
import static com.contentful.tea.java.utils.TestUtils.createBaseParameter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class JadeTemplateTests {
  private static final NullHandler TESTING_NULL_HANDLER = field -> {
    fail("'%s' of '%s' is undefined!", field.getName(), field.getDeclaringClass());
    return "\uD83D\uDE31";
  };

  private JadeHtmlGenerator generator;

  @Before
  public void setup() {
    generator = new JadeHtmlGenerator(false, "src/main/resources");
  }

  @Test
  public void testAllFieldsAreSetForLayoutTemplate() throws Exception {
    final BaseParameter base = new BaseParameter();
    createBaseParameter(base);

    final Map<String, Object> baseMap = new HashMap<>();
    baseMap.put("base", base.toMap(TESTING_NULL_HANDLER));

    final String generatedHtml = generator
        .generate(
            "templates/layout.jade",
            baseMap);

    assertBaseParameterInHtml(generatedHtml);
  }

  @Test
  public void landingPageTemplateHeroCopyModuleTest() throws Exception {
    final LandingPageParameter parameter = new LandingPageParameter();
    createBaseParameter(parameter.getBase());

    parameter.addModule(
        new CopyModule()
            .setHeadline("TEST-Headline")
            .setCopy("TEST-Copy")
            .setCtaTitle("TEST-Cta-title")
            .setCtaLink("TEST-Cta-link")
            .setEmphasizeStyle(true)
    );

    final String generatedHtml = generator
        .generate(
            "templates/landingPage.jade",
            parameter.toMap(TESTING_NULL_HANDLER));

    assertBaseParameterInHtml(generatedHtml);

    assertThat(generatedHtml)
        .doesNotContain("\uD83D\uDE31")
        .contains("TEST-Headline")
        .contains("TEST-Copy")
        .contains("TEST-Cta-title")
        .contains("TEST-Cta-link")
        .contains("--emphasized")
    ;
  }

  @Test
  public void landingPageTemplateHighlightedCourseModuleTest() throws Exception {
    final LandingPageParameter parameter = new LandingPageParameter();
    createBaseParameter(parameter.getBase());

    parameter.addModule(new HighlightedCourseModule()
        .setCourse(
            new Course()
                .setTitle("TEST-Course-Title")
                .setShortDescription("TEST-My_short_description")
                .setSlug("TEST-Courses-Slug")
                .setImageUrl("TEST-ImageUrl")
                .setDraft(true)
                .setPendingChanges(true)
                .addCategory(
                    new Category()
                        .setTitle("TEST-Category-title")
                        .setSlug("TEST-Category-slug")
                )
        )
    );

    final String generatedHtml = generator
        .generate(
            "templates/landingPage.jade",
            parameter.toMap());

    assertBaseParameterInHtml(generatedHtml);

    assertThat(generatedHtml)
        .doesNotContain("\uD83D\uDE31")
        .contains("TEST-My_short_description")
        .contains("TEST-Course-Title")
        .contains("TEST-Course-Title")
        .contains("TEST-Courses-Slug")
        .contains("TEST-ImageUrl")
        .contains("TEST-Category-title")
        .contains("TEST-Category-slug")
        .doesNotContain("TEST-setPendingChangesLabel")
        .doesNotContain("TEST-Pending-title")
        .doesNotContain("TEST-setDraftLabel")
        .doesNotContain("TEST-Draft-title")
    ;
  }

  @Test
  public void landingPageTemplateHeroImageModuleTest() throws Exception {
    final LandingPageParameter parameter = new LandingPageParameter();
    createBaseParameter(parameter.getBase());

    parameter.addModule(new HeroImageModule()
        .setHeadline("TEST-Headline")
        .setBackgroundImageUrl("TEST-background-url")
        .setBackgroundImageTitle("TEST-background-title")
    );

    final String generatedHtml = generator
        .generate(
            "templates/landingPage.jade",
            parameter.toMap(TESTING_NULL_HANDLER));

    assertBaseParameterInHtml(generatedHtml);

    assertThat(generatedHtml)
        .doesNotContain("\uD83D\uDE31")
        .contains("TEST-Headline")
        .contains("TEST-background-url")
        .contains("TEST-background-title")
    ;
  }

  @Test
  public void coursesListTemplateTest() throws Exception {
    final CoursesParameter parameter = new CoursesParameter();
    createBaseParameter(parameter.getBase());

    parameter
        .addCategory(
            new Category()
                .setSlug("TEST-Courses-Category-Slug")
                .setTitle("TEST-Courses-Category-Title")
        )
        .addCourse(new Course()
            .addCategory(
                new Category()
                    .setSlug("TEST-Category-Slug")
                    .setTitle("TEST-Category-Title")
            )
            .setDraft(true)
            .setPendingChanges(true)
            .setImageUrl("TEST-Course-Image-Url")
            .setShortDescription("Test-Course-Short-Description")
            .setSlug("Test-Course-Slug")
            .setTitle("Test-Course-Title")
        )
    ;

    final String generatedHtml = generator
        .generate(
            "templates/courses.jade",
            parameter.toMap());

    assertBaseParameterInHtml(generatedHtml);
    assertThat(generatedHtml)
        .doesNotContain("\uD83D\uDE31")
        .contains("TEST-Courses-Category-Slug")
        .contains("TEST-Courses-Category-Title")
        .contains("TEST-Category-Slug")
        .contains("TEST-Category-Title")
        .contains("Test-Course-Short-Description")
        .contains("Test-Course-Slug")
        .contains("Test-Course-Title")
        .doesNotContain("TEST-Course-Image-Url")
    ;
  }

  @Test
  public void courseTemplateTest() throws Exception {
    final CourseParameter parameter = new CourseParameter();
    createBaseParameter(parameter.getBase());

    final LessonParameter testLesson = new LessonParameter()
        .setSlug("TEST-lesson-slug")
        .setTitle("TEST-lesson-title")
        .setCssClass("TEST-lesson-css")
        .addModule(
            new com.contentful.tea.java.models.courses.lessons.modules.CopyModule()
                .setCopy("TEST-lesson-module-copy")
                .setTitle("TEST-lesson-module-title")
        );
    createBaseParameter(testLesson.getBase());

    parameter
        .setDurationLabel("TEST-setDurationLabel")
        .setCourse(
            new Course()
                .setDuration(42)
                .setImageUrl("TEST-setImageUrl")
                .setDescription("TEST-setDescription")
                .setShortDescription("TEST-setShortDescription")
                .setSkillLevel("TEST-skill-level")
                .setSlug("TEST-setSlug")
                .setTitle("TEST-setTitle")
                .addCategory(
                    new Category()
                        .setTitle("TEST-category-title")
                        .setSlug("TEST-category-slug")
                        .setCssClass("TEST-category-slug")
                )
                .addLesson(
                    testLesson
                )
                .setCurrentLesson(testLesson)
                .setNextLessonSlug("TEST-Nextlesson")
        )
        .setMinutesLabel("TEST-setMinutesLabel")
        .setOverviewLabel("TEST-setOverviewLabel")
        .setCourseOverviewLabel("TEST-setCourseOverviewLabel")
        .setCourseOverviewCssClass("TEST-setCourseOverviewCssClass")
        .setSkillLevelLabel("TEST-setSkillLevelLabel")
        .setStartCourseLabel("TEST-setStartCourseLabel")
        .setTableOfContentsLabel("TEST-setTableOfContentsLabel")
        .setNextLessonLabel("TEST-nextLessonLabel")
    ;


    final String generatedHtml = generator
        .generate(
            "templates/course.jade",
            parameter.toMap(
                TESTING_NULL_HANDLER
            ));

    assertBaseParameterInHtml(generatedHtml);
    assertThat(generatedHtml)
        .doesNotContain("\uD83D\uDE31")
        .contains("TEST-setSlug")
        .contains("TEST-setTitle")
        .contains("TEST-lesson-slug")
        .contains("TEST-lesson-title")
        .contains("TEST-lesson-css")
        .contains("TEST-setCourseOverviewCssClass")
        .contains("TEST-nextLessonLabel")
    ;
  }
}