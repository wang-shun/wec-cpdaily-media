package com.wisedu.wec.media.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class UserListResponseDatas  implements Serializable {

  private static final long serialVersionUID = 1L;

  private List<UserBean> rows = new ArrayList<UserBean>();

  public List<UserBean> getRows() {
    return rows;
  }

  public void setRows(List<UserBean> rows) {
    this.rows = rows;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserListResponseDatas userListResponseDatas = (UserListResponseDatas) o;
    return Objects.equals(rows, userListResponseDatas.rows);
  }

  @Override
  public int hashCode() {
    return Objects.hash(rows);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserListResponseDatas {\n");
    
    sb.append("    rows: ").append(toIndentedString(rows)).append("\n");
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

