package com.youthlin.blog.web.back;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.youthlin.utils.i18n.Translation.__;

/**
 * 创建： lin
 * 时间： 2017-05-07 14:56
 */
@Controller
@RequestMapping("/admin")
public class UserController {
    @RequestMapping("/users/all")
    public String allUsers(Model model) {
        model.addAttribute("title", __("All Users"));
        return "admin/users-all";
    }

    @RequestMapping("/users/my")
    public String profile(Model model) {
        model.addAttribute("title", __("My Profile"));
        return "admin/users-profile";
    }

}
