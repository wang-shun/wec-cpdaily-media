package com.wisedu.wec.media.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CpdailyUserWithTagTopknotVo extends UserOftenInfoVo implements Serializable {
    private List<CpdailyTagVo> tagDTOs; // 标签列表
    private CpdailyTopknotVo topknotDTO; // 头饰信息
}
