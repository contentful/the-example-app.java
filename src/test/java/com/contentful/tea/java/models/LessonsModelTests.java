package com.contentful.tea.java.models;

import com.contentful.java.cda.CDAEntry;
import com.contentful.tea.java.MainController;
import com.contentful.tea.java.models.courses.lessons.LessonParameter;
import com.contentful.tea.java.models.courses.lessons.modules.CodeModule;
import com.contentful.tea.java.models.courses.lessons.modules.CopyModule;
import com.contentful.tea.java.models.courses.lessons.modules.ImageModule;
import com.contentful.tea.java.services.contentful.Contentful;
import com.contentful.tea.java.services.modelconverter.ArrayToCourses;
import com.contentful.tea.java.services.modelconverter.EntryToLandingPage;
import com.contentful.tea.java.services.modelconverter.EntryToLesson;
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
  @EnqueueHttpResponse({"lessons/complete.json", "defaults/locales.json"})
  public void lessonTest() {
    settings.setPath("/courses/one_course");
    settings.setQueryString("");

    final CDAEntry cdaLesson = client.fetch(CDAEntry.class).one("2SAYsnajosIkCOWqSmKaio");

    final LessonParameter lesson = lessonConverter.convert(cdaLesson, 2);
    assertThat(lesson.getSlug()).isEqualTo("complete_lesson");
    assertThat(lesson.getTitle()).isEqualTo("Complete Lesson > all the modules");

    assertThat(lesson.getModules()).hasSize(3);

    int i = 0;
    assertThat(lesson.getModules().get(i)).isInstanceOf(CopyModule.class);
    assertThat(lesson.getModules().get(i).getTitle()).isEqualTo("Complete Lesson &gt; Copy");
    assertThat(((CopyModule) lesson.getModules().get(i)).getCopy()).isEqualTo("This is the copy...");
    i++;

    assertThat(lesson.getModules().get(i)).isInstanceOf(ImageModule.class);
    assertThat(lesson.getModules().get(i).getTitle()).isEqualTo("Complete Lesson &gt; Image Module");
    final ImageModule imageModule = (ImageModule) lesson.getModules().get(i);
    assertThat(imageModule.getCaption()).isEqualTo("Image Module");
    assertThat(imageModule.getImageUrl()).isEqualTo("https://images.contentful.com/jnzexv31feqf/PKYPMsOlqK4SAwEOwMQky/2ccd7c30325fab8a4f34b35cc4e7e427/foo");
    i++;

    assertThat(lesson.getModules().get(i)).isInstanceOf(CodeModule.class);
    assertThat(lesson.getModules().get(i).getTitle()).isEqualTo("Complete Lesson &gt; Code Module");
    final CodeModule codeModule = (CodeModule) lesson.getModules().get(i);
    assertThat(codeModule.getCurl()).isEqualTo("curl");
    assertThat(codeModule.getJava()).isEqualTo("java");
    assertThat(codeModule.getJavaAndroid()).isEqualTo("java-android");
    assertThat(codeModule.getJavascript()).isEqualTo("javascript");
    assertThat(codeModule.getPhp()).isEqualTo("php");
    assertThat(codeModule.getRuby()).isEqualTo("ruby");
    assertThat(codeModule.getPython()).isEqualTo("python");
    i++;
  }
}
