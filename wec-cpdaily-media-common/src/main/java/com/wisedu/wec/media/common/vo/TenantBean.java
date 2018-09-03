package com.wisedu.wec.media.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;


public class TenantBean  implements Serializable {

  private static final long serialVersionUID = 1L;
  
  
  private String tenantId = null;
  
  private String tenantName = null;

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public String getTenantName() {
    return tenantName;
  }

  public void setTenantName(String tenantName) {
    this.tenantName = tenantName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TenantBean tenantBean = (TenantBean) o;
    return Objects.equals(tenantId, tenantBean.tenantId) &&
        Objects.equals(tenantName, tenantBean.tenantName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tenantId, tenantName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TenantBean {\n");
    
    sb.append("    tenantId: ").append(toIndentedString(tenantId)).append("\n");
    sb.append("    tenantName: ").append(toIndentedString(tenantName)).append("\n");
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

