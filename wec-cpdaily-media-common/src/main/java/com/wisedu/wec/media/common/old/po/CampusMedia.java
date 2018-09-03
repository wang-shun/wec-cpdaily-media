package com.wisedu.wec.media.common.old.po;

import java.util.Date;
import java.util.List;

public class CampusMedia {
  private String wid;

  private String icon;

  private String backgroundImg;

  private String name;

  private String tenantId;
  
  private String tenantName;

  private String mediaType;

  private Date updateTime;

  private Date createTime;

  private String createUserId;

  private Integer sortNo;

  private String isDelete;

  private String status;

  private String loginPassword;

  private String email;

  private String isHide;

  private String isAssociatedWechat;

  private String wechatId;

  private String authStatus;

  private String authMaterials;

  private Integer isAnonymous;

  private String description;

  private String code;

  private int smsRemain;

  private int smsTotal;

  private boolean enableSmsNotification;

  /**
   * 最后阅读留言时间
   */
  private Date lastReadCommentTime;
  /**
   * 校园号版本（old，new）
   */
  private String version;

  /**
   * 校园号是否可升级，0不可以，1可以
   */
  private Boolean canUpgrade;


  private List<String> managers;

  private String ownerName;

  private boolean canSeeOrgStructure;//是否可以看到组织架构树

  private String qrcodeUrl;

  private int fansCount;//关注人数

  private String reviewOpinion;//审核意见


  public String getManageType() {
    return manageType;
  }

  public void setManageType(String manageType) {
    this.manageType = manageType;
  }

  private String manageType;

  public List<String> getManagers() {
    return managers;
  }

  public void setManagers(List<String> managers) {
    this.managers = managers;
  }

  public String getOwnerName() {
    return ownerName;
  }

  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }

  public String getWid() {
    return wid;
  }

  public void setWid(String wid) {
    this.wid = wid == null ? null : wid.trim();
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon == null ? null : icon.trim();
  }

  public String getBackgroundImg() {
    return backgroundImg;
  }

  public void setBackgroundImg(String backgroundImg) {
    this.backgroundImg = backgroundImg == null ? null : backgroundImg.trim();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name == null ? null : name.trim();
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId == null ? null : tenantId.trim();
  }

  public String getMediaType() {
    return mediaType;
  }

  public void setMediaType(String mediaType) {
    this.mediaType = mediaType == null ? null : mediaType.trim();
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public String getCreateUserId() {
    return createUserId;
  }

  public void setCreateUserId(String createUserId) {
    this.createUserId = createUserId == null ? null : createUserId.trim();
  }

  public Integer getSortNo() {
    return sortNo;
  }

  public void setSortNo(Integer sortNo) {
    this.sortNo = sortNo;
  }

  public String getIsDelete() {
    return isDelete;
  }

  public void setIsDelete(String isDelete) {
    this.isDelete = isDelete == null ? null : isDelete.trim();
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status == null ? null : status.trim();
  }

  public String getLoginPassword() {
    return loginPassword;
  }

  public void setLoginPassword(String loginPassword) {
    this.loginPassword = loginPassword == null ? null : loginPassword.trim();
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email == null ? null : email.trim();
  }

  public String getIsHide() {
    return isHide;
  }

  public void setIsHide(String isHide) {
    this.isHide = isHide == null ? null : isHide.trim();
  }

  public String getIsAssociatedWechat() {
    return isAssociatedWechat;
  }

  public void setIsAssociatedWechat(String isAssociatedWechat) {
    this.isAssociatedWechat = isAssociatedWechat == null ? null : isAssociatedWechat.trim();
  }

  public String getWechatId() {
    return wechatId;
  }

  public void setWechatId(String wechatId) {
    this.wechatId = wechatId == null ? null : wechatId.trim();
  }

  public String getAuthStatus() {
    return authStatus;
  }

  public void setAuthStatus(String authStatus) {
    this.authStatus = authStatus == null ? null : authStatus.trim();
  }

  public String getAuthMaterials() {
    return authMaterials;
  }

  public void setAuthMaterials(String authMaterials) {
    this.authMaterials = authMaterials == null ? null : authMaterials.trim();
  }

  public Integer getIsAnonymous() {
    return isAnonymous;
  }

  public void setIsAnonymous(Integer isAnonymous) {
    this.isAnonymous = isAnonymous;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description == null ? null : description.trim();
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

public int getFansCount() {
	return fansCount;
}

public void setFansCount(int fansCount) {
	this.fansCount = fansCount;
}

public boolean isCanSeeOrgStructure() {
	return canSeeOrgStructure;
}

public void setCanSeeOrgStructure(boolean canSeeOrgStructure) {
	this.canSeeOrgStructure = canSeeOrgStructure;
}

public String getQrcodeUrl() {
	return qrcodeUrl;
}

public void setQrcodeUrl(String qrcodeUrl) {
	this.qrcodeUrl = qrcodeUrl;
}

public Date getCreateTime() {
	return createTime;
}

public void setCreateTime(Date createTime) {
	this.createTime = createTime;
}

public String getTenantName() {
	return tenantName;
}

public void setTenantName(String tenantName) {
	this.tenantName = tenantName;
}

public String getReviewOpinion() {
	return reviewOpinion;
}

public void setReviewOpinion(String reviewOpinion) {
	this.reviewOpinion = reviewOpinion;
}

  public int getSmsRemain() {
    return smsRemain;
  }

  public void setSmsRemain(int smsRemain) {
    this.smsRemain = smsRemain;
  }

  public int getSmsTotal() {
    return smsTotal;
  }

  public void setSmsTotal(int smsTotal) {
    this.smsTotal = smsTotal;
  }

  public boolean getEnableSmsNotification() {
    return enableSmsNotification;
  }

  public void setEnableSmsNotification(boolean enableSmsNotification) {
    this.enableSmsNotification = enableSmsNotification;
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

  public Date getLastReadCommentTime() {
    return lastReadCommentTime;
  }

  public void setLastReadCommentTime(Date lastReadCommentTime) {
    this.lastReadCommentTime = lastReadCommentTime;
  }
}
