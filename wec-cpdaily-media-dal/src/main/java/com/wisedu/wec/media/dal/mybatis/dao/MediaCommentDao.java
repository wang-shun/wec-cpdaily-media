package com.wisedu.wec.media.dal.mybatis.dao;

import com.wisedu.wec.media.common.base.Page;
import com.wisedu.wec.media.common.base.dao.BaseDao;
import com.wisedu.wec.media.common.old.po.MediaComment;
import com.wisedu.wec.media.common.old.po.MediaCommentExample;
import com.wisedu.wec.media.dal.mybatis.MediaCommentMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MediaCommentDao extends BaseDao<MediaComment, MediaCommentExample, Long, MediaCommentMapper> {
    @Autowired
    private MediaCommentMapper mediaCommentMapper;


    @Override
    public MediaCommentMapper getMapper() {
        return mediaCommentMapper;
    }

    public List<MediaComment> listMediaComment(String mediaId, Long parentId, Page page) {
        MediaCommentExample example = new MediaCommentExample();
        example.createCriteria().andMediaIdEqualTo(mediaId).andParentIdEqualTo(parentId).andIsDeleteEqualTo(false);

        if (page != null) {
            long count = countByExample(example);
            page.setTotalCount(count);
            example.setLimitStart(page.limitStart());
            example.setLimitEnd(page.limitEnd());
        }
        example.setOrderByClause("create_time desc ");
        return selectByExample(example);
    }

    public List<MediaComment> listChildComments(String mediaId, Long parentId, Page page) {
        MediaCommentExample example = new MediaCommentExample();
        MediaCommentExample.Criteria criteria = example.createCriteria().andParentIdEqualTo(parentId).andIsDeleteEqualTo(false);
        if (StringUtils.isNotEmpty(mediaId)) {
            criteria.andMediaIdEqualTo(mediaId);
        }
        if (page != null) {
            example.setLimitStart(page.limitStart());
            example.setLimitEnd(page.limitEnd());
        }
        example.setOrderByClause("create_time desc ");
        return selectByExample(example);
    }

    public MediaComment getMediaComment(Long id) {
        MediaComment mediaComment = selectByPrimaryKey(id);
        if (mediaComment == null || mediaComment.getIsDelete()) {
            return null;
        }
        return mediaComment;
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        MediaComment mediaComment = new MediaComment();
        mediaComment.setId(id);
        mediaComment.setIsDelete(true);
        return updateByPrimaryKeySelective(mediaComment);
    }
}
