package com.youthlin.blog.web.back;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.youthlin.utils.i18n.Translation.__;

/**
 * 创建： lin
 * 时间： 2017-05-06 21:45
 */
@Controller
@RequestMapping("/admin")
public class CategoryController {
    @RequestMapping("/post/category")
    public String categoryPage(Model model) {
        model.addAttribute("title", __("Category"));
        return "admin/post-category";
    }
}
