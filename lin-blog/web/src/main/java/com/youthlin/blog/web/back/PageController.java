package com.youthlin.blog.web.back;

import com.youthlin.blog.model.enums.Role;
import com.youthlin.blog.util.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static com.youthlin.utils.i18n.Translation.__;

/**
 * 创建： lin
 * 时间： 2017-05-06 21:49
 */
@Controller
@RequestMapping("/admin")
public class PageController {
    @RequestMapping("/page/all")
    public String pageList(Model model, HttpServletRequest request) {
        Role role = (Role) request.getAttribute(Constant.K_ROLE);
        if (role != null && role.getCode() < Role.Editor.getCode()) {
            return Constant.REDIRECT_TO_PROFILE;
        }
        model.addAttribute("title", __("All Page"));
        return "admin/page-all";
    }

    @RequestMapping("/page/new")
    public String newPage(Model model, HttpServletRequest request) {
        Role role = (Role) request.getAttribute(Constant.K_ROLE);
        if (role != null && role.getCode() < Role.Editor.getCode()) {
            return Constant.REDIRECT_TO_PROFILE;
        }
        model.addAttribute("title", __("Write New Page"));
        return "admin/page-new";
    }
}
