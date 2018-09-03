package com.wisedu.wec.media.common.old.vo;

import java.io.Serializable;


public class ImMediaMsg implements Serializable {


    private static final long serialVersionUID = 7695402633031667828L;

    private String msgId;

    private String title;

    private String summary;

    private String attachments;

    private String img;

    private String originalLink;

    private String localUrl;

    private String readUrl;

    public String getReadUrl() {
        return readUrl;
    }

    public void setReadUrl(String readUrl) {
        this.readUrl = readUrl;
    }

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

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }
}
