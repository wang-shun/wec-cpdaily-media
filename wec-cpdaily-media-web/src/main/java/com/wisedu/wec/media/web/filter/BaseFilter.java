package com.wisedu.wec.media.web.filter;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.wisedu.wec.media.common.base.response.BaseRestResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class BaseFilter {

  private static final Logger logger = LoggerFactory.getLogger(BaseFilter.class);

  /**
   * 不需要扫码登陆
   *
   * @param uri
   * @return
   */
  protected boolean isNoAuthRequest(String uri) {
    return !Strings.isNullOrEmpty(uri)
        && (uri.startsWith("/wec-cpdaily-media/login")
            || uri.startsWith("/wec-cpdaily-media/logout")
            || uri.startsWith("/wec-cpdaily-media/v3/campusmedia/gateway/msg/view")
            || uri.startsWith("/wec-cpdaily-media/v3/campusmedia/rankings")
            || uri.startsWith("/wec-cpdaily-media/index.html")
            || uri.startsWith("/wec-cpdaily-media/mobile/index.html")
            || uri.startsWith("/wec-cpdaily-media/static/")
            || uri.startsWith("/wec-dev-back/login")
            || uri.startsWith("/wec-dev-back/logout")
            || uri.startsWith("/wec-dev-back/v3/campusmedia/gateway/msg/view")
            || uri.startsWith("/wec-dev-back/v3/campusmedia/rankings")
            || uri.endsWith(".js")
            || uri.endsWith(".vue")
            || uri.endsWith(".png")
            || uri.endsWith(".jpg")
            || uri.endsWith(".jpeg")
            || uri.endsWith(".css"));
  }

  /**
   * 只需要扫码主账号登录
   *
   * @param uri
   * @return
   */
  protected boolean isMainAccountNeed(String uri) {
    return !Strings.isNullOrEmpty(uri) && (uri.startsWith("/v3/campusmedia/account")
        || uri.startsWith("/v3/campusmedia/uploadFile")
        || uri.startsWith("/v3/campusmedia/uploadFileBase64"));
  }

  /**
   * 只需要媒体号，不需要认证
   *
   * @param uri
   * @return
   */
  protected boolean isMediaNoAuthNeed(String uri) {
    return !Strings.isNullOrEmpty(uri) && uri.startsWith("/v3/campusmedia/setting");
  }

  /**
   * 以JSON格式输出
   *
   * @param response
   */
  protected void responseErrorWithJson(HttpServletResponse response, String code, String message) {

    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json; charset=utf-8");

    PrintWriter writer = null;
    try {
      writer = response.getWriter();
      writer.append(JSONObject.toJSONString(new BaseRestResponse(code, message)));
    } catch (IOException e) {
      logger.error("response getWriter IOException", e);
    } finally {
      if (writer != null) {
        writer.close();
      }
    }

  }

}
