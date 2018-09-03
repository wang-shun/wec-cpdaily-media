package com.wisedu.wec.media.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;


public class UserListResponse  implements Serializable {

  private static final long serialVersionUID = 1L;
  
  
  private String code = null;
  
  private String message = null;
  
  private UserListResponseDatas datas = null;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public UserListResponseDatas getDatas() {
    return datas;
  }

  public void setDatas(UserListResponseDatas datas) {
    this.datas = datas;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserListResponse userListResponse = (UserListResponse) o;
    return Objects.equals(code, userListResponse.code) &&
        Objects.equals(message, userListResponse.message) &&
        Objects.equals(datas, userListResponse.datas);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, message, datas);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserListResponse {\n");
    
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    datas: ").append(toIndentedString(datas)).append("\n");
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

