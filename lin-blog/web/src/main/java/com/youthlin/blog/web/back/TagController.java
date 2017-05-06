package com.youthlin.blog.web.back;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.youthlin.utils.i18n.Translation.__;

/**
 * 创建： lin
 * 时间： 2017-05-06 21:46
 */
@Controller
@RequestMapping("/admin")
public class TagController {
    @RequestMapping("/post/tag")
    public String tagPage(Model model) {
        model.addAttribute("title", __("Tag"));
        return "admin/post-tag";
    }
}
