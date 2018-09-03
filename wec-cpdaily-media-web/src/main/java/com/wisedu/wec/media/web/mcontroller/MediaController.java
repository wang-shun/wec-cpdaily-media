package com.wisedu.wec.media.web.mcontroller;

import com.wisedu.wec.media.biz.service.CampusMediaService;
import com.wisedu.wec.media.common.base.controller.BaseController;
import com.wisedu.wec.media.common.base.response.BaseRestResponse;
import com.wisedu.wec.media.common.base.response.ListRestResponse;
import com.wisedu.wec.media.common.old.context.ThreadLocalUserInfo;
import com.wisedu.wec.media.common.old.vo.CampusMediaVo;
import com.wisedu.wec.media.common.vo.CampusMediaHomeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping(value = "/v3/media/mobile", produces = "application/json")
public class MediaController extends BaseController {

    @Autowired
    private CampusMediaService campusMediaService;

    /**
     * 升级校园号
     * @param mediaId
     * @return
     */
    @RequestMapping("/upgrade")
    @ResponseBody
    public BaseRestResponse mediaUpgrade(String mediaId) {
        String personId = ThreadLocalUserInfo.getContext().getPersonId();
        String userId = ThreadLocalUserInfo.getContext().getUserId();
        campusMediaService.mediaUpgrade(mediaId,personId,userId);
        return createReponse();
    }

    @RequestMapping("/switch")
    @ResponseBody
    public BaseRestResponse mediaSwitch(String mediaId) {
        campusMediaService.mediaSwitch(mediaId,ThreadLocalUserInfo.getContext());
        return createReponse();
    }

    /**
     * 查询管理的新版校园号列表
     * @return
     */
    @RequestMapping(value = "/list/manage")
    @ResponseBody
    public ListRestResponse<CampusMediaVo> listManageMedia() {
        String personId = ThreadLocalUserInfo.getContext().getPersonId();
        return createListReponse(campusMediaService.listManageMedia(personId));
    }

    @RequestMapping("/info")
    @ResponseBody
    public BaseRestResponse mediaInfo(String mediaId) {
        String personId = ThreadLocalUserInfo.getContext().getPersonId();
        String loginUserId = ThreadLocalUserInfo.getContext().getLoginUserId();
        return createReponse( campusMediaService.mediaInfo(mediaId,personId,loginUserId));
    }

}
