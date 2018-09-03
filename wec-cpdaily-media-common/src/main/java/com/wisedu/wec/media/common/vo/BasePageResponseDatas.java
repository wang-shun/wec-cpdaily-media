package com.wisedu.wec.media.common.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class BasePageResponseDatas<T> implements Serializable {

  private static final long serialVersionUID = 1L;

  private List<T> rows;

  private int pageNumber;

  private int pageSize;

  private long totalSize;

  public List<T> getRows() {
    return rows;
  }

  public void setRows(List<T> rows) {
    this.rows = rows;
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public long getTotalSize() {
    return totalSize;
  }

  public void setTotalSize(long totalSize) {
    this.totalSize = totalSize;
  }

  @Override
  public int hashCode() {
    return Objects.hash(rows);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BasePageResponseDatas {\n");
    
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

