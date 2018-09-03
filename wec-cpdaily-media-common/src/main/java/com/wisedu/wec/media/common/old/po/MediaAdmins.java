package com.wisedu.wec.media.common.old.po;

import java.util.Date;
import java.util.List;

public class MediaAdmins {
  private String mediaId;

  private List<String> admins;

  public String getMediaId() {
    return mediaId;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

  public List<String> getAdmins() {
    return admins;
  }

  public void setAdmins(List<String> admins) {
    this.admins = admins;
  }
}
