package com.wisedu.wec.media.biz.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.taobao.api.response.OpenimCustmsgPushResponse;
import com.wisedu.wec.media.common.old.constants.MsgConstants;
import com.wisedu.wec.media.common.old.po.MediaMsg;
import com.wisedu.wec.media.common.old.po.MediaMsgError;
import com.wisedu.wec.media.common.old.util.CommUtils;

/**
 * 多线程发送消息的任务
 * @author mdmo
 *
 */
@Component("sendMsgTaskNew")
public class SendMsgTask extends BaseSendMsgTask implements Runnable{
  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

  public void run(){
    Map<String,Object> alert = new HashMap<>();
    alert.put("alert", msg.getTitle());
    List<String> sendUserIds = new ArrayList<>(msg.getSendTotalUserIds());
    List<String> sendErrorUserIds = new ArrayList<>();
    List<String> sendSuccessUserIds = new ArrayList<>();
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

  }

}
