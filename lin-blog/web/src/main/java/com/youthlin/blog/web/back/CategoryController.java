package com.youthlin.blog.web.back;

import com.google.common.collect.Lists;
import com.youthlin.blog.model.bo.Category;
import com.youthlin.blog.service.CategoryService;
import com.youthlin.blog.util.Constant;
import com.youthlin.blog.util.ServletUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

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
        List<Category> categoryList = categoryService.listCategoriesByOrder();
        model.addAttribute("categoryList", categoryList);
        return "admin/post-category";
    }

    @RequestMapping(path = {"/post/category/add"}, method = {RequestMethod.POST})
    public String addCategory(@RequestParam Map<String, String> param, Model model) {
        String name = param.get("name");
        String slug = param.get("slug");
        String parentIdStr = param.get("parent");
        String description = param.get("description");
        StringBuilder errMsg = new StringBuilder();
        if (!StringUtils.hasText(name)) {
            errMsg.append(__("Category name is required.")).append("<br>");
        }
        if (!StringUtils.hasText(slug)) {
            slug = name;
        }
        long parent = 0;
        try {
            parent = Long.parseLong(parentIdStr);
        } catch (NumberFormatException e) {
            errMsg.append(__("Unknown parent.")).append("<br>");
        }
        if (StringUtils.hasText(description)) {
            description = ServletUtil.filterHtml(description);
        }
        if (errMsg.length() == 0) {
            Category category = new Category();
            category.setName(name)
                    .setSlug(slug)
                    .setDescription(description)
                    .setParent(parent);
            categoryService.save(category);
        } else {
            model.addAttribute(Constant.ERROR, errMsg.toString());
        }
        return "redirect:/admin/post/category";
    }
}
