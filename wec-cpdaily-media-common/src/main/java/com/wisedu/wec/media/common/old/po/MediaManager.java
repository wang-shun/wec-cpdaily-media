package com.wisedu.wec.media.common.old.po;

import com.wisedu.wec.media.common.old.vo.UserWithGroup;

/**
 * Created by zsl on 2018/4/26.
 */
public class MediaManager extends CpdailyUser {
    private String manageType;
    private String personId;

    public String getManageType() {
        return manageType;
    }

    public void setManageType(String manageType) {
        this.manageType = manageType;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MediaManager{");
        sb.append("super={").append(super.toString()).append('}');
        sb.append(",manageType='").append(manageType).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
