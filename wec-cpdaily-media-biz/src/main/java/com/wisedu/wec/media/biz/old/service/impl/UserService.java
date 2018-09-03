package com.wisedu.wec.media.biz.old.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.wisedu.wec.media.biz.redis.RedisService;
import com.wisedu.wec.media.common.old.constants.MediaLogOperateType;
import com.wisedu.wec.media.biz.service.impl.LogService;
import com.wisedu.wec.media.common.old.constants.MediaPersonManageType;
import com.wisedu.wec.media.common.old.po.*;
import com.wisedu.wec.media.dal.mybatis.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.gson.Gson;

import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Userinfos;
import com.taobao.api.request.OpenimUsersUpdateRequest;
import com.wisedu.wec.media.biz.old.config.MediaSettings;
import com.wisedu.wec.media.common.old.constants.MediaAuthStatus;
import com.wisedu.wec.media.common.old.exception.BadRequestException;
import com.wisedu.wec.media.common.old.exception.NotFoundException;
import com.wisedu.wec.media.common.old.util.DesUtil;
import com.wisedu.wec.media.common.old.util.ImgUtils;
import com.wisedu.wec.media.common.old.vo.CampusMediaVo;
import com.wisedu.wec.media.common.old.vo.UserWithGroup;

/**
 * Created by dyf on 17/10/24.
 */
@Service("oldUserService")
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Value("${login.qrcodeHost}")
	private String qrcodeHost;

	@Autowired
	private CpdailyUserMapper userMapper;

	@Autowired
	private CpdailyTenantMapper tenantMapper;

	@Autowired
	private CampusMediaMapper mediaMapper;

	@Autowired
	private CpdailyUserMapper cpdailyUserMapper;

	@Autowired
	private CpdailyPersonMapper cpdailyPersonMapper;

	@Autowired
	private MediaSettings settings;

	@Autowired
	private TaobaoClient taobaoClient;
	@Autowired
	private RedisService redisService;
	@Autowired
	private LogService logService;

	@Autowired
	private CpdailyTenantMapper cpdailyTenantMapper;

	@Autowired
	private MediaAdminService mediaAdminService;

	@Autowired
	private MediaPersonRelationMapper mediaPersonRelationMapper;

	public CpdailyUser selectByUserId(String userId) {
		CpdailyUser cpdailyUser = userMapper.selectByUserId(userId);

		if (cpdailyUser == null) {
			throw new NotFoundException("inexist for userId : " + userId, "-1");
		}

		String img = cpdailyUser.getImg();

		if (StringUtils.isNotEmpty(img)) {
			cpdailyUser.setImg(ImgUtils.imgUrlsSAddHost(img));
		}

		String tenantName = tenantMapper.selectNameById(cpdailyUser.getTenantId());

		cpdailyUser.setTenantName(tenantName);

		return cpdailyUser;
	}

	public List<CpdailyUser> usersByIds(List<String> ids) {
		return userMapper.selectByUserIds(ids);
	}

	public List<UserWithGroup> users4ChatByIds(List<String> userIds) {
		return userMapper.selectByUserIds4Group(userIds);
	}

	public CampusMedia mediaInfoById(String mediaId) {
		CampusMedia mediaVo = mediaMapper.selectByMediaId(mediaId);

		if (StringUtils.isNotEmpty(mediaVo.getIcon())) {
			mediaVo.setIcon(ImgUtils.imgUrlsSAddHost(mediaVo.getIcon()));
		}
		if (StringUtils.isNotEmpty(mediaVo.getAuthMaterials())) {
			mediaVo.setAuthMaterials(ImgUtils.imgUrlsSAddHost(mediaVo.getAuthMaterials()));
		}
		if (StringUtils.isNotEmpty(mediaVo.getBackgroundImg())) {
			mediaVo.setBackgroundImg(ImgUtils.imgUrlsSAddHost(mediaVo.getBackgroundImg()));
		}
		// 二维码地址
		mediaVo.setQrcodeUrl(qrcodeHost + "/v3/campusmedia/setting/mediaQrcode/" + mediaId);

		// 租户名称
		mediaVo.setTenantName(cpdailyTenantMapper.selectNameById(mediaVo.getTenantId()));

		// 拥有者

		MediaManager owner = mediaAdminService.getMediaOwner(mediaId);
		if (owner != null) {
			mediaVo.setOwnerName(owner.getName());
		}
		return mediaVo;
	}

	@Transactional
	public void updateMediaInfo(CampusMedia media) {

		try {
			CampusMedia record = mediaMapper.selectByMediaId(media.getWid());
			CpdailyUser user = cpdailyUserMapper.selectByUserId(media.getWid());

			if (StringUtils.isNotEmpty(media.getIcon())) {
				record.setIcon(media.getIcon());
			}
			record.setBackgroundImg(media.getBackgroundImg());
			if (StringUtils.isNotEmpty(media.getDescription())) {
				record.setDescription(media.getDescription());
			} else {
				throw new BadRequestException("param illegal", "-1");
			}
			// 如果不是已认证成功状态，则可以修改认证材料和校园号名称
			if (!record.getAuthStatus().equals(MediaAuthStatus.AUTHED.toString())) {
				if (StringUtils.isNotEmpty(media.getAuthMaterials())) {
					record.setAuthMaterials(media.getAuthMaterials());
					//如果提交了认证材料，且状态是未认证，则状态改为审核中
					if(record.getAuthStatus().equals(MediaAuthStatus.UN_AUTH.toString())){
						record.setAuthStatus(MediaAuthStatus.AUTHING.toString());
					}//如果是认证被拒绝，则修改为待审核
					if (record.getAuthStatus().equals(MediaAuthStatus.AUTH_FAIL.toString())) {
						record.setAuthStatus(MediaAuthStatus.AUTHING.toString());
					}
				}
				if (StringUtils.isNotEmpty(media.getName())) {
					cpdailyUserMapper.updateUserName(media.getWid(), media.getName());
					record.setName(media.getName());
				}
			}


			mediaMapper.updateByPrimaryKeySelective(record);

			// 更新用户表和IM
			if (user != null) {
				// 头像
				if (StringUtils.isNotEmpty(media.getIcon())) {

					cpdailyUserMapper.updateUserImg(media.getIcon(), media.getWid());
					OpenimUsersUpdateRequest updateRequest = new OpenimUsersUpdateRequest();

					Userinfos imUser = new Userinfos();
					imUser.setUserid(media.getWid());
					imUser.setIconUrl(media.getIcon() + "!small");

					List<Userinfos> userinfoList = new ArrayList<>();
					userinfoList.add(imUser);
					updateRequest.setUserinfos(userinfoList);

					try {
						taobaoClient.execute(updateRequest);
					} catch (ApiException e) {
						logger.error("taobao api ApiException", e);
					}
				}
				// 描述
				if (StringUtils.isNotEmpty(media.getDescription())) {
					cpdailyUserMapper.updateUserSignature(media.getDescription(), media.getWid());
				}
			}
			//更新缓存
			redisService.delByKey("listUserVoId:" + media.getWid());
			redisService.delByKey("user:" + media.getWid());

			// 租户名称
			CpdailyTenant tenant = cpdailyTenantMapper.getTenantById(media.getTenantId());
			media.setTenantName(tenant.getTenantName());
			//记录日志
			logService.insertLog(media.getWid(), MediaLogOperateType.EDIT_MEDIA_BASIC_INFO.toString(), new Gson().toJson(media), true);
		} catch (Exception ex) {
			//记录日志
			logService.insertLog(media.getWid(), MediaLogOperateType.EDIT_MEDIA_BASIC_INFO.toString(), new Gson().toJson(media), false);
			throw ex;
		}
	}

	public void updateMediaPassword(String mediaId, String oldCode, String newCode) {

		CampusMedia campusMedia = mediaMapper.selectByPrimaryKey(mediaId);

		String desOldCode = "";
		String desNewCode = "";
		try {
			desOldCode = DesUtil.desEncodeCBC(settings.getDeskey(), oldCode);
			desNewCode = DesUtil.desEncodeCBC(settings.getDeskey(), newCode);
		} catch (Exception e) {
			logger.error("crypto {} {} failed.", oldCode, newCode);
			throw new BadRequestException("crypto fail", "-1");
		}

		if (campusMedia != null && campusMedia.getLoginPassword().equals(desOldCode)) {
			CampusMedia record = new CampusMedia();
			record.setWid(mediaId);
			record.setLoginPassword(desNewCode);
			mediaMapper.updateByPrimaryKeySelective(record);
		} else {
			throw new BadRequestException("原密码不正确", "-1");
		}

	}

	/**
	 * 获取今日校园的用户
	 * 
	 * @param personWid
	 * @return
	 */
	public CpdailyPerson getCpdailyPerson(String personWid) {
		return cpdailyPersonMapper.selectByPrimaryKey(personWid);
	}


	public CpdailyTenant getMediaTenant(String mediaId) {
		CampusMedia mediaVo = mediaMapper.selectByMediaId(mediaId);

		CpdailyTenant tenant = cpdailyTenantMapper.getTenantById(mediaVo.getTenantId());

		return tenant;
	}
}
