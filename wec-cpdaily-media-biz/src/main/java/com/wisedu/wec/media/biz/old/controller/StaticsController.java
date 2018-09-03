package com.wisedu.wec.media.biz.old.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wisedu.wec.media.biz.old.service.impl.OssService;
import com.wisedu.wec.media.common.old.context.ThreadLocalUserInfo;
import com.wisedu.wec.media.common.old.vo.CommonVO;
import com.wisedu.wec.media.common.old.vo.ImgUploadReqVo;
import com.wisedu.wec.media.common.old.vo.UploadVo;
import com.wisedu.wecloud.commons.util.StringUtil;


@RestController
@RequestMapping("/v3/campusmedia")
public class StaticsController extends BaseController {

  private static final Logger logger = LoggerFactory.getLogger(StaticsController.class);

  @Autowired
  private OssService ossService;

  /**
   * 文件上传，不会保留原始文件名
   *
   */
  @RequestMapping(value = "/uploadFile", produces = {MediaType.APPLICATION_JSON_VALUE})
  public CommonVO uploadImg(@RequestParam("file") MultipartFile file) throws Exception {
    String contentType = file.getContentType();
    String fileName = file.getOriginalFilename();
    String tenantId = ThreadLocalUserInfo.getContext().getTenantId();
    String mediaId;
    if (StringUtil.isNotEmpty(ThreadLocalUserInfo.getContext().getMediaId())) {
      mediaId = ThreadLocalUserInfo.getContext().getMediaId();
    } else {
      mediaId = ThreadLocalUserInfo.getContext().getUserId();
    }
    UploadVo msg =
        ossService.upload(tenantId + "/" + mediaId, fileName, contentType, file.getBytes());
    return successResponseWithData(msg);

  }

  /**
   * 图片上传，保留原始文件名但是替换掉特殊符号
   *
   */
  @RequestMapping(value = "/uploadFileBase64", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
  public CommonVO uploadImgBase64(@RequestBody ImgUploadReqVo data) throws Exception {
    String tenantId = ThreadLocalUserInfo.getContext().getTenantId();
    String mediaId;
    if (StringUtil.isNotEmpty(ThreadLocalUserInfo.getContext().getMediaId())) {
      mediaId = ThreadLocalUserInfo.getContext().getMediaId();
    } else {
      mediaId = ThreadLocalUserInfo.getContext().getUserId();
    }
    UploadVo msg = ossService.uploadBase64(tenantId + "/" + mediaId, data.getData());
    return successResponseWithData(msg);

  }
}
