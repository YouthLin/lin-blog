package com.youthlin.blog.web.back;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.youthlin.utils.i18n.Translation.__;

/**
 * 创建者： youthlin.chen 日期： 17-4-4.
 */
@RequestMapping("admin")
@Controller
public class OverViewController {
    @RequestMapping("/")
    public String overview(Model model) {
        model.addAttribute("title", __("Overview"));
        return "admin/index";
    }
}
