package com.wisedu.wec.media.biz.service;

import com.wisedu.wec.media.common.base.Page;
import com.wisedu.wec.media.common.vo.MediaFansVo;

import java.util.List;

public interface MediaFansService {
    int toggleFollow(String loginUserId, String mediaId);

    long countMediaFans(String mediaId);

    List<MediaFansVo> listMediaFans(String mediaId, Page page);
}
