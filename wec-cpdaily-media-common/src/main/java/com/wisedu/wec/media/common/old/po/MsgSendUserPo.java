package com.wisedu.wec.media.common.old.po;

/**
 * 消息接收人数据库映射类
 * @author mdmo
 *
 */
public class MsgSendUserPo {
	private String tenantId;
	private String msgId;
	private String userId;
	private String userName;
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
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
	
	
}
