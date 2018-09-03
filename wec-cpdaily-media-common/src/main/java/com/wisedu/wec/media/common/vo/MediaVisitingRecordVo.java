package com.wisedu.wec.media.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MediaVisitingRecordVo implements Serializable {
    private static final long serialVersionUID = 1L;
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
     * 访客信息
     */
    private CpdailyUserWithTagTopknotVo visitor;

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


}
