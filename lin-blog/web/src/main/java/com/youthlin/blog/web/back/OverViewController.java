package com.youthlin.blog.web.back;

import com.youthlin.blog.model.bo.Category;
import com.youthlin.blog.model.enums.Role;
import com.youthlin.blog.model.po.Comment;
import com.youthlin.blog.model.po.Post;
import com.youthlin.blog.model.po.Taxonomy;
import com.youthlin.blog.service.CategoryService;
import com.youthlin.blog.service.CommentService;
import com.youthlin.blog.service.PostService;
import com.youthlin.blog.service.TagService;
import com.youthlin.blog.util.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.LinkedHashMap;
import java.util.List;

import static com.youthlin.utils.i18n.Translation.__;

/**
 * 创建者： youthlin.chen 日期： 17-4-4.
 */
@RequestMapping("/admin")
@Controller
public class OverViewController {
    @Resource
    private PostService postService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private CommentService commentService;
    @Resource
    private TagService tagService;

    @RequestMapping(path = {"", "/"})
    public String overview(HttpServletRequest request, Model model) {
        Role role = (Role) request.getAttribute(Constant.K_ROLE);
        if (role != null && role.getCode() < Role.Contributor.getCode()) {
            return Constant.REDIRECT_TO_PROFILE;
        }
        model.addAttribute("title", __("Overview"));
        count(model);

        LinkedHashMap<Comment, Post> recentComments = commentService.getRecentComment(5);
        model.addAttribute("recentComments", recentComments);

        List<Post> posts = postService.recentPublished(5);
        model.addAttribute("posts", posts);

        return "admin/index";
    }

    private void count(Model model) {
        long postCount = postService.countByStatus(null);
        model.addAttribute("postCount", postCount);

        List<Category> categories = categoryService.listCategoriesNoPrefix();
        model.addAttribute("categoryCount", categories.size());

        long commentCount = commentService.count();
        model.addAttribute("commentCount", commentCount);

        List<Taxonomy> allTag = tagService.listAllTag();
        model.addAttribute("tagCount", allTag.size());
    }

}
