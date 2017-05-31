package com.youthlin.blog.web.interceptor;

import com.youthlin.blog.model.bo.LoginInfo;
import com.youthlin.blog.model.enums.Role;
import com.youthlin.blog.model.po.User;
import com.youthlin.blog.model.po.UserMeta;
import com.youthlin.blog.service.UserService;
import com.youthlin.blog.support.GlobalInfo;
import com.youthlin.blog.util.Constant;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.youthlin.utils.i18n.Translation._f;

/**
 * 创建： lin
 * 时间： 2017-05-06 16:59
 */
public class CheckLoginInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private UserService userService;
    @Resource
    private GlobalInfo<String, String> globalInfo;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        LoginInfo loginInfo = LoginInfo.fromRequest(request);
        if (loginInfo == null || !userService.checkAndUpdateLoginInfo(request, response)) {
            // cookie 校验失败
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        String userName = loginInfo.getUserName();
        User user = userService.findByUserName(userName);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        UserMeta roleMeta = userService.findMetaByUserIdAndMetaKey(user.getUserId(), Constant.K_ROLE);
        if (roleMeta != null) {
            request.setAttribute(Constant.K_ROLE, Role.nameOf(roleMeta.getMetaValue()));
        }
        request.setAttribute(Constant.USER, user);
        request.setAttribute(Constant.NAME, user.getDisplayName());
        request.setAttribute(Constant.URL, user.getUserUrl());
        request.setAttribute(Constant.EMAIL, user.getUserEmail());
        String blogName = globalInfo.get(Constant.O_BLOG_TITLE, () -> _f("{0}&#39;Blog", userName));
        request.setAttribute(Constant.O_BLOG_TITLE, blogName);
        return true;
    }
}
