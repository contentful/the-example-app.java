package com.contentful.tea.java.models.imprint;

import com.contentful.tea.java.models.base.BaseParameter;
import com.contentful.tea.java.models.mappable.MappableType;

import java.util.Objects;

public class ImprintParameter extends MappableType {
  private BaseParameter base = new BaseParameter();

  private String companyLabel;
  private String officeLabel;
  private String germanyLabel;
  private String registrationCourtLabel;
  private String managingDirectorLabel;
  private String vatNumberLabel;

  public BaseParameter getBase() {
    return base;
  }

  public String getCompanyLabel() {
    return companyLabel;
  }

  public ImprintParameter setCompanyLabel(String companyLabel) {
    this.companyLabel = companyLabel;
    return this;
  }

  public String getOfficeLabel() {
    return officeLabel;
  }

  public ImprintParameter setOfficeLabel(String officeLabel) {
    this.officeLabel = officeLabel;
    return this;
  }

  public String getGermanyLabel() {
    return germanyLabel;
  }

  public ImprintParameter setGermanyLabel(String germanyLabel) {
    this.germanyLabel = germanyLabel;
    return this;
  }

  public String getRegistrationCourtLabel() {
    return registrationCourtLabel;
  }

  public ImprintParameter setRegistrationCourtLabel(String registrationCourtLabel) {
    this.registrationCourtLabel = registrationCourtLabel;
    return this;
  }

  public String getManagingDirectorLabel() {
    return managingDirectorLabel;
  }

  public ImprintParameter setManagingDirectorLabel(String managingDirectorLabel) {
    this.managingDirectorLabel = managingDirectorLabel;
    return this;
  }

  public String getVatNumberLabel() {
    return vatNumberLabel;
  }

  public ImprintParameter setVatNumberLabel(String vatNumberLabel) {
    this.vatNumberLabel = vatNumberLabel;
    return this;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ImprintParameter)) return false;
    final ImprintParameter that = (ImprintParameter) o;
    return Objects.equals(base, that.base) &&
        Objects.equals(getCompanyLabel(), that.getCompanyLabel()) &&
        Objects.equals(getOfficeLabel(), that.getOfficeLabel()) &&
        Objects.equals(getGermanyLabel(), that.getGermanyLabel()) &&
        Objects.equals(getRegistrationCourtLabel(), that.getRegistrationCourtLabel()) &&
        Objects.equals(getManagingDirectorLabel(), that.getManagingDirectorLabel()) &&
        Objects.equals(getVatNumberLabel(), that.getVatNumberLabel());
  }

  @Override public int hashCode() {
    return Objects.hash(base, getCompanyLabel(), getOfficeLabel(), getGermanyLabel(), getRegistrationCourtLabel(), getManagingDirectorLabel(), getVatNumberLabel());
  }

  /**
   * @return a human readable string, representing the object.
   */
  @Override public String toString() {
    return "ImprintParameter { " + super.toString() + " "
        + "companyLabel = " + getCompanyLabel() + ", "
        + "germanyLabel = " + getGermanyLabel() + ", "
        + "managingDirectorLabel = " + getManagingDirectorLabel() + ", "
        + "officeLabel = " + getOfficeLabel() + ", "
        + "registrationCourtLabel = " + getRegistrationCourtLabel() + ", "
        + "vatNumberLabel = " + getVatNumberLabel() + " "
        + "}";
  }
}
