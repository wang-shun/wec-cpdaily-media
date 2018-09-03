package com.wisedu.wec.media.biz.old.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wisedu.wec.media.biz.old.service.impl.UserTreeService;
import com.wisedu.wec.media.common.old.vo.CommonVO;
import com.wisedu.wec.media.common.old.vo.Dept;
import com.wisedu.wec.media.common.old.vo.PageVo;
import com.wisedu.wec.media.common.old.vo.UserWithGroup;


@RestController
@RequestMapping("/v3/campusmedia/user/tree")
public class UserTreeController extends BaseController {

  @Autowired
  private UserTreeService userTreeService;

  /**
   * 我的用户组
   */
  @RequestMapping(value = "/mine", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
  public CommonVO myUserGroup() {

    Dept userGroup = userTreeService.queryUserGroup();

    return successResponseWithData(userGroup);

  }

  /**
   * 公共用户组
   */
  @RequestMapping(value = "/common", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
  public CommonVO commonUserGroup() {

    Dept dept = userTreeService.queryCommonUserGroup();

    return successResponseWithData(dept);

  }

  @RequestMapping(value = "/query", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
  public CommonVO queryUserListByGroupId(@RequestParam String groupId,
      @RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam String optType) {

    PageVo<UserWithGroup> pageVo =
        userTreeService.queryUsersByGroupId(groupId, pageNum, pageSize, optType);

    return successResponseWithData(pageVo);
  }

  @RequestMapping(value = "/search", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
  public CommonVO searchUserListByName(@RequestParam String groupId, @RequestParam Integer pageNumber,
      @RequestParam Integer pageSize, @RequestParam String optType, @RequestParam(required=false) String name) {

    PageVo<UserWithGroup> pageVo =
        userTreeService.searchUsersByName(groupId, pageNumber, pageSize, optType, name);

    return successResponseWithData(pageVo);
  }

}
