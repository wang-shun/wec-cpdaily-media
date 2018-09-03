package com.wisedu.wec.media.biz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wisedu.wec.media.common.old.po.MediaRankRequest;
import com.wisedu.wec.media.common.old.po.RankMedia;
import com.wisedu.wec.media.common.old.po.RankMediaResponse;
import com.wisedu.wec.media.dal.mybatis.MediaRankMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author zsl
 */
@Service
public class MediaRankingService {

    private static final Logger logger = LoggerFactory.getLogger(MediaRankingService.class);

    @Value("${oss.admin.cdn.endpoint}")
    private String imgCdnPrefix;
    @Autowired
    private MediaRankMapper mediaRankMapper;

    public RankMediaResponse getMediaRankings(MediaRankRequest mediaRankRequest) {
        RankMediaResponse mediaRankResponse = new RankMediaResponse();
        List<RankMedia> medias = new ArrayList<>();
        int total = 0;

        String tenantId = mediaRankRequest.getTenantId();
        String periodType = mediaRankRequest.getPeriodType();
        int periodIndex = mediaRankRequest.getPeriodIndex();

        int pageSize = mediaRankRequest.getPageSize();
        int pageNumber = mediaRankRequest.getPageNum();
        int start = (pageNumber - 1) * pageSize;

        String periodFirstDay = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calendar = Calendar.getInstance();
        if (periodType.toUpperCase().equals("WEEK")) {
          // 上/上上周
          calendar.setFirstDayOfWeek(Calendar.MONDAY);
          calendar.setTime(new Date());
          calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
          calendar.add(Calendar.DATE, 7 * periodIndex);
        } else {
          // 上/上上个月
          calendar.add(Calendar.MONTH, periodIndex);
          calendar.set(Calendar.DAY_OF_MONTH, 1);
        }

        periodFirstDay = sdf.format(calendar.getTime());

        Page<Object> page = null;
        if(pageNumber>0 && pageSize>0){
            page = PageHelper.startPage(pageNumber, pageSize);
        }

        medias = mediaRankMapper.getRankingMedias(periodType, periodFirstDay, tenantId);
        for (RankMedia media : medias) {
            media.setIcon(convertOssFile(media.getIcon()));
        }

        mediaRankResponse.setMedias(medias);
        mediaRankResponse.setPageNum(pageNumber);
        if (pageNumber>0 && pageSize>0) {
            mediaRankResponse.setTotal(page.getTotal());
        }
        return mediaRankResponse;
    }

    private String convertOssFile(String ossFile) {
        if (StringUtils.isNotBlank(ossFile) && !ossFile.startsWith("http")) {

            if (!ossFile.startsWith("/")) {
                ossFile = "/" + ossFile;
            }
            ossFile = imgCdnPrefix + ossFile;
        }
        return ossFile;
    }

}
