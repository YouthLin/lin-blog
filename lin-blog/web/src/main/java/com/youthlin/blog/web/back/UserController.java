package com.youthlin.blog.web.back;

import com.youthlin.blog.model.po.User;
import com.youthlin.blog.model.po.UserMeta;
import com.youthlin.blog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

import java.util.List;

import static com.youthlin.utils.i18n.Translation.__;

/**
 * 创建： lin
 * 时间： 2017-05-07 14:56
 */
@Controller
@RequestMapping("/admin")
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping("/users/all")
    public String allUsers(Model model) {
        model.addAttribute("title", __("All Users"));
        List<User> allUser = userService.getAllUser();
        List<UserMeta> allRole = userService.getAllRole();
        model.addAttribute("allUser", allUser);
        model.addAttribute("allRole", allRole);
        return "admin/users-all";
    }

    @RequestMapping("/users/my")
    public String profile(Model model) {
        model.addAttribute("title", __("My Profile"));
        return "admin/users-profile";
    }

}
