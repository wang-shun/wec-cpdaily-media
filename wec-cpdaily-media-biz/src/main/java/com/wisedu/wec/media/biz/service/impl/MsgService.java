package com.wisedu.wec.media.biz.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import com.github.pagehelper.Page;
import com.google.gson.Gson;
import com.wisedu.wec.media.biz.old.service.impl.OssService;
import com.wisedu.wec.media.biz.old.service.impl.UserTreeService;
import com.wisedu.wec.media.common.old.constants.MediaLogOperateType;
import com.wisedu.wec.media.common.old.context.UserInfoContext;
import com.wisedu.wec.media.common.old.po.*;
import com.wisedu.wec.media.common.old.vo.*;
import com.wisedu.wec.media.common.to.InnerInfoSenderBean;
import com.wisedu.wec.media.common.to.ReceiverBean;
import com.wisedu.wec.media.common.to.SendMessageResultBean;
import com.wisedu.wec.media.common.vo.*;
import com.wisedu.wec.media.dal.mybatis.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.github.pagehelper.PageHelper;
import com.wisedu.wec.media.biz.old.task.SendMsgTask;
import com.wisedu.wec.media.common.old.constants.MsgConstants;
import com.wisedu.wec.media.common.old.constants.MsgErrConstants;
import com.wisedu.wec.media.common.old.context.ThreadLocalUserInfo;
import com.wisedu.wec.media.common.old.exception.ServiceException;
import com.wisedu.wec.media.common.old.util.CommUtils;
import com.wisedu.wec.media.common.old.util.ImgUtils;

import freemarker.template.Template;

@Service
public class MsgService {

	  private static final Logger logger = LoggerFactory.getLogger(MsgService.class);
	  
	@Autowired
	private CpdailyUserMapper userMapper;

	@Autowired
	private MediaMsgMapper msgMapper;

	@Autowired
	private CpdailyTenantMapper tenantMapper;

	@Autowired
	private MediaMsgSendGroupMapper msgSendGroupMapper;

	@Autowired
	private MediaMsgSendUserMapper msgSendUserMapper;

	@Autowired
	private MediaMsgErrorMapper msgErrorMapper;

	@Autowired
	private OssService ossService;

	@Autowired
	MediaMsgMapper mediaMsgMapper;

	@Autowired
	LogService logService;

	@Autowired
	private FreeMarkerConfigurer freemarkerConfig;
	
	@Autowired
	SendMsgTask sendMsgTask;

	@Autowired
	SendMsgService sendMsgService;

	@Autowired
	UserTreeService userTreeService;

	@Autowired
	MediaMsgErrorMapper mediaMsgErrorMapper;

	@Autowired
	CampusMediaMapper mediaMapper;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${msg.read.prefix}")
	private String msgReadPrefix;

	@Value("${msg.view.prefix}")
	private String msgViewPrefix;

	@Value("${message.center.api}")
	private String messageCenterApi;

	@Value("${message.center.appid}")
	private String messageAppId;

	@Value("${message.center.accesstoken}")
	private String messageAccessToken;

	@Value("${message.center.schoolcode}")
	private String messageSchoolCode;

	@Value("${message.center.waithours}")
	private Integer messageWaitHours;

	/**
	 * 类型转换
	 *
	 * @param req
	 * @return MediaMsg
	 */
	public MediaMsg convertReqToPo(MediaSendMsgReqVo req) throws Exception {
		MediaMsg record = new MediaMsg();
		BeanUtils.copyProperties(record, req);
		// 设置状态
		record.setStatus(MsgConstants.MsgStatus.SEND.toString());

		// 设置简介
		if (StringUtils.isEmpty(req.getSummary())) {
			String text = CommUtils.stripHT(record.getContent());
			if (StringUtils.isNotEmpty(text)) {
				record.setSummary(text.length() > 54 ? text.substring(0, 54) : text);
			}
		}

		// 设置主键
		if (StringUtils.isBlank(req.getMsgId())) {
			record.setWid(UUID.randomUUID().toString());
		} else {
			record.setWid(req.getMsgId());
		}

		// 设置发送组
		List<MediaMsgSendGroup> groups = new ArrayList<>();
		for (SendUserGroupVo vo : req.getSendUserGroups()) {
			groups.add(groupVoToPo(vo, record.getWid()));
		}
		record.setGroups(groups);
		// 设置所在租户
		String tenantId = ThreadLocalUserInfo.getContext().getTenantId();
		record.setTenantId(tenantId);

		// 设置创建人
		String userId = ThreadLocalUserInfo.getContext().getLoginUserId();// fix bug: 切换校园号导致userId错误， -- zsl 2018.5.17
		record.setcUserId(userId);

		// 设置媒体号编号
		String mediaId = ThreadLocalUserInfo.getContext().getMediaId();
		record.setMediaId(mediaId);

		// 设置发布人数 接收组的组员+接收人
		Set<String> sendUserIds = getUserIdsByGroup(record.getGroups(), tenantId, record.getMediaId(),
				record.getNotSendUserIds());

		if (req.getSendUsers() != null) {
			for (SendUserVo sendUser : req.getSendUsers()) {
				sendUserIds.add(sendUser.getUserId());
			}
		}
		if (req.getOpType().equals(MsgConstants.OpTypeStatus.SAVE_SEND) && sendUserIds.size() == 0) {
			throw new ServiceException("接收人为空", HttpStatus.BAD_REQUEST.name());
		}
		record.setSendTotalUserIds(sendUserIds);
		record.setSendCount(sendUserIds.size());
		record.setReadCount(0);
		record.setReceiveCount(0);

		// 生产本地地址
		if (StringUtils.isNotBlank(record.getContent())) {
			record.setLocalUrl(getHtmlStr(record));
		}

		return record;
	}

	/**
	 * 类型转换
	 *
	 * @param vo
	 * @param msgId
	 * @return MediaMsg
	 */
	public MediaMsgSendGroup groupVoToPo(SendUserGroupVo vo, String msgId) throws Exception {
		MediaMsgSendGroup po = new MediaMsgSendGroup();
		BeanUtils.copyProperties(po, vo);
		po.setMsgId(msgId);
		return po;
	}

	/**
	 * 类型转换
	 *
	 * @param po
	 * @return MediaMsg
	 */
	public SendUserGroupVo groupPoToVo(MediaMsgSendGroup po) throws Exception {
		SendUserGroupVo vo = new SendUserGroupVo();
		BeanUtils.copyProperties(vo, po);
		return vo;
	}

	/**
	 * 处理req请求
	 *
	 * @param req
	 * @return MediaMsg
	 */
	public String saveAndSend(MediaSendMsgReqVo req) throws Exception {
		// req转msg
		MediaMsg msg = convertReqToPo(req);
		// 不存在id则说明是新增数据
		if (StringUtils.isBlank(req.getMsgId())) {
			addMsg(msg);
		} else {
			updateMsg(msg);
		}

		if (MsgConstants.OpTypeStatus.SAVE_SEND.equals(req.getOpType())) {
			sendMsg(msg);
		}
		return msg.getWid();
	}

	/**
	 * 保存用户组
	 *
	 * @param groups
	 * @return int
	 */
	public int saveSendGroups(List<MediaMsgSendGroup> groups) {
		if (CollectionUtils.isNotEmpty(groups)) {
			return msgSendGroupMapper.batchInsert(groups);
		} else {
			return 0;
		}
	}

	/**
	 * 保存消息
	 *
	 * @param msg
	 * @return int
	 */
	public int addMsg(MediaMsg msg) {
		saveSendGroups(msg.getGroups());
		saveSendUsers(msg.getTenantId(), msg.getWid(), msg.getSendUsers());
		return msgMapper.insert(msg);
	}

	private int saveSendUsers(String tenantId, String msgId, List<SendUserVo> sendUserList) {
		if (CollectionUtils.isNotEmpty(sendUserList)) {
			List<MsgSendUserPo> msgSendUserPoList = new ArrayList<>();
			for (SendUserVo sendUserVo : sendUserList) {
				MsgSendUserPo msgSendUserPo = new MsgSendUserPo();
				msgSendUserPo.setMsgId(msgId);
				msgSendUserPo.setTenantId(tenantId);
				msgSendUserPo.setUserId(sendUserVo.getUserId());
				msgSendUserPo.setUserName(sendUserVo.getUserName());
				msgSendUserPoList.add(msgSendUserPo);
			}

			return msgSendUserMapper.batchInsert(msgSendUserPoList);
		} else {
			return 0;
		}
	}

	/**
	 * 修改消息
	 *
	 * @param msg
	 * @return int
	 */
	public int updateMsg(MediaMsg msg) {
		msgSendGroupMapper.deleteByMsgId(msg.getWid());
		msgSendUserMapper.deleteByMsgId(msg.getWid(), msg.getTenantId());
		saveSendGroups(msg.getGroups());
		saveSendUsers(msg.getTenantId(), msg.getWid(), msg.getSendUsers());
		return msgMapper.updateByPrimaryKeyWithBLOBs(msg);
	}

	/**
	 * 发送消息
	 *
	 * @param msg
	 * @return
	 */
	public String sendMsg(MediaMsg msg) throws Exception {
		MediaMsg record = new MediaMsg();
		record.setWid(msg.getWid());
		record.setStatus(MsgConstants.MsgStatus.SEND_IN.toString());
		record.setuTime(new Date());
		msgMapper.updateByPrimaryKeySelective(record);

		if (MsgConstants.MsgStatus.SEND.toString().equals(msg.getStatus())) {
			// 调用异步任务发布消息
//			sendMsgTask.sendMsg(msg);
			this.pushMsg(msg, msg.getSendTotalUserIds());
		} else if (MsgConstants.MsgStatus.SEND_ERROR.toString().equals(msg.getStatus())) {
			// 调用异步任务重发消息
//			sendMsgTask.reSendMsg(msg);

			List<String> sendUserIds = new ArrayList<>();
			List<MediaMsgError> mediaMsgErrors = mediaMsgErrorMapper.selectByMsgId(msg.getWid());
			for (MediaMsgError mediaMsgError : mediaMsgErrors) {
				sendUserIds.add(mediaMsgError.getUserId());
			}
			mediaMsgErrorMapper.delByMsgId(msg.getWid());
			this.pushMsg(msg, new HashSet<String>(sendUserIds));
		}

		return msg.getWid();
	}

	@Async
	@Transactional
	private void pushMsg (MediaMsg msg, Set<String> toBeSendUserIds) throws Exception {
		UserInfoContext context = ThreadLocalUserInfo.getContext();

		InnerInfoSenderBean sender = new InnerInfoSenderBean();
		sender.setFromType(2);
		sender.setSenderId(msg.getMediaId());

		ReceiverBean receiver = new ReceiverBean();
		receiver.setReceiverType(2);

		CampusMedia media = mediaMapper.selectByMediaId(msg.getMediaId());
		CpdailyTenant tenant = tenantMapper.getTenantById(media.getTenantId());
		receiver.setTenantCode(tenant.getTenantCode());

		List<String> sendUserIds = new ArrayList<String>(toBeSendUserIds);
		List<List<String>> subUserIdLists = CommUtils.splitList(sendUserIds, 100);
		String readUrl = msgReadPrefix + msg.getWid();
		String viewUrl = msgViewPrefix + msg.getWid();

		List<String> sendErrorUserIds = new ArrayList<>();
		List<String> sendSuccessUserIds = new ArrayList<>();

		for (List<String> subUserIdList : subUserIdLists) {
			if (!CollectionUtils.isEmpty(subUserIdList)) {

				String[] arr = new String[subUserIdList.size()];
				subUserIdList.toArray(arr);
				receiver.setUserIds(arr);

				try {
					SendMessageResultBean result;
					if (StringUtils.isEmpty(msg.getAttachments())) {
						result = sendMsgService.pushImgMsg(sender, receiver, "【通知】 " + msg.getSummary(), StripHT(msg.getContent()), msg.getImg(), viewUrl, readUrl);
					} else {
						result = sendMsgService.pushAttachmentMsg(sender, receiver, "【通知】 " + msg.getSummary(), StripHT(msg.getContent()), msg.getImg(), msg.getAttachments().split(","), viewUrl, readUrl);
					}
					if (!result.getErrCode().equals("0")) {
						throw new RuntimeException(result.getErrMsg());
					}
					sendSuccessUserIds.addAll(subUserIdList);
				} catch (Exception e) {
					logger.error("媒体号，消息编号:" + msg.getWid() + "推送失败：" + e);
					sendErrorUserIds.addAll(subUserIdList);
				}
			}
		}

		if (sendErrorUserIds.size() > 0) {
			List<MediaMsgError> mediaMsgErrorList = new ArrayList<>();
			for (String userId : sendErrorUserIds) {
				MediaMsgError mediaMsgError = new MediaMsgError(msg.getWid(), userId, new Date());
				mediaMsgErrorList.add(mediaMsgError);
			}
			mediaMsgErrorMapper.batchInsert(mediaMsgErrorList);
		}

		// 更新状态
		MediaMsg recordN = new MediaMsg();
		recordN.setWid(msg.getWid());
		recordN.setStatus(sendSuccessUserIds.size() > 0 ? MsgConstants.MsgStatus.SEND_END.toString() : MsgConstants.MsgStatus.SEND_ERROR.toString());
		recordN.setuTime(new Date());
		if (msg.getSendOverCount() == null || msg.getSendOverCount() < 1) {
			recordN.setSendOverCount(toBeSendUserIds.size());
		}
		if (msg.getSendCount() == null || msg.getSendCount() < 1) {
			recordN.setSendCount(toBeSendUserIds.size());
		}
		recordN.setReceiveCount(sendSuccessUserIds.size() > 0 ? msg.getReceiveCount() + sendSuccessUserIds.size() : msg.getReceiveCount());
		mediaMsgMapper.updateByPrimaryKeySelective(recordN);

		if (sendSuccessUserIds.size() > 0) {
			logService.insertLog(msg.getMediaId(), MediaLogOperateType.PUSH_MESSAGE.toString(), new Gson().toJson(msg), true);
			logger.info("媒体号消息推送成功,消息编号:" + msg.getWid() + ",成功" + sendSuccessUserIds.size() + "条，失败" + sendErrorUserIds.size() + "条。");
			// 入库
			saveMsgUsersReadRelation(msg.getWid(), msg.getTenantId(), sendSuccessUserIds);
		} else {
			logService.insertLog(msg.getMediaId(), MediaLogOperateType.PUSH_MESSAGE.toString(), new Gson().toJson(msg), false);
			logger.info("媒体号消息推送失败,消息编号:" + msg.getWid() + "，失败" + sendErrorUserIds.size() + "条。");
		}

	}

	private void saveMsgUsersReadRelation(String msgId, String tenantId, List<String> userIds) {
		msgMapper.selectByPrimaryKey(msgId);
		msgMapper.insertMsgUserReadRelation(msgId, tenantId, userIds);
	}

	public String StripHT(String strHtml) {
		String txtcontent = strHtml.replaceAll("</?[^>]+>", "");
		txtcontent = txtcontent.replaceAll("<a>\\s*|\t|\r|\n</a>", "");
		txtcontent = txtcontent.replaceAll("&nbsp;", " ");
		if (txtcontent.length() > 100) {
			txtcontent = txtcontent.substring(0, 100) + "...";
		}
		return txtcontent;
	}

	/**
	 * 发送消息
	 *
	 * @param msgId
	 * @return
	 */
	public String sendMsgById(String msgId) throws Exception {
		isHasAuth(msgId);
		MediaMsg msg = getMsgById(msgId);
		sendMsg(msg);
		return msgId;
	}

	/**
	 * 刪除消息
	 *
	 * @param msgId
	 * @return
	 */
	public String delMsgById(String msgId) {
		isHasAuth(msgId);
		msgMapper.delLogic(msgId);
		return msgId;
	}

	/**
	 * 权限校验
	 *
	 * @param msgId
	 * @return
	 */
	public void isHasAuth(String msgId) {
		MediaMsg msg = msgMapper.selectByPrimaryKey(msgId);
		if (null == msg) {
			throw new ServiceException(MsgErrConstants.MsgErrTypeE.NOT_EXIST);
		}

		String mediaId = ThreadLocalUserInfo.getContext().getMediaId();
		if (!mediaId.equals(msg.getMediaId())) {
			throw new ServiceException(MsgErrConstants.MsgErrTypeE.NO_PERMISSIONS);
		}

	}

	/**
	 * 阅读消息消息
	 *
	 * @param msgId
	 * @return
	 */
	public String readMsg(String msgId, String userId) {

		MediaMsg msg = msgMapper.selectByPrimaryKey(msgId);
		if (null == msg) {
			throw new ServiceException(MsgErrConstants.MsgErrTypeE.NOT_EXIST);
		}

        int count = msgMapper.getUserReadMsgCount(msgId, userId);
		if (count < 1) {
            //更新已读未读表状态
            msgMapper.userReadMsg(msgId, userId);
            // 更新消息已读数
            msgMapper.readMsg(msgId);
        }

		return msgId;
	}


	/**
	 * 查看消息消息
	 *
	 * @param msgId
	 * @return
	 */
	public String viewMsgById(String msgId) {

		MediaMsg msg = msgMapper.selectByPrimaryKey(msgId);
		if (null == msg) {
			throw new ServiceException(MsgErrConstants.MsgErrTypeE.NOT_EXIST);
		}
		return msg.getContent();
	}

	/**
	 * 获取一个消息
	 *
	 * @param msgId
	 * @return MediaMsg
	 */
	public MediaMsg getMsgById(String msgId) {

		MediaMsg msg = msgMapper.selectByPrimaryKey(msgId);

		Set<String> sendUserIds = new HashSet<>();
		
		// 设置用户组
		List<MediaMsgSendGroup> groups = msgSendGroupMapper.selectByMsgId(msgId);
		if (!CollectionUtils.isEmpty(groups)) {
			msg.setGroups(groups);
			// 设置发布人数
			sendUserIds.addAll(getUserIdsByGroup(msg.getGroups(), msg.getTenantId(), msg.getMediaId(),
					msg.getNotSendUserIds()));

		}

		sendUserIds.addAll(msgSendUserMapper.selectUserIdByMsgId(msgId, msg.getTenantId()));

		List<SendUserVo> sendUsers = msgSendUserMapper.selectUsersByMsgId(msgId, msg.getTenantId());
		if(sendUsers==null){
			sendUsers = Collections.emptyList();
		}
		msg.setSendUsers(sendUsers);
		if (!CollectionUtils.isEmpty(sendUserIds)) {
			msg.setSendTotalUserIds(sendUserIds);
		}

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		msg.setLastNotifyTimeFmt(msg.getLastNotifyTime() == null ? "" : fmt.format(msg.getLastNotifyTime()));

		return msg;
	}

	/**
	 * 获取一个消息
	 *
	 * @param msgId
	 * @return
	 */
	public SendMsgVo getSendMsgVo(String msgId) throws Exception {
		SendMsgVo sendMsgVo = new SendMsgVo();
		MediaMsg msg = getMsgById(msgId);
		BeanUtils.copyProperties(sendMsgVo, msg);
		sendMsgVo.setMsgId(msgId);
		sendMsgVo.setcTime(msg.getcTime());
		sendMsgVo.setLastNotifyTime(msg.getLastNotifyTime());
		sendMsgVo.setLastNotifyTimeFmt(msg.getLastNotifyTimeFmt());
		List<SendUserGroupVo> sendUserGroupVos = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(msg.getGroups())) {
			for (MediaMsgSendGroup groupPo : msg.getGroups()) {
				sendUserGroupVos.add(groupPoToVo(groupPo));
			}
		}
		sendMsgVo.setSendUserGroups(sendUserGroupVos);

		//显示创建人
		CpdailyUser cUser = userMapper.selectByUserId(msg.getcUserId());
		sendMsgVo.setcUserId(msg.getcUserId());
		sendMsgVo.setcUserName(cUser.getName());

		// 设置屏蔽的用户
		if (StringUtils.isNotBlank(msg.getNotSendUserIds())) {
			List<String> userIds = Arrays.asList(msg.getNotSendUserIds().split(","));
			List<CpdailyUser> users = userMapper.selectByUserIds(userIds);
			List<String> notSendUserIds = new ArrayList<>();
			List<String> notSendUserNames = new ArrayList<>();
			if (CollectionUtils.isNotEmpty(users)) {
				for (CpdailyUser user : users) {
					notSendUserIds.add(user.getWid());
					notSendUserNames.add(user.getName());
				}
			}
			sendMsgVo.setNotSendUserIds(StringUtils.join(notSendUserIds, ","));
			sendMsgVo.setNotSendUserNames(StringUtils.join(notSendUserNames, ","));
		} else {
			sendMsgVo.setNotSendUserIds("");
			sendMsgVo.setNotSendUserNames("");
		}
		return sendMsgVo;
	}

	/**
	 * 生产html文件并上传
	 *
	 * @param msg
	 * @return String
	 */
	public String getHtmlStr(MediaMsg msg) throws Exception {

		Map<String, String> fileImgMap = new HashMap<>();
		fileImgMap.put("DOC", "https://feres.cpdaily.com/custom/forxyh/doc.png");
		fileImgMap.put("PDF", "https://feres.cpdaily.com/custom/forxyh/pdf.png");
		fileImgMap.put("XLS", "https://feres.cpdaily.com/custom/forxyh/xls.png");
		fileImgMap.put("XLSX", "https://feres.cpdaily.com/custom/forxyh/xls.png");
		fileImgMap.put("PNG", "https://feres.cpdaily.com/custom/forxyh/png.png");
		fileImgMap.put("JPEG", "https://feres.cpdaily.com/custom/forxyh/jpeg.png");
		fileImgMap.put("JPG", "https://feres.cpdaily.com/custom/forxyh/jpeg.png");
		fileImgMap.put("PPTX", "https://feres.cpdaily.com/custom/forxyh/pptx.png");
		fileImgMap.put("ZIP", "https://feres.cpdaily.com/custom/forxyh/zip.png");
		fileImgMap.put("RAR", "https://feres.cpdaily.com/custom/forxyh/rar.png");
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> message = new HashMap<String, Object>();
		model.put("message", message);
		message.put("title", msg.getTitle());
		message.put("content", msg.getContent());
		message.put("cTime", MsgConstants.yyyyMMdd.format(msg.getcTime()));
		if (StringUtils.isNotBlank(msg.getOriginalLink())) {
			message.put("originUrl", msg.getOriginalLink());
		}
		message.put("readNum", 0);
		List<Map<String, Object>> attachmentsList = new ArrayList<>();
		if (StringUtils.isNotBlank(msg.getAttachments())) {
			for (String attachment : msg.getAttachments().split(",")) {
				Map<String, Object> attachmentMap = new HashMap<>();
				String fileType = ImgUtils.getImgType(attachment).replace(".", "").toUpperCase();
				String fileImg = fileImgMap.get(fileType) != null ? fileImgMap.get(fileType).toString()
						: "https://feres.cpdaily.com/custom/forxyh/XLS.png";
				attachmentMap.put("fileImg", fileImg);

				String fileName = ImgUtils.getFileName(attachment);

				attachmentMap.put("fileName", fileName.length() <= 15 ? fileName : fileName.substring(0, 14) + "...");
				attachmentMap.put("fullFileName", fileName);
				attachmentMap.put("url", attachment);
				attachmentsList.add(attachmentMap);
			}
		}
		message.put("attachments", attachmentsList);
		
		Template t = freemarkerConfig.createConfiguration().getTemplate("msgView.ftl");
		String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
		if (StringUtils.isBlank(html)) {
			return "";
		} else {
			UploadVo uploadVo = ossService.upload(msg.getTenantId(), msg.getWid() + ".html", ".html", html.getBytes("UTF-8"));
			return uploadVo.getFileUrl();
		}
	}

	/**
	 * 获取用户组对应的人
	 *
	 * @param userGroups
	 * @param tenantId
	 * @param mediaId
	 * @return List<String>
	 */
	public Set<String> getUserIdsByGroup(List<MediaMsgSendGroup> userGroups, String tenantId, String mediaId,
			String notSendUserIds) {
		Set<String> userIds = new HashSet<String>();
		if (CollectionUtils.isNotEmpty(userGroups)) {
			for (MediaMsgSendGroup userGroup : userGroups) {
				if (MsgConstants.SendUserGroupType.PERSONAL.equals(userGroup.getGroupType())) {
					userIds.addAll(getGroupUser(userGroup.getGroupId(), mediaId));
				} else if (MsgConstants.SendUserGroupType.DEPART.equals(userGroup.getGroupType())) {
					userIds.addAll(getDepartUser(userGroup.getGroupId(), tenantId));
				} else if (MsgConstants.SendUserGroupType.ACADEMY_MAJOR.equals(userGroup.getGroupType())) {
					userIds.addAll(getAcademyMajorUser(userGroup.getGroupId(), tenantId));
				}
			}
		}
		// 排除
		userIds.removeAll(Arrays.asList(notSendUserIds.split(",")));

		return userIds;
	}

	/**
	 * 查询老师
	 *
	 * @param depId
	 * @param tenantId
	 * @return List<String>
	 */
	public List<String> getDepartUser(String depId, String tenantId) {
		if (depId.contains(MsgConstants.DEP_OWNER_MARK)) {
			return userMapper.selectWidsByDepartId(tenantId, depId.replace(MsgConstants.DEP_OWNER_MARK, ""));
		} else if (MsgConstants.TEACHER.equals(depId)) {
			return userMapper.selectWidsByUserType(tenantId, MsgConstants.TEACHER.toUpperCase());
		} else {
			return userMapper.selectWidsByDepartIdFuzzy(tenantId, depId);
		}
	}

	/**
	 * 查询学生
	 *
	 * @param depId
	 * @param tenantId
	 * @return List<String>
	 */
	public List<String> getAcademyMajorUser(String depId, String tenantId) {
		if (MsgConstants.STUDENT.equals(depId)) {
			return userMapper.selectWidsByUserType(tenantId, MsgConstants.STUDENT.toUpperCase());
		} else if (MsgConstants.NEWER.equals(depId)) {
            return userMapper.selectWidsByYear(tenantId, MsgConstants.STUDENT.toUpperCase(), String.valueOf(LocalDate.now().getYear()));
        } else if (depId.replace(tenantId, "").length() == 2) {
			return userMapper.selectWidsByAcademyId(tenantId, depId);
		} else {
			return userMapper.selectWidsByMajorId(tenantId, depId);
		}
	}

	/**
	 * 查询用户组对应的用户
	 *
	 * @param groupId
	 * @param mediaId
	 * @return List<String>
	 */
	public List<String> getGroupUser(String groupId, String mediaId) {
		if (MsgConstants.FANS.equals(groupId)) {
			return userMapper.selectWidsByUserFans(mediaId);
		} else {
			return userMapper.selectWidsByGroup(groupId);
		}
	}

	/**
	 * 获取消息列表
	 *
	 * @param mediaId
	 * @param msgStatus
	 * @param msgStatus
	 * @param pageSize
	 * @param pageNumer
	 * @return MediaMsg
	 */
	public PageVo<SendMsgItemVo> getMgsList(String mediaId, String msgStatus, int pageSize, int pageNumer)
			throws Exception {
		PageVo<SendMsgItemVo> pageVo = new PageVo<>();
		List<SendMsgItemVo> sendMsgItemVos = new ArrayList<>();
		PageHelper.startPage(pageNumer, pageSize);
		List<String> statusList = Arrays.asList(msgStatus.split(","));
		List<MediaMsg> msgs = msgMapper.selectMsgs(mediaId, statusList);
		if (CollectionUtils.isNotEmpty(msgs)) {
			for (MediaMsg msg : msgs) {
				SendMsgItemVo itemVo = msgToMsgItemVo(msg);
				sendMsgItemVos.add(itemVo);
			}
		}
		int count = msgMapper.countMsgs(mediaId, statusList);
		pageVo.setRows(sendMsgItemVos);
		pageVo.setPageNumber(pageNumer);
		pageVo.setPageSize(pageSize);
		pageVo.setTotalSize(count);
		return pageVo;
	}

	/**
	 * 获取消息列表
	 *
	 * @param msg
	 * @return SendMsgItemVo
	 */
	private SendMsgItemVo msgToMsgItemVo(MediaMsg msg) {
		SendMsgItemVo itemVo = new SendMsgItemVo();
		itemVo.setMsgId(msg.getWid());
		itemVo.setTitle(msg.getTitle());
		itemVo.setcTime(MsgConstants.yyyyMMddHHmmss.format(msg.getcTime()));
		itemVo.setReceiveCount(msg.getReceiveCount());
		itemVo.setReadCount(msg.getReadCount());
		itemVo.setSendCount(msg.getSendCount());
		itemVo.setStatus(msg.getStatus());
		// 设置发布失败的用户数量
		if (MsgConstants.MsgStatus.SEND_ERROR.toString().equals(msg.getStatus())) {
			List<MediaMsgError> mediaMsgErrors = msgErrorMapper.selectByMsgId(msg.getWid());
			itemVo.setSendErrorCount(mediaMsgErrors.size());
		}

		// 设置接收对象名称
		List<MediaMsgSendGroup> sendGroups = msgSendGroupMapper.selectByMsgId(msg.getWid());
		List<SendUserVo> sendUsers = msgSendUserMapper.selectUsersByMsgId(msg.getWid(), msg.getTenantId());
		List<String> sendTargetNames = new ArrayList<>();
		if (!CollectionUtils.isEmpty(sendGroups) || !CollectionUtils.isEmpty(sendUsers)) {
			for (MediaMsgSendGroup mediaMsgSendGroup : sendGroups) {
				sendTargetNames.add(mediaMsgSendGroup.getGroupName());
				if(sendTargetNames.size()>10){
					break;
				}
			}
			for (SendUserVo sendUser : sendUsers) {
				sendTargetNames.add(sendUser.getUserName());
				if(sendTargetNames.size()>10){
					break;
				}
			}
			itemVo.setSendUserGroup(StringUtils.join(sendTargetNames, ","));
		} else {
			itemVo.setSendUserGroup("");
		}

		return itemVo;
	}

	/**
	 * 获取已读、未读列表
	 *
	 * @param msgId
	 * @param name
	 * @param status
	 * @param pageNumber
	 * @param pageSize
	 * @return BasePageResponseDatas
	 */
	public BasePageResponseDatas<UserWithGroup> getReadUserList(String msgId, String name, String status, Integer pageNumber, Integer pageSize) throws Exception {
		long total = 0;

		BasePageResponseDatas<UserWithGroup> datas = new BasePageResponseDatas<>();

		List<ReadUserBean> readUsers = msgMapper.selectReadUserList(msgId, status);
		List<String> wids = readUsers.stream().map(ReadUserBean::getUserId).collect(Collectors.toList());

		Map<String, Object> searchMap = new HashMap<String, Object>();

		List<UserWithGroup> users = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(wids)) {
			searchMap.put("list", wids);
			searchMap.put("name", "%" + name + "%");

			Page<Object> page = PageHelper.startPage(pageNumber, pageSize);
			users = userMapper.selectByUserIdsAndName4Group(searchMap);
			total = page.getTotal();
		}


		String tenantId = ThreadLocalUserInfo.getContext().getTenantId();
		userTreeService.setClassAndDepartment(users, tenantId);

		datas.setRows(users);
		datas.setPageNumber(pageNumber);
		datas.setPageSize(pageSize);
		datas.setTotalSize(total);
		return datas;
	}

	/**
	 * 未读提醒
	 *
	 * @param msgId
	 */
	@Transactional
	public void notifyMsg(String msgId) throws Exception {
		List<ReadUserBean> users = msgMapper.selectReadUserList(msgId, "unread");
		if (users.size() < 1) {
			throw new RuntimeException("全部用户已读，无需短信提醒");
		}
		String mediaId = ThreadLocalUserInfo.getContext().getMediaId();
		CampusMedia media = mediaMapper.selectByMediaId(mediaId);

		synchronized(this){
			System.out.println("11进入加锁方法");
			MediaMsg mediaMsg = msgMapper.selectByPrimaryKey(msgId);

			if (StringUtils.isEmpty(mediaId)) {
				throw new RuntimeException("上下文信息异常");
			}

			System.out.println("22判断时间间隔");
			// 发送时间校验
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR, -messageWaitHours);
			Date lastNotifyTime = mediaMsg.getLastNotifyTime() == null ? mediaMsg.getcTime() : mediaMsg.getLastNotifyTime();
			if (lastNotifyTime.after(calendar.getTime())) {
				throw new RuntimeException("当前时间间隔不能发送提醒");
			}

			try {
				System.out.println("33扣除短信数量");
				// 更新剩余量
				mediaMapper.udpateSmsRemain(mediaId, users.size());
			} catch(Exception ex) {
				throw new RuntimeException("当前校园号剩余短信数量不够");
			}

			System.out.println("44更新发送时间");
			// 更新发送时间
			msgMapper.updateLastNotifyTime(msgId);
			System.out.println("55完毕");
		}

		List<CpdailyUser> cpdailyUsers =  userMapper.selectByUserIds(users.stream().map(ReadUserBean::getUserId).collect(Collectors.toList()));
		List<String> mobiles = cpdailyUsers.stream().map(CpdailyUser::getMobilephone).collect(Collectors.toList());

		// 发送短信
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("accessToken", messageAccessToken);
		headers.add("appId", messageAppId);

		SmsSendRequest request = new SmsSendRequest();
		request.setSchoolCode(messageSchoolCode);
		request.setAppId(messageAppId);
		request.setContent("同学你好， " + media.getName() + "给你发送了一条重要通知，快上今日校园App查看！");
		request.setSendType(4);
		request.setSendNow(true);
		request.setTagId(1020);

		List<List<String>> subMobileLists = CommUtils.splitList(mobiles, 500);

		for (List<String> subMobileList : subMobileLists) {
			if (!CollectionUtils.isEmpty(subMobileList)) {

				request.setReceivers(new ArrayList<>());
				for (String mobile : subMobileList) {
					SmsReceiverBean receiver = new SmsReceiverBean();
					receiver.setMobile(mobile);
					request.getReceivers().add(receiver);
				}

				try {
					HttpEntity<SmsSendRequest> entity = new HttpEntity<>(request, headers);
					SmsResponse resp = restTemplate.postForEntity(messageCenterApi, entity, SmsResponse.class).getBody();
					if (!resp.getStatus().equals(200)) {
						throw new RuntimeException("提醒失败，" + resp.getMsg());
					}
				} catch (Exception e) {
					logger.error("媒体号，消息编号:" + msgId + "提醒失败：" + e);
				}
			}
		}
	}


	/**
	 * 提醒短信等待小时数
	 *
	 */
	public Integer getNotifyWaitHours() throws Exception {
		return messageWaitHours;
	}
}
