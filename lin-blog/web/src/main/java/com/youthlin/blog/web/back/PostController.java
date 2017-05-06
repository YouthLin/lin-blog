package com.youthlin.blog.web.back;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.youthlin.utils.i18n.Translation.__;

/**
 * 创建： lin
 * 时间： 2017-05-06 21:07
 */
@Controller
@RequestMapping("/admin")
public class PostController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

    @RequestMapping("/post/all")
    public String allPostPage(Model model) {
        model.addAttribute("title", __("All Post"));
        return "admin/post-all";
    }

    @RequestMapping("/post/new")
    public String newPostPage(Model model) {
        model.addAttribute("title", __("Write Post"));
        return "admin/post-write";
    }

}
