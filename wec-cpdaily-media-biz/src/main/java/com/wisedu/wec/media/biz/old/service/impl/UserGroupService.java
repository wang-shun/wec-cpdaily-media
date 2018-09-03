package com.wisedu.wec.media.biz.old.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.wisedu.wec.media.common.old.constants.MediaPlatformCode;
import com.wisedu.wec.media.common.old.context.MediaInfoContext;
import com.wisedu.wec.media.common.old.context.ThreadLocalUserInfo;
import com.wisedu.wec.media.common.old.context.UserInfoContext;
import com.wisedu.wec.media.common.old.exception.BadRequestException;
import com.wisedu.wec.media.common.old.po.CpdailyUser;
import com.wisedu.wec.media.common.old.po.UserGroup;
import com.wisedu.wec.media.common.old.po.UserGroupRelation;
import com.wisedu.wec.media.common.old.po.UserGroupRelationExample;
import com.wisedu.wec.media.common.old.vo.PageVo;
import com.wisedu.wec.media.common.old.vo.UserGroupVo;
import com.wisedu.wec.media.common.old.vo.UserWithGroup;
import com.wisedu.wec.media.dal.mybatis.CpdailyUserMapper;
import com.wisedu.wec.media.dal.mybatis.UserGroupMapper;
import com.wisedu.wec.media.dal.mybatis.UserGroupRelationMapper;

import ma.glasnost.orika.MapperFacade;

@Service
public class UserGroupService {

	private static final Logger logger = LoggerFactory.getLogger(UserGroupService.class);

	@Autowired
	private UserGroupMapper userGroupMapper;

	@Autowired
	private UserGroupRelationMapper userGroupRelationMapper;

	@Autowired
	private CpdailyUserMapper cpdailyUserMapper;
	
	@Autowired
	private MapperFacade mapper;

	public void delGroupById(String groupId) {

		// 删除用户组
		userGroupMapper.deleteByPrimaryKey(groupId);

		// 删除用户组保存的用户
		UserGroupRelationExample example = new UserGroupRelationExample();
		example.createCriteria().andGroupIdEqualTo(groupId);
		userGroupRelationMapper.deleteByExample(example);

	}

	/**
	 * 新增用户组名称
	 *
	 * @param groupName
	 */
	public String addGroupByName(String groupName) {

		String mediaId = ThreadLocalUserInfo.getContext().getMediaId();
		String ownerId = ThreadLocalUserInfo.getContext().getLoginUserId();// fix bug: 切换校园号导致userId错误， -- zsl 2018.5.17
		String tenantId = ThreadLocalUserInfo.getContext().getTenantId();

		// 判断名字是否重复
		UserGroup group = userGroupMapper.selectByGroupName(mediaId, groupName);

		if (group != null) {
			throw new BadRequestException("用户组名称已存在", MediaPlatformCode.PARAM_ILLEGAL);
		}

		UserGroup record = new UserGroup();
		String groupId = UUID.randomUUID().toString();
		record.setGroupId(groupId);
		record.setGroupName(groupName);
		record.setMediaId(mediaId);
		record.setOwnerId(ownerId);
		record.setTenantId(tenantId);
		record.setUserCount(0);
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
		userGroupMapper.insert(record);

		return groupId;
	}

	/**
	 * 给用户打用户组
	 *
	 * @param groupIds
	 * @param userIds
	 */
	public void brandGroupSingle(List<String> groupIds, String userId) {

		String mediaId = ThreadLocalUserInfo.getContext().getMediaId();

		UserGroupRelationExample example = new UserGroupRelationExample();
		example.createCriteria().andUserIdEqualTo(userId).andMediaIdEqualTo(mediaId);

		userGroupRelationMapper.deleteByExample(example);

		if (CollectionUtils.isEmpty(groupIds)) {
			return;
		}

		List<UserGroupRelation> records = new ArrayList<>();

		CpdailyUser cpdailyUser = cpdailyUserMapper.selectByUserId(userId);

		if (cpdailyUser != null) {
			for (String groupId : groupIds) {

				UserGroupRelation record = new UserGroupRelation();
				record.setGroupId(groupId);
				record.setUserId(cpdailyUser.getWid());
				record.setUserName(cpdailyUser.getName());
				record.setImgUrl(cpdailyUser.getImg());
				record.setMediaId(mediaId);
				record.setCreateTime(new Date());

				records.add(record);
			}
		}

		if (CollectionUtils.isNotEmpty(records)) {
			userGroupRelationMapper.batchInsert(records);
		}
	}

	/**
	 * 给用户打用户组
	 *
	 * @param groupIds
	 * @param userIds
	 */
	public void brandGroup(List<String> groupIds, List<String> userIds) {

		String mediaId = ThreadLocalUserInfo.getContext().getMediaId();

		List<UserGroupRelation> all = userGroupRelationMapper.selectByMediaId(mediaId);

		List<UserWithGroup> users = cpdailyUserMapper.selectByUserIds4Group(userIds);

		List<UserGroupRelation> records = new ArrayList<>();

		Multimap<String, String> multiGroupMap = HashMultimap.create();
		for (UserGroupRelation one : all) {
			if (groupIds.contains(one.getGroupId())) {
				multiGroupMap.put(one.getGroupId(), one.getUserId());
			}
		}

		for (String groupId : groupIds) {
			if (multiGroupMap.keySet().contains(groupId)) {

				for (UserWithGroup user : users) {
					if (!multiGroupMap.get(groupId).contains(user.getUserId())) {
						UserGroupRelation record = new UserGroupRelation();
						record.setGroupId(groupId);
						record.setUserId(user.getUserId());
						record.setUserName(user.getUserName());
						record.setImgUrl(user.getImgUrl());
						record.setMediaId(mediaId);
						record.setCreateTime(new Date());

						records.add(record);
					}
				}

			} else {

				for (UserWithGroup user : users) {
					UserGroupRelation record = new UserGroupRelation();
					record.setGroupId(groupId);
					record.setUserId(user.getUserId());
					record.setUserName(user.getUserName());
					record.setImgUrl(user.getImgUrl());
					record.setMediaId(mediaId);
					record.setCreateTime(new Date());

					records.add(record);
				}

			}
		}

		if (CollectionUtils.isNotEmpty(records)) {
			userGroupRelationMapper.batchInsert(records);
		}
	}

	public List<UserGroupVo> getMyGroups(String groupName) {
		UserInfoContext context = ThreadLocalUserInfo.getContext();
		String tenantId = context.getTenantId();
		String mediaId = context.getMediaId();
		List<UserGroup> list = userGroupMapper.selectMyGroups(tenantId, mediaId, groupName);
		List<UserGroupVo> groupList = mapper.mapAsList(list, UserGroupVo.class);
		return groupList;
	}


	public PageVo<UserGroupVo> getMyGroupsPaged(String groupName, Integer pageNumber, Integer pageSize) {
		UserInfoContext context = ThreadLocalUserInfo.getContext();
		String mediaId = context.getMediaId();
		MediaInfoContext mediaInfoContext = ThreadLocalUserInfo.getContext().getMediaMap().get(mediaId);
		String tenantId = mediaInfoContext.getTenantId();
		if(pageNumber==null){
			pageNumber = 1;
		}
		if(pageSize==null){
			pageSize = 0;
		}
		Page<Object> page = null;
		if(pageNumber>0 && pageSize>0){
			PageHelper.startPage(pageNumber, pageSize);
		}
		
		List<UserGroup> list = userGroupMapper.selectMyGroups(tenantId, mediaId, groupName);
		List<UserGroupVo> groupList = mapper.mapAsList(list, UserGroupVo.class);
		
		PageVo<UserGroupVo> pageVo = new PageVo<>();
		pageVo.setPageNumber(pageNumber);
		pageVo.setPageSize(pageSize);
		pageVo.setRows(groupList);
		pageVo.setTotalSize(page==null?groupList.size(): Long.valueOf(page.getTotal()).intValue());
		return pageVo;
	}
}
