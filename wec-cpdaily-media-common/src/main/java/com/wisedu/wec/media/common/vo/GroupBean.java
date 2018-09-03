package com.wisedu.wec.media.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;


public class GroupBean  implements Serializable {

  private static final long serialVersionUID = 1L;
  
  
  private String groupId = null;
  
  private String groupName = null;
  
  private String groupType = null;


  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public String getGroupType() {
    return groupType;
  }

  public void setGroupType(String groupType) {
    this.groupType = groupType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GroupBean groupBean = (GroupBean) o;
    return Objects.equals(groupId, groupBean.groupId) &&
        Objects.equals(groupName, groupBean.groupName) &&
        Objects.equals(groupType, groupBean.groupType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupId, groupName, groupType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GroupBean {\n");
    
    sb.append("    groupId: ").append(toIndentedString(groupId)).append("\n");
    sb.append("    groupName: ").append(toIndentedString(groupName)).append("\n");
    sb.append("    groupType: ").append(toIndentedString(groupType)).append("\n");
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

