package com.wisedu.wec.media.common.old.vo;

import java.util.ArrayList;
import java.util.List;

public class Dept {

  private String id;

  private String pid;

  private String title;

  private String path;

  private String fullName;

  private Integer quantity;

  private Integer departId;

  private List<Dept> children = new ArrayList<>();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<Dept> getChildren() {
    return children;
  }

  public void setChildren(List<Dept> children) {
    this.children = children;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public Integer getDepartId() {
    return departId;
  }

  public void setDepartId(Integer departId) {
    this.departId = departId;
  }
}
