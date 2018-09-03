package com.wisedu.wec.media.web.mcontroller;

import com.wisedu.wec.media.biz.service.MediaFansService;
import com.wisedu.wec.media.common.base.Page;
import com.wisedu.wec.media.common.base.controller.BaseController;
import com.wisedu.wec.media.common.base.response.BaseRestResponse;
import com.wisedu.wec.media.common.base.response.PageRestResponse;
import com.wisedu.wec.media.common.base.response.RestResponse;
import com.wisedu.wec.media.common.old.context.ThreadLocalUserInfo;
import com.wisedu.wec.media.common.vo.MediaFansVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/v3/media/mobile/fans", produces = "application/json")
public class MediaFansController extends BaseController {

    @Autowired
    private MediaFansService mediaFansService;

    @RequestMapping("/toggle")
    @ResponseBody
    public BaseRestResponse toggleFollow(String mediaId) {
        String loginUserId = ThreadLocalUserInfo.getContext().getLoginUserId();
        mediaFansService.toggleFollow(loginUserId,mediaId);
        return createReponse();
    }

    @RequestMapping("/list")
    @ResponseBody
    public PageRestResponse<MediaFansVo> listMediaFans(String mediaId, Page page) {
        return createPagedResponse(mediaFansService.listMediaFans(mediaId,page), page);
    }

    @RequestMapping("/count")
    @ResponseBody
    public RestResponse<Long> countMediaFans(String mediaId) {
        return createReponse(mediaFansService.countMediaFans(mediaId));
    }
}
