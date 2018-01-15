package com.contentful.tea.java.services.modelconverter;

import com.contentful.java.cda.CDAAsset;
import com.contentful.java.cda.CDAEntry;
import com.contentful.tea.java.models.courses.Category;
import com.contentful.tea.java.models.courses.Course;
import com.contentful.tea.java.models.courses.CourseParameter;
import com.contentful.tea.java.models.courses.lessons.Lesson;
import com.contentful.tea.java.services.localization.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.contentful.java.cda.image.ImageOption.http;

@Component
public class EntryToCourse extends ContentfulModelToMappableTypeConverter<EntryToCourse.Compound, CourseParameter> {
  public static class Compound {
    private CDAEntry course;
    private String courseSlug;
    private String lessonSlug;
    private Set<String> visitedLessons;

    public CDAEntry getCourse() {
      return course;
    }

    public Compound setCourse(CDAEntry course) {
      this.course = course;
      return this;
    }

    public String getCourseSlug() {
      return courseSlug;
    }

    public Compound setCourseSlug(String selectedId) {
      this.courseSlug = selectedId;
      return this;
    }

    public String getLessonSlug() {
      return lessonSlug;
    }

    public Compound setLessonSlug(String lessonSlug) {
      this.lessonSlug = lessonSlug;
      return this;
    }

    public Set<String> getVisitedLessons() {
      return visitedLessons;
    }

    public Compound setVisitedLessons(Set<String> visitedLessons) {
      this.visitedLessons = visitedLessons;
      return this;
    }
  }

  @Autowired
  @SuppressWarnings("unused")
  private EntryToLesson entryToLesson;

  @Override
  public CourseParameter convert(EntryToCourse.Compound compound) {
    final CDAEntry course = compound.getCourse();
    final List<CDAEntry> cdaCategories = course.getField("categories");
    final List<Category> categories = getCategories(cdaCategories);

    final List<CDAEntry> cdaLessons = course.getField("lessons");
    final List<Lesson> lessons = new ArrayList<>();
    Lesson sluggedLesson = null;
    final Set<String> visitedLessons = compound.getVisitedLessons() == null ? new HashSet<>() : compound.getVisitedLessons();
    for (final CDAEntry cdaLesson : cdaLessons) {
      final Lesson lesson = entryToLesson.convert(cdaLesson);
      final boolean matchingSlug = Objects.equals(compound.getLessonSlug(), lesson.getSlug());
      if (matchingSlug) {
        sluggedLesson = lesson;
      }
      final String active = matchingSlug ? "active" : "";
      final String visited = visitedLessons.contains(lesson.getSlug()) ? "visited" : "";
      lesson.setCssClass(String.format("%s %s", active, visited));
      lessons.add(lesson);
    }

    final Lesson nextLesson = getNextLesson(lessons, sluggedLesson);

    final CDAAsset image = course.getField("image");
    final CourseParameter courseParameter = new CourseParameter();
    courseParameter
        .getBase()
        .getMeta()
        .setTitle(sluggedLesson == null ? course.getField("title") : sluggedLesson.getTitle())
    ;

    final String active = sluggedLesson == null ? "active" : "";
    final String visited = visitedLessons.contains("/") ? "visited" : "";
    final String overviewCssClass = String.format("%s %s", active, visited);

    return courseParameter
        .setDurationLabel(t(Keys.durationLabel))
        .setStartCourseLabel(t(Keys.startCourseLabel))
        .setMinutesLabel(t(Keys.minutesLabel))
        .setOverviewLabel(t(Keys.overviewLabel))
        .setCourseOverviewLabel(t(Keys.courseOverviewLabel))
        .setCourseOverviewCssClass(overviewCssClass)
        .setSkillLevelLabel(t(Keys.skillLevelLabel))
        .setTableOfContentsLabel(t(Keys.tableOfContentsLabel))
        .setNextLessonLabel(t(Keys.nextLessonLabel))
        .setCourse(new Course()
            .setCategories(categories)
            .setImageUrl(image.urlForImageWith(http()))
            .setDescription(m(course.getField("description")))
            .setShortDescription(course.getField("shortDescription"))
            .setTitle(course.getField("title"))
            .setSlug(course.getField("slug"))
            .setDuration((((Double) course.getField("duration"))).intValue())
            .setDraft(isDraft(course))
            .setPendingChanges(hasPendingChanges(course))
            .setSkillLevel(t(Keys.valueOf(course.getField("skillLevel") + "Label")))
            .setLessons(lessons)
            .setNextLessonSlug(nextLesson != null ? nextLesson.getSlug() : null)
            .setCurrentLesson(sluggedLesson)
        );
  }

  private Lesson getNextLesson(List<Lesson> lessons, Lesson sluggedLesson) {
    Lesson nextLesson = null;
    if (sluggedLesson != null) {
      final int index = lessons.indexOf(sluggedLesson);
      if (index < lessons.size() - 1 - 1) {
        nextLesson = lessons.get(index + 1);
      }
    }
    return nextLesson;
  }

  private List<Category> getCategories(List<CDAEntry> cdaCategories) {
    final List<Category> categories = new ArrayList<>();
    for (final CDAEntry cdaCategory : cdaCategories) {
      categories.add(
          new Category()
              .setSlug(cdaCategory.getField("slug"))
              .setTitle(cdaCategory.getField("title"))
      );
    }
    return categories;
  }
}
