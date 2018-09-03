package com.wisedu.wec.media.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MediaFansVo implements Serializable {
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
     * 关注者id
     */
    private String fansId;

    /**
     * 关注者
     */
    private CpdailyUserWithTagTopknotVo fans;

    /**
     * 状态（1有效，0失效）
     */
    private Byte status;

    /**
     * 关注时间
     */
    private Date createTime;

}
