package com.contentful.tea.java.services.modelconverter;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAAsset;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.CDAResource;
import com.contentful.tea.java.models.courses.Category;
import com.contentful.tea.java.models.courses.Course;
import com.contentful.tea.java.models.courses.CoursesParameter;
import com.contentful.tea.java.services.localization.Keys;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.contentful.java.cda.image.ImageOption.http;

@Component
public class ArrayToCourses extends ContentfulModelToMappableTypeConverter<CDAArray, CoursesParameter> {

  @Override
  public CoursesParameter convert(CDAArray courses) {
    final CoursesParameter parameter = new CoursesParameter();
    parameter.getBase().getMeta().setTitle(t(Keys.allCoursesLabel));

    parameter
        .setCategories(createCategories(courses))
        .setCourses(createCourses(courses))
    ;

    return parameter;
  }

  private List<Category> createCategories(CDAArray courses) {
    final Map<String, Category> categories = new HashMap<>();

    for (final CDAResource resource : courses.items()) {
      if (resource instanceof CDAEntry) {
        final CDAEntry course = (CDAEntry) resource;
        final String courseLocale = course.locale();
        course.setLocale(settings.getLocale());

        final List<CDAEntry> cdaCategories = course.getField("categories");
        for (final CDAEntry category : cdaCategories) {
          final String categoryLocale = category.locale();
          category.setLocale(settings.getLocale());

          if (!categories.containsKey((String) category.getField("slug"))) {
            categories.put(
                category.getField("slug"),
                new Category()
                    .setSlug(category.getField("slug"))
                    .setTitle(category.getField("title"))
            );
          }

          category.setLocale(categoryLocale);
        }

        course.setLocale(courseLocale);
      } else {
        throw new IllegalStateException("Courses found of non entry type");
      }
    }

    return new ArrayList<>(categories.values());
  }

  private List<Course> createCourses(CDAArray cdaCourses) {
    final List<Course> courses = new ArrayList<>();

    for (final CDAResource resource : cdaCourses.items()) {
      if (resource instanceof CDAEntry) {
        final CDAEntry course = (CDAEntry) resource;
        final String courseLocale = course.locale();
        course.setLocale(settings.getLocale());

        final CDAAsset image = course.getField("image");
        final Course createdCourse = new Course()
            .setImageUrl(image.urlForImageWith(http()))
            .setTitle(course.getField("title"))
            .setShortDescription(course.getField("description"))
            .setSlug(course.getField("slug"));

        final List<CDAEntry> categories = course.getField("categories");
        for (final CDAEntry category : categories) {
          final String categoryLocale = category.locale();
          category.setLocale(settings.getLocale());

          createdCourse.addCategory(
              new Category()
                  .setSlug(category.getField("slug"))
                  .setTitle(category.getField("title"))
          );

          category.setLocale(categoryLocale);
        }

        courses.add(createdCourse);

        course.setLocale(courseLocale);
      } else {
        throw new IllegalStateException("Courses found of non entry type");
      }
    }

    return courses;
  }

}
