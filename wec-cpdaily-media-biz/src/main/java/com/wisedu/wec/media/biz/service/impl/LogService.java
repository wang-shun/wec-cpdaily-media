package com.wisedu.wec.media.biz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wisedu.wec.media.common.old.context.ThreadLocalUserInfo;
import com.wisedu.wec.media.common.old.context.UserInfoContext;
import com.wisedu.wec.media.common.old.po.Log;
import com.wisedu.wec.media.common.old.po.MediaLogRequest;
import com.wisedu.wec.media.common.old.po.MediaLogResponse;
import com.wisedu.wec.media.common.to.*;
import com.wisedu.wec.media.dal.mybatis.MediaLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;
import java.util.List;

/**
 * @author zsl
 */
@Service
public class LogService {

  private static final Logger logger = LoggerFactory.getLogger(LogService.class);

  @Autowired
  private MediaLogMapper mediaLogMapper;

  /**
  * @Description: 插入日志
  * @Param: [mediaId, operateType, operateContent, operateResult, operatorId可选，不传则取当前对象, operatorName，同上]
  * @return: void
  */
  public void insertLog(String mediaId, String operateType, String operateContent, boolean operateResult, String operatorId, String operatorName) {

    UserInfoContext context = ThreadLocalUserInfo.getContext();

    Log log = new Log();
    log.setMediaId(mediaId);
    log.setOperateType(operateType);
    log.setOperateContent(operateContent);
    log.setOperateResult(operateResult);
    if (operatorId == null) {
      log.setOperatorId(context.getLoginUserId()); // fix bug: 切换校园号导致userId错误， -- zsl 2018.5.17
      log.setOperatorName(context.getLoginUserName());
    } else {
      log.setOperatorId(operatorId);
      log.setOperatorName(operatorName);
    }
    mediaLogMapper.insertLog(log);
  }

  /**
   * @Description: 插入日志
   * @Param: [mediaId, operateType, operateContent, operateResult, operatorId可选，不传则取当前对象, operatorName，同上]
   * @return: void
   */
  public void insertLog(String mediaId, String operateType, String operateContent, boolean operateResult) {

    UserInfoContext context = ThreadLocalUserInfo.getContext();

    Log log = new Log();
    log.setMediaId(mediaId);
    log.setOperateType(operateType);
    log.setOperateContent(operateContent);
    log.setOperateResult(operateResult);
    log.setOperatorId(context.getPersonId());
    log.setOperatorName(context.getPersonName());
    mediaLogMapper.insertLog(log);
  }


  @Autowired
  private MediaLogMapper mediaLogDao;


  public MediaLogResponse getLogs(MediaLogRequest mediaLogRequest) {
    MediaLogResponse mediaLogResponse = new MediaLogResponse();

    String mediaId = ThreadLocalUserInfo.getContext().getMediaId();
    String beginDate = mediaLogRequest.getBeginDate();
    String endDate = mediaLogRequest.getEndDate();
    Integer pageNum = mediaLogRequest.getPageNum();
    Integer pageSize = mediaLogRequest.getPageSize();

    int total = 0;
    int start = (pageNum - 1) * pageSize;

    Page<Object> page = null;
    if(pageNum>0 && pageSize>0){
      page = PageHelper.startPage(pageNum, pageSize);
    }

    List<Log> logs = mediaLogMapper.getLogs(mediaId, beginDate, endDate);

    mediaLogResponse.setLogs(logs);
    mediaLogResponse.setPageNum(pageNum);
    if (pageNum>0 && pageSize>0) {
      mediaLogResponse.setTotal(page.getTotal());
    }

    return mediaLogResponse;
  }

}
