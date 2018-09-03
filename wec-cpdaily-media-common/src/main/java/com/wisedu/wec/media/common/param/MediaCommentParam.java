package com.wisedu.wec.media.common.param;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
@Data
public class MediaCommentParam implements Serializable {
    /**
     * 校园号ID
     */
    @NotBlank(message="校园号不能为空")
    private String mediaId;

    /**
     * 评论内容
     */
    @NotBlank(message="评论内容不能为空")
    private String content;

    /**
     * 回复的评论编号
     */
    private Long parentId;

    /**
     * 回复的评论人编号
     */
    private String replyUserId;
}
