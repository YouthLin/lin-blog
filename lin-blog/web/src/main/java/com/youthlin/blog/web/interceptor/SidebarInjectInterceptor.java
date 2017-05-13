package com.youthlin.blog.web.interceptor;

import com.youthlin.blog.model.po.Comment;
import com.youthlin.blog.model.po.Post;
import com.youthlin.blog.service.CommentService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-14 00:22.
 */
public class SidebarInjectInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private CommentService commentService;
    private int defaultCount = 6;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LinkedHashMap<Comment, Post> recentComment = commentService.getRecentComment(defaultCount);
        modelAndView.addObject("recentCommentMap", recentComment);
    }
}
