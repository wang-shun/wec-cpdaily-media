package com.wisedu.wec.media.common.old.vo;

import java.io.Serializable;

public class SendUserGroupVo implements Serializable {

    /*组编号*/
    private String groupId;
    /*组名称*/
    private String groupName;
    /*组类型 0 标签  1 院系专业 2 部门*/
    private String groupType;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }
}
