package com.wisedu.wec.media.dal.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.wisedu.wec.media.common.old.po.AcademyMajor;

/**
 *  学院专业数据库访问类
 */
public interface AcademyMajorMapper {

  @Select("select * from t_cpdaily_academymajors where TENANT_ID = #{tenantId}")
  @Results({@Result(property = "majorId", column = "majors_id"),
      @Result(property = "majorName", column = "major_Name"),
      @Result(property = "academyId", column = "academy_Id"),
      @Result(property = "academyName", column = "academy_Name")})
  List<AcademyMajor> selectByTenantId(String tenantId);

  @Select("select count(wid) from t_cpdaily_academymajors where TENANT_ID = #{tenantId} and academy_Id = #{academyId}")
  int selectWidByAcademyId(@Param("tenantId") String tenantId,
      @Param("academyId") String academyId);

}
