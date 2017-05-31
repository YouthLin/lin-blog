package com.youthlin.blog.util;

import com.google.common.base.Charsets;
import com.youthlin.blog.model.bo.LoginInfo;
import com.youthlin.blog.model.enums.Role;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Map;

/**
 * 创建： lin
 * 时间： 2017-05-05 17:44
 */
public class ServletUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServletUtil.class);

    public static String getHostLink(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        int indexOfProtocol = url.indexOf("://") + 3;
        String protocol = url.substring(0, indexOfProtocol);
        url = url.substring(indexOfProtocol);
        if (url.contains("/")) {
            url = url.substring(0, url.indexOf("/"));
        }
        url = protocol + url + request.getContextPath();
        return url;
    }

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
        byte[] decode = new byte[0];
        try {
            decode = Base64.getDecoder().decode(base64String.getBytes(Charsets.UTF_8));
        } catch (Exception ignore) {
        }
        return new String(decode);
    }

    public static String filterHtml(String source) {
        if (!StringUtils.hasText(source)) {
            return "";
        }
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

    public static String filterXss(String html) {
        if (!StringUtils.hasText(html)) {
            return "";
        }
        return Jsoup.clean(html, Whitelist.relaxed()
                .addAttributes("a", "target")
                .addProtocols("a", "href", "#"));
    }

    public static String getGravatarUrl(String email, int size) {
        return Gravatar.withEmail(email).defaults(Gravatar.DefaultType.MONSTERID).size(size).getUrl();
    }

    /**
     * 当前用户的权限小于指定角色权限时，返回重定向地址。有权限则返回 null
     */
    public static String checkRole(HttpServletRequest request, Role role) {
        Role currentUserRole = (Role) request.getAttribute(Constant.K_ROLE);
        if (currentUserRole == null || currentUserRole.getCode() < role.getCode()) {
            return Constant.REDIRECT_TO_PROFILE;
        }
        return null;
    }

    public static String substringHtml(String html, int length) {
        if (!StringUtils.hasText(html)) {
            return "";
        }
        Document document = Jsoup.parse(html);
        String text = document.body().text();
        if (text.length() > length) {
            text = text.substring(0, length);
        }
        return text;
    }

    public static int[] parsePageIndexAndSize(Map<String, String> params) {
        int[] result = new int[2];
        String page = params.get("page");
        String size = params.get("size");
        int pageIndex = 1;
        try {
            pageIndex = Integer.parseInt(page);
        } catch (NumberFormatException ignore) {
        }
        int pageSize = Constant.DEFAULT_FRONT_PAGE_SIZE;
        try {
            pageSize = Integer.parseInt(size);
        } catch (NumberFormatException ignore) {
        }
        result[0] = pageIndex;
        result[1] = pageSize;
        return result;
    }
}
