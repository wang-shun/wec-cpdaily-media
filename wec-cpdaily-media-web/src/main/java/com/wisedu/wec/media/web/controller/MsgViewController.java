package com.wisedu.wec.media.web.controller;

import com.google.gson.Gson;
import com.wisedu.wec.media.biz.old.controller.BaseController;
import com.wisedu.wec.media.biz.service.impl.MsgService;
import com.wisedu.wec.media.common.context.LoginUserContext;
import com.wisedu.wec.media.common.old.constants.RedisConstants;
import com.wisedu.wec.media.common.old.context.ThreadLocalUserInfo;
import com.wisedu.wec.media.common.old.context.UserInfoContext;
import com.wisedu.wec.media.common.old.vo.CommonVO;
import com.wisedu.wec.media.common.old.vo.SendMsgVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;

@Controller
@RequestMapping("/v3/campusmedia")
public class MsgViewController extends BaseController {

  private static final Logger logger = LoggerFactory.getLogger(MsgViewController.class);

  @Autowired
  private MsgService msgService;

  /**
   * 查看消息
   */
  @RequestMapping(value = "/gateway/msg/view/{id}", method = RequestMethod.GET)
  @ResponseBody
  public CommonVO viewMsg(@PathVariable String id) throws Exception {
    SendMsgVo sendMsgVo = msgService.getSendMsgVo(id);
    return successResponseWithData(sendMsgVo);
  }

  /**
   * 阅读消息
   */
  @RequestMapping(value = "/gateway/msg/read/{id}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
  public CommonVO readMsg(@PathVariable String id, HttpServletRequest request) throws Exception {
    logger.info("invoke read url: " + id);

    // 读取用户信息
    String userId = this.getuserId(request);
    // 无用户信息，忽略
    if (userId == null) {
      return successResponseWithData("");
    }

    String sendMsgVo = msgService.readMsg(id, userId);
    return successResponseWithData(sendMsgVo);
  }

  private String getuserId (HttpServletRequest request) {

    String loginUserInfoStr = request.getHeader(RedisConstants.CPDAILY_USERINFO_HEADER_KEY);
    if (StringUtils.isEmpty(loginUserInfoStr)) {
      return null;
    }

    if (StringUtils.isNotEmpty(loginUserInfoStr)) {
      try {
        loginUserInfoStr = URLDecoder.decode(loginUserInfoStr, "UTF-8");
      } catch (Exception e) {
        return null;
      }
    }

    LoginUserContext user = new Gson().fromJson(loginUserInfoStr, LoginUserContext.class);
    if (null == user) {
      return null;
    }

    return user.getCpdailyUserWid();
  }

}
