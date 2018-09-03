package com.wisedu.wec.media.biz.service;

import com.wisedu.wec.media.common.vo.GroupMembersRequestBean;
import com.wisedu.wec.media.common.vo.CreateGroupFromSelectedRequestBean;
import com.wisedu.wec.media.common.vo.DeleteGroupMembersRequestBean;
import com.wisedu.wec.media.common.vo.UpdateGroupNameRequestBean;
import com.wisedu.wecloud.commons.model.ResponseResult;



public interface GroupService {

    public ResponseResult<?> addGroupMembers(GroupMembersRequestBean groupMembers);

    public ResponseResult<?> createGroupFromSelected(CreateGroupFromSelectedRequestBean groupMembers);

    public ResponseResult<?> deleteGroupMembers(DeleteGroupMembersRequestBean groupMembers);

    public ResponseResult<?> updateGroupName(UpdateGroupNameRequestBean groupName);

}
