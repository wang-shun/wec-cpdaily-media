package com.wisedu.wec.media.common.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class TenantListResponseDatas  implements Serializable {

  private static final long serialVersionUID = 1L;
  
  
  private List<TenantBean> rows = new ArrayList<TenantBean>();

  public List<TenantBean> getRows() {
    return rows;
  }

  public void setRows(List<TenantBean> rows) {
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
    TenantListResponseDatas tenantListResponseDatas = (TenantListResponseDatas) o;
    return Objects.equals(rows, tenantListResponseDatas.rows);
  }

  @Override
  public int hashCode() {
    return Objects.hash(rows);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TenantListResponseDatas {\n");
    
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

