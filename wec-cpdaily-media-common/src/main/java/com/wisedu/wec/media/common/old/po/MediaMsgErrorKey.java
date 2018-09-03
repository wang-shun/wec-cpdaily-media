package com.wisedu.wec.media.common.old.po;

public class MediaMsgErrorKey {
    private String msgId;

    private String userId;

    public MediaMsgErrorKey(String msgId, String userId) {
        this.msgId = msgId;
        this.userId = userId;
    }

    public MediaMsgErrorKey() {
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId == null ? null : msgId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }
}