package com.contentful.tea.java.models.courses.lessons.modules;

import java.util.Objects;

public class ImageModule extends Module {
  private String imageUrl;
  private String caption;
  private String missingImageLabel;

  public ImageModule() {
    super("image");
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public ImageModule setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }

  public String getCaption() {
    return caption;
  }

  public ImageModule setCaption(String caption) {
    this.caption = caption;
    return this;
  }

  public String getMissingImageLabel() {
    return missingImageLabel;
  }

  public ImageModule setMissingImageLabel(String missingImageLabel) {
    this.missingImageLabel = missingImageLabel;
    return this;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ImageModule)) return false;
    if (!super.equals(o)) return false;
    final ImageModule that = (ImageModule) o;
    return Objects.equals(getImageUrl(), that.getImageUrl()) &&
        Objects.equals(getCaption(), that.getCaption()) &&
        Objects.equals(getMissingImageLabel(), that.getMissingImageLabel());
  }

  @Override public int hashCode() {
    return Objects.hash(super.hashCode(), getImageUrl(), getCaption(), getMissingImageLabel());
  }

  @Override public String toString() {
    return "ImageModule { " + super.toString() + " "
        + "caption = " + getCaption() + ", "
        + "missingImageLabel = " + getMissingImageLabel() + ", "
        + "image = " + getImageUrl() + " "
        + "}";
  }
}
