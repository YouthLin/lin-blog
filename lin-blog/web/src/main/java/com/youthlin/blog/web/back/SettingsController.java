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
 * 时间： 2017-05-06 21:54
 */
@Controller
@RequestMapping("/admin")
public class SettingsController {
    @RequestMapping("/settings/general")
    public String generalSettings(Model model, HttpServletRequest request) {
        Role role = (Role) request.getAttribute(Constant.K_ROLE);
        if (role != null && role.getCode() < Role.Administrator.getCode()) {
            return Constant.REDIRECT_TO_PROFILE;
        }
        model.addAttribute("title", __("General Settings"));
        return "admin/settings-general";
    }

    @RequestMapping("/settings/post")
    public String postSettings(Model model, HttpServletRequest request) {
        Role role = (Role) request.getAttribute(Constant.K_ROLE);
        if (role != null && role.getCode() < Role.Administrator.getCode()) {
            return Constant.REDIRECT_TO_PROFILE;
        }
        model.addAttribute("title", __("Post Settings"));
        return "admin/settings-post";
    }

    @RequestMapping("/settings/comment")
    public String commentSettings(Model model, HttpServletRequest request) {
        Role role = (Role) request.getAttribute(Constant.K_ROLE);
        if (role != null && role.getCode() < Role.Administrator.getCode()) {
            return Constant.REDIRECT_TO_PROFILE;
        }
        model.addAttribute("title", __("Comment Settings"));
        return "admin/settings-comment";
    }

}
