package com.wisedu.wec.media.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;


public class UserBean  implements Serializable {

  private static final long serialVersionUID = 1L;
  
  
  private String wid = null;
  
  private String openId = null;
  
  private String name = null;
  
  private String gender = null;
  
  private String PropertyClass = null;
  
  private String userType = null;

  public String getWid() {
    return wid;
  }

  public void setWid(String wid) {
    this.wid = wid;
  }

  public String getOpenId() {
    return openId;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getPropertyClass() {
    return PropertyClass;
  }

  public void setPropertyClass(String propertyClass) {
    PropertyClass = propertyClass;
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserBean userBean = (UserBean) o;
    return Objects.equals(wid, userBean.wid) &&
        Objects.equals(openId, userBean.openId) &&
        Objects.equals(name, userBean.name) &&
        Objects.equals(gender, userBean.gender) &&
        Objects.equals(PropertyClass, userBean.PropertyClass) &&
        Objects.equals(userType, userBean.userType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(wid, openId, name, gender, PropertyClass, userType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserBean {\n");
    
    sb.append("    wid: ").append(toIndentedString(wid)).append("\n");
    sb.append("    openId: ").append(toIndentedString(openId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
    sb.append("    PropertyClass: ").append(toIndentedString(PropertyClass)).append("\n");
    sb.append("    userType: ").append(toIndentedString(userType)).append("\n");
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

