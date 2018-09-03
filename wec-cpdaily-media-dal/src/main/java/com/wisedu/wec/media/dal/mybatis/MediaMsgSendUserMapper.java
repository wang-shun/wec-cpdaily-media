package com.wisedu.wec.media.dal.mybatis;

import java.util.List;

import com.wisedu.wec.media.common.vo.ReadUserBean;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import com.wisedu.wec.media.common.old.po.MsgSendUserPo;
import com.wisedu.wec.media.common.old.vo.SendUserVo;
import com.wisedu.wec.media.dal.mybatis.sqlprovider.MediaMsgSendUserSqlProvider;

/**
 * 消息接收者的数据库访问层。
 * 注意：这里的接收者，指的是除接收组组员以外，单独选择的消息接收者
 * @author mdmo
 *
 */
public interface MediaMsgSendUserMapper {
	/**
	 * 查询消息接收人ID
	 * @param msgId
	 * @return
	 */
	@Select("select user_id from t_cpdaily_media_msg_send_users where msg_id=#{msgId} and tenant_id=#{tenantId}")
    List<String> selectUserIdByMsgId(@Param("msgId")String msgId,@Param("tenantId")String tenantId);


	/**
	 * 查询消息接收人
	 * @param msgId
	 * @return
	 */
	@Select("select s.user_id,s.msg_id,u.name as userName from t_cpdaily_media_msg_send_users s inner join t_cpdaily_users u on s.user_id=u.wid"
			+ " where s.msg_id=#{msgId} and s.tenant_id=#{tenantId}")
	@ResultType(SendUserVo.class)
	List<SendUserVo> selectUsersByMsgId(@Param("msgId")String msgId,@Param("tenantId")String tenantId);
	
	/**
	 * 批量插入消息接收人
	 * @param records
	 * @return
	 */
	@InsertProvider(type=MediaMsgSendUserSqlProvider.class,method="batchInsert")
	int batchInsert(@Param("list")List<MsgSendUserPo> records);

	/**
	 * 根据消息ID删除记录
	 * @param msgId
	 * @param tenantId
	 */
	@Delete("delete from t_cpdaily_media_msg_send_users where msg_id=#{msgId} and tenant_id=#{tenantId}")
	int deleteByMsgId(@Param("msgId")String msgId,@Param("tenantId")String tenantId);

}