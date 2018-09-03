package com.wisedu.wec.media.dal.mybatis.dao;

import com.wisedu.wec.media.common.base.Page;
import com.wisedu.wec.media.common.base.dao.BaseDao;
import com.wisedu.wec.media.common.old.po.MediaVisitingRecord;
import com.wisedu.wec.media.common.old.po.MediaVisitingRecordExample;
import com.wisedu.wec.media.dal.mybatis.MediaVisitingRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MediaVisitingRecordDao extends BaseDao<MediaVisitingRecord, MediaVisitingRecordExample, Long, MediaVisitingRecordMapper> {
    @Autowired
    private MediaVisitingRecordMapper mediaVisitingRecordMapper;

    @Override
    public MediaVisitingRecordMapper getMapper() {
        return mediaVisitingRecordMapper;
    }

    public long countMediaVisitingRecord(String mediaId) {
        MediaVisitingRecordExample example = new MediaVisitingRecordExample();
        example.createCriteria().andMediaIdEqualTo(mediaId);
        return countByExample(example);
    }

    public List<MediaVisitingRecord> listMediaVisitingRecord(String mediaId, Page page) {
        MediaVisitingRecordExample example = new MediaVisitingRecordExample();
        example.createCriteria().andMediaIdEqualTo(mediaId);

        page.setTotalCount(countByExample(example));

        example.setOrderByClause("create_time desc");
        example.setLimitStart(page.limitStart());
        example.setLimitEnd(page.limitEnd());

        return selectByExample(example);
    }

    public int deleteById(Long visitingId) {
       return deleteByPrimaryKey(visitingId);
    }

    public int batchInsertVisitor(String mediaId, List<String> visitorIds) {
        return mediaVisitingRecordMapper.batchInsertVisitors(mediaId,visitorIds);
    }
}
