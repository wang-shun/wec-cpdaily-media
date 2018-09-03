package com.wisedu.wec.media.common.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class UserOftenInfoVo extends UserBaseInfoVo implements Serializable {
    private String tenantCode = ""; // 租户code
    private String tenantName = ""; // 租户名称
    private String branchId = ""; // 用户类型为老师departId、用户类型为学生academyId
    private String branchName = ""; // 用户类型为老师departName、用户类型为学生academyName
}
