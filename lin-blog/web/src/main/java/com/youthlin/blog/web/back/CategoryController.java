package com.youthlin.blog.web.back;

import com.youthlin.blog.model.bo.Category;
import com.youthlin.blog.service.CategoryService;
import com.youthlin.blog.util.Constant;
import com.youthlin.blog.util.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);
    @Resource
    private CategoryService categoryService;

    private void showPage(Model model) {
        model.addAttribute("title", __("Category"));
        List<Category> categoryList = categoryService.listCategoriesByOrder();
        model.addAttribute("categoryList", categoryList);
    }

    @RequestMapping("/post/category")
    public String categoryPage(Model model) {
        showPage(model);
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
            errMsg.append(__("Name is required.")).append("<br>");
        }
        if (!StringUtils.hasText(slug)) {
            slug = name;
        }
        long parent = 0;
        try {
            parent = Long.parseLong(parentIdStr);
        } catch (NumberFormatException e) {
            errMsg.append(__("Illegal parent.")).append("<br>");
        }
        if (StringUtils.hasText(description)) {
            description = ServletUtil.filterHtml(description);
        } else {
            description = "";
        }
        if (errMsg.length() == 0) {
            Category category = new Category();
            category.setName(name)
                    .setSlug(slug)
                    .setDescription(description)
                    .setParent(parent);
            String msg = categoryService.save(category);
            if (StringUtils.hasText(msg)) {
                errMsg.append(msg).append("<br>");
            }
        }
        if (errMsg.length() > 0) {
            model.addAttribute(Constant.ERROR, errMsg.toString());
            showPage(model);
            return "admin/post-category";
        }
        return "redirect:/admin/post/category";
    }

    @RequestMapping(path = {"/post/category/edit"}, method = {RequestMethod.GET})
    public String editCategoryPage(@RequestParam(name = "id", required = false, defaultValue = "0") String idStr, Model model) {
        long id = 0;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException ignore) {
        }
        if (id < 2) {
            return "redirect:/admin/post/category";
        }
        Category category = categoryService.findById(id);
        if (category == null) {
            return "redirect:/admin/post/category";
        }
        model.addAttribute("category", category);
        List<Category> categoryList = categoryService.listCategoriesByOrder();
        model.addAttribute("categoryList", categoryList);
        return "admin/post-category-edit";
    }

    @RequestMapping(path = {"/post/category/edit"}, method = {RequestMethod.POST})
    public String editCategory(@RequestParam Map<String, String> param, Model model) {
        StringBuilder errMsg = new StringBuilder();
        long id = 0;
        String idStr = param.get("id");
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            errMsg.append(__("Illegal category.")).append("<br>");
        }

        String name = param.get("name");
        String slug = param.get("slug");
        long parentId = 0;
        String parentIdStr = param.get("parent");
        try {
            parentId = Long.parseLong(parentIdStr);
        } catch (NumberFormatException e) {
            errMsg.append(__("Illegal parent.")).append("<br>");
        }

        String description = param.get("description");

        if (id < 2) {
            errMsg.append(__("Unknown category.")).append("<br>");
        }
        if (parentId < 0) {
            errMsg.append(__("Illegal parent.")).append("<br>");
        }

        if (!StringUtils.hasText(name)) {
            errMsg.append(__("Category name must not be empty.")).append("<br>");
        }
        if (StringUtils.hasText(slug)) {
            // 看是否重复
            List<Category> categoryList = categoryService.listCategoriesByOrder();
            for (Category category : categoryList) {
                if (category.getTaxonomyId() != id && category.getSlug().equals(slug)) {
                    errMsg.append(__("The slug has be used by another item."));
                    break;
                }
            }
        } else {
            slug = name;
        }

        if (StringUtils.hasText(description)) {
            description = ServletUtil.filterHtml(description);
        }
        if (errMsg.length() > 0) {
            model.addAttribute(Constant.ERROR, errMsg.toString());
            return "admin/post-category-edit";
        }
        Category category = categoryService.findById(id);
        category.setName(name)
                .setSlug(slug)
                .setParent(parentId)
                .setDescription(description);
        categoryService.update(category);
        return "redirect:/admin/post/category";
    }

    @RequestMapping(path = {"/post/category/delete"})
    public String delete(@RequestParam(name = "id", required = false, defaultValue = "0") String idStr, Model model) {
        long id = 0;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException ignore) {
        }
        if (id < 2) {
            log.warn("不能删除默认目录");
        }
        categoryService.delete(id);
        return "redirect:/admin/post/category";
    }

}
