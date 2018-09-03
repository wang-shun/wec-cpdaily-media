package com.wisedu.wec.media.web.controller;

import com.wisedu.wec.media.biz.old.controller.BaseController;
import com.wisedu.wec.media.biz.old.service.impl.MediaAdminService;
import com.wisedu.wec.media.biz.old.service.impl.UserTreeService;
import com.wisedu.wec.media.biz.service.impl.LogService;
import com.wisedu.wec.media.common.old.po.MediaAdmins;
import com.wisedu.wec.media.common.old.po.MediaLogRequest;
import com.wisedu.wec.media.common.old.po.MediaLogResponse;
import com.wisedu.wec.media.common.old.po.MediaManager;
import com.wisedu.wec.media.common.old.vo.CommonVO;
import com.wisedu.wec.media.common.old.vo.PageVo;
import com.wisedu.wec.media.common.old.vo.UserWithGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/log")
public class MediaLogController extends BaseController {

  @Autowired
  private LogService logService;

  // 查看管理员
  @RequestMapping(value = "/list", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
  public CommonVO queryAdminsByMediaId(@RequestBody MediaLogRequest request) {
    MediaLogResponse response = logService.getLogs(request);
    return successResponseWithData(response);
  }

}
