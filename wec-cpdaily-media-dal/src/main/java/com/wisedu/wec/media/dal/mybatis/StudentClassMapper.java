package com.wisedu.wec.media.dal.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.wisedu.wec.media.common.old.po.StudentClass;

/**
 * 班级数据库访问类
 * @author mdmo
 *
 */
public interface StudentClassMapper {
	
	@Select("SELECT DISTINCT(class_id),class_name,tenant_id FROM t_cpdaily_academymajors where tenant_id=#{tenantId}")
	@ResultType(value=StudentClass.class)
	List<StudentClass> getAllClasses(@Param("tenantId")String tenantId);
}
