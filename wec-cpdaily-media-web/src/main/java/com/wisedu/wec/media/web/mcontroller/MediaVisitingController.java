package com.wisedu.wec.media.web.mcontroller;

import com.wisedu.wec.media.biz.service.MediaVisitingService;
import com.wisedu.wec.media.common.base.Page;
import com.wisedu.wec.media.common.base.controller.BaseController;
import com.wisedu.wec.media.common.base.response.BaseRestResponse;
import com.wisedu.wec.media.common.base.response.PageRestResponse;
import com.wisedu.wec.media.common.base.response.RestResponse;
import com.wisedu.wec.media.common.param.MediaVisitingRecordParam;
import com.wisedu.wec.media.common.vo.MediaVisitingRecordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/v3/media/mobile/visiting", produces = "application/json")
public class MediaVisitingController extends BaseController {

    @Autowired
    private MediaVisitingService mediaVisitingService;

    @RequestMapping("/add")
    @ResponseBody
    public BaseRestResponse addMediaVisitingRecord(@Valid MediaVisitingRecordParam visitingParam, BindingResult result) {
        handleHibernateError(result);
        mediaVisitingService.addMediaVisiting(visitingParam);
        return createReponse();
    }

    @RequestMapping("/list")
    @ResponseBody
    public PageRestResponse<MediaVisitingRecordVo> listMediaVisitingRecord(String mediaId, Page page) {
        return createPagedResponse( mediaVisitingService.listMediaVisitingRecord(mediaId,page),page);
    }

    @RequestMapping("/count")
    @ResponseBody
    public RestResponse<Long> countMediaVisitingRecord(String mediaId) {
        return createReponse( mediaVisitingService.countMediaVisitingRecord(mediaId));
    }
    @RequestMapping("/delete")
    @ResponseBody
    public BaseRestResponse deleteMediaVisitingRecord(Long visitingId) {
        mediaVisitingService.deleteMediaVisiting(visitingId);
        return createReponse();
    }
}
