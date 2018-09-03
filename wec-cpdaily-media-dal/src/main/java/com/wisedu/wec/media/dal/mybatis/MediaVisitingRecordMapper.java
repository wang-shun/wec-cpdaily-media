package com.wisedu.wec.media.dal.mybatis;

import com.wisedu.wec.media.common.old.po.MediaVisitingRecord;
import com.wisedu.wec.media.common.old.po.MediaVisitingRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MediaVisitingRecordMapper {
    long countByExample(MediaVisitingRecordExample example);

    int deleteByExample(MediaVisitingRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MediaVisitingRecord record);

    int insertSelective(MediaVisitingRecord record);

    List<MediaVisitingRecord> selectByExample(MediaVisitingRecordExample example);

    MediaVisitingRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MediaVisitingRecord record, @Param("example") MediaVisitingRecordExample example);

    int updateByExample(@Param("record") MediaVisitingRecord record, @Param("example") MediaVisitingRecordExample example);

    int updateByPrimaryKeySelective(MediaVisitingRecord record);

    int updateByPrimaryKey(MediaVisitingRecord record);

    int batchInsertVisitors(@Param("mediaId") String mediaId, @Param("visitorIds") List<String> visitorIds);
}