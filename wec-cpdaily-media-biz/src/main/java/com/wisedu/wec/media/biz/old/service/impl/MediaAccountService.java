package com.wisedu.wec.media.biz.old.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wisedu.wec.cpdaily.config.provider.api.TenantsService;
import com.wisedu.wec.cpdaily.config.provider.api.dto.TenantsDto;
import com.wisedu.wec.media.biz.redis.RedisService;
import com.wisedu.wec.media.common.old.po.*;
import com.wisedu.wec.media.common.old.vo.TenantVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wisedu.wec.media.biz.old.config.MediaSettings;
import com.wisedu.wec.media.common.old.constants.MediaAuthStatus;
import com.wisedu.wec.media.common.old.constants.MediaIsDeletedStatus;
import com.wisedu.wec.media.common.old.constants.MediaPersonManageType;
import com.wisedu.wec.media.common.old.constants.MediaPlatformCode;
import com.wisedu.wec.media.common.old.constants.RedisConstants;
import com.wisedu.wec.media.common.old.context.ThreadLocalUserInfo;
import com.wisedu.wec.media.common.old.context.UserInfoContext;
import com.wisedu.wec.media.common.old.exception.BadRequestException;
import com.wisedu.wec.media.common.old.exception.NotFoundException;
import com.wisedu.wec.media.common.old.util.DesUtil;
import com.wisedu.wec.media.common.old.util.ImgUtils;
import com.wisedu.wec.media.dal.mybatis.CampusMediaMapper;
import com.wisedu.wec.media.dal.mybatis.CpdailyTenantMapper;
import com.wisedu.wec.media.dal.mybatis.CpdailyUserMapper;
import com.wisedu.wec.media.dal.mybatis.MediaApplyLimitMapper;
import com.wisedu.wec.media.dal.mybatis.MediaPersonRelationMapper;
import com.wisedu.wecloud.commons.util.UniqueIdentifierUtil;


@Service
public class MediaAccountService {

	private static final Logger logger = LoggerFactory.getLogger(MediaAccountService.class);

	@Autowired
	private CampusMediaMapper campusMediaMapper;
	
	@Autowired
	private CpdailyUserMapper cpdailyUserMapper;
	
	@Reference(version = "1.0.0")
	private TenantsService tenantsService;
	
	@Autowired
	private MediaApplyLimitMapper applyLimitMapper;

	@Autowired
	private MediaPersonRelationMapper mediaPersonRelationMapper;

	@Autowired
	private MediaSettings settings;
	@Autowired
	private RedisService redisService;

	@Autowired
	private MediaAdminService mediaAdminService;

	@Autowired
	private CpdailyUserMapper userMapper;

	@Value("${media.own.count.limit:5}")
	private Integer personOwnMediaCountLimit;
	
	/**
	 * 申请首页列表（0-未认证，1-认证审核中，2-认证未通过，3-已认证）
	 *
	 * @return
	 */
//	public List<CampusMedia> queryMediaListById(String userId) {
//
//		List<CampusMedia> list = campusMediaMapper.selectByOwnerId(userId);
//
//		for (CampusMedia cm : list) {
//			if (StringUtils.equals(cm.getAuthStatus(), MediaAuthStatus.AUTHED.toString())) {
//				try {
//					String unencrypted = cm.getWid() + "cpdaily123";
//					cm.setCode(DigestUtils.md5DigestAsHex(unencrypted.getBytes("UTF-8")));
//				} catch (UnsupportedEncodingException e) {
//					logger.error("UnsupportedEncodingException for wid {}.", cm.getWid());
//				}
//			}
//		}
//
//		return list;
//	}

	/**
	 * 单独提交认证材料
	 *
	 * @param wid
	 * @param authMaterials
	 */
	public void sumbitMaterial(String wid, String authMaterials) {

		CampusMedia campusMdia = campusMediaMapper.selectByPrimaryKey(wid);

		if (campusMdia == null) {
			throw new NotFoundException("media account not found for mediaId : " + wid, "-1");
		}

		CampusMedia record = new CampusMedia();
		record.setWid(wid);

		String materialsWithHead = ImgUtils.imgUrlsSAddHost(authMaterials);

		record.setAuthMaterials(materialsWithHead);
		record.setAuthStatus(MediaAuthStatus.AUTHING.toString());
		campusMediaMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 校园号申请提交表单验证
	 *
	 * @param campusMedia
	 */
	public void formVerify(CampusMedia campusMedia) {

//		String wid = campusMedia.getWid();
		String name = campusMedia.getName();
		String tenantId = ThreadLocalUserInfo.getContext().getTenantId();

		//校园号ID程序生成，不再由用户填写
//		if (StringUtils.isNotEmpty(wid)) {
//			// 校园号ID全局唯一
//			CampusMedia exist = campusMediaMapper.selectByPrimaryKey(wid);
//
//			if (exist != null) {
//				throw new BadRequestException("校园号ID已存在" + wid, MediaPlatformCode.PARAM_ILLEGAL);
//			}
//		}

		if(mediaNameExist(tenantId,name)){
			throw new BadRequestException("名称已经被占用", MediaPlatformCode.PARAM_ILLEGAL);
		}

//		if (StringUtils.isNotEmpty(name)) {
//
//			// 校园号名称 同一学校唯一
//			CampusMediaExample example = new CampusMediaExample();
//			example.createCriteria().andNameEqualTo(name).andTenantIdEqualTo(tenantId).andIsDeleteEqualTo(MediaIsDeletedStatus.ENABLE.toString());
//
//			List<CampusMedia> list = campusMediaMapper.selectByExample(example);
//
//			if (CollectionUtils.isNotEmpty(list)) {
//				throw new BadRequestException("校园号名称已存在" + name, MediaPlatformCode.PARAM_ILLEGAL);
//			}
//		}

	}

	/**
	 * 创建校园号
	 *
	 * @param campusMedia
	 */
	@Transactional
	public void createMedia(CampusMedia campusMedia) {

		formVerify(campusMedia);
		UserInfoContext userContext = ThreadLocalUserInfo.getContext();
		String personId = userContext.getPersonId();
		String loginUserId = userContext.getLoginUserId();// fix bug: 切换校园号导致userId错误， -- zsl 2018.5.17
//		String tenantId = ThreadLocalUserInfo.getContext().getTenantId();

		String icon = ImgUtils.imgUrlsSAddHost(campusMedia.getIcon());
		String authMaterials = ImgUtils.imgUrlsSAddHost(campusMedia.getAuthMaterials());

		campusMedia.setWid(UniqueIdentifierUtil.generateUUID().replaceAll("-", "").toLowerCase());//全部小写
		campusMedia.setIcon(icon);
		campusMedia.setAuthMaterials(authMaterials);

		if (StringUtils.isNotEmpty(campusMedia.getAuthMaterials())) {
			campusMedia.setAuthStatus(MediaAuthStatus.AUTHING.toString());
		} else {
			campusMedia.setAuthStatus(MediaAuthStatus.UN_AUTH.toString());
		}
		campusMedia.setUpdateTime(new Date());
		campusMedia.setCreateUserId(loginUserId);// fix bug: 切换校园号导致userId错误， -- zsl 2018.5.17
//		campusMedia.setTenantId(tenantId);
		campusMedia.setIsDelete("ENABLE");
		campusMedia.setStatus("DISABLE");
		campusMedia.setIsHide("YES");
		campusMedia.setEmail(campusMedia.getWid() + "@cpdaily.com");
		campusMedia.setIsAssociatedWechat("NO");
		campusMedia.setIsAnonymous(0);
		campusMedia.setSortNo(100);
		if(StringUtils.isNotEmpty(campusMedia.getLoginPassword())){
			try {
				campusMedia.setLoginPassword(DesUtil.desEncodeCBC(settings.getDeskey(), campusMedia.getLoginPassword()));
			} catch (Exception e) {
				logger.error("crypto {} failed.", campusMedia.getLoginPassword());
				throw new BadRequestException("crypto fail", "-1");
			}
		}

		campusMediaMapper.insert(campusMedia);

		if (StringUtils.isNotEmpty(personId)) {
			// 创建用户与校园号的关联
			MediaPersonRelation mediaPersonRelation = new MediaPersonRelation();
			mediaPersonRelation.setManageType(MediaPersonManageType.OWNER.toString());
			mediaPersonRelation.setMediaId(campusMedia.getWid());
			mediaPersonRelation.setPersonId(personId);
			mediaPersonRelation.setUserId(loginUserId);// fix bug: 切换校园号导致userId错误， -- zsl 2018.5.17
			mediaPersonRelationMapper.insertSelective(mediaPersonRelation);
		}
	}

	/**
	 * 删除媒体号
	 *
	 * @param wid
	 */
	public void deleteMedia(String wid) {

		CampusMedia campusMedia = campusMediaMapper.selectByPrimaryKey(wid);

		if (campusMedia == null) {
			throw new NotFoundException("media account not found for mediaId : " + wid, "-1");
		}

		if (StringUtils.equals(campusMedia.getAuthStatus(), MediaAuthStatus.AUTHED.toString())) {
			// 已认证的软删除
			CampusMedia record = new CampusMedia();
			record.setWid(campusMedia.getWid());
			record.setIsDelete("DISABLE");

			campusMediaMapper.updateByPrimaryKeySelective(record);
		} else {
			campusMediaMapper.deleteByPrimaryKey(wid);
		}

		// 删除用户与校园号的关联-软删除
		mediaPersonRelationMapper.deleteByColumn("media_id", wid);
		
		//删除身份并踢下线
		deleteUser(wid);
		offLine(wid);
		
	}

	private void deleteUser(String userId) {
		
		cpdailyUserMapper.deleteUser(userId);
		
	}
	 /**
     * 将该身份踢下线
     *
     * @param userId
     */
    private void offLine(String userId) {
        String existToken = "";
        if (StringUtils.isNotBlank(userId)) {
            Object existTokenObj = redisService.leftPop(RedisConstants.REDIS_REGION_CPDAILY_UID_SESSION, userId);
            if(existTokenObj!=null){
            	existToken = existTokenObj.toString();
            }
        }
        if (StringUtils.isNotBlank(existToken)) {
            redisService.delByRegion(RedisConstants.REDIS_REGION_CPDAILY_SESSION, existToken);
        }
    }

	/**
	 * 检查是否达到数量限额
	 *
	 * @param mediaType
	 * @return
	 */
	public boolean checkQualify(String mediaType) {

		String tenantId = ThreadLocalUserInfo.getContext().getTenantId();
		String userType = ThreadLocalUserInfo.getContext().getUserType();
		String userId = ThreadLocalUserInfo.getContext().getLoginUserId();// fix bug: 切换校园号导致userId错误， -- zsl 2018.5.17

		CampusMediaExample example = new CampusMediaExample();
		example.createCriteria().andCreateUserIdEqualTo(userId).andMediaTypeEqualTo(mediaType);
		long existNumber = campusMediaMapper.countByExample(example);

		Integer limitNumber = applyLimitMapper.selectLimitNumber(tenantId, userType, mediaType);

		// 0代表不限制
		if (limitNumber == null || limitNumber == 0) {
			return true;
		}

		if (existNumber >= limitNumber.longValue()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 根据用户ID查询有管理权限的校园号
	 * @param personId
	 * @return
	 */
	public List<CampusMedia> queryMediaListByPersonId(String personId) {

		List<CampusMedia> medias = campusMediaMapper.selectByPersonId(personId);
		if(CollectionUtils.isEmpty(medias)){
			return medias;
		}
		// 过滤禁用租户的校园号
		List<String> tenantIds = medias.stream().map(CampusMedia::getTenantId).collect(Collectors.toList());
		Set<String> enableTenantIds = tenantsService.listByWids(tenantIds)
				.stream().map(TenantsDto::getWid).collect(Collectors.toSet());
		medias = medias.stream().filter(campusMedia -> enableTenantIds.contains(campusMedia.getTenantId())).collect(Collectors.toList());
		return medias;
	}

	/**
	 * 获取粉丝数量
	 * @param list
	 */
	public void getFansCount(List<CampusMedia> list) {
		List<String> mediaIds = new ArrayList<>();
		Map<String,CampusMedia> mediaMap = new HashMap<>();
		for(CampusMedia media : list){
			mediaIds.add(media.getWid());
			mediaMap.put(media.getWid(), media);
		}
		List<MediaFansCount> mediaFansCountList = cpdailyUserMapper.batchSelectFansNumber(mediaIds);
		for(MediaFansCount mediaFansCount : mediaFansCountList){
			CampusMedia media = mediaMap.get(mediaFansCount.getMediaId());
			if(media!=null){
				media.setFansCount(mediaFansCount.getFansCount());
			}
		}
	}

	/**
	 * 获取管理员
	 * @param list
	 */
	public void getManagers(List<CampusMedia> list) {
		List<String> mediaIds = new ArrayList<>();
		Map<String,CampusMedia> mediaMap = new HashMap<>();
		for(CampusMedia media : list) {
			List<MediaManager> managers = mediaAdminService.getMediaManagerList(media.getWid());

			List<String> names = managers.stream().map(manager -> manager.getName()).collect(Collectors.toList());
			media.setManagers(names);
		}
	}
	/**
	 * 检查拥有的校园号有没有达到数量限制
	 * @return
	 */
	public boolean checkMediaCount() {
		String personId = ThreadLocalUserInfo.getContext().getPersonId();
		int count = campusMediaMapper.selectMyMediaCount(personId);
		
		return count<personOwnMediaCountLimit;
	}

	/**
	 * 获取我申请的未认证通过的校园号
	 * @param personId
	 * @return
	 */
	public List<CampusMedia> getMyUnAuthedMediaList(String personId) {
		List<CampusMedia> all = campusMediaMapper.selectMyOwnMedia(personId);
		List<CampusMedia> result = new ArrayList<>(); 
		for(CampusMedia media : all){
			if(!media.getAuthStatus().equals(MediaAuthStatus.AUTHED.toString())){
				// 图标增加前缀
				if (StringUtils.isNotEmpty(media.getIcon())) {
					media.setIcon(ImgUtils.imgUrlsSAddHost(media.getIcon()));
				}
				TenantsDto tenantsDto = tenantsService.getByWid(media.getTenantId());
				media.setTenantName(tenantsDto == null ? "" : tenantsDto.getTenantName());
				result.add(media);
			}
		}
		
		return result;
		
	}

	/**
	 * 校验校园号名称是否存在
	 * @param tenantId
	 * @param name
     * @return
     */
	public boolean mediaNameExist(String tenantId, String name){
		if (StringUtils.isNotEmpty(name)) {

			// 校园号名称 同一学校唯一
			CampusMediaExample example = new CampusMediaExample();
			example.createCriteria().andNameEqualTo(name).andTenantIdEqualTo(tenantId).andIsDeleteEqualTo(MediaIsDeletedStatus.ENABLE.toString());

			List<CampusMedia> list = campusMediaMapper.selectByExample(example);

			if (CollectionUtils.isNotEmpty(list)) {
				return true;
			}
		}

		return false;
	}

}
