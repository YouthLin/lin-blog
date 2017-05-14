package com.youthlin.blog.web.interceptor;

import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;
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
import java.util.Set;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-14 00:22.
 */
public class SidebarInjectInterceptor extends HandlerInterceptorAdapter {
    private int defaultCount = 6;
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
        LinkedHashMap<Comment, Post> recentComment = commentService.getRecentComment(defaultCount);
        modelAndView.addObject("recentCommentMap", recentComment);

        List<Category> categoryList = categoryService.listCategoriesByOrder();
        modelAndView.addObject("categoryList", categoryList);

        List<Taxonomy> tagList = tagService.listAllTag();
        modelAndView.addObject("tagList", tagList);

        LinkedHashMultiset<String> months = postService.archiveCount();
        modelAndView.addObject("months", months);

    }

    public int getDefaultCount() {
        return defaultCount;
    }

    public void setDefaultCount(int defaultCount) {
        this.defaultCount = defaultCount;
    }
}
