package com.wisedu.wec.media.common.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CpdailyTagVo implements Serializable {
    private static final long serialVersionUID = 4409694355217905635L;

    private String tagId = "";
    private String tagName = "";
    private String tagCategory = "";
    private String smallIcon = "";
    private String bigIcon = "";
    private String tagTone = "";
    private String status="";
}
