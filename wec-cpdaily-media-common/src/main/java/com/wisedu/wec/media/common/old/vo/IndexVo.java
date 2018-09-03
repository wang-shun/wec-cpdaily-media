package com.wisedu.wec.media.common.old.vo;

import java.util.List;
import java.util.Map;

public class IndexVo {

  private Integer fansCount;

  private Map<String, List<MediaMsg7DayVo>> msgMap;

  public Integer getFansCount() {
    return fansCount;
  }

  public void setFansCount(Integer fansCount) {
    this.fansCount = fansCount;
  }

  public Map<String, List<MediaMsg7DayVo>> getMsgMap() {
    return msgMap;
  }

  public void setMsgMap(Map<String, List<MediaMsg7DayVo>> msgMap) {
    this.msgMap = msgMap;
  }

}
