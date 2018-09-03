package com.wisedu.wec.media.biz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wisedu.wec.media.biz.old.service.impl.UserTreeService;
import com.wisedu.wec.media.common.old.constants.MediaPlatformCode;
import com.wisedu.wec.media.common.old.context.MediaInfoContext;
import com.wisedu.wec.media.common.old.context.ThreadLocalUserInfo;
import com.wisedu.wec.media.common.old.exception.BadRequestException;
import com.wisedu.wec.media.common.old.po.CpdailyUser;
import com.wisedu.wec.media.common.old.po.UserGroup;
import com.wisedu.wec.media.common.old.po.UserGroupExample;
import com.wisedu.wec.media.common.old.po.UserGroupRelation;
import com.wisedu.wec.media.common.old.po.UserGroupRelationExample;
import com.wisedu.wec.media.common.old.vo.PageVo;
import com.wisedu.wec.media.common.old.vo.UserWithGroup;
import com.wisedu.wec.media.dal.mybatis.CpdailyUserMapper;
import com.wisedu.wec.media.dal.mybatis.UserGroupMapper;
import com.wisedu.wec.media.dal.mybatis.UserGroupRelationMapper;
import com.wisedu.wec.media.common.vo.CreateGroupFromSelectedRequestBean;
import com.wisedu.wec.media.common.vo.DeleteGroupMembersRequestBean;
import com.wisedu.wec.media.common.vo.GroupBean;
import com.wisedu.wec.media.common.vo.GroupMembersRequestBean;
import com.wisedu.wec.media.common.vo.UpdateGroupNameRequestBean;
import com.wisedu.wec.media.biz.service.GroupService;
import com.wisedu.wecloud.commons.model.ResponseResult;

@Service
public class GroupServiceImpl implements GroupService{

	@Autowired
	private UserGroupMapper userGroupMapper;

	@Autowired
	private UserGroupRelationMapper userGroupRelationMapper;

	@Autowired
	private CpdailyUserMapper cpdailyUserMapper;
	
	@Autowired
	private UserTreeService userTreeService;
	
	/**
	 * 将用户和用户组保存为另外一个用户组
	 */
	@Transactional
	@Override
	public ResponseResult<?> createGroupFromSelected(CreateGroupFromSelectedRequestBean groupMembers) {
		String mediaId = ThreadLocalUserInfo.getContext().getMediaId();
		MediaInfoContext mediaInfo = ThreadLocalUserInfo.getContext().getMediaMap().get(mediaId);
		String ownerId = mediaInfo.getMediaId();
		String tenantId = mediaInfo.getTenantId();
		
		String groupName = groupMembers.getGroupName();

		// 判断名字是否重复
		UserGroup group = userGroupMapper.selectByGroupName(mediaId, groupName);

		if (group != null) {
			throw new BadRequestException("用户组名称已存在", MediaPlatformCode.PARAM_ILLEGAL);
		}

		String groupId = createGroup(tenantId, mediaId, ownerId, groupName);
		
		List<UserGroupRelation> records = new ArrayList<>();//总的要增加的组员
		Set<String> userIds = new HashSet<>();//用于去重
		
		//先处理单独选择的用户
		List<String> selectedUserIds = groupMembers.getUserIds();
		if(!CollectionUtils.isEmpty(selectedUserIds)){
			List<CpdailyUser> selectedUsers = cpdailyUserMapper.selectByUserIds(selectedUserIds);
			addSelectedUsers(groupId, mediaId,records, userIds, selectedUsers);
		}
		
		//再处理选择的组
		List<GroupBean> selectedGroups = groupMembers.getGroups();
		
		if(!CollectionUtils.isEmpty(selectedGroups)){
			addSelectedGroupMembers(groupId, mediaId,records, userIds, selectedGroups);
		}
		
		if(!CollectionUtils.isEmpty(records)){
			int subSize = 1000;
			while(records.size()>subSize){
				List<UserGroupRelation> part = records.subList(0, subSize);
				userGroupRelationMapper.batchInsert(part);
				records.subList(0, subSize).clear();
			}

			if(!records.isEmpty()){
				userGroupRelationMapper.batchInsert(records);
			}
			
		}
		
		return ResponseResult.success(groupId);
	}

	/**
	 * 将选择的组的组员组装成插入的对象，注意去重
	 * @param groupId
	 * @param mediaId
	 * @param records
	 * @param userIds
	 * @param selectedGroups
	 */
	private void addSelectedGroupMembers(String groupId, String mediaId, List<UserGroupRelation> records, Set<String> userIds, List<GroupBean> selectedGroups) {
		
		for(GroupBean g : selectedGroups){
			PageVo<UserWithGroup> groupMembers = userTreeService.queryUsersByGroupId(g.getGroupId(), 0, 0, g.getGroupType());
			
			if(CollectionUtils.isNotEmpty(groupMembers.getRows())){
				for(UserWithGroup user : groupMembers.getRows()){
					if(userIds.contains(user.getUserId())){
						continue;
					}
					UserGroupRelation record = new UserGroupRelation();
					record.setCreateTime(new Date());
					record.setGroupId(groupId);
					record.setImgUrl(user.getImgUrl());
					record.setMediaId(mediaId);
					record.setUserId(user.getUserId());
					record.setUserName(user.getUserName());
					records.add(record);
					userIds.add(user.getUserId());
				}
			}
			
		}
		
	}


	/**
	 * 将选择的人组装成插入的对象，注意去重
	 * @param records
	 * @param userIds
	 * @param selectedUsers
	 */
	private void addSelectedUsers(String groupId, String mediaId, List<UserGroupRelation> records, Set<String> userIds, List<CpdailyUser> selectedUsers) {
		for(CpdailyUser user : selectedUsers){
			if(userIds.contains(user.getWid())){
				continue;
			}
			UserGroupRelation record = new UserGroupRelation();
			record.setCreateTime(new Date());
			record.setGroupId(groupId);
			record.setImgUrl(user.getImg());
			record.setMediaId(mediaId);
			record.setUserId(user.getWid());
			record.setUserName(user.getName());
			records.add(record);
			userIds.add(user.getWid());
		}
		
	}


	private String createGroup(String tenantId, String mediaId, String ownerId, String groupName) {
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

	@Override
	public ResponseResult<?> addGroupMembers(GroupMembersRequestBean groupMembers) {
		String mediaId = ThreadLocalUserInfo.getContext().getMediaId();
		String groupId = groupMembers.getGroupId();
		
		List<UserGroupRelation> records = new ArrayList<>();//总的要增加的组员
		Set<String> userIds = new HashSet<>();//用于去重
		//排除已经是组员的用户
		List<UserWithGroup> existsMembers =	userGroupRelationMapper.selectByGroupId(groupId);
		if(CollectionUtils.isNotEmpty(existsMembers)){
			for(UserWithGroup userWithGroup : existsMembers){
				userIds.add(userWithGroup.getUserId());
			}
		}
		
		//先处理单独选择的用户
		List<String> selectedUserIds = groupMembers.getUserIds();
		if(CollectionUtils.isNotEmpty(selectedUserIds)){
			List<CpdailyUser> selectedUsers = cpdailyUserMapper.selectByUserIds(selectedUserIds);
			addSelectedUsers(groupId, mediaId,records, userIds, selectedUsers);
		}
		
		//再处理选择的组
		List<GroupBean> selectedGroups = groupMembers.getGroups();
		
		if(!CollectionUtils.isEmpty(selectedGroups)){
			addSelectedGroupMembers(groupId, mediaId,records, userIds, selectedGroups);
		}
		
		if(!CollectionUtils.isEmpty(records)){
			userGroupRelationMapper.batchInsert(records);
		}
		
		return ResponseResult.success();
	}

	@Override
	public ResponseResult<?> deleteGroupMembers(DeleteGroupMembersRequestBean groupMembers) {
		UserGroupRelationExample example = new UserGroupRelationExample();
		example.createCriteria().andGroupIdEqualTo(groupMembers.getGroupId()).andUserIdIn(groupMembers.getUserIds());
		userGroupRelationMapper.deleteByExample(example);
		return ResponseResult.success();
	}

	@Override
	public ResponseResult<?> updateGroupName(UpdateGroupNameRequestBean groupNameReq) {
		String mediaId = ThreadLocalUserInfo.getContext().getMediaId();
		String tenantId = ThreadLocalUserInfo.getContext().getTenantId();
		String groupId = groupNameReq.getGroupId();
		String groupName = groupNameReq.getGroupName();

		// 判断名字是否重复
		UserGroup group = userGroupMapper.selectByGroupName(mediaId, groupName);

		if (group != null && !group.getGroupId().equals(groupId)) {
			throw new BadRequestException("用户组名称已存在", MediaPlatformCode.PARAM_ILLEGAL);
		}
		
		UserGroup record = new UserGroup();
		record.setGroupName(groupName);
		UserGroupExample example = new UserGroupExample();
		example.createCriteria().andGroupIdEqualTo(groupId).andTenantIdEqualTo(tenantId).andMediaIdEqualTo(mediaId);
		int i = userGroupMapper.updateByExampleSelective(record, example);
		
		if(i>0){
			return ResponseResult.success();
		}
		return ResponseResult.failure("更新组名失败，用户组不存在");
	}

}
