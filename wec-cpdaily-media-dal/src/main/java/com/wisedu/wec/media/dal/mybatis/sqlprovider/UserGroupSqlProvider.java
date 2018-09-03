package com.wisedu.wec.media.dal.mybatis.sqlprovider;

import org.apache.ibatis.annotations.Param;

public class UserGroupSqlProvider {

	public String selectMyGroups(@Param("tenantId") String tenantId, @Param("mediaId") String mediaId,
			@Param("groupName") String groupName) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT g.group_id, count(rel.user_id) as user_count,g.media_id,g.tenant_id,g.group_name,g.owner_id,g.create_time,g.update_time "
				+ " FROM t_cpdaily_media_user_group g " + " left join t_cpdaily_media_user_group_relation rel on g.group_id=rel.group_id"
				+ " where g.media_id = #{mediaId} and g.tenant_id=#{tenantId} ");
		if(groupName!=null && groupName.trim().length()>0){
			sql.append(" and INSTR(g.group_name,#{groupName})>0 ");
		}
		sql.append(" group by g.group_id");

		return sql.toString();
	}
}
