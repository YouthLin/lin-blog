package com.youthlin.blog.util;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.youthlin.blog.model.bo.LoginInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Base64;

/**
 * 创建： lin
 * 时间： 2017-05-05 17:44
 */
public class ServletUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServletUtil.class);

    public static String getUrl(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        if (StringUtils.hasText(queryString)) {
            return uri + "?" + queryString;
        }
        return uri;
    }

    public static String base64Encode(String source) {
        byte[] encode = Base64.getEncoder().encode(source.getBytes(Charsets.UTF_8));
        return new String(encode);
    }

    public static String base64Decode(String base64String) {
        byte[] decode = Base64.getDecoder().decode(base64String.getBytes(Charsets.UTF_8));
        return new String(decode);
    }

    public static String filterHtml(String source) {
        return source
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;");
    }

    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Cookies = {}", JsonUtil.toJson(cookies));
            }
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    LOGGER.info("Find Cookie = {}", JsonUtil.toJson(cookie));
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
        Cookie cookie = new Cookie(Constant.TOKEN, loginInfo.toCookieValue());
        //Cookie 在客户端都保存 7 天，真正过期时间在meta数据库里记录
        cookie.setMaxAge(Constant.DEFAULT_EXPIRE_DAYS_7_SECOND);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }

}
