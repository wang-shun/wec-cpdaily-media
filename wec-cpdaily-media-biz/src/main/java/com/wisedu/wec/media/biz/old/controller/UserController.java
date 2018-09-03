package com.wisedu.wec.media.biz.old.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wisedu.wec.media.biz.old.service.impl.UserService;
import com.wisedu.wec.media.common.old.exception.BadRequestException;
import com.wisedu.wec.media.common.old.po.CpdailyUser;
import com.wisedu.wec.media.common.old.vo.CommonVO;
import com.wisedu.wec.media.common.old.vo.UserWithGroup;


/**
 * Created by dyf on 17/10/27.
 */
@RestController("oldUserController")
@RequestMapping("/v3/campusmedia")
public class UserController extends BaseController {

  @Value("${cpdaily.downloadUrl}")
  private String cpdailyDownloadUrl;

  @Value("${login.qrcodeHost}")
  private String qrcodeHost;

  @Autowired
  private UserService oldUserService;

  /**
   * 获取用户信息
   *
   * @param userId 用户id
   * @return
   */
  @RequestMapping(value="/userInfo/{userId}", method = RequestMethod.GET)
  public CommonVO userInfoById(@PathVariable String userId) {
    CpdailyUser user = oldUserService.selectByUserId(userId);
    if (null == user) {
      return failResponseWithMsg("用户不存在");
    } else {
      return successResponseWithData(user);
    }
  }

  /**
   * 批量获取用户信息
   *
   * @param
   * @return
   */
  @RequestMapping(value = "/userInfosBatch", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
  public CommonVO userInfosByIds(@RequestBody Map<String, ArrayList<String>> ids) {
    ArrayList<String> idArray = ids.get("ids");
    if (idArray == null) {
      return failResponseWithMsg("key ids 不能为空");
    }
    List<CpdailyUser> users = oldUserService.usersByIds(idArray);
    return successResponseWithData(users);
  }

  /**
   * 批量获取用户头像和名字
   *
   * @param
   * @return
   */
  @RequestMapping(value = "/userInfos/chat", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
  public CommonVO userInfos4Chat(@RequestBody List<String> userIds) {
    if (CollectionUtils.isEmpty(userIds)) {
      throw new BadRequestException("userIds is empty.", "-1");
    }
    List<UserWithGroup> users = oldUserService.users4ChatByIds(userIds);
    return successResponseWithData(users);
  }

}
