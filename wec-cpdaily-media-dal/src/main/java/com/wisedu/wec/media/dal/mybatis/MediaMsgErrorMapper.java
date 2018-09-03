package com.wisedu.wec.media.dal.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.wisedu.wec.media.common.old.po.MediaMsgError;
import com.wisedu.wec.media.common.old.po.MediaMsgErrorExample;
import com.wisedu.wec.media.common.old.po.MediaMsgErrorKey;

public interface MediaMsgErrorMapper {
    long countByExample(MediaMsgErrorExample example);

    @Delete("delete from t_cpdaily_media_msg_error where msg_id = #{msgId} ")
    int delByMsgId(String msgId);

    int deleteByExample(MediaMsgErrorExample example);

    int deleteByPrimaryKey(MediaMsgErrorKey key);

    int insert(MediaMsgError record);

    int batchInsert(@Param("records")List<MediaMsgError> record);

    int insertSelective(MediaMsgError record);

    List<MediaMsgError> selectByExample(MediaMsgErrorExample example);

    MediaMsgError selectByPrimaryKey(MediaMsgErrorKey key);

    @Select("select * from t_cpdaily_media_msg_error where msg_id = #{msgId}")
    @ResultMap("BaseResultMap")
    List<MediaMsgError> selectByMsgId(@Param("msgId") String msgId);

    int updateByExampleSelective(@Param("record") MediaMsgError record, @Param("example") MediaMsgErrorExample example);

    int updateByExample(@Param("record") MediaMsgError record, @Param("example") MediaMsgErrorExample example);

    int updateByPrimaryKeySelective(MediaMsgError record);

    int updateByPrimaryKey(MediaMsgError record);
}