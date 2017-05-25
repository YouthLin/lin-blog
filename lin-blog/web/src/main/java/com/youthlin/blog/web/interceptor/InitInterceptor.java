package com.youthlin.blog.web.interceptor;

import com.youthlin.blog.support.GlobalInfo;
import com.youthlin.blog.util.Constant;
import com.youthlin.blog.util.ServletUtil;
import com.youthlin.utils.i18n.Translation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static com.youthlin.utils.i18n.Translation.__;

/**
 * 创建者： youthlin.chen 日期： 17-4-4.
 */
public class InitInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitInterceptor.class);
    @Resource
    private GlobalInfo<String, String> globalInfo;

    static {
        try {
            Translation.setDft(Translation.getBundle("Blog"));
            Translation.removeResource(Translation.DEFAULT_DOMAIN);
            LOGGER.info("加载语言包完成[{}]", Translation.getDft().getBaseBundleName());
        } catch (Exception e) {
            LOGGER.error("加载语言包出错", e);
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.put("sessionId", UUID.randomUUID().toString());
        LOGGER.debug("{} {} {}", ServletUtil.getRemoteIP(request), request.getMethod(), ServletUtil.getUrl(request));
        request.setAttribute(Constant.O_BLOG_TITLE, globalInfo.get(Constant.O_BLOG_TITLE, () -> __("Home")));
        return true;
    }
}
