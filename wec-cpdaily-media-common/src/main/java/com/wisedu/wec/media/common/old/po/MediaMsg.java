package com.wisedu.wec.media.common.old.po;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.wisedu.wec.media.common.old.vo.SendUserVo;

public class MediaMsg implements Serializable {

    private static final long serialVersionUID = -7399646240411536151L;

    private String wid;

    private String notSendUserIds;

    private String title;

    private String summary;

    private String attachments;

    private String img;

    private String originalLink;

    private String localUrl;

    private String mediaId;

    private String tenantId;

    private String cUserId;

    private Date cTime;

    private Date uTime;

    private Integer sendCount;//总共需要发送的数量
    
    private Integer sendOverCount;//已发送数量，发送失败的也算已发送

    private Integer receiveCount;

    private Integer readCount;

    private String status;

    private String content;

    private Integer isDelete;

    private Date lastNotifyTime;

    private String lastNotifyTimeFmt;

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }


    /**
    * 关联用户组
    * */
    private List<MediaMsgSendGroup> groups;

    /**
     * 需要发送的人员列表
     * */
    private List<SendUserVo> sendUsers;
    /**
     * 最终需要发送的所有人的ID列表，包括组员和单独选的人
     */
    private Set<String> sendTotalUserIds;


    public List<MediaMsgSendGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<MediaMsgSendGroup> groups) {
        this.groups = groups;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid == null ? null : wid.trim();
    }

    public String getNotSendUserIds() {
        return notSendUserIds;
    }

    public void setNotSendUserIds(String notSendUserIds) {
        this.notSendUserIds = notSendUserIds == null ? null : notSendUserIds.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments == null ? null : attachments.trim();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public String getOriginalLink() {
        return originalLink;
    }

    public void setOriginalLink(String originalLink) {
        this.originalLink = originalLink == null ? null : originalLink.trim();
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl == null ? null : localUrl.trim();
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId == null ? null : mediaId.trim();
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId == null ? null : tenantId.trim();
    }

    public String getcUserId() {
        return cUserId;
    }

    public void setcUserId(String cUserId) {
        this.cUserId = cUserId == null ? null : cUserId.trim();
    }

    public Date getcTime() {
        if (cTime == null) {
            return new Date();
        } else {
            return cTime;
        }
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public Date getuTime() {
        if (uTime == null) {
            return new Date();
        } else {
            return uTime;
        }
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public List<SendUserVo> getSendUsers() {
		return sendUsers;
	}

	public void setSendUsers(List<SendUserVo> sendUsers) {
		this.sendUsers = sendUsers;
	}

	public Set<String> getSendTotalUserIds() {
		return sendTotalUserIds;
	}

	public void setSendTotalUserIds(Set<String> sendTotalUserIds) {
		this.sendTotalUserIds = sendTotalUserIds;
	}

	public Integer getSendOverCount() {
		return sendOverCount;
	}

	public void setSendOverCount(Integer sendOverCount) {
		this.sendOverCount = sendOverCount;
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