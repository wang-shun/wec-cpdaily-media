package com.wisedu.wec.media.common.old.po;

/**
 * 学院专业
 *
 */
public class AcademyMajor {
  private String majorId;

  private String majorName;

  private String academyId;

  private String academyName;

  public String getMajorId() {
    return majorId;
  }

  public void setMajorId(String majorId) {
    this.majorId = majorId == null ? null : majorId.trim();;
  }

  public String getMajorName() {
    return majorName;
  }

  public void setMajorName(String majorName) {
    this.majorName = majorName == null ? null : majorName.trim();
  }

  public String getAcademyId() {
    return academyId;
  }

  public void setAcademyId(String academyId) {
    this.academyId = academyId == null ? null : academyId.trim();
  }

  public String getAcademyName() {
    return academyName;
  }

  public void setAcademyName(String academyName) {
    this.academyName = academyName == null ? null : academyName.trim();
  }
}
