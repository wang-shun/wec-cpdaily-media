package com.wisedu.wec.media.common.old.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by croyson on 2017/10/23.
 * success:Boolean,id:String,name:String,fileUrl:String
 */
public class MediaSendMsgReqVo implements Serializable {

    private static final long serialVersionUID = -4297782902417401666L;

    /* 消息编号 添加的时候为空 */
    private String msgId;
    /* 排除用户 */
    private String notSendUserIds;
    /* 发布用户组 */
    private List<SendUserGroupVo> sendUserGroups;
    /* 发布用户 */
    private List<SendUserVo> sendUsers;
    /*标题*/
    private String title;
    /*内容*/
    private String content;
    /*简介*/
    private String summary;
    /*附件地址*/
    private String attachments;
    /*封面图*/
    private String img;
    /*原文链接*/
    private String originalLink;

    /*操作类型 0 保存 1 保存并发送*/
    private String opType;



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


    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

	public List<SendUserVo> getSendUsers() {
		return sendUsers;
	}

	public void setSendUsers(List<SendUserVo> sendUsers) {
		this.sendUsers = sendUsers;
	}
}
