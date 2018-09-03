package com.wisedu.wec.media.biz.service.impl;


import com.wisedu.wec.media.biz.manager.UserServiceManager;
import com.wisedu.wec.media.biz.service.MediaFansService;

import com.wisedu.wec.media.common.base.Page;
import com.wisedu.wec.media.common.constants.MediaFansConst;
import com.wisedu.wec.media.common.exception.ValidateException;
import com.wisedu.wec.media.common.old.po.CampusMedia;
import com.wisedu.wec.media.common.old.po.MediaFans;
import com.wisedu.wec.media.common.vo.CpdailyUserWithTagTopknotVo;
import com.wisedu.wec.media.common.vo.MediaFansVo;
import com.wisedu.wec.media.dal.mybatis.CampusMediaMapper;
import com.wisedu.wec.media.dal.mybatis.dao.MediaFansDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MediaFansServiceImpl implements MediaFansService {
    @Autowired
    private MediaFansDao mediaFansDao;
    @Autowired
    private UserServiceManager userServiceManager;
    @Autowired
    private CampusMediaMapper campusMediaMapper;


    @Override
    public int toggleFollow(String loginUserId, String mediaId) {
        if (StringUtils.isEmpty(loginUserId) || StringUtils.isEmpty(mediaId)) {
            throw new ValidateException("参数错误");
        }
        CampusMedia campusMedia = campusMediaMapper.selectByMediaId(mediaId);
        if (campusMedia == null) {
            throw new ValidateException("校园号不存在");
        }
        boolean statusEnable = "ENABLE".equals(campusMedia.getStatus()) && "AUTHED".equals(campusMedia.getAuthStatus());
        if (!statusEnable) {
            throw new ValidateException("校园号状态不允许关注");
        }

        MediaFans old = mediaFansDao.get( mediaId,loginUserId);
        if (old != null) {
            byte status = MediaFansConst.Status.ENABLE;
            if (old.getStatus() == MediaFansConst.Status.ENABLE) {
                status = MediaFansConst.Status.DISABLE;
            }
            return mediaFansDao.updateStatus(old.getId(), status);

        }

        MediaFans mediaFans = new MediaFans();
        mediaFans.setFansId(loginUserId);
        mediaFans.setMediaId(mediaId);
        return mediaFansDao.insertSelective(mediaFans);
    }

    @Override
    public long countMediaFans(String mediaId) {
        if (StringUtils.isEmpty(mediaId)) {
            throw new ValidateException("参数错误");
        }
        return mediaFansDao.countFans(mediaId);
    }

    @Override
    public List<MediaFansVo> listMediaFans(String mediaId, Page page) {
        List<MediaFans> mediaFans = mediaFansDao.listMediaFans(mediaId, page);
        if (mediaFans.isEmpty()) {
            return new ArrayList<>();
        }
        List<MediaFansVo> fansVos = mediaFans.stream().map(bean -> {
            MediaFansVo vo = new MediaFansVo();
            BeanUtils.copyProperties(bean, vo);
            return vo;
        }).collect(Collectors.toList());

        List<String> userIds = mediaFans.stream().map(MediaFans::getFansId).filter(StringUtils::isNotEmpty).collect(Collectors.toList());
        List<CpdailyUserWithTagTopknotVo> userWithTagTopknotVos = userServiceManager.listUserWithTagTopknotByUserIdList(userIds);

        for (CpdailyUserWithTagTopknotVo userWithTagTopknotVo : userWithTagTopknotVos) {
            for (MediaFansVo fansVo : fansVos) {
                if (Objects.equals(fansVo.getFansId(), userWithTagTopknotVo.getUserId())) {
                    fansVo.setFans(userWithTagTopknotVo);
                }
            }
        }
        return fansVos;
    }
}
