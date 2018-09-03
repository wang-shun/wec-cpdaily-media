package com.wisedu.wec.media.dal.mybatis;

import com.wisedu.wec.media.common.old.po.MediaComment;
import com.wisedu.wec.media.common.old.po.MediaCommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MediaCommentMapper {
    long countByExample(MediaCommentExample example);

    int deleteByExample(MediaCommentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MediaComment record);

    int insertSelective(MediaComment record);

    List<MediaComment> selectByExample(MediaCommentExample example);

    MediaComment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MediaComment record, @Param("example") MediaCommentExample example);

    int updateByExample(@Param("record") MediaComment record, @Param("example") MediaCommentExample example);

    int updateByPrimaryKeySelective(MediaComment record);

    int updateByPrimaryKey(MediaComment record);
}