package com.wisedu.wec.media.dal.mybatis;

import java.util.Date;
import java.util.List;

import com.wisedu.wec.media.common.vo.ReadUserBean;
import org.apache.ibatis.annotations.*;

import com.wisedu.wec.media.common.old.po.MediaMsg;
import com.wisedu.wec.media.common.old.po.MediaMsgExample;
import com.wisedu.wec.media.common.old.vo.MediaMsg7DayVo;


public interface MediaMsgMapper {
  long countByExample(MediaMsgExample example);

  int deleteByExample(MediaMsgExample example);

  int deleteByPrimaryKey(String wid);

  @Update("update t_cpdaily_media_msg set is_delete = 1 where wid = #{wid}")
  int delLogic(String wid);

  @Update("update t_cpdaily_media_msg set read_count = read_count + 1 where wid = #{wid}  and  read_count < receive_count")
  int readMsg(String wid);

  int insert(MediaMsg record);

  int insertSelective(MediaMsg record);

  List<MediaMsg> selectByExampleWithBLOBs(MediaMsgExample example);

  List<MediaMsg> selectByExample(MediaMsgExample example);

  MediaMsg selectByPrimaryKey(String wid);

  int updateByExampleSelective(@Param("record") MediaMsg record,
      @Param("example") MediaMsgExample example);

  int updateByExampleWithBLOBs(@Param("record") MediaMsg record,
      @Param("example") MediaMsgExample example);

  int updateByExample(@Param("record") MediaMsg record, @Param("example") MediaMsgExample example);

  int updateByPrimaryKeySelective(MediaMsg record);

  int updateByPrimaryKeyWithBLOBs(MediaMsg record);

  int updateByPrimaryKey(MediaMsg record);


  @Select("select wid, title, c_time, status, send_count, send_over_count, read_count, receive_count from t_cpdaily_media_msg where is_delete = 0 and media_id = #{mediaId} and c_time <= #{endTime} and c_time >= #{startTime} and status in ('SEND_IN','SEND_END','SEND_ERROR') order by c_time desc")
  @Results({@Result(property = "msgId", column = "wid"),
      @Result(property = "title", column = "title"),
      @Result(property = "createTime", column = "c_time"),
      @Result(property = "status", column = "status"),
      @Result(property = "sendCount", column = "send_count"),
      @Result(property = "sendOverCount", column = "send_over_count"),
      @Result(property = "readCount", column = "read_count"),
      @Result(property = "receiveCount", column = "receive_count"),})
  List<MediaMsg7DayVo> select7DaysByMediaId(@Param("mediaId") String mediaId,
      @Param("startTime") Date startTime, @Param("endTime") Date endTime);



  @Select(" <script> select * from t_cpdaily_media_msg where  is_delete = 0 and  media_Id = #{mediaId} and status in ("
      + " <foreach collection =\"statusList\" item=\"status\"     separator=\",\" >"
      + "              #{status,jdbcType=VARCHAR}" + "              </foreach>"
      + ")   order by u_time desc </script>")
  @ResultType(MediaMsg.class)
  List<MediaMsg> selectMsgs(@Param("mediaId") String mediaId,
      @Param("statusList") List<String> statusList);

  @Select(" <script> select count(1) from t_cpdaily_media_msg where  is_delete = 0 and  media_Id = #{mediaId} and status in ("
      + " <foreach collection =\"statusList\" item=\"status\"    separator=\",\" >"
      + "              #{status,jdbcType=VARCHAR}" + "              </foreach>"
      + ")  order by c_time desc </script>")
  int countMsgs(@Param("mediaId") String mediaId, @Param("statusList") List<String> statusList);
  
  /**
   * 增加消息的已发送数量
   * @param delta 增加的数值
   * @param msgId
   * @return
   */
  @Update("update t_cpdaily_media_msg set send_over_count=#{delta} where wid=#{msgId}")
  int increaseMsgSendOverCount(@Param("delta")int delta, @Param("msgId")String msgId);


  /**
   * 新增消息用户记录表，供已读未读使用
   * @return
   */
  @Insert("<script>" +
          " insert into t_cpdaily_media_msg_read_relation (msg_id, user_id, tenant_id, create_time) values  " +
          " <foreach item=\"userId\" collection=\"userIds\" separator=\",\"> " +
          "   (#{msgId}, #{userId,jdbcType=VARCHAR}, #{tenantId}, now()) " +
          " </foreach> " +
          "</script>")
  void insertMsgUserReadRelation(@Param("msgId")String msgId, @Param("tenantId")String tenantId, @Param("userIds")List<String> userIds);


  /**
   * 已读未读列表
   * @param msgId
   * @return
   */
  @Select("<script>select * from t_cpdaily_media_msg_read_relation "
          + " where msg_id=#{msgId} " +
          "<if test=\"status == 'unread'\">" +
          " and read_status = 0 " +
          "</if>" +
          "<if test=\"status == 'read'\">" +
          " and read_status = 1 " +
          "</if>" +
          "</script>"
  )
  @ResultType(ReadUserBean.class)
  List<ReadUserBean> selectReadUserList(@Param("msgId")String msgId, @Param("status")String status);


  /**
   * 用户读消息
   * @param msgId
   * @return
   */
  @Select("<script>update t_cpdaily_media_msg_read_relation " +
          " set read_status = 1, read_time=now() " +
          " where msg_id=#{msgId} " +
          " and user_id=#{userId} " +
          "</script>"
  )
  @ResultType(ReadUserBean.class)
  void userReadMsg(@Param("msgId")String msgId, @Param("userId")String userId);

  /**
   * 判断当前用户是否已读过消息（防客户端某些情况下重复发读消息请求）
   * @param msgId
   * @return
   */
  @Select(" select count(1) as count from t_cpdaily_media_msg_read_relation where user_id=#{userId} and msg_id=#{msgId} and read_status=1 ")
  @ResultType(java.lang.Integer.class)
  int getUserReadMsgCount(@Param("msgId")String msgId, @Param("userId")String userId);

  /**
   * 更新最后提醒时间
   * @param msgId
   * @return
   */
  @Update(" update t_cpdaily_media_msg set last_notify_time =now() where wid=#{msgId} ")
  void updateLastNotifyTime(@Param("msgId")String msgId);
}
