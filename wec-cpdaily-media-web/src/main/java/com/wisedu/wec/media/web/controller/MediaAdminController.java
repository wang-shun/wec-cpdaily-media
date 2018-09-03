package com.wisedu.wec.media.web.controller;

import com.wisedu.wec.media.biz.old.controller.BaseController;
import com.wisedu.wec.media.biz.old.service.impl.MediaAdminService;
import com.wisedu.wec.media.biz.old.service.impl.UserTreeService;
import com.wisedu.wec.media.common.old.po.MediaAdmins;
import com.wisedu.wec.media.common.old.po.MediaManager;
import com.wisedu.wec.media.common.old.vo.CommonVO;
import com.wisedu.wec.media.common.old.vo.Dept;
import com.wisedu.wec.media.common.old.vo.PageVo;
import com.wisedu.wec.media.common.old.vo.UserWithGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class MediaAdminController extends BaseController {

  @Autowired
  private UserTreeService userTreeService;
  @Autowired
  private MediaAdminService mediaAdminService;

  // 查看管理员
  @RequestMapping(value = "/admins", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
  public CommonVO queryAdminsByMediaId(@RequestParam String mediaId) {
    List<MediaManager> admins =
            mediaAdminService.queryAdminsByMediaId(mediaId);

    return successResponseWithData(admins);
  }

  // 查看粉丝
  @RequestMapping(value = "/fans", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
  public CommonVO queryFansByMediaId(@RequestParam String mediaId,
                                     @RequestParam String name, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
    PageVo<UserWithGroup> pageVo =
            mediaAdminService.queryFansByMediaId(mediaId, name, pageNum, pageSize);

    return successResponseWithData(pageVo);
  }
  /**
   * 添加校园号管理员
   *
   * @param mediaAdmins
   * @return
   */
  @RequestMapping(value = "/admins/add", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
  public CommonVO addAdmins(@RequestBody MediaAdmins mediaAdmins) {

    mediaAdminService.addMediaAdmins(mediaAdmins);

    return successResponse();
  }

  /**
   * 删除校园号管理员
   *
   * @param mediaAdmins
   * @return
   */
  @RequestMapping(value = "/admins/del", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
  public CommonVO delAdmins(@RequestBody MediaAdmins mediaAdmins) {

    mediaAdminService.delMediaAdmins(mediaAdmins);

    return successResponse();

  }


}
