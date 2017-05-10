package com.youthlin.blog.web.back;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.youthlin.blog.model.bo.Category;
import com.youthlin.blog.model.enums.PostStatus;
import com.youthlin.blog.model.po.Post;
import com.youthlin.blog.model.po.User;
import com.youthlin.blog.service.CategoryService;
import com.youthlin.blog.service.PostService;
import com.youthlin.blog.util.Constant;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.youthlin.utils.i18n.Translation.__;

/**
 * 创建： lin
 * 时间： 2017-05-06 21:07
 */
@Controller
@RequestMapping("/admin")
public class PostController {
    private static final Logger log = LoggerFactory.getLogger(PostController.class);
    private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm");
    private static final Splitter SPLITTER = Splitter.on(",").trimResults().omitEmptyStrings();
    @Resource
    private CategoryService categoryService;
    @Resource
    private PostService postService;

    @RequestMapping("/post")
    public String allPostPage(Model model) {
        model.addAttribute("title", __("All Post"));
        return "admin/post-all";
    }

    @RequestMapping("/post/new")
    public String newPostPage(Model model) {
        model.addAttribute("title", __("Write Post"));
        model.addAttribute("editor", true);

        List<Category> categoryList = categoryService.listCategoriesByOrder();
        model.addAttribute("categoryList", categoryList);
        return "admin/post-write";
    }

    @RequestMapping(path = {"/post/add"}, method = {RequestMethod.POST})
    public String addPost(@RequestParam Map<String, String> param, HttpServletRequest request, Model model) {
        String title = param.get("title");
        String content = param.get("content");
        String markdownContent = param.get("md-content");
        String excerpt = param.get("excerpt");
        String date = param.get("date");
        String[] categories = request.getParameterValues("category");
        String tags = param.get("tags");
        String commentOpenStr = param.get("commentOpen");
        String pingOpenStr = param.get("pingOpen");
        String password = param.get("password");
        String postName = param.get("postName");
        String draft = param.get("draft");
        log.info("param = {}", param);

        DateTime dateTime = DateTime.now();
        try {
            dateTime = DateTime.parse(date, FORMATTER);
        } catch (Exception e) {
            log.warn("日期转化异常：{}", date, e);
        }

        List<String> categoriesStr = Lists.newArrayList(categories);
        List<Long> categoryList = Lists.newArrayList();
        for (String id : categoriesStr) {
            try {
                long categoryId = Long.parseLong(id);
                categoryList.add(categoryId);
            } catch (NumberFormatException e) {
                log.warn("数字转化失败，分类目录编号。{}", id, e);
            }
        }

        List<String> tagList = SPLITTER.splitToList(tags);
        boolean commentOpen = true;
        if (!"on".equals(commentOpenStr)) {
            commentOpen = false;
        }
        boolean pingOpen = true;
        if (!"on".equals(pingOpenStr)) {
            pingOpen = false;
        }

        PostStatus status = PostStatus.PUBLISHED;
        if (StringUtils.hasText(draft)) {
            status = PostStatus.DRAFT;
        }

        User user = (User) request.getAttribute(Constant.USER);//CheckLoginInterceptor
        Post post = new Post();
        post.setPostAuthorId(user.getUserId())
                .setPostDate(dateTime.toDate())
                .setPostContent(content)
                .setPostTitle(title)
                .setPostExcerpt(excerpt)
                .setPostStatus(status)
                .setCommentOpen(commentOpen)
                .setPingOpen(pingOpen)
                .setPostPassword(password)
                .setPostName(postName);

        postService.save(post, categoryList, tagList, markdownContent);

        return "redirect:/admin/post";
    }

}
