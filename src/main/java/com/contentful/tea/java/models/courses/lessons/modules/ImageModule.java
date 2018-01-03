package com.contentful.tea.java.models.courses.lessons.modules;

import java.util.Objects;

public class ImageModule extends Module {
  private String image;
  private String caption;

  public String getImage() {
    return image;
  }

  public ImageModule setImage(String image) {
    this.image = image;
    return this;
  }

  public String getCaption() {
    return caption;
  }

  public ImageModule setCaption(String caption) {
    this.caption = caption;
    return this;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ImageModule)) return false;
    if (!super.equals(o)) return false;
    final ImageModule that = (ImageModule) o;
    return Objects.equals(getImage(), that.getImage()) &&
        Objects.equals(getCaption(), that.getCaption());
  }

  @Override public int hashCode() {
    return Objects.hash(super.hashCode(), getImage(), getCaption());
  }

  /**
   * @return a human readable string, representing the object.
   */
  @Override public String toString() {
    return "ImageModule { " + super.toString() + " "
        + "caption = " + getCaption() + ", "
        + "image = " + getImage() + " "
        + "}";
  }
}
