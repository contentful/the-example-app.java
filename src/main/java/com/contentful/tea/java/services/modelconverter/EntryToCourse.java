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
import java.util.Set;

import static com.contentful.java.cda.image.ImageOption.http;

@Component
public class EntryToCourse extends ContentfulModelToMappableTypeConverter<EntryToCourse.Compound, CourseParameter> {
  public static class Compound {
    private CDAEntry course;
    private String slug;
    private Set<String> visitedLessons;

    public CDAEntry getCourse() {
      return course;
    }

    public Compound setCourse(CDAEntry course) {
      this.course = course;
      return this;
    }

    public String getSlug() {
      return slug;
    }

    public Compound setSlug(String selectedId) {
      this.slug = selectedId;
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
    final List<Category> categories = new ArrayList<>();
    for (final CDAEntry cdaCategory : cdaCategories) {
      categories.add(
          new Category()
              .setSlug(cdaCategory.getField("slug"))
              .setTitle(cdaCategory.getField("title"))
      );
    }

    final List<CDAEntry> cdaLessons = course.getField("lessons");
    final List<Lesson> lessons = new ArrayList<>();
    final Set<String> visitedLessons = compound.getVisitedLessons() == null ? new HashSet<>() : compound.getVisitedLessons();
    for (final CDAEntry cdaLesson : cdaLessons) {
      final Lesson lesson = entryToLesson.convert(cdaLesson);
      final String cssClass = lesson.getCssClass();
      final String visited = visitedLessons.contains(lesson.getSlug()) ? "visited" : "";
      lesson.setCssClass(String.format("%s %s", cssClass, visited));
      lessons.add(lesson);
    }

    final String selectedId = compound.getSlug() != null && !compound.getSlug().isEmpty() ? compound.getSlug() : "";
    final CDAAsset image = course.getField("image");
    final CourseParameter courseParameter = new CourseParameter();
    courseParameter
        .getBase()
        .getMeta()
        .setTitle(course.getField("title"));

    return courseParameter
        .setDurationLabel(t(Keys.durationLabel))
        .setStartCourseLabel(t(Keys.startCourseLabel))
        .setMinutesLabel(t(Keys.minutesLabel))
        .setOverviewLabel(t(Keys.overviewLabel))
        .setCourseOverviewLabel(t(Keys.courseOverviewLabel))
        .setCourseOverviewCssClass("active " + (visitedLessons.contains("/") ? "visited" : ""))
        .setSkillLevelLabel(t(Keys.skillLevelLabel))
        .setTableOfContentsLabel(t(Keys.tableOfContentsLabel))
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
            .setCssClass(selectedId)
            .setLessons(lessons)
        );
  }
}
