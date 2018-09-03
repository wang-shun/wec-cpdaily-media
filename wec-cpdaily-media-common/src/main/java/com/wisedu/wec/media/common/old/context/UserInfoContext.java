package com.wisedu.wec.media.common.old.context;

import java.util.HashMap;
import java.util.Map;

public class UserInfoContext {

	private String personId;//人的ID
	
	private String personName;//人的姓名

	private String userId;//身份ID，当进入校园号时，会被设置为某校园号id

	private String userName; // 当进入校园号时，会被设置为某校园号name

	private String loginUserId;// 登录身份ID

	private String loginUserName;// 登录身份ID

	private String userType; // 校园号的，null

	private String tenantCode; // 校园号的

	private String tenantId; // 校园号的

	private String tenantName; // 校园号的

	private String portrait; // 校园号的

	private String mediaId; // 校园号的

	private Map<String, MediaInfoContext> mediaMap = new HashMap<>();

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Map<String, MediaInfoContext> getMediaMap() {
		return mediaMap;
	}

	public void setMediaMap(Map<String, MediaInfoContext> mediaMap) {
		this.mediaMap = mediaMap;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getTenantCode() {
		return tenantCode;
	}

	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}

	public String getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}

	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}
}
