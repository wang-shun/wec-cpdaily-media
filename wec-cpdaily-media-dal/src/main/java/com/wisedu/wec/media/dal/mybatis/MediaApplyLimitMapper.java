package com.wisedu.wec.media.dal.mybatis;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface MediaApplyLimitMapper {

  @Select("select limit_number from t_cpdaily_media_type_limit where tenant_id = #{tenantId} and user_type = #{userType} and type_id = #{typeId}")
  Integer selectLimitNumber(@Param("tenantId") String tenantId, @Param("userType") String userType,
      @Param("typeId") String typeId);

}
