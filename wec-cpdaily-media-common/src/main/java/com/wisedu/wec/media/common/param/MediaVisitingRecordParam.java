package com.wisedu.wec.media.common.param;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class MediaVisitingRecordParam implements Serializable {
    /**
     * 校园号id
     */
    @NotBlank(message="校园号不能为空")
    private String mediaId;

    /**
     * 访问描述
     */
    @NotBlank(message="访问描述不能为空")
    private String visitDesc;

    /**
     * 访问类型（0全部，1访问主页）
     */
    @NotNull(message="访问类型能为空")
    private Byte type;

}
