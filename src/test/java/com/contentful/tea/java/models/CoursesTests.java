package com.contentful.tea.java.models;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAEntry;
import com.contentful.tea.java.MainController;
import com.contentful.tea.java.models.courses.Course;
import com.contentful.tea.java.models.courses.CoursesParameter;
import com.contentful.tea.java.models.landing.LandingPageParameter;
import com.contentful.tea.java.models.landing.modules.CopyModule;
import com.contentful.tea.java.models.landing.modules.HeroImageModule;
import com.contentful.tea.java.models.landing.modules.HighlightedCourseModule;
import com.contentful.tea.java.services.contentful.Contentful;
import com.contentful.tea.java.services.modelconverter.ArrayToCourses;
import com.contentful.tea.java.services.modelconverter.ArrayToCourses.ArrayAndSelectedCategory;
import com.contentful.tea.java.services.modelconverter.EntryToCourse;
import com.contentful.tea.java.services.modelconverter.EntryToLandingPage;
import com.contentful.tea.java.services.settings.Settings;
import com.contentful.tea.java.utils.http.EnqueueHttpResponse;
import com.contentful.tea.java.utils.http.EnqueuedHttpResponseTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainController.class)
public class CoursesTests extends EnqueuedHttpResponseTests {

  @Autowired
  @SuppressWarnings("unused")
  private EntryToLandingPage landingPageConverter;

  @Autowired
  @SuppressWarnings("unused")
  private ArrayToCourses arrayToCourses;

  @Autowired
  @SuppressWarnings("unused")
  private EntryToCourse entryToCourse;

  @MockBean
  @SuppressWarnings("unused")
  private Contentful contentful;

  @Autowired
  @SuppressWarnings("unused")
  private Settings settings;

  @Before
  public void setup() {
    given(this.contentful.getCurrentClient()).willReturn(client);
  }

  @After
  public void tearDown() {
    contentful.reset();
    settings.reset();
  }

  @Test
  @EnqueueHttpResponse({"home/main.json", "defaults/locales.json"})
  public void homeTest() {
    settings.setPath("/");
    settings.setQueryString("");

    final CDAEntry homeEntry = (CDAEntry) client.fetch(CDAEntry.class)
        .withContentType("layout")
        .all()
        .items()
        .get(0);

    final LandingPageParameter p = landingPageConverter.convert(homeEntry, 2);

    assertThat(p.getBase().getMeta().getTitle()).isEqualTo("Home");

    assertThat(p.getModules()).isNotNull().doesNotContainNull().hasSize(4);

    assertThat(p.getModules().get(0)).isInstanceOf(HighlightedCourseModule.class);
    final HighlightedCourseModule highlight = (HighlightedCourseModule) p.getModules().get(0);
    assertThat(highlight.getCourse().getTitle()).isEqualTo("Hello world");

    assertThat(p.getModules().get(1)).isInstanceOf(HeroImageModule.class);
    final HeroImageModule hero = (HeroImageModule) p.getModules().get(1);
    assertThat(hero.getHeadline()).isEqualTo("[Draft] Hero Image Copy");

    assertThat(p.getModules().get(2)).isInstanceOf(CopyModule.class);
    final CopyModule emphasized = (CopyModule) p.getModules().get(2);
    assertThat(emphasized.hasEmphasizeStyle()).isTrue();
    assertThat(emphasized.getHeadline()).isEqualTo("Emphasized headline");

    assertThat(p.getModules().get(3)).isInstanceOf(CopyModule.class);
    final CopyModule nonEmphasized = (CopyModule) p.getModules().get(3);
    assertThat(nonEmphasized.hasEmphasizeStyle()).isFalse();
    assertThat(nonEmphasized.getHeadline()).isEqualTo("Not emphasized, but pending");
  }

  @Test
  @EnqueueHttpResponse({"models/courses.json", "models/categories.json", "defaults/locales.json"})
  public void allCoursesTest() {
    settings.setPath("/courses");
    settings.setQueryString("");

    final CDAArray courses = client.fetch(CDAEntry.class)
        .withContentType("layout")
        .all();

    final ArrayAndSelectedCategory compound = new ArrayAndSelectedCategory().setList(courses.items());
    final CoursesParameter p = arrayToCourses.convert(compound, 2);

    assertThat(p.getBase().getMeta().getTitle()).isEqualTo("All courses (5)");

    assertThat(p.getCategories()).hasSize(2);
    assertThat(p.getCategories().get(0).getTitle()).isEqualTo("Getting started");
    assertThat(p.getCategories().get(0).getSlug()).isEqualTo("getting-started");

    assertThat(p.getCourses()).hasSize(5);
    assertThat(p.getCourses().get(3).getTitle()).isEqualTo("How the example app is built");
    assertThat(p.getCourses().get(3).getImageUrl()).isEqualTo("//images.contentful.com/jnzexv31feqf/2KUZRfRHgk8Q8MqOYaa4aA/142bb20449dedbe70bd039d214dab2e3/Contentful_Architecture_101.jpg");
    assertThat(p.getCourses().get(3).getShortDescription()).isEqualTo("By looking at the code of the example app, you will get a sense of how to use a Contentful SDK in your programming language. For any third-party dependency, refer to its documentation.");
    assertThat(p.getCourses().get(3).getSlug()).isEqualTo("how-the-example-app-is-built");
    assertThat(p.getCourses().get(3).getCategories()).hasSize(1);
    assertThat(p.getCourses().get(3).getCategories().get(0).getSlug()).isEqualTo("application-development");
    assertThat(p.getCourses().get(3).getCategories().get(0).getTitle()).isEqualTo("Application Development");
  }

  @Test
  @EnqueueHttpResponse({"courses/one_category.json", "models/categories.json", "defaults/locales.json"})
  public void courseCategoryTest() {
    settings.setPath("/courses/categories/application-development");
    settings.setQueryString("");

    final CDAArray courses = client.fetch(CDAEntry.class)
        .withContentType("courses")
        .all();

    final ArrayAndSelectedCategory compound = new ArrayAndSelectedCategory().setList(courses.items()).setCategorySlug("application-development");
    final CoursesParameter p = arrayToCourses.convert(compound, 2);

    assertThat(p.getBase().getMeta().getTitle()).isEqualTo("Application Development (2)");

    assertThat(p.getCategories()).hasSize(2);
    assertThat(p.getCategories().get(1).getTitle()).isEqualTo("Application Development");
    assertThat(p.getCategories().get(1).getSlug()).isEqualTo("application-development");

    assertThat(p.getCourses()).hasSize(2);
    assertThat(p.getCourses().get(0).getTitle()).isEqualTo("How the example app is built");
    assertThat(p.getCourses().get(0).getImageUrl()).isEqualTo("//images.contentful.com/jnzexv31feqf/2KUZRfRHgk8Q8MqOYaa4aA/142bb20449dedbe70bd039d214dab2e3/Contentful_Architecture_101.jpg");
    assertThat(p.getCourses().get(0).getShortDescription()).isEqualTo("By looking at the code of the example app, you will get a sense of how to use a Contentful SDK in your programming language. For any third-party dependency, refer to its documentation.");
    assertThat(p.getCourses().get(0).getSlug()).isEqualTo("how-the-example-app-is-built");
    assertThat(p.getCourses().get(0).getCategories()).hasSize(1);
    assertThat(p.getCourses().get(0).getCategories().get(0).getSlug()).isEqualTo("application-development");
    assertThat(p.getCourses().get(0).getCategories().get(0).getTitle()).isEqualTo("Application Development");
  }

  @Test
  @EnqueueHttpResponse({"courses/one.json", "defaults/locales.json"})
  public void singleCourseTest() {
    settings.setPath("/courses/one_course");
    settings.setQueryString("");

    final CDAEntry cdaCourse = client.fetch(CDAEntry.class)
        .one("34MlmiuMgU8wKCOOIkAuMy");

    final EntryToCourse.Compound compound = new EntryToCourse.Compound().setCourse(cdaCourse);
    final Course course = entryToCourse.convert(compound, 2).getCourse();

    assertThat(course.getSlug()).isEqualTo("how-the-example-app-is-built");
    assertThat(course.getTitle()).isEqualTo("How the example app is built");
    assertThat(course.getCategories()).hasSize(1);
    assertThat(course.getCategories().get(0).getSlug()).isEqualTo("application-development");
    assertThat(course.getCategories().get(0).getTitle()).isEqualTo("Application Development");
    assertThat(course.getDuration()).isEqualTo(5);
    assertThat(course.getImageUrl()).isEqualTo("//images.contentful.com/jnzexv31feqf/2KUZRfRHgk8Q8MqOYaa4aA/142bb20449dedbe70bd039d214dab2e3/Contentful_Architecture_101.jpg");
    assertThat(course.getShortDescription()).isEqualTo("Learn about best practices when building an application.");
    assertThat(course.getSkillLevel()).isEqualTo("Beginner");

    assertThat(course.getLessons()).hasSize(5).doesNotContainNull();
  }
}
