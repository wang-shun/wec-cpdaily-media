package com.wisedu.wec.media.common.old.vo;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * Created by dyf on 17/11/1.
 */
public class CampusMediaVo {

    private String wid;

    private String icon;

    private String qrcodeUrl;

    private String name;

    private String description;

    private String mediaType;

    private String authStatus;

    private String tenantId;

    private boolean canSeeOrgStructure;//是否可以看到组织架构树
    /**
     * 校园号版本（old，new）
     */
    private String version;

    /**
     * 校园号是否可升级，0不可以，1可以
     */
    private Boolean canUpgrade;

    /**
     * 最后阅读留言时间
     */
    private Date lastReadCommentTime;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public boolean isCanSeeOrgStructure() {
        return canSeeOrgStructure;
    }

    public void setCanSeeOrgStructure(boolean canSeeOrgStructure) {
        this.canSeeOrgStructure = canSeeOrgStructure;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean getCanUpgrade() {
        return canUpgrade;
    }

    public void setCanUpgrade(Boolean canUpgrade) {
        this.canUpgrade = canUpgrade;
    }
}
