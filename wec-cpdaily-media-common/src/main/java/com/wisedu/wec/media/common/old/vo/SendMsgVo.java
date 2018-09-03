package com.wisedu.wec.media.common.old.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class SendMsgVo implements Serializable {
    private static final long serialVersionUID = -1431794880061522258L;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    /*消息编号*/
    private String msgId;
    /*屏蔽人的编号*/
    private String notSendUserIds;
    /*屏蔽人的名称*/
    private String notSendUserNames;
    /*发送用户组*/
    private List<SendUserGroupVo> sendUserGroups;
    /**
     * 需要发送的人员列表
     * */
    private List<SendUserVo> sendUsers;
    /*标题*/
    private String title;
    /*内容*/
    private String content;
    /*摘要*/
    private String summary;
    /*附件*/
    private String attachments;
    /*封面图*/
    private String img;
    /*原文链接*/
    private String originalLink;
    /*媒体号编号*/
    private String mediaId;
    /*租户编号*/
    private String tenantId;
    /*本地地址*/
    private String localUrl;

    private String status;
    
    private Date cTime;

    private String cUserId;

    private String cUserName;

    private Date uTime;

    private Integer sendCount;//总共需要发送的数量
    
    private Integer sendOverCount;//已发送数量，发送失败的也算已发送

    private Integer receiveCount;

    private Integer readCount;

    private Date lastNotifyTime;

    private String lastNotifyTimeFmt;

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getcTime() {
		return cTime==null?"":sdf.format(cTime);
	}

	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}

	public Date getuTime() {
		return uTime;
	}

	public void setuTime(Date uTime) {
		this.uTime = uTime;
	}

	public Integer getSendCount() {
		return sendCount;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}

	public Integer getSendOverCount() {
		return sendOverCount;
	}

	public void setSendOverCount(Integer sendOverCount) {
		this.sendOverCount = sendOverCount;
	}

	public Integer getReceiveCount() {
		return receiveCount;
	}

	public void setReceiveCount(Integer receiveCount) {
		this.receiveCount = receiveCount;
	}

	public Integer getReadCount() {
		return readCount;
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getNotSendUserIds() {
        return notSendUserIds;
    }

    public void setNotSendUserIds(String notSendUserIds) {
        this.notSendUserIds = notSendUserIds;
    }

    public String getNotSendUserNames() {
        return notSendUserNames;
    }

    public void setNotSendUserNames(String notSendUserNames) {
        this.notSendUserNames = notSendUserNames;
    }

    public List<SendUserGroupVo> getSendUserGroups() {
        return sendUserGroups;
    }

    public void setSendUserGroups(List<SendUserGroupVo> sendUserGroups) {
        this.sendUserGroups = sendUserGroups;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getOriginalLink() {
        return originalLink;
    }

    public void setOriginalLink(String originalLink) {
        this.originalLink = originalLink;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

	public List<SendUserVo> getSendUsers() {
		return sendUsers;
	}

	public void setSendUsers(List<SendUserVo> sendUsers) {
		this.sendUsers = sendUsers;
	}

    public String getcUserId() {
        return cUserId;
    }

    public void setcUserId(String cUserId) {
        this.cUserId = cUserId;
    }

    public String getcUserName() {
        return cUserName;
    }

    public void setcUserName(String cUserName) {
        this.cUserName = cUserName;
    }

    public Date getLastNotifyTime() {
        return lastNotifyTime;
    }

    public void setLastNotifyTime(Date lastNotifyTime) {
        this.lastNotifyTime = lastNotifyTime;
    }

    public String getLastNotifyTimeFmt() {
        return lastNotifyTimeFmt;
    }

    public void setLastNotifyTimeFmt(String lastNotifyTimeFmt) {
        this.lastNotifyTimeFmt = lastNotifyTimeFmt;
    }
}
