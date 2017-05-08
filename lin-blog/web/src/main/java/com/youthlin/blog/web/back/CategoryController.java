package com.youthlin.blog.web.back;

import com.youthlin.blog.model.bo.Category;
import com.youthlin.blog.service.CategoryService;
import com.youthlin.blog.util.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

import java.util.List;

import static com.youthlin.utils.i18n.Translation.__;

/**
 * 创建： lin
 * 时间： 2017-05-06 21:45
 */
@Controller
@RequestMapping("/admin")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @RequestMapping("/post/category")
    public String categoryPage(Model model) {
        model.addAttribute("title", __("Category"));
        List<Category> categoryList = categoryService.listCategories();
        model.addAttribute("categoryList", categoryList);
        return "admin/post-category";
    }
}
