package com.wisedu.wec.media.common.old.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class MediaComment implements Serializable {
    /**
     * 自增长主键
     */
    private Long id;

    /**
     * 商品ID
     */
    private String mediaId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 回复的评论编号
     */
    private Long parentId;

    /**
     * 回复的评论人编号
     */
    private String replyUserId;

    /**
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}