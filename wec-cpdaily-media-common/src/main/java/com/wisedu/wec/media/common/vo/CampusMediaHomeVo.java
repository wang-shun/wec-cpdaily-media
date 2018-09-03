package com.wisedu.wec.media.common.vo;

import com.wisedu.wec.media.common.old.vo.CampusMediaVo;
import lombok.Data;

@Data
public class CampusMediaHomeVo extends CampusMediaVo {
    /**
     * 来访数
     */
    private Long visitingCount;
    /**
     * 粉丝数
     */
    private Long fansCount;
    /**
     * 是否管理员
     */
    private boolean isManager;
    /**
     * 是否是粉丝
     */
    private boolean isFans;
}
