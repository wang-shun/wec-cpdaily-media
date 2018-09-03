package com.wisedu.wec.media.common.old.po;

public class MediaApplyLimit {
  private Integer id;

  private String tenantId;

  private String userType;

  private String typeId;

  private Integer limitNumber;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId == null ? null : tenantId.trim();
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType == null ? null : userType.trim();
  }

  public String getTypeId() {
    return typeId;
  }

  public void setTypeId(String typeId) {
    this.typeId = typeId == null ? null : typeId.trim();
  }

  public Integer getLimitNumber() {
    return limitNumber;
  }

  public void setLimitNumber(Integer limitNumber) {
    this.limitNumber = limitNumber;
  }
}
