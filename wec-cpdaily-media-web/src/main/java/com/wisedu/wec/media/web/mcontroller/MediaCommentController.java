package com.wisedu.wec.media.web.mcontroller;

import com.wisedu.wec.media.biz.service.MediaCommentService;
import com.wisedu.wec.media.common.base.Page;
import com.wisedu.wec.media.common.base.controller.BaseController;
import com.wisedu.wec.media.common.base.response.BaseRestResponse;
import com.wisedu.wec.media.common.base.response.PageRestResponse;
import com.wisedu.wec.media.common.param.MediaCommentParam;
import com.wisedu.wec.media.common.vo.MediaCommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/v3/media/mobile/comment", produces = "application/json")
public class MediaCommentController extends BaseController {
    @Autowired
    private MediaCommentService mediaCommentService;

    @RequestMapping("/add")
    @ResponseBody
    public BaseRestResponse addMediaComment(@Valid MediaCommentParam mediaCommentParam, BindingResult result) {
        handleHibernateError(result);
        mediaCommentService.addMediaComment(mediaCommentParam);
        return createReponse();
    }

    @RequestMapping("/list")
    @ResponseBody
    public PageRestResponse<MediaCommentVo> listMediaComment(String mediaId, Page page) {
        List<MediaCommentVo> mediaCommentVos = mediaCommentService.listMediaComment(mediaId, page);
        return createPagedResponse(mediaCommentVos,page);
    }

    @RequestMapping("/child/list")
    @ResponseBody
    public BaseRestResponse listChildMediaComment(Long id, String mediaId, Page page) {
        return createPagedResponse(mediaCommentService.listChildMediaComment(id, mediaId, page),page);
    }

    @RequestMapping("/get")
    @ResponseBody
    public BaseRestResponse getMediaComment(Long id) {
        return createReponse(mediaCommentService.getMediaComment(id));
    }

    @RequestMapping("/delete")
    @ResponseBody
    public BaseRestResponse deleteMediaComment(Long id) {
        mediaCommentService.deleteMediaComment(id);
        return createReponse();
    }

}
