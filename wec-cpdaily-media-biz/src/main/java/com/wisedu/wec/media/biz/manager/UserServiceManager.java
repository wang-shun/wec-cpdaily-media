package com.wisedu.wec.media.biz.manager;

import com.wisedu.wec.cpdaily.user.dubbo.dto.CpdailyUserBaseDTO;
import com.wisedu.wec.media.common.vo.CpdailyUserWithTagTopknotVo;

import java.util.List;

public interface UserServiceManager {
    List<String> listFansIdByUserId(String userId);

    List<String> listVisitIdByUserId(String userId);

    CpdailyUserBaseDTO getUserBaseByUserId(String userId);

    void disableUser(String userId);

    List<CpdailyUserBaseDTO> listUserBaseByPwid(String personId);

    List<CpdailyUserWithTagTopknotVo> listUserWithTagTopknotByUserIdList(List<String> userIds);
}
