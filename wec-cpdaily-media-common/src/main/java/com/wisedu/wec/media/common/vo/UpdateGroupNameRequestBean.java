package com.wisedu.wec.media.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Objects;


public class UpdateGroupNameRequestBean  implements Serializable {

  private static final long serialVersionUID = 1L;
  
  
  private String groupId = null;
  @NotBlank @Length(max = 50)
  private String groupName = null;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateGroupNameRequestBean updateGroupNameRequestBean = (UpdateGroupNameRequestBean) o;
    return Objects.equals(groupId, updateGroupNameRequestBean.groupId) &&
        Objects.equals(groupName, updateGroupNameRequestBean.groupName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupId, groupName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateGroupNameRequestBean {\n");
    
    sb.append("    groupId: ").append(toIndentedString(groupId)).append("\n");
    sb.append("    groupName: ").append(toIndentedString(groupName)).append("\n");
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

