package com.wisedu.wec.media.common.old.po;

import java.util.Date;

/**
 * 今日校园身份表 一个用户对应多个身份
 */
public class 	CpdailyUser {
	private String wid;

	private String name;
	
	private String personName;

	private String img;

	private String userType;

	private String tenantId;

	private String academyId;

	private String majorId;

	private String departId;
	private String departName;
	private String mobilephone;

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	private Long fansCount;

	private Long followCount;

	private String tenantName;

	// below 生成新用户所需属性
	private String gender;

	private Date updateTime;

	private String userSrcType;

	private String status;

	private String openId;

	private String degree;
	/** 用户ID */
	private String pid;

	/** person ID */

	private String pwid;

	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getAcademyId() {
		return academyId;
	}

	public void setAcademyId(String academyId) {
		this.academyId = academyId;
	}

	public String getMajorId() {
		return majorId;
	}

	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public Long getFansCount() {
		return fansCount;
	}

	public void setFansCount(Long fansCount) {
		this.fansCount = fansCount;
	}

	public Long getFollowCount() {
		return followCount;
	}

	public void setFollowCount(Long followCount) {
		this.followCount = followCount;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUserSrcType() {
		return userSrcType;
	}

	public void setUserSrcType(String userSrcType) {
		this.userSrcType = userSrcType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}


	public String getPwid() {
		return pwid;
	}

	public void setPwid(String pwid) {
		this.pwid = pwid;
	}

	@Override
	public String toString() {
		return "CpdailyUser{" + "wid='" + wid + '\'' + ", name='" + name + '\'' + ", img='" + img + '\''
				+ ", userType='" + userType + '\'' + ", tenantId='" + tenantId + '\'' + ", academyId='" + academyId
				+ '\'' + ", majorId='" + majorId + '\'' + ", departId='" + departId + '\'' + ", fansCount=" + fansCount
				+ ", followCount=" + followCount + '}';
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}
}
