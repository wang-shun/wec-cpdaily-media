package com.wisedu.wec.media.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class GroupMembersRequestBean  implements Serializable {

  private static final long serialVersionUID = 1L;
  
  
  private String groupId = null;
  
  private List<String> userIds = new ArrayList<String>();
  
  private List<GroupBean> groups = new ArrayList<GroupBean>();


  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public List<String> getUserIds() {
    return userIds;
  }

  public void setUserIds(List<String> userIds) {
    this.userIds = userIds;
  }

  public List<GroupBean> getGroups() {
    return groups;
  }

  public void setGroups(List<GroupBean> groups) {
    this.groups = groups;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GroupMembersRequestBean groupMembersRequestBean = (GroupMembersRequestBean) o;
    return Objects.equals(groupId, groupMembersRequestBean.groupId) &&
        Objects.equals(userIds, groupMembersRequestBean.userIds) &&
        Objects.equals(groups, groupMembersRequestBean.groups);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupId, userIds, groups);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GroupMembersRequestBean {\n");
    
    sb.append("    groupId: ").append(toIndentedString(groupId)).append("\n");
    sb.append("    userIds: ").append(toIndentedString(userIds)).append("\n");
    sb.append("    groups: ").append(toIndentedString(groups)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

