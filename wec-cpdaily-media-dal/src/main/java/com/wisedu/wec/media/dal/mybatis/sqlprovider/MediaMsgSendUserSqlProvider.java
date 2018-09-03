package com.wisedu.wec.media.dal.mybatis.sqlprovider;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wisedu.wec.media.common.old.po.MsgSendUserPo;

/**
 * 消息接收者SQL组装类
 * @author mdmo
 *
 */
public class MediaMsgSendUserSqlProvider {
	
	public String batchInsert(@Param("list")List<MsgSendUserPo> records){
		StringBuilder sql = new StringBuilder("insert into t_cpdaily_media_msg_send_users(msg_id,tenant_id,user_id,user_name) values ");
		for(MsgSendUserPo sendUser : records){
			sql.append("('").append(sendUser.getMsgId()).append("','").append(sendUser.getTenantId()).append("','")
			.append(sendUser.getUserId()).append("','").append(sendUser.getUserName()).append("'),");
		}
		sql.deleteCharAt(sql.length()-1);
		return sql.toString();
	}
}
