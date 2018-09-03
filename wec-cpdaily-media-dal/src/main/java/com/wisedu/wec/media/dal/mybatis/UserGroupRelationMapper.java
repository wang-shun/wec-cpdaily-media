package com.wisedu.wec.media.dal.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.wisedu.wec.media.common.old.po.UserGroup;
import com.wisedu.wec.media.common.old.po.UserGroupRelation;
import com.wisedu.wec.media.common.old.po.UserGroupRelationExample;
import com.wisedu.wec.media.common.old.vo.UserWithGroup;
import com.wisedu.wec.media.dal.mybatis.sqlprovider.UserGroupRelSqlProvider;

public interface UserGroupRelationMapper {

	long countByExample(UserGroupRelationExample example);

	int deleteByExample(UserGroupRelationExample example);

	int insert(UserGroupRelation record);

	int insertSelective(UserGroupRelation record);

	void batchInsert(List<UserGroupRelation> records);

	List<UserGroupRelation> selectByExample(UserGroupRelationExample example);

	@Select("select group_id, user_id from t_cpdaily_media_user_group_relation where media_id = #{mediaId}")
	@Results({ @Result(property = "groupId", column = "group_id"), @Result(property = "userId", column = "user_id") })
	List<UserGroupRelation> selectByMediaId(String mediaId);

	int updateByExampleSelective(@Param("record") UserGroupRelation record, @Param("example") UserGroupRelationExample example);

	int updateByExample(@Param("record") UserGroupRelation record, @Param("example") UserGroupRelationExample example);

	@Select("select rel.user_id, u.name, u.img,u.depart_Id,u.gender,u.class_id,u.open_id "
			+ " from t_cpdaily_media_user_group_relation rel inner join t_cpdaily_users u on rel.user_id=u.wid "
			+ "where rel.group_id = #{groupId}")
	@Results({ @Result(property = "userId", column = "user_id"), 
			@Result(property = "userName", column = "name"),
			@Result(property = "imgUrl", column = "img"),
		      @Result(property = "classId", column = "class_id"),
			  @Result(property = "gender", column = "gender"),
			  @Result(property = "departId", column = "depart_Id"),
			  @Result(property = "openId", column = "open_id")})
	List<UserWithGroup> selectByGroupId(@Param("groupId") String groupId);

	/**
	 * 查询多个自定义组的组员
	 * 
	 * @param groupIds
	 * @param mediaId
	 * @return
	 */
	@InsertProvider(type = UserGroupRelSqlProvider.class, method = "selectByGroupIds")
	List<UserWithGroup> selectByGroupIds(@Param("list") List<String> groupIds, @Param("mediaId") String mediaId);

	@Select("select rel.user_id, u.name, u.open_id,rel.img_url,u.class_id,u.depart_Id,u.gender,u.open_id "
			+ " from t_cpdaily_media_user_group_relation rel "
			+ " inner join t_cpdaily_users u on rel.user_id=u.wid  " 
			+ "where rel.group_id = #{groupId} and (u.name like #{name} or u.open_id like #{name})")
	@Results({ @Result(property = "userId", column = "user_id"), @Result(property = "userName", column = "name"),
			@Result(property = "classId", column = "class_id"), @Result(property = "gender", column = "gender"),
			@Result(property = "departId", column = "depart_Id") , @Result(property = "openId", column = "open_Id") })
	List<UserWithGroup> searchByName(@Param("groupId") String groupId, @Param("name") String name);

	@Select("SELECT g.group_id, count(rel.user_id) as user_count FROM t_cpdaily_media_user_group g "
			+ " inner join t_cpdaily_media_user_group_relation rel on g.group_id=rel.group_id" + " where g.media_id = #{mediaId} group by g.group_id")
	@Results({ @Result(property = "groupId", column = "group_id"), @Result(property = "userCount", column = "user_count") })
	List<UserGroup> selectGroupIdUserCount(String mediaId);

}
