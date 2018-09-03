package com.wisedu.wec.media.dal.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.wisedu.wec.media.common.old.po.MediaMsgSendGroup;
import com.wisedu.wec.media.common.old.po.MediaMsgSendGroupExample;
import com.wisedu.wec.media.common.old.po.MediaMsgSendGroupKey;

public interface MediaMsgSendGroupMapper {
    long countByExample(MediaMsgSendGroupExample example);

    int deleteByExample(MediaMsgSendGroupExample example);

    int deleteByPrimaryKey(MediaMsgSendGroupKey key);

    @Delete("delete from t_cpdaily_media_msg_send_groups where msg_id =  #{msgId}")
    int deleteByMsgId(String msgId);

    int insert(MediaMsgSendGroup record);


    int batchInsert(@Param("records") List records);

    int insertSelective(MediaMsgSendGroup record);

    @Select("select * from t_cpdaily_media_msg_send_groups where msg_id =  #{msgId}")
    @ResultMap("BaseResultMap")
    List<MediaMsgSendGroup> selectByMsgId(String msgId);

    List<MediaMsgSendGroup> selectByExample(MediaMsgSendGroupExample example);

    MediaMsgSendGroup selectByPrimaryKey(MediaMsgSendGroupKey key);

    int updateByExampleSelective(@Param("record") MediaMsgSendGroup record, @Param("example") MediaMsgSendGroupExample example);

    int updateByExample(@Param("record") MediaMsgSendGroup record, @Param("example") MediaMsgSendGroupExample example);

    int updateByPrimaryKeySelective(MediaMsgSendGroup record);

    int updateByPrimaryKey(MediaMsgSendGroup record);
}