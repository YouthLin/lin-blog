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

    public User save(User user) {
        userDao.save(user);
        return user;
    }

    public User findByUserName(String username) {
        return userDao.findByUserName(username);
    }

    public void saveMeta(UserMeta userMeta) {
        userMetaDao.save(userMeta);
    }

    public String queryMetaValue(Long userId, String metaName) {
        UserMeta meta = userMetaDao.findByUserIdAndMetaKey(userId, metaName);
        if (meta != null) {
            return meta.getMetaValue();
        }
        return null;
    }

    /**
     * 检查 Cookie 信息，并更新 token 值重新给客户端
     *
     * @return false 若校验失败
     */
    public boolean checkAndUpdateLoginInfo(HttpServletRequest request, HttpServletResponse response) {
        LoginInfo loginInfo = LoginInfo.fromRequest(request);
        if (loginInfo == null) {
            LOGGER.info("Cookie 不存在");
            return false;
        }
        if (new DateTime(loginInfo.getExpire()).isBefore(DateTime.now())) {
            LOGGER.info("Cookie 已过期");
            return false;
        }
        UserMeta loginInfoMeta = userMetaDao.findByUserNameAndMetaKey(loginInfo.getUserName(), Constant.K_LOGIN_INFO);
        String loginInfoJson = loginInfoMeta.getMetaValue();
        @SuppressWarnings("unchecked")
        Map<String, LoginInfo> map = JsonUtil.fromJson(loginInfoJson, Map.class);
        if (map == null) {
            LOGGER.warn("数据库中没有登录记录");
            return false;
        }
        removeExpire(map);
        LoginInfo savedInfo = map.get(String.valueOf(loginInfo.getId()));
        if (!Objects.equals(loginInfo, savedInfo)) {
            LOGGER.error("Cookie 校验失败, 可能被篡改了. cookie = {}, saved = {}", loginInfo, savedInfo);
            return false;
        }
        loginInfo.setToken(UUID.randomUUID().toString());        // 设置新的 token
        map.put(String.valueOf(loginInfo.getId()), loginInfo);                   // 更新 map
        loginInfoJson = JsonUtil.toJson(map);
        loginInfoMeta.setMetaValue(loginInfoJson);
        userMetaDao.updateValue(loginInfoMeta);                  // 更新数据库
        Cookie cookie = ServletUtil.makeCookie(loginInfo);       // 更新 Cookie
        response.addCookie(cookie);
        return true;
    }

    /**
     * 登录
     *
     * @param username                 用户名
     * @param md5ByUserNameAndPassword 用户名与密码 MD5 后的值
     * @param request                  req 因为需要用到 UserAgent, Ip 等信息
     * @param response                 res 因为需要添加 Cookie
     * @return true if login success
     */
    public boolean login(String username, String md5ByUserNameAndPassword,
                         HttpServletRequest request, HttpServletResponse response) {
        User user = userDao.findByUserName(username);
        if (user == null) {
            return false;
        }
        String userPass = user.getUserPass();
        if (userPass.length() != Constant.PASS_LEN) {
            return false;
        }
        String rand = userPass.substring(0, Constant.RAND_LEN);
        String md5WithRand = rand + MD5Util.md5(rand + md5ByUserNameAndPassword);
        boolean success = md5WithRand.equalsIgnoreCase(userPass);
        if (success) {
            saveUserLoginInfo(user, request, response);
        }
        return success;
    }

    /**
     * 保存登录信息到数据库
     */
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
        Map<String, LoginInfo> infoMap = JsonUtil.fromJson(loginInfoValue, Map.class);
        if (infoMap == null) {
            LOGGER.warn("InfoMap 为空");
            infoMap = Maps.newHashMap();
        }
        removeExpire(infoMap);
        infoMap.put(String.valueOf(id), loginInfo);
        loginInfoValue = JsonUtil.toJson(infoMap);
        loginInfoMeta.setMetaValue(loginInfoValue);
        userMetaDao.updateValue(loginInfoMeta);
    }

    private void removeExpire(Map<String, LoginInfo> infoMap) {
        Set<Map.Entry<String, LoginInfo>> entries = infoMap.entrySet();
        for (Map.Entry<String, LoginInfo> entry : entries) {
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
