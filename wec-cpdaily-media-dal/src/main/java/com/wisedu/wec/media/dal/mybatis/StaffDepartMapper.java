package com.wisedu.wec.media.dal.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.wisedu.wec.media.common.old.vo.Dept;


public interface StaffDepartMapper {

  // @Select("select * from t_cpdaily_departs where TENANT_ID = #{tenantId}")
  // @Results({@Result(property = "departId", column = "DEPART_ID"),
  // @Result(property = "departName", column = "DEPART_NAME"),
  // @Result(property = "parentDepartId", column = "P_DEPART_ID")})
  // List<StaffDepart> selectByTenantId(String tenantId);

  @Select("select * from t_cpdaily_departs where TENANT_ID = #{tenantId}")
  @Results({@Result(property = "id", column = "DEPART_ID"),
      @Result(property = "title", column = "DEPART_NAME"),
      @Result(property = "fullName", column = "DEPART_NAME"),
      @Result(property = "pid", column = "P_DEPART_ID")})
  List<Dept> selectByTenantIdConvert2Dept(String tenantId);

}
