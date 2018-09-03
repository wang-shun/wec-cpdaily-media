package com.wisedu.wec.media.biz.service;

import com.wisedu.wec.media.common.base.Page;
import com.wisedu.wec.media.common.param.MediaCommentParam;
import com.wisedu.wec.media.common.vo.MediaCommentVo;

import java.util.List;

public interface MediaCommentService {
    int addMediaComment(MediaCommentParam mediaComment);

    List<MediaCommentVo> listMediaComment(String mediaId, Page page);

    List<MediaCommentVo> listChildMediaComment(Long id, String mediaId, Page page);

    MediaCommentVo getMediaComment(Long id);

    int deleteMediaComment(Long id);

    void exception();
}
