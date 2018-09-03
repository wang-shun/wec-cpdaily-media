package com.wisedu.wec.media.biz.old.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wisedu.wec.media.biz.old.controller.BaseController;
import com.wisedu.wec.media.biz.service.impl.AuthService;
import com.wisedu.wec.media.common.old.constants.MediaConstants;
import com.wisedu.wec.media.common.old.po.CpdailyTenant;
import com.wisedu.wec.media.common.old.po.MediaAdmins;
import com.wisedu.wec.media.common.old.vo.TenantVO;
import com.wisedu.wec.media.dal.mybatis.CampusMediaMapper;
import com.wisedu.wec.media.dal.mybatis.CpdailyUserMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.glassfish.grizzly.utils.ArraySet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wisedu.wec.media.biz.old.service.impl.MediaAccountService;
import com.wisedu.wec.media.common.old.constants.MediaPlatformCode;
import com.wisedu.wec.media.common.old.constants.RedisConstants;
import com.wisedu.wec.media.common.old.context.MediaInfoContext;
import com.wisedu.wec.media.common.old.context.ThreadLocalUserInfo;
import com.wisedu.wec.media.common.old.context.UserInfoContext;
import com.wisedu.wec.media.common.old.exception.BadRequestException;
import com.wisedu.wec.media.common.old.po.CampusMedia;
import com.wisedu.wec.media.common.old.po.CpdailyUser;
import com.wisedu.wec.media.common.old.util.CookieUtils;
import com.wisedu.wec.media.common.old.util.InputValidator;
import com.wisedu.wec.media.common.old.vo.CommonVO;
import com.wisedu.wec.media.dal.mybatis.CpdailyTenantMapper;


/**
 * 管理者页面
 *
 * @author dell
 *
 */
@RestController
@RequestMapping("/v3/campusmedia/account")
public class OwnerController extends BaseController {

	@Autowired
	private MediaAccountService mediaAccountService;
	@Autowired
	private AuthService authService;

	@Autowired
	private CpdailyTenantMapper cpdailyTenantMapper;

	@Autowired
	private CpdailyUserMapper cpdailyUserMapper;

	@Autowired
	private CampusMediaMapper campusMediaMapper;

	/**
	 * 校园号列表
	 */
	@RequestMapping(value = "/list", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET)
	public CommonVO myMediaList() {
        String personId = ThreadLocalUserInfo.getContext().getPersonId();
		List<CampusMedia> list = mediaAccountService.queryMediaListByPersonId(personId);

		if(CollectionUtils.isNotEmpty(list)){
			mediaAccountService.getFansCount(list);
		}
		// 管理员
		if(CollectionUtils.isNotEmpty(list)){
			mediaAccountService.getManagers(list);
		}

		return successResponseWithData(list);
	}

	/**
	 * 我申请的未认证通过的校园号列表
	 */
	@RequestMapping(value = "/list/unauthed", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET)
	public CommonVO getMyUnAuthedMediaList() {
		String userId = ThreadLocalUserInfo.getContext().getLoginUserId();
		CpdailyUser user = cpdailyUserMapper.selectByUserId(userId);
		if (user.getUserType().equals("MEDIA")) {
			return failResponseWithMsg(MediaConstants.MEDIA_LOGIN_DENY_CODE, "校园号身份不能申请校园号");
		}

		String personId = ThreadLocalUserInfo.getContext().getPersonId();
		List<CampusMedia> list = mediaAccountService.getMyUnAuthedMediaList(personId);

		return successResponseWithData(list);
	}

	/**
	 * 校验是否可以申请
	 *
	 * @return
	 */
	@RequestMapping(value = "/apply/check", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET)
	public CommonVO qualifyCheck() {

		boolean canApply = mediaAccountService.checkMediaCount();
		if(!canApply){
			return failResponseWithMsg(MediaPlatformCode.MEDIA_COUNT_LIMITED, "每人最多只能拥有5个校园号</br>包括审核通过、待审核、待认证的哦～");
		}

		return successResponseWithData("");

	}

	/**
	 *
	 * @param campusMedia
	 * @return
	 */
	@RequestMapping(value = "/apply/verify", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public CommonVO verify(@RequestBody CampusMedia campusMedia) {

		mediaAccountService.formVerify(campusMedia);

		return successResponse();

	}

	/**
	 * 申请校园号
	 *
	 * @param campusMedia
	 * @return
	 */
	@RequestMapping(value = "/apply/action", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public CommonVO apply(@RequestBody CampusMedia campusMedia) {

		// 阿里百川ID都为小写，校园号ID转为小写
//		campusMedia.setWid(campusMedia.getWid().toLowerCase());

		//校验校园号数量是否已经达到上限
		boolean canApply = mediaAccountService.checkMediaCount();
		if(!canApply){
			return failResponseWithMsg(MediaPlatformCode.MEDIA_COUNT_LIMITED, "每人最多只能拥有5个校园号</br>包括审核通过、待审核、待认证的哦～");
		}
		// 参数校验证
		if (!InputValidator.isValidMediaApply(campusMedia)) {
			return failResponseWithMsg("param illegal");
		}

		try{
			mediaAccountService.createMedia(campusMedia);
		}catch (BadRequestException e) {
			return failResponseWithMsg(e.getMessage());
		}
		return successResponse();

	}

	/**
	 * 删除校园号
	 *
	 * @param wid
	 * @return
	 */
	@RequestMapping(value = "/remove/{wid}", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public CommonVO delete(@PathVariable String wid) {

		mediaAccountService.deleteMedia(wid);

		return successResponse();

	}

	/**
	 * 单独提交申请材料
	 *
	 * @param campusMedia
	 * @return
	 */
	@RequestMapping(value = "/apply/material/action", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public CommonVO applyMaterial(@RequestBody CampusMedia campusMedia) {

		String wid = campusMedia.getWid();

		//校验校园号数量是否已经达到上限
		boolean canApply = mediaAccountService.checkMediaCount();
		if(!canApply){
			return failResponseWithMsg(MediaPlatformCode.MEDIA_COUNT_LIMITED, "每人最多只能拥有5个校园号</br>包括审核通过、待审核、待认证的哦～");
		}
		String authMaterials = campusMedia.getAuthMaterials();

		mediaAccountService.sumbitMaterial(wid, authMaterials);

		return successResponse();

	}

	/**
	 * 切换校园号
	 *
	 * @param mediaId
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/switch/{mediaId}", method = RequestMethod.GET)
	public CommonVO mediaSwitch(@PathVariable String mediaId, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfoContext userInfoContext = ThreadLocalUserInfo.getContext();

		MediaInfoContext mediaInfoContext = userInfoContext.getMediaMap().get(mediaId);
		if(mediaInfoContext==null ){
			// 在登录之后，已有新校园号

			CampusMedia media = campusMediaMapper.selectByMediaId(mediaId);
			if (media == null) {
				return failResponseWithMsg("校园号不存在");
			}

			MediaInfoContext mediaInfo = new MediaInfoContext();
			mediaInfo.setMediaId(media.getWid());
			mediaInfo.setMediaName(media.getName());
			mediaInfo.setPortraitUrl(media.getIcon());
			mediaInfo.setAuthStatus(media.getAuthStatus());
			mediaInfo.setMediaType(media.getMediaType());
			mediaInfo.setTenantId(media.getTenantId());
			mediaInfo.setCanSeeOrgStructure(media.isCanSeeOrgStructure());
			Map<String, MediaInfoContext> map = userInfoContext.getMediaMap();
			map.put(media.getWid(), mediaInfo);
			userInfoContext.setMediaMap(map);

			mediaInfoContext = mediaInfo;
		}

		userInfoContext.setMediaId(mediaInfoContext.getMediaId());
		userInfoContext.setTenantId(mediaInfoContext.getTenantId());
		CpdailyTenant tenant = cpdailyTenantMapper.getTenantById(mediaInfoContext.getTenantId());
		userInfoContext.setTenantCode(tenant.getTenantCode());
		userInfoContext.setTenantName(tenant.getTenantName());
		userInfoContext.setUserId(mediaInfoContext.getMediaId());
		userInfoContext.setUserName(mediaInfoContext.getMediaName());
		userInfoContext.setPortrait(mediaInfoContext.getPortraitUrl());
		userInfoContext.setUserType(null);
		ThreadLocalUserInfo.setContext(userInfoContext);

		String mediaLoginKey = CookieUtils.getCookieValue((HttpServletRequest) request, RedisConstants.MEDIA_LOGIN_KEY);

		authService.setLoginUserContextToCache(mediaLoginKey, userInfoContext);
		return successResponse();
	}

	/**
	 * 获取当前身份
	 *
	 */
	@RequestMapping(value="/owner", method = RequestMethod.GET)
	public CommonVO mediaOwner() {

		UserInfoContext context = ThreadLocalUserInfo.getContext();

		CpdailyUser cpdailyUser = new CpdailyUser();
		cpdailyUser.setWid(context.getLoginUserId());// fix bug: 切换校园号导致userId错误， -- zsl 2018.5.17
		cpdailyUser.setName(context.getLoginUserName());
		cpdailyUser.setPersonName(context.getPersonName());
		cpdailyUser.setUserType(context.getUserType());
		cpdailyUser.setImg(context.getPortrait());
		cpdailyUser.setTenantId(context.getTenantId());
		cpdailyUser.setTenantName(context.getTenantName());

		return successResponseWithData(cpdailyUser);

	}

}