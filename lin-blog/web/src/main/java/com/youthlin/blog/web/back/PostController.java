package com.youthlin.blog.web.back;

import com.youthlin.blog.model.bo.Category;
import com.youthlin.blog.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

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
    @Resource
    private CategoryService categoryService;

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
    public String addPost(@RequestParam Map<String, String> param, Model model) {
        String title = param.get("title");
        String content = param.get("content");
        String markdownContent = param.get("md-content");
        String excerpt = param.get("excerpt");
        String date = param.get("date");
        String category = param.get("category");
        String tags = param.get("tags");
        String commentOpen = param.get("commentOpen");
        String pingOpen = param.get("pingOpen");
        String password = param.get("password");
        String postName = param.get("postName");
        log.info("param = {}", param);

        return "redirect:/admin/post";
    }

}
