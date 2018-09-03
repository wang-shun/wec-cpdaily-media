package com.wisedu.wec.media.common.old.vo;

import java.util.Date;

public class MediaMsg7DayVo {

  private String msgId;

  private String title;

  private String status;

  private Integer sendCount;

  private Integer readCount;

  private Integer receiveCount;

  private Date createTime;

  private String showDate;

  private String showTime;

  public String getMsgId() {
    return msgId;
  }

  public void setMsgId(String msgId) {
    this.msgId = msgId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Integer getSendCount() {
    return sendCount;
  }

  public void setSendCount(Integer sendCount) {
    this.sendCount = sendCount;
  }

  public Integer getReadCount() {
    return readCount;
  }

  public void setReadCount(Integer readCount) {
    this.readCount = readCount;
  }

  public Integer getReceiveCount() {
    return receiveCount;
  }

  public void setReceiveCount(Integer receiveCount) {
    this.receiveCount = receiveCount;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getShowDate() {
    return showDate;
  }

  public void setShowDate(String showDate) {
    this.showDate = showDate;
  }

  public String getShowTime() {
    return showTime;
  }

  public void setShowTime(String showTime) {
    this.showTime = showTime;
  }

}
