package com.youthlin.blog.web.interceptor;

import com.google.common.collect.LinkedHashMultiset;
import com.youthlin.blog.model.bo.Category;
import com.youthlin.blog.model.po.Comment;
import com.youthlin.blog.model.po.Post;
import com.youthlin.blog.model.po.Taxonomy;
import com.youthlin.blog.service.CategoryService;
import com.youthlin.blog.service.CommentService;
import com.youthlin.blog.service.PostService;
import com.youthlin.blog.service.TagService;
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
    private CommentService commentService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private TagService tagService;
    @Resource
    private PostService postService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null) {
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
