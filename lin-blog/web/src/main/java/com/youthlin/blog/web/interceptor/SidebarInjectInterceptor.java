package com.youthlin.blog.web.interceptor;

import com.google.common.collect.LinkedHashMultiset;
import com.youthlin.blog.model.bo.Category;
import com.youthlin.blog.model.bo.LoginInfo;
import com.youthlin.blog.model.enums.Role;
import com.youthlin.blog.model.po.Comment;
import com.youthlin.blog.model.po.Post;
import com.youthlin.blog.model.po.Taxonomy;
import com.youthlin.blog.model.po.User;
import com.youthlin.blog.model.po.UserMeta;
import com.youthlin.blog.service.CategoryService;
import com.youthlin.blog.service.CommentService;
import com.youthlin.blog.service.PostService;
import com.youthlin.blog.service.TagService;
import com.youthlin.blog.service.UserService;
import com.youthlin.blog.util.Constant;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-14 00:22.
 */
@SuppressWarnings("unused")
public class SidebarInjectInterceptor extends HandlerInterceptorAdapter {
    private int recentCommentsCount = 6;
    @Resource
    private UserService userService;
    @Resource
    private CommentService commentService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private TagService tagService;
    @Resource
    private PostService postService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LoginInfo loginInfo = LoginInfo.fromRequest(request);
        if (loginInfo != null) {
            String userName = loginInfo.getUserName();
            User user = userService.findByUserName(userName);
            UserMeta roleMeta = userService.findMetaByUserIdAndMetaKey(user.getUserId(), Constant.K_ROLE);
            if (roleMeta != null) {
                request.setAttribute(Constant.K_ROLE, Role.nameOf(roleMeta.getMetaValue()));
            }
            request.setAttribute(Constant.USER, user);
            request.setAttribute(Constant.NAME, user.getDisplayName());
            request.setAttribute(Constant.URL, user.getUserUrl());
            request.setAttribute(Constant.EMAIL, user.getUserEmail());
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null) {
            return;
        }
        if (!request.getMethod().equalsIgnoreCase("GET")) {
            return;
        }
        LinkedHashMap<Comment, Post> recentComment = commentService.getRecentComment(recentCommentsCount);
        modelAndView.addObject("recentCommentMap", recentComment);

        List<Category> categoryList = categoryService.listCategoriesByOrder();
        modelAndView.addObject("categoryList", categoryList);

        List<Taxonomy> tagList = tagService.listAllTag();
        modelAndView.addObject("tagList", tagList);

        LinkedHashMultiset<String> months = postService.archiveCount();
        modelAndView.addObject("months", months);

    }

    public int getRecentCommentsCount() {
        return recentCommentsCount;
    }

    public void setRecentCommentsCount(int recentCommentsCount) {
        this.recentCommentsCount = recentCommentsCount;
    }
}
