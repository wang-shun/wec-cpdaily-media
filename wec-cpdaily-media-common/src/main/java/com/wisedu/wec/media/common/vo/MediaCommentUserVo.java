package com.wisedu.wec.media.common.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class MediaCommentUserVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;
    private String alias;
    private String avatar;
    private String branchName;

    public MediaCommentUserVo() {
    }

    public MediaCommentUserVo(String userId, String alias, String avatar, String branchName) {
        this.userId = userId;
        this.alias = alias;
        this.avatar = avatar;
        this.branchName = branchName;
    }

}
