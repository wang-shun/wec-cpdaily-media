package com.wisedu.wec.media.common.old.po;

public class StaffDepart {
  private String departId;

  private String departName;

  private String parentDepartId;

  public String getDepartId() {
    return departId;
  }

  public void setDepartId(String departId) {
    this.departId = departId == null ? null : departId.trim();
  }

  public String getDepartName() {
    return departName;
  }

  public void setDepartName(String departName) {
    this.departName = departName == null ? null : departName.trim();
  }

  public String getParentDepartId() {
    return parentDepartId;
  }

  public void setParentDepartId(String parentDepartId) {
    this.parentDepartId = parentDepartId == null ? null : parentDepartId.trim();
  }

}
