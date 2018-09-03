package com.wisedu.wec.media.biz.old.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Userinfos;
import com.taobao.api.request.OpenimCustmsgPushRequest;
import com.taobao.api.request.OpenimUsersAddRequest;
import com.taobao.api.request.OpenimUsersUpdateRequest;
import com.taobao.api.response.OpenimCustmsgPushResponse;
import com.taobao.api.response.OpenimUsersAddResponse;
import com.taobao.api.response.OpenimUsersUpdateResponse;

/**
 * @author 14116004
 */
@Service
public class ImService {

  private static final Logger logger = LoggerFactory.getLogger(ImService.class);

  @Autowired
  private TaobaoClient client;

  @Autowired
  private ObjectMapper objectMapper;

  /**
   * im用户添加
   */
  public OpenimUsersAddResponse addImUser(List<Userinfos> users) throws Exception {
    OpenimUsersAddRequest req = new OpenimUsersAddRequest();
    req.setUserinfos(users);
    OpenimUsersAddResponse resp = client.execute(req);
    logger.info("IM账号添加,请求参数:{}.,返回参数:{}", objectMapper.writeValueAsString(users),
        objectMapper.writeValueAsString(resp));
    return resp;
  }

  /**
   * im用户修改
   */
  public OpenimUsersUpdateResponse updateImUser(List<Userinfos> users) throws Exception {
    OpenimUsersUpdateRequest req = new OpenimUsersUpdateRequest();
    req.setUserinfos(users);
    OpenimUsersUpdateResponse resp = client.execute(req);
    logger.info("IM账号修改,请求参数:{}.,返回参数:{}", objectMapper.writeValueAsString(users),
        objectMapper.writeValueAsString(resp));
    return resp;
  }

  /**
   * 服务端推送自定义消息,群发
   */
  public OpenimCustmsgPushResponse pushCustomMsgToUsers(String from, List<String> to,
      String summary, String content, String aps, String apnsParam, Long invisible)
      throws Exception {
    OpenimCustmsgPushRequest req = new OpenimCustmsgPushRequest();
    OpenimCustmsgPushRequest.CustMsg imMsg = new OpenimCustmsgPushRequest.CustMsg();
    imMsg.setFromUser(from);
    imMsg.setToUsers(to);
    imMsg.setData(content);

    // 阿里消息不允许为空
    if (StringUtils.isBlank(summary)) {
      imMsg.setSummary(" ");
    } else {
      imMsg.setSummary(summary);
    }

    imMsg.setAps(aps);
    imMsg.setApnsParam(apnsParam);
    imMsg.setInvisible(invisible);
    req.setCustmsg(imMsg);
    OpenimCustmsgPushResponse resp = client.execute(req);
    logger.info("服务端推送自定义消息,群发,返回参数:{}", objectMapper.writeValueAsString(resp));
    return resp;
  }


}
