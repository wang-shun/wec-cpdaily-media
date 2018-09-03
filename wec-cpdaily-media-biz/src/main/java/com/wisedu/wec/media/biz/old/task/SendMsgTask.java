package com.wisedu.wec.media.biz.old.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taobao.api.response.OpenimCustmsgPushResponse;
import com.wisedu.wec.media.biz.old.service.impl.ImService;
import com.wisedu.wec.media.common.old.constants.MsgConstants;
import com.wisedu.wec.media.common.old.po.MediaMsg;
import com.wisedu.wec.media.common.old.po.MediaMsgError;
import com.wisedu.wec.media.common.old.util.CommUtils;
import com.wisedu.wec.media.common.old.util.ImgUtils;
import com.wisedu.wec.media.common.old.vo.ImMediaMsg;
import com.wisedu.wec.media.common.old.vo.ImMsg;
import com.wisedu.wec.media.dal.mybatis.MediaMsgErrorMapper;
import com.wisedu.wec.media.dal.mybatis.MediaMsgMapper;

@Component("sendMsgTask")
public class SendMsgTask {
  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  ImService imService;


  @Autowired
  MediaMsgErrorMapper mediaMsgErrorMapper;

  @Autowired
  MediaMsgMapper mediaMsgMapper;

  @Autowired
  ObjectMapper objectMapper;

  @Value("${login.qrcodeHost}")
  String domain;

  @Async("sendMsgAsync")
  public Future<String> sendMsg(MediaMsg msg) throws InterruptedException {
    Map<String,String> alert = new HashMap<>();
    alert.put("alert", msg.getTitle());
    List<String> sendUserIds = new ArrayList<>(msg.getSendTotalUserIds());
    List<String> sendErrorUserIds = new ArrayList<>();
    List<String> sendSuccessUserIds = new ArrayList<>();
    int sendOverCount = 0;
    if (CollectionUtils.isNotEmpty(sendUserIds)) {
      List<List<String>> subUserIdLists = CommUtils.splitList(sendUserIds, 100);
      for (List<String> subUserIdList : subUserIdLists) {
        try {
          if (!CollectionUtils.isEmpty(subUserIdList)) {
            OpenimCustmsgPushResponse resp =
                imService.pushCustomMsgToUsers(msg.getMediaId(), subUserIdList, msg.getSummary(),
                    getMsgStr(msg), objectMapper.writeValueAsString(alert), "", 0L);
            if (!resp.isSuccess()) {
              logger.error("媒体号消息推送失败,消息编号:{},错误信息:{}", msg.getWid(),
                  objectMapper.writeValueAsString(resp));
              sendErrorUserIds.addAll(subUserIdList);
            } else {
              sendSuccessUserIds.addAll(subUserIdList);
            }
          }
        } catch (Exception e) {
          logger.error("媒体号消息推送失败,消息编号:" + msg.getWid() + ",", e);
          sendErrorUserIds.addAll(subUserIdList);
        }
        sendOverCount += subUserIdList.size();
        mediaMsgMapper.increaseMsgSendOverCount(sendOverCount, msg.getWid());
      }
    }


    if (CollectionUtils.isNotEmpty(sendErrorUserIds)) {
      // 插入失败的数据
      List<MediaMsgError> mediaMsgErrors = new ArrayList<>();

      for (String userId : sendErrorUserIds) {
        MediaMsgError mediaMsgError = new MediaMsgError(msg.getWid(), userId, new Date());
        mediaMsgErrors.add(mediaMsgError);
      }
      mediaMsgErrorMapper.batchInsert(mediaMsgErrors);

      MediaMsg record = new MediaMsg();
      record.setWid(msg.getWid());
      record.setStatus(MsgConstants.MsgStatus.SEND_ERROR.toString());
      record.setuTime(new Date());
      record.setReceiveCount(sendSuccessUserIds.size());
      mediaMsgMapper.updateByPrimaryKeySelective(record);

    } else {
      // 更新状态和时间
      MediaMsg record = new MediaMsg();
      record.setWid(msg.getWid());
      record.setStatus(MsgConstants.MsgStatus.SEND_END.toString());
      record.setuTime(new Date());
      record.setReceiveCount(sendSuccessUserIds.size());
      record.setSendCount(sendSuccessUserIds.size());
      mediaMsgMapper.updateByPrimaryKeySelective(record);
    }


    return new AsyncResult<>("sendMsg accomplished!");
  }


  @Async("sendMsgAsync")
  public Future<String> reSendMsg(MediaMsg msg) throws InterruptedException {

    Map<String,String> alert = new HashMap<>();
    alert.put("alert", msg.getTitle());
    List<String> sendErrorUserIds = new ArrayList<>();
    List<String> sendSuccessUserIds = new ArrayList<>();
    List<String> sendUserIds = new ArrayList<>();
    List<MediaMsgError> mediaMsgErrors = mediaMsgErrorMapper.selectByMsgId(msg.getWid());
    for (MediaMsgError mediaMsgError : mediaMsgErrors) {
      sendUserIds.add(mediaMsgError.getUserId());
    }
    mediaMsgErrorMapper.delByMsgId(msg.getWid());
    if (CollectionUtils.isNotEmpty(sendUserIds)) {
      List<List<String>> subUserIdLists = CommUtils.splitList(sendUserIds, 100);
      for (List<String> subUserIdList : subUserIdLists) {
        try {
          if (!CollectionUtils.isEmpty(subUserIdList)) {
            OpenimCustmsgPushResponse resp =
                imService.pushCustomMsgToUsers(msg.getMediaId(), subUserIdList, msg.getSummary(),
                    getMsgStr(msg), objectMapper.writeValueAsString(alert), "", 0L);
            if (!resp.isSuccess()) {
              logger.error("媒体号消息推送失败,消息编号:{},错误信息:{}", msg.getWid(),
                  objectMapper.writeValueAsString(resp));
              sendErrorUserIds.addAll(subUserIdList);
            } else {
              sendSuccessUserIds.addAll(subUserIdList);
            }
          }
        } catch (Exception e) {
          logger.error("媒体号消息推送失败,消息编号:" + msg.getWid() + ",", e);
          sendErrorUserIds.addAll(subUserIdList);
        }
      }
    }

    if (CollectionUtils.isNotEmpty(sendErrorUserIds)) {
      // 插入失败的数据
      List<MediaMsgError> mediaMsgErrorList = new ArrayList<>();
      for (String userId : sendErrorUserIds) {
        MediaMsgError mediaMsgError = new MediaMsgError(msg.getWid(), userId, new Date());
        mediaMsgErrorList.add(mediaMsgError);
      }
      mediaMsgErrorMapper.batchInsert(mediaMsgErrorList);

      // 更新状态和时间
      MediaMsg record = new MediaMsg();
      record.setWid(msg.getWid());
      record.setStatus(MsgConstants.MsgStatus.SEND_ERROR.toString());
      record.setuTime(new Date());
      record.setReceiveCount(msg.getReceiveCount() + sendSuccessUserIds.size());
      mediaMsgMapper.updateByPrimaryKeySelective(record);

    } else {
      // 更新状态和时间
      MediaMsg record = new MediaMsg();
      record.setWid(msg.getWid());
      record.setStatus(MsgConstants.MsgStatus.SEND_END.toString());
      record.setuTime(new Date());
      record.setReceiveCount(msg.getReceiveCount() + sendSuccessUserIds.size());
      record.setSendCount(record.getReceiveCount());
      mediaMsgMapper.updateByPrimaryKeySelective(record);
    }

    return new AsyncResult<>("reSendMsg accomplished!");
  }


  private String getMsgStr(MediaMsg msg) throws Exception {
    ImMsg<ImMediaMsg> imMsg = new ImMsg<>();
    imMsg.setCustomizeMessageType(MsgConstants.IM_MSG_TYPE);
    imMsg.setNeedPush(true);
    ImMediaMsg imMediaMsg = new ImMediaMsg();
    imMediaMsg.setMsgId(msg.getWid());
    BeanUtils.copyProperties(imMediaMsg, msg);
    imMediaMsg.setReadUrl(domain + MsgConstants.IM_MSG_READ_URL + msg.getWid());
    imMediaMsg.setLocalUrl(ImgUtils.imgUrlsSAddHost(msg.getLocalUrl()));
    imMediaMsg.setImg(ImgUtils.imgUrlsSAddHost(msg.getImg()));
    imMsg.setMsg_data(imMediaMsg);
    return objectMapper.writeValueAsString(imMsg);
  }
}
