package com.wisedu.wec.media.common.to;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by zsl on 2018/4/20.
 */
public class AttachInnerMsgBean {

    private String title;
    private String summary;
    private String img;
    private String linkUrl;
    private String[] attachments;
    private String readUrl;

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        if (StringUtils.isEmpty(linkUrl)) {
            this.linkUrl = "";
        } else {
            this.linkUrl = linkUrl;
        }
    }

    public String[] getAttachments() {
        return attachments;
    }

    public void setAttachments(String[] attachments) {
        this.attachments = attachments;
    }

    public String getReadUrl() {
        return readUrl;
    }

    public void setReadUrl(String readUrl) {
        this.readUrl = readUrl;
    }
}
