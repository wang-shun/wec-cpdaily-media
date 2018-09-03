package com.wisedu.cpdaily.core.models.auth;

import java.io.Serializable;

/**
 * @author 01112143
 */
public class Session implements Serializable{

    private static final long serialVersionUID  = -8966630418777718982L;

    private String token;
    private String userId;
    private String pwid;
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Session{");
        sb.append("token='").append(token).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append('}');
        return sb.toString();
    }

	public String getPwid() {
		return pwid;
	}

	public void setPwid(String pwid) {
		this.pwid = pwid;
	}
}

