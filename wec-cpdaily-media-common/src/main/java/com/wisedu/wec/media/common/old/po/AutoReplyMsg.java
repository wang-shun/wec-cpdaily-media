package com.wisedu.wec.media.common.old.po;

import java.util.Date;

public class AutoReplyMsg {
  private String mediaId;

  private String title;

  private String originalLink;

  private String img;

  private String summary;

  private String localUrl;

  private String ownerId;

  private String tenantId;

  private Date updateTime;

  private String content;

  public String getMediaId() {
    return mediaId;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId == null ? null : mediaId.trim();
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title == null ? null : title.trim();
  }

  public String getOriginalLink() {
    return originalLink;
  }

  public void setOriginalLink(String originalLink) {
    this.originalLink = originalLink == null ? null : originalLink.trim();
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img == null ? null : img.trim();
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary == null ? null : summary.trim();
  }

  public String getLocalUrl() {
    return localUrl;
  }

  public void setLocalUrl(String localUrl) {
    this.localUrl = localUrl == null ? null : localUrl.trim();
  }

  public String getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId == null ? null : ownerId.trim();
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId == null ? null : tenantId.trim();
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content == null ? null : content.trim();
  }
}
