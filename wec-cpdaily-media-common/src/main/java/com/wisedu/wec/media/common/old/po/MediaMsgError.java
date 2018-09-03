package com.wisedu.wec.media.common.old.po;

import java.io.Serializable;
import java.util.Date;

public class MediaMsgError extends MediaMsgErrorKey implements Serializable{
    private static final long serialVersionUID = 4055481579406965555L;

    private Date cTime;

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public MediaMsgError(String msgId, String userId) {
        super(msgId, userId);
    }

    public MediaMsgError() {
    }

    public MediaMsgError(String msgId, String userId, Date cTime) {
        super(msgId, userId);
        this.cTime = cTime;
    }
}