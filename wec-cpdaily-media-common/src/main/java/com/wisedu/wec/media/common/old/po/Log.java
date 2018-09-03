package com.wisedu.wec.media.common.old.po;

import java.util.Date;

/**
 * Created by zsl on 2018/4/26.
 */
public class Log {
    private Long wid;
    private String mediaId;
    private String operateType;
    private String operateContent;
    private String operateTime;
    private boolean operateResult;
    private String operatorId;


    private String operatorName;

    public Long getWid() {
        return wid;
    }

    public void setWid(Long wid) {
        this.wid = wid;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getOperateContent() {
        return operateContent;
    }

    public void setOperateContent(String operateContent) {
        this.operateContent = operateContent;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public boolean isOperateResult() {
        return operateResult;
    }

    public void setOperateResult(boolean operateResult) {
        this.operateResult = operateResult;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    @Override
    public String toString() {
        return "Log{" +
                "wid=" + wid +
                ", mediaId='" + mediaId + '\'' +
                ", mediaType='" + operateType + '\'' +
                ", mediaContent='" + operateContent + '\'' +
                ", operateTime=" + operateTime +
                ", operateResult=" + operateResult +
                ", operatorId='" + operatorId + '\'' +
                ", operatorName='" + operatorName + '\'' +
                '}';
    }
}
