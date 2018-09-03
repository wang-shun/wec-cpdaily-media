package com.wisedu.wec.media.common.old.vo;

/**
 * 校园号管理平台的用户组
 * 
 * @author mdmo
 *
 */
public class UserGroupVo {
	private String tenantId;
	private String mediaId;
	private String groupId;
	private String groupName;
	private String groupType;
	private String ownerId;
	private Integer userCount;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public Integer getUserCount() {
		return userCount;
	}

	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}

}
