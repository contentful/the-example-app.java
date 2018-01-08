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
public class ArrayToCourses extends ContentfulModelToMappableTypeConverter<ArrayToCourses.ArrayAndSelectedCategory, CoursesParameter> {

  public static class ArrayAndSelectedCategory {
    private CDAArray array;
    private String categorySlug;
    private String categoryName;

    public CDAArray getArray() {
      return array;
    }

    public ArrayAndSelectedCategory setArray(CDAArray array) {
      this.array = array;
      return this;
    }

    public String getCategorySlug() {
      return categorySlug;
    }

    public ArrayAndSelectedCategory setCategorySlug(String categorySlug) {
      this.categorySlug = categorySlug;
      return this;
    }

    public String getCategoryName() {
      return categoryName;
    }

    public ArrayAndSelectedCategory setCategoryName(String categoryName) {
      this.categoryName = categoryName;
      return this;
    }
  }

  @Override
  public CoursesParameter convert(ArrayAndSelectedCategory compound) {
    final CoursesParameter parameter = new CoursesParameter();
    final String categorySlug = compound.getCategorySlug() == null ? "" : compound.getCategorySlug();
    final String categoryName = compound.getCategoryName() == null ? "" : compound.getCategoryName();
    final String allCssClass = categorySlug.isEmpty() ? "active" : "";
    final String title = createTitle(categoryName, compound.array.total());
    parameter.getBase().getMeta().setTitle(title);

    parameter
        .setCategories(createCategories(compound.array, categorySlug))
        .setCourses(createCourses(compound.array))
        .setTitle(title)
        .getBase()
        .getMeta()
        .setAllCoursesCssClass(allCssClass)
    ;

    return parameter;
  }

  private String createTitle(String categoryName, int total) {
    return String.format("%s (%d)", categoryName.isEmpty() ? t(Keys.allCoursesLabel) : categoryName, total);
  }

  private List<Category> createCategories(CDAArray courses, String selectedCategory) {
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

          final String slug = category.getField("slug");
          if (!categories.containsKey((String) slug)) {
            categories.put(
                slug,
                new Category()
                    .setSlug(slug)
                    .setTitle(category.getField("title"))
                    .setCssClass(selectedCategory.equals(slug) ? "active" : "")
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
