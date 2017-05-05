package com.youthlin.blog.service;

import com.google.common.collect.Maps;
import com.youthlin.blog.dao.UserDao;
import com.youthlin.blog.dao.UserMetaDao;
import com.youthlin.blog.model.bo.LoginInfo;
import com.youthlin.blog.model.po.User;
import com.youthlin.blog.model.po.UserMeta;
import com.youthlin.blog.util.Constant;
import com.youthlin.blog.util.JsonUtil;
import com.youthlin.blog.util.MD5Util;
import com.youthlin.blog.util.ServletUtil;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * 创建者： youthlin.chen 日期： 17-4-4.
 */
@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    @Resource
    private UserDao userDao;
    @Resource
    private UserMetaDao userMetaDao;

    public void save(User user) {
        userDao.save(user);
    }

    public boolean login(String username, String md5ByUserNameAndPassword,
                         HttpServletRequest request, HttpServletResponse response) {
        User user = userDao.findByUserName(username);
        if (user != null) {
            String userPass = user.getUserPass();
            if (userPass.length() == Constant.PASS_LEN) {
                String rand = userPass.substring(0, Constant.RAND_LEN);
                String md5WithRand = rand + MD5Util.md5(rand + md5ByUserNameAndPassword);
                boolean succ = md5WithRand.equalsIgnoreCase(userPass);
                if (succ) {
                    saveUserLoginInfo(user, request, response);
                }
                return succ;
            }
        }
        return false;
    }

    @Transactional
    private void saveUserLoginInfo(User user, HttpServletRequest request, HttpServletResponse response) {
        // LoginInfo 保存 infoId, userName, userAgent, userIp, token, expire
        // nextInfoIdMeta 保存下一个 infoId

        UserMeta loginInfoMeta = userMetaDao.findByUserIdAndMetaKey(user.getUserId(), Constant.K_LOGIN_INFO);

        UserMeta nextInfoIdMeta = userMetaDao.findByUserIdAndMetaKey(user.getUserId(), Constant.K_NEXT_LOGIN_ID);

        LoginInfo loginInfo = makeLoginInfo(nextInfoIdMeta, user, request);
        updateLoginInfo(user, loginInfo, loginInfoMeta);
        updateNextId(loginInfo, nextInfoIdMeta, user);

        Cookie cookie = ServletUtil.makeCookie(loginInfo);
        response.addCookie(cookie);
    }

    public boolean checkLoginCookie(User user, HttpServletRequest request, UserMeta loginInfoMeta) {
        String cookieValue = ServletUtil.getCookieValue(request, Constant.TOKEN);
        if (cookieValue == null || loginInfoMeta == null) {
            return false;
        }
        //id:username:token
        String[] split = cookieValue.split(":");
        if (split.length != 3) {
            return false;
        }
        int id;
        try {
            id = Integer.parseInt(split[0]);
        } catch (NumberFormatException e) {
            return false;
        }
        if (!split[1].equals(user.getUserLogin())) {
            return false;
        }
        String token = split[2];
        String loginInfoMetaMetaValue = loginInfoMeta.getMetaValue();
        @SuppressWarnings("unchecked")
        Map<Integer, LoginInfo> loginInfoMap = JsonUtil.fromJson(loginInfoMetaMetaValue, Map.class);
        if (loginInfoMap == null) {
            return false;
        }
        LoginInfo loginInfo = loginInfoMap.get(id);
        return token.equals(loginInfo.getToken())
                && Objects.equals(loginInfo.getUserAgent(), request.getHeader(Constant.UA))
                && Objects.equals(loginInfo.getUserIp(), ServletUtil.getRemoteIP(request));
    }

    private LoginInfo makeLoginInfo(UserMeta nextInfoIdMeta, User user, HttpServletRequest request) {
        int id = 1;
        if (nextInfoIdMeta != null) {
            String nextIdStr = nextInfoIdMeta.getMetaValue();
            try {
                id = Integer.parseInt(nextIdStr);
            } catch (NumberFormatException e) {
                LOGGER.error("转换数字异常", e);
            }
        }
        String token = UUID.randomUUID().toString();
        Date expire = DateTime.now().plusDays(Constant.DEFAULT_EXPIRE_DAYS_7).toDate();
        return new LoginInfo()
                .setId(id)
                .setUserName(user.getUserLogin())
                .setUserAgent(request.getHeader(Constant.UA))
                .setUserIp(ServletUtil.getRemoteIP(request))
                .setToken(token)
                .setExpire(expire);

    }

    private void updateLoginInfo(User user, LoginInfo loginInfo, UserMeta loginInfoMeta) {
        int id = loginInfo.getId();
        if (loginInfoMeta == null) {
            Map<Integer, LoginInfo> info = Maps.newHashMap();
            info.put(id, loginInfo);
            loginInfoMeta = new UserMeta();
            loginInfoMeta.setUserId(user.getUserId())
                    .setMetaKey(Constant.K_LOGIN_INFO)
                    .setMetaValue(JsonUtil.toJson(info));
            userMetaDao.save(loginInfoMeta);
            return;
        }
        String loginInfoValue = loginInfoMeta.getMetaValue();
        @SuppressWarnings("unchecked")
        Map<Integer, LoginInfo> infoMap = JsonUtil.fromJson(loginInfoValue, Map.class);
        if (infoMap == null) {
            LOGGER.warn("InfoMap 为空");
            infoMap = Maps.newHashMap();
        }
        checkExpire(infoMap);
        infoMap.put(id, loginInfo);
        loginInfoValue = JsonUtil.toJson(infoMap);
        loginInfoMeta.setMetaValue(loginInfoValue);
        userMetaDao.updateValue(loginInfoMeta);
    }

    private void checkExpire(Map<Integer, LoginInfo> infoMap) {
        Set<Map.Entry<Integer, LoginInfo>> entries = infoMap.entrySet();
        for (Map.Entry<Integer, LoginInfo> entry : entries) {
            LoginInfo info = entry.getValue();
            Date expire = info.getExpire();
            DateTime dateTime = new DateTime(expire);
            if (dateTime.isBefore(DateTime.now())) {
                // 已过期
                infoMap.remove(entry.getKey());
            }
        }
    }

    private void updateNextId(LoginInfo loginInfo, UserMeta nextInfoIdMeta, User user) {
        int next = loginInfo.getId() + 1;
        String nextId = String.valueOf(next);
        if (nextInfoIdMeta == null) {
            nextInfoIdMeta = new UserMeta();
            nextInfoIdMeta.setUserId(user.getUserId())
                    .setMetaKey(Constant.K_NEXT_LOGIN_ID)
                    .setMetaValue(nextId);
            userMetaDao.save(nextInfoIdMeta);
            return;
        }
        nextInfoIdMeta.setMetaValue(nextId);
        userMetaDao.updateValue(nextInfoIdMeta);
    }

}
