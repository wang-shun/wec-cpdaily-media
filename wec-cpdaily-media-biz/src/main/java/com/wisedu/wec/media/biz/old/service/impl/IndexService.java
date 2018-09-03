package com.wisedu.wec.media.biz.old.service.impl;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.wisedu.wec.media.common.old.context.ThreadLocalUserInfo;
import com.wisedu.wec.media.common.old.vo.MediaMsg7DayVo;
import com.wisedu.wec.media.dal.mybatis.MediaMsgMapper;

@Service
public class IndexService {

  @Autowired
  private MediaMsgMapper mediaMsgMapper;

  /**
   * 分页查询最近七天的消息
   */
  public Map<String, List<MediaMsg7DayVo>> query7DaysMsg(Integer pageNum, Integer pageSize) {

    String mediaId = ThreadLocalUserInfo.getContext().getMediaId();

    Date endTime = new Date();
    Instant midnightInstant = LocalTime.MIDNIGHT.atDate(LocalDate.now().minusDays(6))
        .atZone(ZoneId.of(ZoneId.SHORT_IDS.get("CTT"))).toInstant();
    Date startTime = Date.from(midnightInstant);

    PageHelper.startPage(pageNum, pageSize);

    List<MediaMsg7DayVo> msgList = mediaMsgMapper.select7DaysByMediaId(mediaId, startTime, endTime);

    Map<String, List<MediaMsg7DayVo>> msgMap = new LinkedHashMap<>();

    // 设置发送时间
    for (MediaMsg7DayVo vo : msgList) {
      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
      String showTime = sdf.format(vo.getCreateTime());
      vo.setShowTime(showTime);
    }

    // 按天分组
    for (MediaMsg7DayVo vo : msgList) {
      String showDate = convertDate(vo.getCreateTime());

      if (msgMap.get(showDate) == null) {
        List<MediaMsg7DayVo> tmpList = new ArrayList<>();
        tmpList.add(vo);
        msgMap.put(showDate, tmpList);
      } else {
        msgMap.get(showDate).add(vo);
      }
    }

    return msgMap;
  }

  private String convertDate(Date date) {
    LocalDateTime today = LocalDateTime.now();

    Instant timestamp = date.toInstant();
    LocalDateTime dateTime =
        LocalDateTime.ofInstant(timestamp, ZoneId.of(ZoneId.SHORT_IDS.get("CTT")));
    if (dateTime.getDayOfMonth() == today.getDayOfMonth()) {
      return "今天";
    } else if (dateTime.getDayOfMonth() == today.getDayOfMonth() - 1) {
      return "昨天";
    } else if (dateTime.getYear() == today.getYear()) {
      return dateTime.format(DateTimeFormatter.ofPattern("MM/dd"));
    } else {
      return dateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }
  }

}
