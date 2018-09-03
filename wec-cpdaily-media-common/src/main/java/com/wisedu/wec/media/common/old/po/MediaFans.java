package com.wisedu.wec.media.common.old.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class MediaFans implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 校园号id
     */
    private String mediaId;

    /**
     * 关注者id
     */
    private String fansId;

    /**
     * 状态（1有效，0失效）
     */
    private Byte status;

    /**
     * 关注时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}