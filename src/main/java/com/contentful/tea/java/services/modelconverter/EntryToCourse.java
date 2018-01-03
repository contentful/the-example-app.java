package com.contentful.tea.java.services.modelconverter;

import com.contentful.java.cda.CDAAsset;
import com.contentful.java.cda.CDAEntry;
import com.contentful.tea.java.models.courses.Category;
import com.contentful.tea.java.models.courses.Course;
import com.contentful.tea.java.models.courses.lessons.Lesson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.contentful.java.cda.image.ImageOption.http;

@Component
public class EntryToCourse extends ContentfulModelToMappableTypeConverter<CDAEntry, Course> {

  @Autowired
  @SuppressWarnings("unused")
  private EntryToLesson entryToLesson;

  @Override
  public Course convert(CDAEntry cdaCourse) {
    final List<CDAEntry> cdaCategories = cdaCourse.getField("categories");
    final List<Category> categories = new ArrayList<>();
    for (final CDAEntry cdaCategory : cdaCategories) {
      categories.add(
          new Category()
              .setSlug(cdaCategory.getField("slug"))
              .setTitle(cdaCategory.getField("title"))
      );
    }

    final List<CDAEntry> cdaLessons = cdaCourse.getField("lessons");
    final List<Lesson> lessons = new ArrayList<>();
    for (final CDAEntry cdaLesson : cdaLessons) {
      lessons.add(
          entryToLesson.convert(cdaLesson)
      );
    }

    final CDAAsset image = cdaCourse.getField("image");
    final Course course = new Course()
        .setCategories(categories)
        .setImageUrl(image.urlForImageWith(http()))
        .setShortDescription(cdaCourse.getField("shortDescription"))
        .setTitle(cdaCourse.getField("title"))
        .setSlug(cdaCourse.getField("slug"))
        .setDuration((((Double) cdaCourse.getField("duration"))).intValue())
        .setDraft(isDraft(cdaCourse))
        .setPendingChanges(hasPendingChanges(cdaCourse))
        .setSkillLevel(cdaCourse.getField("skillLevel"))
        .setCssClass("")
        .setLessons(lessons);
    return course;
  }

}
