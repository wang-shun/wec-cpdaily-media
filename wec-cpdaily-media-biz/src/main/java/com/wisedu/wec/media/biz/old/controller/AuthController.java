package com.wisedu.wec.media.biz.old.controller;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Userinfos;
import com.taobao.api.request.OpenimUsersAddRequest;
import com.taobao.api.response.OpenimUsersAddResponse;
import com.wisedu.wec.media.common.old.constants.MediaAuthStatus;
import com.wisedu.wec.media.common.old.exception.BadRequestException;
import com.wisedu.wec.media.common.old.po.CampusMedia;
import com.wisedu.wec.media.common.old.po.CpdailyUser;
import com.wisedu.wec.media.common.old.vo.CommonVO;
import com.wisedu.wec.media.dal.mybatis.CampusMediaMapper;
import com.wisedu.wec.media.dal.mybatis.CpdailyUserMapper;

@RestController
public class AuthController extends BaseController {

  private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private CampusMediaMapper campusMediaMapper;

  @Autowired
  private TaobaoClient taobaoClient;

  @Autowired
  private CpdailyUserMapper cpdailyUserMapper;

  @RequestMapping(value = "/v3/campusmedia/gateway/auth/{mediaId}/{sign}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
  public CommonVO authPass(@PathVariable String mediaId, @PathVariable String sign) {

    // 创建im用户
    // 新增User
    // 更新校园号信息

    String s = mediaId + "657QWEc0bLo3+_)(!4#$615a98a1c93";

    String genSign = DigestUtils.md5DigestAsHex(s.getBytes(StandardCharsets.UTF_8));

    if (!StringUtils.equals(genSign, sign)) {
      throw new BadRequestException("param illegal", "-1");
    }

    // 是否已经创建
    CpdailyUser existUser = cpdailyUserMapper.selectByUserId(mediaId);
    if (existUser != null) {
      return failResponseWithMsg("user already creates.");
    }

    CampusMedia campusMedia = campusMediaMapper.selectByPrimaryKey(mediaId);

    if (campusMedia == null) {
      throw new BadRequestException("inexistence for mediaId : " + mediaId, "-1");
    }

    Userinfos imUser = new Userinfos();
    imUser.setNick(campusMedia.getName());
    imUser.setName(campusMedia.getName());
    imUser.setUserid(campusMedia.getWid());
    if (StringUtils.isNotEmpty(campusMedia.getIcon())) {
      imUser.setIconUrl(campusMedia.getIcon() + "!small");
    }
    imUser.setGender("M");

    // 密码
    String unencrypted = campusMedia.getWid() + "cpdaily123";
    String password = DigestUtils.md5DigestAsHex(unencrypted.getBytes(StandardCharsets.UTF_8));
    imUser.setPassword(password.toUpperCase());

    OpenimUsersAddRequest imRequest = new OpenimUsersAddRequest();

    List<Userinfos> userinfoList = new ArrayList<>();
    userinfoList.add(imUser);
    imRequest.setUserinfos(userinfoList);

    try {
      OpenimUsersAddResponse response = taobaoClient.execute(imRequest);

      logger.info("create im user taobao response : " + objectMapper.writeValueAsString(response));

      if (CollectionUtils.isNotEmpty(response.getFailMsg())) {
        return failResponseWithData(response);
      }

    } catch (ApiException e) {
      logger.error("taobao api ApiException", e);
    } catch (JsonProcessingException e) {
      logger.error("JsonProcessingException", e);
    }

    CpdailyUser cpdailyUser = new CpdailyUser();
    cpdailyUser.setWid(campusMedia.getWid());
    cpdailyUser.setImg(campusMedia.getIcon());
    cpdailyUser.setName(campusMedia.getName());
    cpdailyUser.setUserType("MEDIA");
    cpdailyUser.setTenantId(campusMedia.getTenantId());

    cpdailyUser.setUserSrcType("MEDIA");
    cpdailyUser.setUpdateTime(new Date());
    cpdailyUser.setGender("MALE");
    cpdailyUser.setStatus("ENABLE");
    cpdailyUser.setOpenId(campusMedia.getWid());
    cpdailyUser.setDegree("BACHELOR");

    cpdailyUserMapper.insert(cpdailyUser);


    // 更新媒体号状态
    CampusMedia record = new CampusMedia();
    record.setWid(campusMedia.getWid());
    record.setAuthStatus(MediaAuthStatus.AUTHED.toString());
    record.setIsHide("NO");
    record.setSortNo(100);
    record.setStatus("ENABLE");
    campusMediaMapper.updateByPrimaryKeySelective(record);

    return successResponse();
  }
}
