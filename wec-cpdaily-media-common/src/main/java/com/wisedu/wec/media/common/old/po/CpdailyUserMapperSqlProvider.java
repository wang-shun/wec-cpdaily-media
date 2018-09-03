package com.wisedu.wec.media.common.old.po;

import java.util.List;

import org.apache.ibatis.annotations.Param;


/**
 * 用户身份查询SQL组装类
 * @author mdmo
 *
 */
public class CpdailyUserMapperSqlProvider {
	
	/**
	 * 组装批量查询校园号的粉丝数的SQL
	 * @param mediaIds
	 * @return
	 */
	public String batchSelectFansNumber(@Param("list") List<String> mediaIds){
		StringBuilder sql = new StringBuilder("select media.wid as mediaId,count(rel.user_id) as fansCount from t_cpdaily_campus_media media "
		 		+ " inner join t_cpdaily_userrelations rel on rel.FOLLOW_ID=media.wid where media.wid in (");
		
		for(String mediaId : mediaIds){
			sql.append("'").append(mediaId).append("',");
		}
		sql.deleteCharAt(sql.length()-1);
		sql.append(") group by media.wid");
		
		return sql.toString();
	}
	
}
