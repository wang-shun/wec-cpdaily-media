package com.wisedu.wec.media.common.old.vo;

import java.io.Serializable;


public class SendMsgItemVo implements Serializable{

    private static final long serialVersionUID = 4363349608998845200L;

    private String msgId;
    private String title;
    private String cTime;
    private String sendUserGroup;
    private String status;
    private int receiveCount;
    private int readCount;
    private int sendCount;
    private int sendErrorCount;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getcTime() {
        return cTime;
    }

    public void setcTime(String cTime) {
        this.cTime = cTime;
    }

    public String getSendUserGroup() {
        return sendUserGroup;
    }

    public void setSendUserGroup(String sendUserGroup) {
        this.sendUserGroup = sendUserGroup;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getReceiveCount() {
        return receiveCount;
    }

    public void setReceiveCount(int receiveCount) {
        this.receiveCount = receiveCount;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public int getSendCount() {
        return sendCount;
    }

    public void setSendCount(int sendCount) {
        this.sendCount = sendCount;
    }

    public int getSendErrorCount() {
        return sendErrorCount;
    }

    public void setSendErrorCount(int sendErrorCount) {
        this.sendErrorCount = sendErrorCount;
    }
}
