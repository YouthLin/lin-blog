package com.youthlin.blog.web.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 创建者： youthlin.chen 日期： 17-4-4.
 */
@RequestMapping("admin")
@Controller
public class OverViewController {
    @RequestMapping("/")
    public String overivew() {
        return "admin/index";
    }
}
