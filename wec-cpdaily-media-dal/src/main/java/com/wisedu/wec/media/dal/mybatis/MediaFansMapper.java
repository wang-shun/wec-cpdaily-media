package com.wisedu.wec.media.dal.mybatis;

import com.wisedu.wec.media.common.old.po.MediaFans;
import com.wisedu.wec.media.common.old.po.MediaFansExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MediaFansMapper {
    long countByExample(MediaFansExample example);

    int deleteByExample(MediaFansExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MediaFans record);

    int insertSelective(MediaFans record);

    List<MediaFans> selectByExample(MediaFansExample example);

    MediaFans selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MediaFans record, @Param("example") MediaFansExample example);

    int updateByExample(@Param("record") MediaFans record, @Param("example") MediaFansExample example);

    int updateByPrimaryKeySelective(MediaFans record);

    int updateByPrimaryKey(MediaFans record);

    int batchInsertFans(@Param("mediaId") String mediaId, @Param("fansIds") List<String> fansIds);
}