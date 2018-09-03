package com.wisedu.wec.media.biz.old.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wisedu.wec.media.biz.old.service.impl.UserGroupService;
import com.wisedu.wec.media.common.old.vo.BrandGroupReq;
import com.wisedu.wec.media.common.old.vo.CommonVO;
import com.wisedu.wec.media.common.old.vo.PageVo;
import com.wisedu.wec.media.common.old.vo.UserGroupVo;


@RestController
@RequestMapping("/v3/campusmedia/user/group")
public class UserGroupController extends BaseController {

  @Autowired
  private UserGroupService userGroupService;

  /**
   * 新建用户组
   */
  @RequestMapping(value = "/add/{groupName}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
  public CommonVO createNewGroup(@PathVariable String groupName) {

    String groupId = userGroupService.addGroupByName(groupName);

    return successResponseWithData(groupId);
  }
  

  /**
   * 获取用户自定义的用户组
   */
  @RequestMapping(value = "/list", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
  public CommonVO getMyGroups(String groupName, Integer pageNum, Integer pageSize) {

    PageVo<UserGroupVo> groups = userGroupService.getMyGroupsPaged(groupName, pageNum, pageSize);

    return successResponseWithData(groups);
  }

  /**
   * 把用户加入用户组
   */
  @RequestMapping(value = "/action", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
  public CommonVO brandGroup(@RequestBody BrandGroupReq brandGroupReq) {

    List<String> groupIds = brandGroupReq.getGroupIds();
    List<String> userIds = brandGroupReq.getUserIds();

    if (CollectionUtils.isEmpty(groupIds) || CollectionUtils.isEmpty(userIds)) {
      return illegalParameterResponse();
    }

    userGroupService.brandGroup(groupIds, userIds);

    return successResponse();
  }

  /**
   * 只更新一个用户的用户组
   */
  @RequestMapping(value = "/single", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
  public CommonVO brandGroupSingle(@RequestBody BrandGroupReq brandGroupReq) {

    List<String> groupIds = brandGroupReq.getGroupIds();
    List<String> userIds = brandGroupReq.getUserIds();

    if (CollectionUtils.isEmpty(userIds) || userIds.size() > 1) {
      return illegalParameterResponse();
    }

    userGroupService.brandGroupSingle(groupIds, userIds.get(0));

    return successResponse();
  }

  /**
   * 删除用户组
   */
  @RequestMapping(value = "/del/{groupId}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
  public CommonVO removeGroup(@PathVariable String groupId) {

    userGroupService.delGroupById(groupId);

    return successResponse();

  }

}
