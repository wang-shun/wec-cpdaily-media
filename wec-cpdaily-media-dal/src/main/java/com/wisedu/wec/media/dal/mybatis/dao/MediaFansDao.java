package com.wisedu.wec.media.dal.mybatis.dao;

import com.wisedu.wec.media.common.base.Page;
import com.wisedu.wec.media.common.base.dao.BaseDao;
import com.wisedu.wec.media.common.constants.MediaFansConst;
import com.wisedu.wec.media.common.old.po.MediaFans;
import com.wisedu.wec.media.common.old.po.MediaFansExample;
import com.wisedu.wec.media.dal.mybatis.MediaFansMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class MediaFansDao extends BaseDao<MediaFans, MediaFansExample, Long, MediaFansMapper> {
    @Autowired
    private MediaFansMapper mediaFansMapper;

    @Override
    public MediaFansMapper getMapper() {
        return mediaFansMapper;
    }

    public MediaFans get(String mediaId, String userId) {
        MediaFansExample example = new MediaFansExample();
        example.createCriteria().andFansIdEqualTo(userId).andMediaIdEqualTo(mediaId);
        return getByExample(example);
    }

    public int updateStatus(Long id, byte status) {
        MediaFans mediaFans = new MediaFans();
        mediaFans.setId(id);
        mediaFans.setStatus(status);
        return updateByPrimaryKeySelective(mediaFans);
    }

    public long countFans(String mediaId) {
        MediaFansExample example = new MediaFansExample();
        example.createCriteria().andMediaIdEqualTo(mediaId).andStatusEqualTo(MediaFansConst.Status.ENABLE);
        return countByExample(example);
    }

    public List<MediaFans> listMediaFans(String mediaId, Page page) {
        MediaFansExample example = new MediaFansExample();
        example.createCriteria().andMediaIdEqualTo(mediaId).andStatusEqualTo(MediaFansConst.Status.ENABLE);

        page.setTotalCount(countByExample(example));
        example.setLimitStart(page.limitStart());
        example.setLimitEnd(page.limitEnd());

        return selectByExample(example);
    }

    public boolean isFans(String mediaId, String userId) {
        MediaFans mediaFans = get(mediaId, userId);
        return mediaFans != null && Objects.equals(mediaFans.getStatus(), MediaFansConst.Status.ENABLE);
    }

    public int batchInsertFans(String mediaId, List<String> fansIds) {
        return mediaFansMapper.batchInsertFans(mediaId,fansIds);
    }
}
