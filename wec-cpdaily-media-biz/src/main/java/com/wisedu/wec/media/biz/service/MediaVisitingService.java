package com.wisedu.wec.media.biz.service;

import com.wisedu.wec.media.common.base.Page;
import com.wisedu.wec.media.common.param.MediaVisitingRecordParam;
import com.wisedu.wec.media.common.vo.MediaVisitingRecordVo;

import java.util.List;

public interface MediaVisitingService {
    void addMediaVisiting(MediaVisitingRecordParam visitingParam);

    long countMediaVisitingRecord(String mediaId);

    List<MediaVisitingRecordVo> listMediaVisitingRecord(String mediaId, Page page);

    void deleteMediaVisiting(Long  visitingId);
}
