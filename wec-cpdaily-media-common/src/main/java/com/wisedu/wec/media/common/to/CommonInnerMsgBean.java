package com.wisedu.wec.media.common.to;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by zsl on 2018/4/20.
 */
public class CommonInnerMsgBean {

    private String title;
    private String content;
    private String linkUrl;
    private String readUrl;

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

    public String getReadUrl() {
        return readUrl;
    }

    public void setReadUrl(String readUrl) {
        this.readUrl = readUrl;
    }
}
