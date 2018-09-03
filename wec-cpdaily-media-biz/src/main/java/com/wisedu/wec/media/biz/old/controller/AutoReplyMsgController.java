package com.wisedu.wec.media.biz.old.controller;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wisedu.wec.media.biz.old.service.impl.AutoReplyMsgService;
import com.wisedu.wec.media.common.old.context.ThreadLocalUserInfo;
import com.wisedu.wec.media.common.old.context.UserInfoContext;
import com.wisedu.wec.media.common.old.po.AutoReplyMsg;
import com.wisedu.wec.media.common.old.util.CommUtils;
import com.wisedu.wec.media.common.old.util.ImgUtils;
import com.wisedu.wec.media.common.old.vo.CommonVO;


@RestController
@RequestMapping("/v3/campusmedia/auto/reply/msg")
public class AutoReplyMsgController extends BaseController {

  private static final Logger logger = LoggerFactory.getLogger(AutoReplyMsgController.class);

  @Autowired
  private AutoReplyMsgService service;

  @RequestMapping(value = "/index", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
  public CommonVO autoReplyMsgIndex() {

    String mediaId = ThreadLocalUserInfo.getContext().getMediaId();

    AutoReplyMsg msg = service.queryMsg(mediaId);
    if(msg==null){
    	msg = new AutoReplyMsg();
    }
    return successResponseWithData(msg);
  }

  @RequestMapping(value = "/update", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
  public CommonVO autoReplyMsgUpdate(@RequestBody AutoReplyMsg msg) {

    UserInfoContext context = ThreadLocalUserInfo.getContext();

    if (StringUtils.isNotEmpty(msg.getImg())) {
      String img = ImgUtils.imgUrlsSAddHost(msg.getImg());
      msg.setImg(img);
    }

    if (StringUtils.isEmpty(msg.getSummary())) {
      String text = CommUtils.stripHT(msg.getContent());
      if (StringUtils.isNotEmpty(text)) {
        msg.setSummary(text.length() > 54 ? text.substring(0, 54) : text);
      }
    }

    msg.setMediaId(context.getMediaId());
    msg.setOwnerId(context.getLoginUserId());// fix bug: 切换校园号导致userId错误， -- zsl 2018.5.17
    msg.setTenantId(context.getTenantId());
    msg.setUpdateTime(new Date());

    service.saveMsg(msg);

    return successResponse();
  }

}
