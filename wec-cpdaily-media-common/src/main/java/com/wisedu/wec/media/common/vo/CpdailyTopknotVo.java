package com.wisedu.wec.media.common.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CpdailyTopknotVo implements Serializable {
    private static final long serialVersionUID = -355162375751297799L;

    private String topknotId = "";
    private String topknotName = "";
    private String icon = "";
    private String backgroundImg = "";
    private String status="";
}
