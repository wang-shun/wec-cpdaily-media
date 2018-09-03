package com.wisedu.wec.media.web.filter;

import com.wisedu.wec.media.biz.redis.RedisService;
import com.wisedu.wec.media.biz.service.impl.AuthService;
import com.wisedu.wec.media.common.context.LoginUserContext;
import com.wisedu.wec.media.web.controller.LoginController;
import com.wisedu.wec.media.web.controller.MediaLoginException;
import org.apache.commons.lang3.StringUtils;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.wisedu.wec.media.common.old.constants.RedisConstants;
import com.wisedu.wec.media.common.old.context.ThreadLocalUserInfo;
import com.wisedu.wec.media.common.old.context.UserInfoContext;
import com.wisedu.wec.media.common.old.util.CookieUtils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Objects;

@Component
public class LoginFilter extends BaseFilter implements Filter {

    @Autowired
    private RedisService redisService;

    @Autowired
    private AuthService authService;

    @Autowired
    private LoginController loginController;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestUri = httpRequest.getRequestURI();
        if (!isNoAuthRequest(requestUri)) {

            // 头部取扫码的openKey
            /*String loginUserInfoStr = httpRequest.getHeader(RedisConstants.CPDAILY_USERINFO_HEADER_KEY);
            if (StringUtils.isEmpty(loginUserInfoStr)) {
                logger.error("无扫码用户信息");
                returnError(httpResponse, "403", "无扫码用户信息");
                return;
            }*/

            UserInfoContext userInfoContext = null;

            // 从cookie取自己存的sessionKey，无：初次登录；有，后续访问
            String mediaLoginKey = CookieUtils.getCookieValue(httpRequest, RedisConstants.MEDIA_LOGIN_KEY);
            if (StringUtils.isNotEmpty(mediaLoginKey)) {
                userInfoContext = authService.getLoginUserContextFromCache(mediaLoginKey);
            }

            //app端登录方式
            String userId = httpRequest.getHeader("cpdaily-userid");
            String personId = httpRequest.getHeader("cpdaily-pwid");

            //pc端登录方式
            if (StringUtils.isEmpty(userId) && StringUtils.isEmpty(personId) && Objects.isNull(userInfoContext)) {
                LoginUserContext user = getLoginUserContext(httpRequest);
                if (user != null) {
                    userId = user.getCpdailyUserWid();
                    personId = user.getCpdailyPersonId();
                }
            }

            // 初次登录
            if (Objects.isNull(userInfoContext)) {
                try {
                    userInfoContext = loginController.innerLogin(personId, userId, httpResponse);

                } catch (MediaLoginException mediaLoginEx) {
                    returnError(httpResponse, "999", "校园号身份不能登录");
                    return;
                }
            }

            if (Objects.isNull(userInfoContext)) {
                returnError(httpResponse, "403", "无登录信息");
                return;
            }
            ThreadLocalUserInfo.setContext(userInfoContext);
        }

        chain.doFilter(httpRequest, httpResponse);
    }

    private LoginUserContext getLoginUserContext(HttpServletRequest httpRequest) {
        String loginUserInfoStr = httpRequest.getHeader(RedisConstants.CPDAILY_USERINFO_HEADER_KEY);
        if (StringUtils.isEmpty(loginUserInfoStr)) {
            return null;
        }
        if (StringUtils.isNotEmpty(loginUserInfoStr)) {
            try {
                loginUserInfoStr = URLDecoder.decode(loginUserInfoStr, "UTF-8");
            } catch (Exception e) {
                return null;
            }
        }

        LoginUserContext user = new Gson().fromJson(loginUserInfoStr, LoginUserContext.class);
        if (null == user || StringUtils.isEmpty(user.getCpdailyUserWid())) {
            return null;
        }
        return user;
    }

    private void returnError(HttpServletResponse response, String code, String message) {
        // 删除Cookie
        CookieUtils.removeCookie(response, RedisConstants.MEDIA_LOGIN_KEY);

        responseErrorWithJson(response, code, message);
    }

    private boolean isMobileIndex(String requestUri) {
        boolean startsWith = requestUri.startsWith("/wec-cpdaily-media/v3/campusmedia/account/") ||
                requestUri.startsWith("/wec-cpdaily-media/v3/media/");
        return !Strings.isNullOrEmpty(requestUri) && startsWith;
    }

    @Override
    public void destroy() {
        ThreadLocalUserInfo.clearContext();
    }

}
