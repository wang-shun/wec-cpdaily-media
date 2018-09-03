package com.wisedu.wec.media.common.old.po;

import java.io.Serializable;
import java.util.Date;

public class MediaPersonRelation implements Serializable {
    private String personId;
    
    private String userId;

    private String mediaId;

    private String manageType;
    
    private Date createTime;
    
    private Date updateTime;
    
    private int isDeleted;

    private static final long serialVersionUID = 1L;

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getManageType() {
		return manageType;
	}

	public void setManageType(String manageType) {
		this.manageType = manageType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

    
}