package com.wisedu.wec.media.common.old.vo;

import java.util.List;

public class BrandGroupReq {

  private List<String> groupIds;

  private List<String> userIds;

  public List<String> getGroupIds() {
    return groupIds;
  }

  public void setGroupIds(List<String> groupIds) {
    this.groupIds = groupIds;
  }

  public List<String> getUserIds() {
    return userIds;
  }

  public void setUserIds(List<String> userIds) {
    this.userIds = userIds;
  }

}
