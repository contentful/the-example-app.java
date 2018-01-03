package com.contentful.tea.java.models;

import com.contentful.java.cda.CDAEntry;
import com.contentful.tea.java.MainController;
import com.contentful.tea.java.models.courses.lessons.Lesson;
import com.contentful.tea.java.models.courses.lessons.modules.CodeModule;
import com.contentful.tea.java.models.courses.lessons.modules.CopyModule;
import com.contentful.tea.java.models.courses.lessons.modules.ImageModule;
import com.contentful.tea.java.services.modelconverter.ArrayToCourses;
import com.contentful.tea.java.services.modelconverter.EntryToLandingPage;
import com.contentful.tea.java.services.modelconverter.EntryToLesson;
import com.contentful.tea.java.utils.http.EnqueueHttpResponse;
import com.contentful.tea.java.utils.http.EnqueuedHttpResponseTests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainController.class)
public class LessonsModelTests extends EnqueuedHttpResponseTests {

  @Autowired
  @SuppressWarnings("unused")
  private EntryToLandingPage landingPageConverter;

  @Autowired
  @SuppressWarnings("unused")
  private ArrayToCourses coursesConverter;

  @Autowired
  @SuppressWarnings("unused")
  private EntryToLesson lessonConverter;

  @Autowired
  @SuppressWarnings("unused")
  private Settings settings;

  @Before
  public void setup() {
    settings.setSpaceId("jnzexv31feqf");
    settings.setDeliveryAccessToken("<DELIVERY_TOKEN>");

    settings.contentfulDeliveryClient = client;
  }


  @Test
  @EnqueueHttpResponse({"lessons/complete.json", "defaults/space.json"})
  public void lessonTest() {
    settings.setPath("/courses/one_course");
    settings.setQueryString("");

    final CDAEntry cdaLesson = client.fetch(CDAEntry.class).one("2SAYsnajosIkCOWqSmKaio");

    final Lesson lesson = lessonConverter.convert(cdaLesson);
    assertThat(lesson.getSlug()).isEqualTo("complete_lesson");
    assertThat(lesson.getTitle()).isEqualTo("Complete Lesson > all the modules");

    assertThat(lesson.getModules()).hasSize(3);

    int i = 0;
    assertThat(lesson.getModules().get(i)).isInstanceOf(CopyModule.class);
    assertThat(lesson.getModules().get(i).getTitle()).isEqualTo("Complete Lesson > Copy");
    assertThat(((CopyModule) lesson.getModules().get(i)).getCopy()).isEqualTo("Complete Lesson &gt; Copy");
    i++;

    assertThat(lesson.getModules().get(i)).isInstanceOf(ImageModule.class);
    assertThat(lesson.getModules().get(i).getTitle()).isEqualTo("Complete Lesson > Image Module");
    assertThat(((ImageModule) lesson.getModules().get(i)).getCaption()).isEqualTo("Image Module");
    assertThat(((ImageModule) lesson.getModules().get(i)).getImage()).isEqualTo("http://images.contentful.com/jnzexv31feqf/PKYPMsOlqK4SAwEOwMQky/2ccd7c30325fab8a4f34b35cc4e7e427/foo");
    i++;

    assertThat(lesson.getModules().get(i)).isInstanceOf(CodeModule.class);
    assertThat(lesson.getModules().get(i).getTitle()).isEqualTo("Complete Lesson > Code Module");
    assertThat(((CodeModule) lesson.getModules().get(i)).getCurl()).isEqualTo("curl");
    assertThat(((CodeModule) lesson.getModules().get(i)).getJava()).isEqualTo("java");
    assertThat(((CodeModule) lesson.getModules().get(i)).getJavaAndroid()).isEqualTo("java-android");
    assertThat(((CodeModule) lesson.getModules().get(i)).getJavascript()).isEqualTo("javascript");
    assertThat(((CodeModule) lesson.getModules().get(i)).getPhp()).isEqualTo("php");
    assertThat(((CodeModule) lesson.getModules().get(i)).getRuby()).isEqualTo("ruby");
    assertThat(((CodeModule) lesson.getModules().get(i)).getPython()).isEqualTo("python");
    i++;
  }
}
