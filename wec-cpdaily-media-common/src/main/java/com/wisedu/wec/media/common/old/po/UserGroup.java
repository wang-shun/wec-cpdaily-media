package com.wisedu.wec.media.common.old.po;

import java.util.Date;

public class UserGroup {

  private String groupId;

  private String groupName;

  private String mediaId;

  private String ownerId;

  private String tenantId;

  private Integer userCount;

  private Date createTime;

  private Date updateTime;

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId == null ? null : groupId.trim();
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName == null ? null : groupName.trim();
  }

  public String getMediaId() {
    return mediaId;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId == null ? null : mediaId.trim();
  }

  public String getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId == null ? null : ownerId.trim();
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId == null ? null : tenantId.trim();
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public Integer getUserCount() {
    return userCount;
  }

  public void setUserCount(Integer userCount) {
    this.userCount = userCount;
  }

}
