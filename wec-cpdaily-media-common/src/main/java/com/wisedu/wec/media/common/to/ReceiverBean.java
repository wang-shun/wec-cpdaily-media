package com.wisedu.wec.media.common.to;

import java.util.List;

/**
 * Created by zsl on 2018/4/20.
 */
public class ReceiverBean {
    private int receiverType;
    private String tenantCode;
    private String[] openIds;
    private String[] userIds;

    public int getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(int receiverType) {
        this.receiverType = receiverType;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String[] getOpenIds() {
        return openIds;
    }

    public void setOpenIds(String[] openIds) {
        this.openIds = openIds;
    }

    public String[] getUserIds() {
        return userIds;
    }

    public void setUserIds(String[] userIds) {
        this.userIds = userIds;
    }
}
