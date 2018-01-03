package com.contentful.tea.java.models;

import com.contentful.tea.java.MainController;
import com.contentful.tea.java.models.base.BreadcrumbParameter;
import com.contentful.tea.java.models.courses.CoursesParameter;
import com.contentful.tea.java.services.StaticContentSetter;
import com.contentful.tea.java.utils.http.EnqueueHttpResponse;
import com.contentful.tea.java.utils.http.EnqueuedHttpResponseTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainController.class)
public class BreadcrumbTest extends EnqueuedHttpResponseTests {
  @Autowired
  @SuppressWarnings("unused")
  private StaticContentSetter setter;

  @Autowired()
  @SuppressWarnings("unused")
  private Settings settings;

  @Before
  public void setup() {
    settings.setSpaceId("jnzexv31feqf");
    settings.setDeliveryAccessToken("<DELIVERY_TOKEN>");
    settings.setLocale("en-US");

    settings.contentfulDeliveryClient = client;
  }

  @After
  public void shutDown() {
    settings.reset();
  }

  @Test
  @EnqueueHttpResponse({"models/courses.json", "defaults/space.json"})
  public void coursesListBreadcrumb() {
    settings.setPath("/courses");
    settings.setQueryString("");

    final CoursesParameter p = new CoursesParameter();
    p.getBase().getMeta().setTitle("title");
    setter.applyBaseContent(p.getBase());

    final List<BreadcrumbParameter.Breadcrumb> breadcrumbs = p.getBase().getBreadcrumb().getBreadcrumbs();
    assertThat(breadcrumbs).hasSize(2);
    assertThat(breadcrumbs.get(0).getLabel()).isEqualTo("Home");
    assertThat(breadcrumbs.get(0).getUrl()).isEqualTo("/");
    assertThat(breadcrumbs.get(1).getLabel()).isEqualTo("Courses");
    assertThat(breadcrumbs.get(1).getUrl()).isEqualTo("/courses");
  }

  @Test
  @EnqueueHttpResponse({"models/courses.json", "defaults/space.json"})
  public void categoriesBreadcrumb() {
    settings.setPath("/courses/categories");
    settings.setQueryString("");

    final CoursesParameter p = new CoursesParameter();
    p.getBase().getMeta().setTitle("title");
    setter.applyBaseContent(p.getBase());

    final List<BreadcrumbParameter.Breadcrumb> breadcrumbs = p.getBase().getBreadcrumb().getBreadcrumbs();
    assertThat(breadcrumbs).hasSize(3);
    assertThat(breadcrumbs.get(0).getLabel()).isEqualTo("Home");
    assertThat(breadcrumbs.get(0).getUrl()).isEqualTo("/");
    assertThat(breadcrumbs.get(1).getLabel()).isEqualTo("Courses");
    assertThat(breadcrumbs.get(1).getUrl()).isEqualTo("/courses");
    assertThat(breadcrumbs.get(2).getLabel()).isEqualTo("Categories");
    assertThat(breadcrumbs.get(2).getUrl()).isEqualTo("/courses/categories");
  }

  @Test
  @EnqueueHttpResponse({"models/courses.json", "defaults/space.json"})
  public void specificCategoryBreadcrumb() {
    settings.setPath("/courses/categories/something_cheesy");
    settings.setQueryString("");

    final CoursesParameter p = new CoursesParameter();
    p.getBase().getMeta().setTitle("Something Cheesy");
    setter.applyBaseContent(p.getBase());

    final List<BreadcrumbParameter.Breadcrumb> breadcrumbs = p.getBase().getBreadcrumb().getBreadcrumbs();
    assertThat(breadcrumbs).hasSize(4);
    assertThat(breadcrumbs.get(0).getLabel()).isEqualTo("Home");
    assertThat(breadcrumbs.get(0).getUrl()).isEqualTo("/");
    assertThat(breadcrumbs.get(1).getLabel()).isEqualTo("Courses");
    assertThat(breadcrumbs.get(1).getUrl()).isEqualTo("/courses");
    assertThat(breadcrumbs.get(2).getLabel()).isEqualTo("Categories");
    assertThat(breadcrumbs.get(2).getUrl()).isEqualTo("/courses/categories");
    assertThat(breadcrumbs.get(3).getLabel()).isEqualTo("Something Cheesy");
    assertThat(breadcrumbs.get(3).getUrl()).isEqualTo("/courses/categories/something_cheesy");
  }

  @Test
  @EnqueueHttpResponse({"models/courses.json", "defaults/space.json"})
  public void specificCourseBreadcrumb() {
    settings.setPath("/courses/something_meaty");
    settings.setQueryString("");

    final CoursesParameter p = new CoursesParameter();
    p.getBase().getMeta().setTitle("Something Meaty");
    setter.applyBaseContent(p.getBase());

    final List<BreadcrumbParameter.Breadcrumb> breadcrumbs = p.getBase().getBreadcrumb().getBreadcrumbs();
    assertThat(breadcrumbs).hasSize(3);
    assertThat(breadcrumbs.get(0).getLabel()).isEqualTo("Home");
    assertThat(breadcrumbs.get(0).getUrl()).isEqualTo("/");
    assertThat(breadcrumbs.get(1).getLabel()).isEqualTo("Courses");
    assertThat(breadcrumbs.get(1).getUrl()).isEqualTo("/courses");
    assertThat(breadcrumbs.get(2).getLabel()).isEqualTo("Something Meaty");
    assertThat(breadcrumbs.get(2).getUrl()).isEqualTo("/courses/something_meaty");
  }

  @Test
  @EnqueueHttpResponse({"courses/one.json", "defaults/space.json"})
  public void specificCourseLessonBreadcrumb() {
    settings.setPath("/courses/something_meaty/lessons/1HR1QvURo4MoSqO0eqmUeO");
    settings.setQueryString("");

    final CoursesParameter p = new CoursesParameter();
    p.getBase().getMeta().setTitle("Beef is the best");
    setter.applyBaseContent(p.getBase());

    final List<BreadcrumbParameter.Breadcrumb> breadcrumbs = p.getBase().getBreadcrumb().getBreadcrumbs();
    assertThat(breadcrumbs).hasSize(5);
    assertThat(breadcrumbs.get(0).getLabel()).isEqualTo("Home");
    assertThat(breadcrumbs.get(0).getUrl()).isEqualTo("/");
    assertThat(breadcrumbs.get(1).getLabel()).isEqualTo("Courses");
    assertThat(breadcrumbs.get(1).getUrl()).isEqualTo("/courses");
    assertThat(breadcrumbs.get(2).getLabel()).isEqualTo("How the example app is built");
    assertThat(breadcrumbs.get(2).getUrl()).isEqualTo("/courses/something_meaty");
    assertThat(breadcrumbs.get(3).getLabel()).isEqualTo("Lessons");
    assertThat(breadcrumbs.get(3).getUrl()).isEqualTo("/courses/something_meaty/lessons");
    assertThat(breadcrumbs.get(4).getLabel()).isEqualTo("Beef is the best");
    assertThat(breadcrumbs.get(4).getUrl()).isEqualTo("/courses/something_meaty/lessons/1HR1QvURo4MoSqO0eqmUeO");
  }

  @Test
  @EnqueueHttpResponse({"courses/one.json", "defaults/space.json"})
  public void settingsBreadcrumb() {
    settings.setPath("/settings");
    settings.setQueryString("");

    final CoursesParameter p = new CoursesParameter();
    p.getBase().getMeta().setTitle("Beef is the best");
    setter.applyBaseContent(p.getBase());

    final List<BreadcrumbParameter.Breadcrumb> breadcrumbs = p.getBase().getBreadcrumb().getBreadcrumbs();
    assertThat(breadcrumbs).hasSize(2);
    assertThat(breadcrumbs.get(0).getLabel()).isEqualTo("Home");
    assertThat(breadcrumbs.get(0).getUrl()).isEqualTo("/");
    assertThat(breadcrumbs.get(1).getLabel()).isEqualTo("Settings");
    assertThat(breadcrumbs.get(1).getUrl()).isEqualTo("/settings");
  }

  @Test
  @EnqueueHttpResponse({"courses/one.json", "defaults/space.json"})
  public void imprintBreadcrumb() {
    settings.setPath("/imprint");
    settings.setQueryString("");

    final CoursesParameter p = new CoursesParameter();
    p.getBase().getMeta().setTitle("Beef is the best");
    setter.applyBaseContent(p.getBase());

    final List<BreadcrumbParameter.Breadcrumb> breadcrumbs = p.getBase().getBreadcrumb().getBreadcrumbs();
    assertThat(breadcrumbs).hasSize(2);
    assertThat(breadcrumbs.get(0).getLabel()).isEqualTo("Home");
    assertThat(breadcrumbs.get(0).getUrl()).isEqualTo("/");
    assertThat(breadcrumbs.get(1).getLabel()).isEqualTo("Imprint");
    assertThat(breadcrumbs.get(1).getUrl()).isEqualTo("/imprint");
  }

  @Test
  @EnqueueHttpResponse({"courses/one.json", "defaults/space.json"})
  public void imprintBreadcrumbDeutsch() {
    settings.setPath("/imprint");
    settings.setQueryString("");
    settings.setLocale("de-DE");

    final CoursesParameter p = new CoursesParameter();
    p.getBase().getMeta().setTitle("Rindfleisch ist das Beste");
    setter.applyBaseContent(p.getBase());

    final List<BreadcrumbParameter.Breadcrumb> breadcrumbs = p.getBase().getBreadcrumb().getBreadcrumbs();
    assertThat(breadcrumbs).hasSize(2);
    assertThat(breadcrumbs.get(0).getLabel()).isEqualTo("Startseite");
    assertThat(breadcrumbs.get(0).getUrl()).isEqualTo("/");
    assertThat(breadcrumbs.get(1).getLabel()).isEqualTo("Impressum");
    assertThat(breadcrumbs.get(1).getUrl()).isEqualTo("/imprint");
  }

}
