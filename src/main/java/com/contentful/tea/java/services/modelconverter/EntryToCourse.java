package com.contentful.tea.java.services.modelconverter;

import com.contentful.java.cda.CDAAsset;
import com.contentful.java.cda.CDAEntry;
import com.contentful.tea.java.models.base.MetaParameter;
import com.contentful.tea.java.models.courses.Category;
import com.contentful.tea.java.models.courses.Course;
import com.contentful.tea.java.models.courses.CourseParameter;
import com.contentful.tea.java.models.courses.lessons.LessonParameter;
import com.contentful.tea.java.services.localization.Keys;
import com.contentful.tea.java.services.modelenhancers.EditorialFeaturesEnhancer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

  @Autowired
  @SuppressWarnings("unused")
  private EditorialFeaturesEnhancer enhancer;

  @Override
  public CourseParameter convert(EntryToCourse.Compound compound, int depth) {
    final CDAEntry course = compound.getCourse();
    final List<CDAEntry> cdaCategories = course.getField("categories");
    final List<Category> categories = getCategories(cdaCategories);

    List<CDAEntry> cdaLessons = course.getField("lessons");
    if (cdaLessons == null) {
      cdaLessons = Collections.emptyList();
    }

    final List<LessonParameter> lessons = new ArrayList<>();
    LessonParameter sluggedLesson = null;
    final Set<String> visitedLessons = compound.getVisitedLessons() == null ? new HashSet<>() : compound.getVisitedLessons();
    for (final CDAEntry cdaLesson : cdaLessons) {
      final LessonParameter lesson = entryToLesson.convert(cdaLesson, depth - 1);
      final boolean matchingSlug = Objects.equals(compound.getLessonSlug(), lesson.getSlug());
      if (matchingSlug) {
        sluggedLesson = lesson;
      }
      final String active = matchingSlug ? "active" : "";
      final String visited = visitedLessons.contains(lesson.getSlug()) ? "visited" : "";
      lesson.setCssClass(String.format("%s %s", active, visited));
      lessons.add(lesson);
    }

    if (compound.getLessonSlug() != null && sluggedLesson == null) {
      throw new IllegalStateException("Could not find slug '" + compound.getLessonSlug() + "'.");
    }

    final LessonParameter nextLesson = getNextLesson(lessons, sluggedLesson);

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

    String skillLevelField = course.getField("skillLevel");
    if (skillLevelField == null) {
      skillLevelField = "beginner";
    }

    final String skillLevel = t(Keys.valueOf(skillLevelField.toLowerCase() + "Label"));
    final Double durationFieldValue = course.getField("duration");
    final int duration = durationFieldValue == null ? 0 : durationFieldValue.intValue();
    courseParameter
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
            .setImageUrl(image != null ? image.url() : "")
            .setDescription(m(course.getField("description")))
            .setShortDescription(course.getField("shortDescription"))
            .setTitle(course.getField("title"))
            .setSlug(course.getField("slug"))
            .setDuration(duration)
            .setDraft(depth > 0 ? enhancer.isDraft(course) : false)
            .setPendingChanges(depth > 0 ? enhancer.isPending(course) : false)
            .setSkillLevel(skillLevel)
            .setLessons(lessons)
            .setNextLessonSlug(nextLesson != null ? nextLesson.getSlug() : null)
            .setCurrentLesson(sluggedLesson)
        );

    if (depth > 0) {
      enhancer.enhance(course, courseParameter.getBase());
    }

    if (sluggedLesson != null) {
      final MetaParameter sluggedMeta = sluggedLesson.getBase().getMeta();
      final MetaParameter courseMeta = courseParameter.getBase().getMeta();
      courseMeta.setDraft(courseMeta.isDraft() || sluggedMeta.isDraft());
      courseMeta.setPendingChanges(courseMeta.hasPendingChanges() || sluggedMeta.hasPendingChanges());
      courseMeta.setDeeplinkToContentful(sluggedMeta.getDeeplinkToContentful());
    }

    return courseParameter;
  }

  private LessonParameter getNextLesson(List<LessonParameter> lessons, LessonParameter sluggedLesson) {
    LessonParameter nextLesson = null;
    if (sluggedLesson != null) {
      final int index = lessons.indexOf(sluggedLesson);
      if (index + 1 <= lessons.size() - 1) {
        nextLesson = lessons.get(index + 1);
      }
    }
    return nextLesson;
  }

  private List<Category> getCategories(List<CDAEntry> cdaCategories) {
    final List<Category> categories = new ArrayList<>();
    if (cdaCategories != null) {
      for (final CDAEntry cdaCategory : cdaCategories) {
        categories.add(
            new Category()
                .setSlug(cdaCategory.getField("slug"))
                .setTitle(cdaCategory.getField("title"))
        );
      }
    }
    return categories;
  }
}
