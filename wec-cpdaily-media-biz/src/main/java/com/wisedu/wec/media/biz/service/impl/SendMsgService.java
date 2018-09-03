package com.wisedu.wec.media.biz.service.impl;

import com.wisedu.wec.media.common.to.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;
import com.google.gson.Gson;

/**
 * @author zsl
 */
@Service
public class SendMsgService {

  private static final Logger logger = LoggerFactory.getLogger(SendMsgService.class);

  @Autowired
  private RestTemplate restTemplate;

  @Value("${media.appId}")
  private String mediaAppId;

  @Value("${media.appSecret}")
  private String mediaAppSecret;

  @Value("${publish.msg.url}")
  private String publishMsgUrl;


  /**
   * 推送普通文本消息
   * @param title 标题
   * @param content 内容
   * @param sender 发送者 fromType: 1 小黑板 2 今日校园已存在的身份(老师，学生，校园号)；senderId 用户userId
   * @param receiver 接收者 receiverType: 接受者类型 1 openId(学工号)2 userId (今日校园身份编号)；tenantCode 租户Code;openIds，学工号；userIds：身份编号
   * @param linkUrl 详情地址
   * @param readUrl 用户查看消息回调地址
   */
  public SendMessageResultBean pushTextMsg(InnerInfoSenderBean sender, ReceiverBean receiver, String title, String content, String linkUrl, String readUrl)
      throws Exception {
    CommonInnerMsgBean commonMsg = new CommonInnerMsgBean();
    commonMsg.setTitle(title);
    commonMsg.setContent(content);
    commonMsg.setLinkUrl(linkUrl);
    commonMsg.setReadUrl(readUrl);
    return this.pushMsg(sender, receiver, commonMsg, null, null);
  }

  /**
   * 推送普通文本消息
   * @param title 标题
   * @param summary 简介
   * @param img 图片
   * @param sender 发送者 fromType: 1 小黑板 2 今日校园已存在的身份(老师，学生，校园号)；senderId 用户userId
   * @param receiver 接收者 receiverType: 接受者类型 1 openId(学工号)2 userId (今日校园身份编号)；tenantCode 租户Code;openIds，学工号；userIds：身份编号
   * @param linkUrl 详情地址
   * @param readUrl 用户查看消息回调地址
   */
  public SendMessageResultBean pushImgMsg(InnerInfoSenderBean sender, ReceiverBean receiver, String title, String summary, String img, String linkUrl, String readUrl)
          throws Exception {
    ImgInnerMsgBean imgMsg = new ImgInnerMsgBean();
    imgMsg.setTitle(title);
    imgMsg.setSummary(summary);
    imgMsg.setImg(img);
    imgMsg.setLinkUrl(linkUrl);
    imgMsg.setReadUrl(readUrl);
    return this.pushMsg(sender, receiver, null, imgMsg, null);
  }


  /**
   * 推送附件消息
   * @param title 标题
   * @param summary 简介
   * @param img 图片
   * @param sender 发送者 fromType: 1 小黑板 2 今日校园已存在的身份(老师，学生，校园号)；senderId 用户userId
   * @param receiver 接收者 receiverType: 接受者类型 1 openId(学工号)2 userId (今日校园身份编号)；tenantCode 租户Code;openIds，学工号；userIds：身份编号
   * @param linkUrl 详情地址
   * @param readUrl 用户查看消息回调地址
   * @param attachments 附件地址数组
   */
  public SendMessageResultBean pushAttachmentMsg(InnerInfoSenderBean sender, ReceiverBean receiver,
                                                 String title, String summary, String img, String[] attachments, String linkUrl, String readUrl)
          throws Exception {

    AttachInnerMsgBean attachInnerMsgBean = new AttachInnerMsgBean();
    attachInnerMsgBean.setTitle(title);
    attachInnerMsgBean.setSummary(summary);
    attachInnerMsgBean.setImg(img);
    attachInnerMsgBean.setAttachments(attachments);
    attachInnerMsgBean.setLinkUrl(linkUrl);
    attachInnerMsgBean.setReadUrl(readUrl);

//    发送方
//    InnerInfoSenderBean senderBean = new InnerInfoSenderBean();
//    senderBean.setFromType(2); //
//    senderBean.setSenderId(from); // 1 小黑板 2 今日校园已存在的身份(老师，学生，校园号)
//    接收方
//    ReceiverBean receiverBean = new ReceiverBean();
//    receiverBean.setReceiverType(1);
//    receiverBean.setTenantCode(tenantCode);
//    receiverBean.setOpenIds(to);
//    requestBean.setReceiver(receiverBean);

    return this.pushMsg(sender, receiver, null, null, attachInnerMsgBean);
  }

  /**
   * 消息发送 - 基础接口
   * 接口文档：http://confluence.next.wisedu.com:8090/pages/viewpage.action?pageId=9475642
   */
  public SendMessageResultBean pushMsg(InnerInfoSenderBean sender, ReceiverBean receiver, CommonInnerMsgBean commonInnerMsg,
                                       ImgInnerMsgBean imgInnerMsg, AttachInnerMsgBean attachment)
          throws Exception {

    SendMessageRequestBean requestBean = new SendMessageRequestBean();
    long timestamp = new Date().getTime() / 1000;
    requestBean.setAppId(mediaAppId);
    requestBean.setTimestamp(timestamp);
    String sign = getMD5(mediaAppId + timestamp + mediaAppSecret);
    requestBean.setSign(sign);
    requestBean.setReceiver(receiver);
    requestBean.setSender(sender);
    requestBean.setIsPush(true);

    if (commonInnerMsg != null) {
      requestBean.setMsgType(1);
      requestBean.setCommonInnerMsg(commonInnerMsg);
    }
    if (imgInnerMsg != null) {
      requestBean.setMsgType(2);
      requestBean.setImgInnerMsg(imgInnerMsg);
    }
    if (attachment != null) {
      requestBean.setMsgType(3);
      requestBean.setAttachInnerMsg(attachment);
    }
    if (commonInnerMsg == null && imgInnerMsg == null && attachment == null) {
      throw new RuntimeException("发送消息不能为空");
    }

    SendMessageResultBean resp = restTemplate.postForEntity(publishMsgUrl, requestBean, SendMessageResultBean.class).getBody();

    return resp;
  }


  /**
   * 获取md5值
   *
   * @param str
   */
  public String getMD5(String str) {
    String res = null;
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(str.getBytes());

      byte[] hash = md.digest();
      StringBuilder secpwd = new StringBuilder();
      for (int i = 0; i < hash.length; i++) {
        int v = hash[i] & 0xFF;
        if (v < 16) {
          secpwd.append(0);
        }
        secpwd.append(Integer.toString(v, 16));
      }
      res = secpwd.toString();
    } catch (Exception e) {
      throw new RuntimeException("MD5加密出现错误：" + e);
    }
    return res;
  }

}
