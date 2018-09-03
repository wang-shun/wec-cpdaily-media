package com.wisedu.wec.media.common.old.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class MediaVisitingRecord implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 校园号id
     */
    private String mediaId;

    /**
     * 访客id
     */
    private String visitorId;

    /**
     * 访问描述
     */
    private String visitDesc;

    /**
     * 访问类型（0全部，1访问主页）
     */
    private Byte type;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}