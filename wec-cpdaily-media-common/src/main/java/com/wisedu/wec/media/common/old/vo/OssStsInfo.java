package com.wisedu.wec.media.common.old.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by croyson on 2017/10/23.
 */
public class OssStsInfo implements Serializable{


    private static final long serialVersionUID = 7022479927468332192L;

    private String accessKeyId;

    private String accessKeySecret;

    private String securityToken;

    private Date expiration;


    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
}
