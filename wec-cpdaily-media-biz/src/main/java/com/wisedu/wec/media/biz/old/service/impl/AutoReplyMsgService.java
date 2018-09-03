package com.wisedu.wec.media.biz.old.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;
import com.wisedu.wec.media.biz.service.impl.LogService;
import com.wisedu.wec.media.common.old.constants.MediaLogOperateType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.wisedu.wec.media.common.old.exception.ServiceException;
import com.wisedu.wec.media.common.old.po.AutoReplyMsg;
import com.wisedu.wec.media.common.old.vo.UploadVo;
import com.wisedu.wec.media.dal.mybatis.AutoReplyMsgMapper;

import freemarker.template.Template;

@Service
public class AutoReplyMsgService {

	private static final Logger logger = LoggerFactory.getLogger(AutoReplyMsgService.class);

	@Autowired
	private AutoReplyMsgMapper mapper;

	@Autowired
	private FreeMarkerConfigurer freemarkerConfig;

	@Autowired
	private OssService ossService;

	@Autowired
	LogService logService;

	public AutoReplyMsg queryMsg(String mediaId) {
		AutoReplyMsg msg = mapper.selectByPrimaryKey(mediaId);

		return msg;
	}

	/**
	 * 保存自动恢复之前要上传文件
	 *
	 * @param record
	 */
	public void saveMsg(AutoReplyMsg record) {

		String msghtml = generateHtml(record);

		try {

			// String filePath = "mp/autoReplyMsg/" + record.getMediaId() + "/"
			// +
			// System.currentTimeMillis()
			// + "_" + RandomUtils.nextInt() + ".html";

			UploadVo uploadVo = ossService.upload(record.getTenantId(), record.getMediaId() + ".html", ".html", msghtml.getBytes("UTF-8"));

			record.setLocalUrl(uploadVo.getFileUrl());
		} catch (Exception e) {
			logService.insertLog(record.getMediaId(), MediaLogOperateType.CONFIG_AUTO_REPLY.toString(), new Gson().toJson(record), false);
			logger.error("auto reply msg upload oss fail", e);
			throw new ServiceException("auto reply msg upload oss fail", "-1");
		}

		AutoReplyMsg msg = mapper.selectByPrimaryKey(record.getMediaId());

		if (msg == null) {
			mapper.insert(record);
		} else {
			mapper.updateByPrimaryKey(record);
		}

		logService.insertLog(record.getMediaId(), MediaLogOperateType.CONFIG_AUTO_REPLY.toString(), new Gson().toJson(record), true);
	}

	private String generateHtml(AutoReplyMsg msg) {

		String html = "";

		Map<String, Object> model = new HashMap<>();
		Map<String, Object> message = new HashMap<>();

		message.put("title", msg.getTitle());
		message.put("content", msg.getContent());
		message.put("cTime", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		if (StringUtils.isNotBlank(msg.getOriginalLink())) {
			message.put("originUrl", msg.getOriginalLink());
		}

		model.put("message", message);

		try {
			
			Template t = freemarkerConfig.createConfiguration().getTemplate("autoReplyMsgView.ftl");
			html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
		} catch (Exception e) {
			logger.error("auto reply msg generate html fail", e);
			throw new ServiceException("auto reply msg generate html fail", "-1");
		}

		return html;
	}

	private String getFourRandom() {
		Random random = new Random();
		String fourRandom = random.nextInt(10000) + "";
		int randLength = fourRandom.length();
		if (randLength < 4) {
			for (int i = 1; i <= 4 - randLength; i++) {
				fourRandom = "0" + fourRandom;
			}
		}
		return fourRandom;
	}

}
