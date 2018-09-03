package com.wisedu.wec.media.common.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserBaseInfoVo  implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId = "";		// 身份id(身份表wid)--
    private String alias = "";		// 用户别名--
    private String name = "";		// 用户名--
    private String gender;		// 用户性别--
    private String avatar = "";		// 用户头像--
    private String grade = "";		// 入学年份

}
