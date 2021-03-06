package com.youthlin.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.youthlin.blog.dao.UserDao;
import com.youthlin.blog.dao.UserMetaDao;
import com.youthlin.blog.model.bo.LoginInfo;
import com.youthlin.blog.model.bo.Page;
import com.youthlin.blog.model.enums.Role;
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
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * 创建者： youthlin.chen 日期： 17-4-4.
 */
@SuppressWarnings("unused")
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

    @Transactional
    public void saveNewUser(User user, Role role) {
        userDao.save(user);
        UserMeta meta = new UserMeta();
        meta.setUserId(user.getUserId())
                .setMetaKey(Constant.K_ROLE)
                .setMetaValue(role.name());
        userMetaDao.save(meta);
    }

    public User update(User user) {
        int i = userDao.update(user);
        if (i == 1) {
            LOGGER.info("已更新{}", user);
        }
        return user;
    }

    public UserMeta updateMeta(UserMeta userMeta) {
        userMetaDao.updateValue(userMeta);
        return userMeta;
    }

    public User findByUserName(String username) {
        return userDao.findByUserName(username);
    }

    public List<UserMeta> listUserMeta(long userId) {
        return userMetaDao.listByUserId(userId);
    }

    public UserMeta findMetaByUserIdAndMetaKey(long userId, String metaKey) {
        return userMetaDao.findByUserIdAndMetaKey(userId, metaKey);
    }

    public User findById(Long id) {
        return userDao.findById(id);
    }

    public void saveMeta(UserMeta userMeta) {
        userMetaDao.save(userMeta);
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
        Map<String, LoginInfo> map = getLoginInfoMapFromMeta(loginInfoMeta);
        if (map == null || map.isEmpty()) {
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
        map.put(String.valueOf(loginInfo.getId()), loginInfo);   // 更新 map
        updateLoginInfo(loginInfoMeta, map);
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

    @SuppressWarnings("UnusedReturnValue")
    public boolean logout(HttpServletRequest request, HttpServletResponse response) {
        LoginInfo loginInfo = LoginInfo.fromRequest(request);
        if (loginInfo != null) {
            String userName = loginInfo.getUserName();
            UserMeta loginInfoMeta = getLoginInfoMeta(userName);
            Map<String, LoginInfo> loginInfoMap = getLoginInfoMapFromMeta(loginInfoMeta);
            loginInfoMap.remove(loginInfo.getId());
            updateLoginInfo(loginInfoMeta, loginInfoMap);
            Cookie cookie = ServletUtil.makeCookie(loginInfo);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return true;
        }
        LOGGER.warn("未登录不需要登出");
        return false;
    }

    private UserMeta getLoginInfoMeta(String username) {
        return userMetaDao.findByUserNameAndMetaKey(username, Constant.K_LOGIN_INFO);
    }

    private Map<String, LoginInfo> getLoginInfoMapFromMeta(UserMeta loginInfoMeta) {
        if (loginInfoMeta == null) {
            return Collections.emptyMap();
        }
        if (Constant.K_LOGIN_INFO.equals(loginInfoMeta.getMetaKey())) {
            String loginInfoJson = loginInfoMeta.getMetaValue();
            @SuppressWarnings("unchecked")
            Map<String, LoginInfo> map = JsonUtil.fromJson(loginInfoJson, Map.class);
            return map;
        }
        LOGGER.warn("不是 Login Info Meta");
        return Collections.emptyMap();
    }

    private void updateLoginInfo(UserMeta loginInfoMeta, Map<String, LoginInfo> map) {
        if (Constant.K_LOGIN_INFO.equals(loginInfoMeta.getMetaKey())) {
            String loginInfoJson = JsonUtil.toJson(map);
            loginInfoMeta.setMetaValue(loginInfoJson);
            userMetaDao.updateValue(loginInfoMeta);
            return;
        }
        LOGGER.warn("不是 Login Info Meta");
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
        String id = "1";
        if (nextInfoIdMeta != null) {
            id = nextInfoIdMeta.getMetaValue();
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
        String id = loginInfo.getId();
        if (loginInfoMeta == null) {
            Map<String, LoginInfo> info = Maps.newHashMap();
            info.put(id, loginInfo);
            loginInfoMeta = new UserMeta();
            loginInfoMeta.setUserId(user.getUserId())
                    .setMetaKey(Constant.K_LOGIN_INFO)
                    .setMetaValue(JsonUtil.toJson(info));
            userMetaDao.save(loginInfoMeta);
            return;
        }
        @SuppressWarnings("unchecked")
        Map<String, LoginInfo> infoMap = getLoginInfoMapFromMeta(loginInfoMeta);
        if (infoMap == null || infoMap.isEmpty()) {
            LOGGER.warn("InfoMap 为空");
            infoMap = Maps.newHashMap();
        }
        removeExpire(infoMap);
        infoMap.put(id, loginInfo);
        updateLoginInfo(loginInfoMeta, infoMap);
    }

    private void removeExpire(Map<String, LoginInfo> infoMap) {
        Set<Map.Entry<String, LoginInfo>> entries = infoMap.entrySet();
        Iterator<Map.Entry<String, LoginInfo>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, LoginInfo> entry = iterator.next();
            LoginInfo info = entry.getValue();
            Date expire = info.getExpire();
            DateTime dateTime = new DateTime(expire);
            if (dateTime.isBefore(DateTime.now())) {
                // 已过期
                iterator.remove();
            }
        }
    }

    private void updateNextId(LoginInfo loginInfo, UserMeta nextInfoIdMeta, User user) {
        int id = Integer.parseInt(loginInfo.getId());
        int next = id + 1;
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


    public List<User> getAllUser() {
        return userDao.listAll();
    }

    public Page<User> listByPage(int pageIndex, int pageSize) {
        PageInfo<User> pageInfo = PageHelper.startPage(pageIndex, pageSize).doSelectPageInfo(() -> userDao.listAll());
        Page<User> page = new Page<>(pageInfo);
        return page;
    }

    public List<User> listById(Set<Long> userIds) {
        return userDao.listByIds(userIds);
    }

    public List<UserMeta> getAllRole() {
        return userMetaDao.listByMetaKey(Constant.K_ROLE);
    }
}
