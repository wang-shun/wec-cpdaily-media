package com.wisedu.wec.media.common.context;

/**
 * 当前登录用户的信息
 * @author mdmo
 *
 */
public class LoginUserContext {
	private String cpdailyPersonId;
	private String cpdailyTenantCode;//学校代码
	private String cpdailyTenantId;
	private String cpdailyUserWid;//开放平台的身份ID
	private String cpdailyUserType; // cpdaily 身份类型,1-学生   2-老师
	private String cpdailyUserName;
	private String cpdailyUserAccount;
	private String userGenderCode;
	private String userDepartment;
	private String userDepartmentCode;

	public String getCpdailyPersonId() {
		return cpdailyPersonId;
	}

	public void setCpdailyPersonId(String cpdailyPersonId) {
		this.cpdailyPersonId = cpdailyPersonId;
	}

	public String getCpdailyTenantCode() {
		return cpdailyTenantCode;
	}

	public void setCpdailyTenantCode(String cpdailyTenantCode) {
		this.cpdailyTenantCode = cpdailyTenantCode;
	}

	public String getCpdailyTenantId() {
		return cpdailyTenantId;
	}

	public void setCpdailyTenantId(String cpdailyTenantId) {
		this.cpdailyTenantId = cpdailyTenantId;
	}

	public String getCpdailyUserWid() {
		return cpdailyUserWid;
	}

	public void setCpdailyUserWid(String cpdailyUserWid) {
		this.cpdailyUserWid = cpdailyUserWid;
	}

	public String getCpdailyUserType() {
		return cpdailyUserType;
	}

	public void setCpdailyUserType(String cpdailyUserType) {
		this.cpdailyUserType = cpdailyUserType;
	}

	public String getCpdailyUserName() {
		return cpdailyUserName;
	}

	public void setCpdailyUserName(String cpdailyUserName) {
		this.cpdailyUserName = cpdailyUserName;
	}

	public String getCpdailyUserAccount() {
		return cpdailyUserAccount;
	}

	public void setCpdailyUserAccount(String cpdailyUserAccount) {
		this.cpdailyUserAccount = cpdailyUserAccount;
	}

	public String getUserGenderCode() {
		return userGenderCode;
	}

	public void setUserGenderCode(String userGenderCode) {
		this.userGenderCode = userGenderCode;
	}

	public String getUserDepartment() {
		return userDepartment;
	}

	public void setUserDepartment(String userDepartment) {
		this.userDepartment = userDepartment;
	}

	public String getUserDepartmentCode() {
		return userDepartmentCode;
	}

	public void setUserDepartmentCode(String userDepartmentCode) {
		this.userDepartmentCode = userDepartmentCode;
	}

}
