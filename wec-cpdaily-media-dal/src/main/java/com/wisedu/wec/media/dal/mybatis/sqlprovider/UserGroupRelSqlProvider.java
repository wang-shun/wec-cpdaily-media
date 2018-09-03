package com.wisedu.wec.media.dal.mybatis.sqlprovider;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 用户和用户组关系SQL组装器
 * @author mdmo
 *
 */
public class UserGroupRelSqlProvider {

	/**
	 * 组装把组员复制到另外一个组的SQL
	 * @param groupId
	 * @param mediaId
	 * @return
	 */
	public String selectByGroupIds(@Param("list")List<String> groupIds,@Param("mediaId")String mediaId){
		StringBuilder sql = new StringBuilder("select * from t_cpdaily_media_user_group_relation where media_id=#{mediaId} group_id in (");
		
		for(String groupId : groupIds){
			sql.append("'").append(groupId).append("',");
		}
		sql.deleteCharAt(sql.length()-1);
		
		sql.append(")");
		return sql.toString();
	}
}
