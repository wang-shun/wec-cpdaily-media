package com.wisedu.wec.media.dal.mybatis;

import java.util.List;

import com.wisedu.wec.media.common.old.po.CpdailyPerson;
import com.wisedu.wec.media.common.old.vo.TenantVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.wisedu.wec.media.common.old.po.CpdailyTenant;

public interface CpdailyTenantMapper {

  @Select("SELECT TENANT_NAME FROM t_cpdaily_tenants where WID = #{tenantId}")
  String selectNameById(String tenantId);

  /**
   * 获取人所属的学校列表
   * @param personId
   * @return
   */
  @Select("SELECT DISTINCT(t.`TENANT_NAME`) as tenantName,t.`WID` as tenantId FROM t_cpdaily_users u "
  		+ "INNER JOIN `t_cpdaily_tenants` t ON u.`tenant_Id`=t.`WID` WHERE u.`pwid`=#{personId} AND u.user_type !='MEDIA' AND u.status ='ENABLE' and t.status='ENABLE'")
  List<CpdailyTenant> getPersonTenantList(@Param("personId")String personId);

  /**
   * 获取学校信息
   * @param tenantId
   * @return
   */
  @Select("SELECT TENANT_NAME as tenantName, `WID` as tenantId, `TENANT_CODE` as tenantCode FROM "
          + " `t_cpdaily_tenants`  WHERE wid = #{tenantId} and status='ENABLE'")
  CpdailyTenant getTenantById(@Param("tenantId")String tenantId);


  @Select("<script> SELECT TENANT_NAME, WID as tenantId FROM t_cpdaily_tenants where WID in ( " +
          "       <foreach collection=\"wids\" item=\"wid\"     separator=\",\" >" +
          "           #{wid,jdbcType=VARCHAR}" +
          "       </foreach>" +
          " ) </script>"
  )
  @ResultType(CpdailyTenant.class)
  List<CpdailyTenant> selectNameByIds(@Param("wids") List<String> wids);


  @Select("<script> SELECT wid, tenant_code, tenant_name, status FROM t_cpdaily_tenants where WID in ( "+
          "       <foreach collection=\"wids\" item=\"wid\"     separator=\",\" >" +
          "           #{wid,jdbcType=VARCHAR}" +
          "       </foreach>" +
          " ) and status='ENABLE' </script>")
  @ResultType(TenantVO.class)
  List<TenantVO> selectTenantsBywids(@Param("wids") List<String> wids);
}
