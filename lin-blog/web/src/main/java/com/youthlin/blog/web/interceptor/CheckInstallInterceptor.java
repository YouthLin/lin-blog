package com.youthlin.blog.web.interceptor;

import com.youthlin.blog.service.OptionService;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 创建： lin
 * 时间： 2017-05-05 15:21
 */
public class CheckInstallInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private OptionService optionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return checkInstall(request, response);
    }

    private boolean checkInstall(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!optionService.installed()) {
            response.sendRedirect(request.getContextPath() + "/install");
            return false;
        }
        request.setAttribute("siteTitle", optionService.getSiteTitle());
        return true;
    }
}
