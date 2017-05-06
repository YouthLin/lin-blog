package com.youthlin.blog.web.interceptor;

import com.youthlin.blog.util.ServletUtil;
import com.youthlin.utils.i18n.Translation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 创建者： youthlin.chen 日期： 17-4-4.
 */
public class InitInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitInterceptor.class);

    static {
        try {
            Translation.setDft(Translation.getBundle("Blog"));
            LOGGER.info("加载语言包完成[{}]", Translation.getDft().getBaseBundleName());
        } catch (Exception e) {
            LOGGER.error("加载语言包出错", e);
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.put("sessionId", UUID.randomUUID().toString());
        LOGGER.debug("{} {} {}", request.getMethod(), ServletUtil.getUrl(request), ServletUtil.getRemoteIP(request));
        return true;
    }
}
