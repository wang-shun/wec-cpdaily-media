package com.wisedu.wec.media.web.controller;

import com.google.gson.Gson;
import com.wisedu.wec.media.biz.service.TenantService;
import com.wisedu.wec.media.biz.service.impl.AuthService;
import com.wisedu.wec.media.common.context.LoginUserContext;
import com.wisedu.wec.media.common.old.constants.RedisConstants;
import com.wisedu.wec.media.common.old.context.ThreadLocalUserInfo;
import com.wisedu.wec.media.common.old.context.UserInfoContext;
import com.wisedu.wec.media.common.old.po.CpdailyUser;
import com.wisedu.wec.media.common.old.util.CookieUtils;
import com.wisedu.wec.media.dal.mybatis.CpdailyUserMapper;
import com.wisedu.wecloud.commons.model.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;


@RestController
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthService authService;

    @Autowired
    private CpdailyUserMapper userMapper;

    @Value("${login.qrcodeHost}")
    private String mediaDomainUrl;

//    final String loginPage = mediaDomainUrl + "/wec-cpdaily-media/index.html#/login";
//    final String mainPage = mediaDomainUrl + "/wec-cpdaily-media/index.html#/outer";
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String mediaLoginKey = CookieUtils.getCookieValue(request, RedisConstants.MEDIA_LOGIN_KEY);
        if (StringUtils.isNotEmpty(mediaLoginKey)) {
            authService.delLoginUserContextFromCache(mediaLoginKey);
        }
        CookieUtils.removeCookie(response, RedisConstants.MEDIA_LOGIN_KEY);

        response.sendRedirect(getLoginPage()); // mediaDomainUrl + "/wec-cpdaily-media/index.html"
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String loginPage = getLoginPage();
        String mainPage = getMainPage();

        try {
            String loginUserInfoStr = request.getHeader(RedisConstants.CPDAILY_USERINFO_HEADER_KEY);
            if (StringUtils.isEmpty(loginUserInfoStr)) {
                throw new RuntimeException("无扫码用户信息");
            }
            if (StringUtils.isNotEmpty(loginUserInfoStr)) {
                try {
                    loginUserInfoStr = URLDecoder.decode(loginUserInfoStr, "UTF-8");
                } catch (Exception e) {
                    throw new RuntimeException("扫码信息解码失败");
                }
            }

            LoginUserContext user = new Gson().fromJson(loginUserInfoStr, LoginUserContext.class);
            if (null == user || StringUtils.isEmpty(user.getCpdailyUserWid())) {
                throw new RuntimeException("扫码信息解析失败");
            }
            this.innerLogin(user.getCpdailyPersonId(),user.getCpdailyUserWid(), response);
        } catch (MediaLoginException mediaEx) {
            logger.error(mediaEx.toString());
            response.sendRedirect(loginPage + "/1");

        } catch (Exception ex) {
            logger.error(ex.toString());
            response.sendRedirect(loginPage);
        }

        logger.info("登录成功，重定向：{}", mainPage);
        response.sendRedirect(mainPage);
    }

    public UserInfoContext innerLogin(String personId,String userId, HttpServletResponse response) throws MediaLoginException {
        // 从 loginUserInfo 到 userInfoContext
        UserInfoContext userInfoContext = authService.generateContextByPerson(personId, userId, null);
        if (null == userInfoContext) {
            throw new RuntimeException("根据登录信息获取上下文信息失败");
        }

        if (userInfoContext.getUserType().toUpperCase().equals("MEDIA")) {
            MediaLoginException mediaLoginException = new MediaLoginException();
            mediaLoginException.setErrorCode("999");
            mediaLoginException.setErrorMsg("校园号不能登录");
            throw mediaLoginException;
        }

        this.saveToRedisAndCookie(userInfoContext, response);
        logger.info("用户{}登录成功",userId);
        return userInfoContext;
    }

    private String getMediaKey (String tenantId, String userId) {
        return "media_key_"  + tenantId + "_" + userId;
    }

    private void saveToRedisAndCookie (UserInfoContext userInfoContext, HttpServletResponse httpResponse) {
        String redisKey = this.getMediaKey(userInfoContext.getTenantId(), userInfoContext.getLoginUserId());
        authService.setLoginUserContextToCache(redisKey, userInfoContext);
        CookieUtils.addCookie(httpResponse, RedisConstants.MEDIA_LOGIN_KEY, redisKey, false);
    }

    private String getLoginPage(){
        return mediaDomainUrl + "/wec-cpdaily-media/index.html#/login";
    }

    private String getMainPage(){
        return  mediaDomainUrl + "/wec-cpdaily-media/index.html#/outer";
    }
}
