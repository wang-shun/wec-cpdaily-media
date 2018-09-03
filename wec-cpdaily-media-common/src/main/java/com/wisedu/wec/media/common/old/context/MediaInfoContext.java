package com.wisedu.wec.media.common.old.context;

public class MediaInfoContext {

	private String mediaId;

	private String mediaType;

	private String mediaName;

	private String portraitUrl;

	private Integer fansCount;

	private String authStatus;

	private String tenantId;

	private boolean canSeeOrgStructure;
	
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getPortraitUrl() {
		return portraitUrl;
	}

	public void setPortraitUrl(String portraitUrl) {
		this.portraitUrl = portraitUrl;
	}

	public Integer getFansCount() {
		return fansCount;
	}

	public void setFansCount(Integer fansCount) {
		this.fansCount = fansCount;
	}

	public String getMediaName() {
		return mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}

	public String getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public boolean isCanSeeOrgStructure() {
		return canSeeOrgStructure;
	}

	public void setCanSeeOrgStructure(boolean canSeeOrgStructure) {
		this.canSeeOrgStructure = canSeeOrgStructure;
	}

}
