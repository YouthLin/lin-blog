package com.youthlin.blog.util;

import com.youthlin.blog.model.bo.LoginInfo;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 创建： lin
 * 时间： 2017-05-05 17:44
 */
public class ServletUtil {
    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static String getRemoteIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.hasText(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static Cookie makeCookie(LoginInfo loginInfo) {
        int id = loginInfo.getId();
        String token = loginInfo.getToken();
        String userName = loginInfo.getUserName();
        Cookie cookie = new Cookie(Constant.TOKEN, id + ":" + userName + ":" + token);
        cookie.setHttpOnly(true);
        return cookie;
    }

}
