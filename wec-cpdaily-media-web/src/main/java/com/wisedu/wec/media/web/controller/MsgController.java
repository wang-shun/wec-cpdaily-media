package com.wisedu.wec.media.web.controller;

import com.wisedu.wec.media.biz.old.controller.BaseController;
import com.wisedu.wec.media.common.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.wisedu.wec.media.biz.service.impl.MsgService;
import com.wisedu.wec.media.common.old.context.ThreadLocalUserInfo;
import com.wisedu.wec.media.common.old.vo.CommonVO;
import com.wisedu.wec.media.common.old.vo.MediaSendMsgReqVo;
import com.wisedu.wec.media.common.old.vo.PageVo;
import com.wisedu.wec.media.common.old.vo.SendMsgVo;

@RestController
public class MsgController extends BaseController {

  private static final Logger logger = LoggerFactory.getLogger(MsgController.class);

  @Autowired
  private MsgService msgService;

  /**
   * 推送消息保存或者保存并发布
   */
  @RequestMapping(value = "/msg/saveAndSend", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
  public CommonVO saveAndSend(@RequestBody MediaSendMsgReqVo req) throws Exception {

    String msg = msgService.saveAndSend(req);
    return successResponseWithData(msg);
  }

  /**
   * 推送消息删除
   */
  @RequestMapping(value = "/msg/del/{id}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
  public CommonVO del(@PathVariable String id) throws Exception {
    String msg = msgService.delMsgById(id);
    return successResponseWithData(msg);
  }

  /**
   * 发送消息
   */
  @RequestMapping(value = "/msg/send/{id}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
  public CommonVO send(@PathVariable String id) throws Exception {
    String msg = msgService.sendMsgById(id);
    return successResponseWithData(msg);
  }


  /**
   * 获取一个消息
   */
  @RequestMapping(value = "/msg/{id}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
  public CommonVO getMsg(@PathVariable String id) throws Exception {
    SendMsgVo sendMsgVo = msgService.getSendMsgVo(id);
    return successResponseWithData(sendMsgVo);
  }

  /**
   * 消息列表
   */
  @RequestMapping(value = "/msg/list", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
  public CommonVO msgList(@RequestParam String msgStatus,
      @RequestParam(defaultValue = "10") int pageSize,
      @RequestParam(defaultValue = "1") int pageNumber) throws Exception {
    String mediaId = ThreadLocalUserInfo.getContext().getMediaId();
    PageVo page = msgService.getMgsList(mediaId, msgStatus, pageSize, pageNumber);
    return successResponseWithData(page);
  }


  /**
   * 已读未读列表
   */
  @RequestMapping(value = "/msg/read/list", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
  public BasePageResponse readMsg(@RequestParam String msgId, @RequestParam Integer pageNumber,
                                  @RequestParam Integer pageSize, @RequestParam String status, @RequestParam(required=false) String name) throws Exception {
    logger.info("get read list ");
    BasePageResponseDatas data = msgService.getReadUserList(msgId, name, status, pageNumber, pageSize);
    BasePageResponse basePageResponse = new BasePageResponse();
    basePageResponse.setDatas(data);
    return basePageResponse;
  }


  /**
   * 短信提醒未读人员
   */
  @RequestMapping(value = "/msg/unread/sms/notify/{id}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
  public BaseResponse notifyMsg(@PathVariable String id) throws Exception {
    logger.info("get read list ");
    msgService.notifyMsg(id);
    BaseResponse baseResponse = new BaseResponse();
    return baseResponse;
  }


  /**
   * 短信提醒等待时间
   */
  @RequestMapping(value = "/msg/sms/notify/wait", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
  public BaseDatasResponse notifyMsgWaitHours() throws Exception {
    Integer hours = msgService.getNotifyWaitHours();
    BaseDatasResponse<Integer> baseResponse = new BaseDatasResponse();
    baseResponse.setDatas(hours);
    return baseResponse;
  }
}
