package com.wisedu.wec.media.biz.task;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wisedu.wec.media.biz.old.service.impl.ImService;
import com.wisedu.wec.media.common.old.constants.MsgConstants;
import com.wisedu.wec.media.common.old.po.MediaMsg;
import com.wisedu.wec.media.common.old.util.ImgUtils;
import com.wisedu.wec.media.common.old.vo.ImMediaMsg;
import com.wisedu.wec.media.common.old.vo.ImMsg;
import com.wisedu.wec.media.dal.mybatis.MediaMsgErrorMapper;
import com.wisedu.wec.media.dal.mybatis.MediaMsgMapper;

public abstract class BaseSendMsgTask {

	@Autowired
	protected ImService imService;

	@Autowired
	protected MediaMsgErrorMapper mediaMsgErrorMapper;

	@Autowired
	protected MediaMsgMapper mediaMsgMapper;

	@Autowired
	protected ObjectMapper objectMapper;

	@Value("${login.qrcodeHost}")
	protected String domain;

	protected MediaMsg msg;

	public void setMsg(MediaMsg msg) {
		this.msg = msg;
	}

	protected String getMsgStr(MediaMsg msg) throws Exception {
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
