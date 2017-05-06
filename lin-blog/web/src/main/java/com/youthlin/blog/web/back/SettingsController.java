package com.youthlin.blog.web.back;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.youthlin.utils.i18n.Translation.__;

/**
 * 创建： lin
 * 时间： 2017-05-06 21:54
 */
@Controller
@RequestMapping("/admin")
public class SettingsController {
    @RequestMapping("/settings/general")
    public String generalSettings(Model model) {
        model.addAttribute("title", __("General Settings"));
        return "admin/settings-general";
    }

    @RequestMapping("/settings/post")
    public String postSettings(Model model) {
        model.addAttribute("title", __("Post Settings"));
        return "admin/settings-post";
    }
    @RequestMapping("/settings/comment")
    public String commentSettings(Model model) {
        model.addAttribute("title", __("Comment Settings"));
        return "admin/settings-comment";
    }

}
