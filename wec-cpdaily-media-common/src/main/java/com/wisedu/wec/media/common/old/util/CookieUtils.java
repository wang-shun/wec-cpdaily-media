package com.wisedu.wec.media.common.old.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cookie工具类
 *
 * @author 01112143
 * @since 1.0.0
 */
public class CookieUtils {

    /**
     * 获取cookieName对应的cookie值
     *
     * @param request    request请求
     * @param cookieName cookie的name
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (!ArrayUtils.isEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return "";
    }

    /**
     * 设置cookie
     * @param response
     * @param name
     * @param value
     */
    public static void addCookie(HttpServletResponse response, String name, String value , boolean secure) {
        addCookie(response, name, value, null, null, -1, false, true);
    }

    /**
     * 设置cookie
     *
     * @param response
     * @param name
     * @param value
     * @param domain
     * @param path
     * @param maxAge
     * @param secure
     * @param httpOnly
     * @since 0.1
     */
    public static void addCookie(HttpServletResponse response, String name, String value,
                                 String domain, String path, int maxAge, boolean secure, boolean httpOnly) {
        Cookie cookie = new Cookie(name, value);
        if (StringUtils.isNotBlank(domain)) {
            cookie.setDomain(domain);
        }
        if (StringUtils.isNotBlank(path)) {
            cookie.setPath(path);
        } else {
            cookie.setPath("/");
        }
        // 0的时候表示删除，这不是我们想要的，直接置为-1
        if (maxAge == 0) {
            cookie.setMaxAge(-1);
        } else {
            cookie.setMaxAge(maxAge);
        }
        cookie.setSecure(secure);
        cookie.setHttpOnly(httpOnly);
        response.addCookie(cookie);
    }

    /**
     * 清除cookie
     * @param response
     * @param name
     */
    public static void removeCookie(HttpServletResponse response, String name){
        removeCookie(response, name,"","",false,false);
    }

    /**
     * 删除response中的cookie
     *
     * @param response
     * @param name
     * @param domain
     * @param path
     * @param secure
     * @param httpOnly
     */
    public static void removeCookie(HttpServletResponse response, String name,
                                    String domain, String path, boolean secure, boolean httpOnly) {
        Cookie cookie = new Cookie(name, "");
        if (StringUtils.isNotBlank(domain)) {
            cookie.setDomain(domain);
        }
        if (StringUtils.isNotBlank(path)) {
            cookie.setPath(path);
        } else {
            cookie.setPath("/");
        }
        cookie.setMaxAge(0);
        cookie.setSecure(secure);
        cookie.setHttpOnly(httpOnly);
        response.addCookie(cookie);
    }


}
