package com.wisedu.wec.media.common.old.vo;

import java.io.Serializable;


public class ImMsg<T> implements Serializable {

  private static final long serialVersionUID = 3530314730531478247L;

  private String customizeMessageType;

  private Boolean needPush = false;

  private T msg_data;

  public String getCustomizeMessageType() {
    return customizeMessageType;
  }

  public void setCustomizeMessageType(String customizeMessageType) {
    this.customizeMessageType = customizeMessageType;
  }

  public T getMsg_data() {
    return msg_data;
  }

  public void setMsg_data(T msg_data) {
    this.msg_data = msg_data;
  }

  public Boolean getNeedPush() {
    return needPush;
  }

  public void setNeedPush(Boolean needPush) {
    this.needPush = needPush;
  }

}
