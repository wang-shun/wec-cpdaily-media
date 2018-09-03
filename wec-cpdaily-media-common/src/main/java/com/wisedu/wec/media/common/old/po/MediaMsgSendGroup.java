package com.wisedu.wec.media.common.old.po;

public class MediaMsgSendGroup extends MediaMsgSendGroupKey {
    private String groupName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }
}