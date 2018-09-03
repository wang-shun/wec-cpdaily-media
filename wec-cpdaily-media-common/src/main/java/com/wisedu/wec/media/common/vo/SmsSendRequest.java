package com.wisedu.wec.media.common.vo;

import java.util.List;

/**
 * Created by zsl on 2018/6/12.
 */
public class SmsSendRequest {
    private String schoolCode;
    private String appId;
    private String content;
    private int sendType;
    private boolean sendNow;
    private int tagId;
    private List<SmsReceiverBean> receivers;

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSendType() {
        return sendType;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }

    public boolean isSendNow() {
        return sendNow;
    }

    public void setSendNow(boolean sendNow) {
        this.sendNow = sendNow;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public List<SmsReceiverBean> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<SmsReceiverBean> receivers) {
        this.receivers = receivers;
    }
}
