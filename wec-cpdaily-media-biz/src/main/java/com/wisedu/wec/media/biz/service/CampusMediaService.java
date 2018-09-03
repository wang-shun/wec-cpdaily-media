package com.wisedu.wec.media.biz.service;

import com.wisedu.wec.media.common.old.context.UserInfoContext;
import com.wisedu.wec.media.common.old.vo.CampusMediaVo;
import com.wisedu.wec.media.common.vo.CampusMediaHomeVo;

import java.util.List;

public interface CampusMediaService {
    List<CampusMediaVo> listManageMedia(String personId);

    void mediaUpgrade(String mediaId, String personId, String userId);

    void mediaSwitch(String mediaId, UserInfoContext userInfoContext);

    CampusMediaHomeVo mediaInfo(String mediaId, String personId, String loginUserId);
}
