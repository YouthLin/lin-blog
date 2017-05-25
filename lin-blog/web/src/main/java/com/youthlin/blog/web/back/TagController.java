package com.youthlin.blog.web.back;

import com.youthlin.blog.model.bo.Pageable;
import com.youthlin.blog.model.bo.Tag;
import com.youthlin.blog.model.enums.Role;
import com.youthlin.blog.model.po.Taxonomy;
import com.youthlin.blog.service.TagService;
import com.youthlin.blog.util.Constant;
import com.youthlin.blog.util.ServletUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.youthlin.utils.i18n.Translation.__;

/**
 * 创建： lin
 * 时间： 2017-05-06 21:46
 */
@Controller
@RequestMapping("/admin")
public class TagController {
    @Resource
    private TagService tagService;

    private void showPage(int pageIndex, int pageSize, Model model) {
        model.addAttribute("title", __("Tag"));
        Pageable<Taxonomy> page = tagService.findByPage(pageIndex, pageSize);
        model.addAttribute("page", page);
    }

    @RequestMapping("/post/tag")
    public String tagPage(@RequestParam Map<String, String> param, Model model, HttpServletRequest request) {
        Role role = (Role) request.getAttribute(Constant.K_ROLE);
        if (role != null && role.getCode() < Role.Editor.getCode()) {
            return Constant.REDIRECT_TO_PROFILE;
        }
        String page = param.get("page");
        int pageIndex = Constant.DEFAULT_PAGE_INDEX;
        try {
            pageIndex = Integer.parseInt(page);
        } catch (NumberFormatException ignore) {
        }
        showPage(pageIndex, Constant.DEFAULT_PAGE_SIZE, model);
        return "admin/post-tag";
    }

    @RequestMapping(path = {"/post/tag/add"}, method = {RequestMethod.POST})
    public String addTag(@RequestParam Map<String, String> param, Model model, HttpServletRequest request) {
        Role role = (Role) request.getAttribute(Constant.K_ROLE);
        if (role != null && role.getCode() < Role.Editor.getCode()) {
            return Constant.REDIRECT_TO_PROFILE;
        }
        String name = param.get("name");
        String slug = param.get("slug");
        String description = param.get("description");
        if (!StringUtils.hasText(name)) {
            model.addAttribute(Constant.ERROR, __("Name is required."));
            return "admin/post-tag";
        }
        if (!StringUtils.hasText(slug)) {
            slug = name;
        }
        if (StringUtils.hasText(description)) {
            description = ServletUtil.filterHtml(description);
        } else {
            description = "";
        }
        Tag tag = new Tag();
        tag.setName(name)
                .setSlug(slug)
                .setDescription(description);
        String errMsg = tagService.save(tag);
        if (StringUtils.hasText(errMsg)) {
            model.addAttribute(Constant.ERROR, errMsg);
            showPage(Constant.DEFAULT_PAGE_INDEX, Constant.DEFAULT_PAGE_SIZE, model);
            return "admin/post-tag";
        }
        return "redirect:/admin/post/tag";
    }
}
