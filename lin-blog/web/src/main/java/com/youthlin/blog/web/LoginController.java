package com.youthlin.blog.web;

import com.youthlin.blog.model.enums.Role;
import com.youthlin.blog.model.po.User;
import com.youthlin.blog.service.UserService;
import com.youthlin.blog.util.Constant;
import com.youthlin.blog.util.MD5Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;

import static com.youthlin.utils.i18n.Translation.__;

/**
 * 创建： lin
 * 时间： 2017-05-04 11:38
 */
@Controller
public class LoginController {
    @Resource
    private UserService userService;

    @RequestMapping(path = {"login"}, method = {RequestMethod.GET})
    public String page(@RequestParam Map<String, String> param, Model model) {
        model.addAllAttributes(param);
        return "login";
    }

    @RequestMapping(path = {"login"}, method = {RequestMethod.POST})
    public String login(@RequestParam Map<String, String> param, Model model, HttpServletRequest request, HttpServletResponse response) {
        String user = param.get("user");
        String pass = param.get("pass");
        if (!StringUtils.hasText(user) || !StringUtils.hasText(pass) || pass.length() != Constant.MD5_LEN) {
            model.addAttribute(Constant.MSG, __("Username, password are all required."));
            return "login";
        }
        if (userService.login(user, pass, request, response)) {
            return "redirect:admin/";
        }
        model.addAttribute(Constant.ERROR, __("Username or password is incorrect."));
        return "login";
    }

    @RequestMapping(path = {"login.out"}, method = {RequestMethod.GET})
    public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {
        userService.logout(request, response);
        model.addAttribute(Constant.MSG, __("You are logged out now."));
        return "login";
    }

    @RequestMapping(path = {"login.register"}, method = RequestMethod.GET)
    public String register() {
        return "register";
    }

    @RequestMapping(path = {"login.register"}, method = RequestMethod.POST)
    public String register(@RequestParam Map<String, String> param, Model model) {
        String user = param.get("user");
        String email = param.get("email");
        String pass = param.get("pass");
        if (!StringUtils.hasText(user) || !StringUtils.hasText(email) || !StringUtils.hasText(pass) || pass.length() != Constant.MD5_LEN) {
            model.addAttribute(Constant.ERROR, __("Invalid param."));
            return "register";
        }
        User byUserName = userService.findByUserName(user);
        if (byUserName != null) {
            model.addAttribute(Constant.ERROR, __("This username has been used."));
            return "register";
        }
        String rand = UUID.randomUUID().toString().substring(0, Constant.RAND_LEN);
        pass = rand + MD5Util.md5(rand + pass);
        User newUser = new User()
                .setUserLogin(user)
                .setUserEmail(email)
                .setUserPass(pass)
                .setDisplayName(user);
        userService.saveNewUser(newUser, Role.Subscriber);
        return "redirect:/login";
    }
}
