package com.wisedu.wec.media.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class MediaCommentVo implements Serializable {
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
     * 用户
     */
    private MediaCommentUserVo commenter;

    /**
     * 回复的评论编号
     */
    private Long parentId;

    /**
     * 回复的评论人编号
     */
    private String replyUserId;

    /**
     * 回复的评论人昵称
     */
    private String replyUserNickName;
    /**
     * 回复的评论人
     */
    private MediaCommentUserVo commentee;

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

    /**
     * 子评论列表
     */
    private List<MediaCommentVo> childComments;

    private static final long serialVersionUID = 1L;
}
