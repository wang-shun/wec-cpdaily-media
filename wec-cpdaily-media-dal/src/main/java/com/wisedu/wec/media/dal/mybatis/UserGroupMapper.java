package com.wisedu.wec.media.dal.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.wisedu.wec.media.common.old.po.UserGroup;
import com.wisedu.wec.media.common.old.po.UserGroupExample;
import com.wisedu.wec.media.common.old.vo.Dept;
import com.wisedu.wec.media.dal.mybatis.sqlprovider.UserGroupSqlProvider;

public interface UserGroupMapper {

	long countByExample(UserGroupExample example);

	int deleteByExample(UserGroupExample example);

	int deleteByPrimaryKey(String groupId);

	int insert(UserGroup record);

	int insertSelective(UserGroup record);

	List<UserGroup> selectByExample(UserGroupExample example);

	UserGroup selectByPrimaryKey(String groupId);

	int updateByExampleSelective(@Param("record") UserGroup record, @Param("example") UserGroupExample example);

	int updateByExample(@Param("record") UserGroup record, @Param("example") UserGroupExample example);

	int updateByPrimaryKeySelective(UserGroup record);

	int updateByPrimaryKey(UserGroup record);

	@Select("select * from t_cpdaily_media_user_group where media_id = #{mediaId} and group_name = #{groupName}")
	UserGroup selectByGroupName(@Param("mediaId") String mediaId, @Param("groupName") String groupName);

	@Select("select group_id, group_name from t_cpdaily_media_user_group where media_id = #{mediaId} order by create_time")
	@Results({ @Result(property = "id", column = "group_id"), @Result(property = "title", column = "group_name") })
	List<Dept> selectByMediaId(String mediaId);

	@Select("select group_id, group_name from t_cpdaily_media_user_group g WHERE g.media_id = #{mediaId} "
			+ " AND g.tenant_id=#{tenantId} order by create_time")
	@Results({ @Result(property = "id", column = "group_id"), 
		@Result(property = "title", column = "group_name") })
	List<Dept> selectMineGroup(@Param("mediaId")String mediaId, @Param("tenantId")String tenantId);

	@Select("SELECT group_id, group_name FROM t_cpdaily_media_user_group where media_id = #{mediaId}")
	@Results({ @Result(property = "groupId", column = "group_id"), @Result(property = "groupName", column = "group_name") })
	List<UserGroup> selectGroupIdNamesByMediaId(String mediaId);

	/**
	 * 查找用户自己创建的用户组
	 * @param tenantId
	 * @param mediaId
	 * @param userId
	 * @return
	 */
	@SelectProvider(type=UserGroupSqlProvider.class, method="selectMyGroups")
	@ResultType(UserGroup.class)
	List<UserGroup> selectMyGroups(@Param("tenantId")String tenantId, @Param("mediaId")String mediaId, @Param("groupName")String groupName);
}
