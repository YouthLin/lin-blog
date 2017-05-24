package com.youthlin.blog.web.back;

import com.youthlin.blog.model.po.User;
import com.youthlin.blog.model.po.UserMeta;
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

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    @RequestMapping(path = "/users/my", method = RequestMethod.GET)
    public String profile(Model model) {
        model.addAttribute("title", __("My Profile"));
        return "admin/users-profile";
    }

    @RequestMapping(path = {"/users/my"}, method = {RequestMethod.POST})
    public String updateProfile(@RequestParam Map<String, String> params, HttpServletRequest request, Model model) {
        String email = params.get("email");
        String name = params.get("name");
        String url = params.get("url");
        String oldPass = params.get("oldPass");
        String newPass = params.get("newPass");
        if (!StringUtils.hasText(email) || !StringUtils.hasText(name)) {
            model.addAttribute(Constant.ERROR, __("Email and display are required."));
            return "admin/users-profile";
        }
        if (StringUtils.hasText(url)) {
            try {
                //noinspection ResultOfMethodCallIgnored
                URI.create(url).toURL();
            } catch (MalformedURLException e) {
                model.addAttribute(Constant.ERROR, __("illegal url."));
                return "admin/users-profile";
            }
        }
        User user = (User) request.getAttribute(Constant.USER);
        if (StringUtils.hasText(oldPass) && StringUtils.hasText(newPass)) {
            if (oldPass.length() != Constant.MD5_LEN || newPass.length() != Constant.MD5_LEN) {
                model.addAttribute(Constant.ERROR, __("It seem that JavaScript doesn't work, but we need it to generate password."));
                return "admin/users-profile";
            }
            String userPass = user.getUserPass();
            String rand = userPass.substring(0, Constant.RAND_LEN);
            userPass = rand + MD5Util.md5(rand + oldPass);
            if (!userPass.equalsIgnoreCase(user.getUserPass())) {
                model.addAttribute(Constant.ERROR, __("Password is incorrect."));
                return "admin/users-profile";
            }
            rand = UUID.randomUUID().toString().substring(0, Constant.RAND_LEN);
            newPass = rand + MD5Util.md5(rand + newPass);
            user.setUserPass(newPass);
        }
        user.setUserEmail(email);
        user.setDisplayName(name);
        user.setUserUrl(url);
        userService.update(user);
        model.addAttribute(Constant.MSG, __("Your profile has been updated."));
        return "admin/users-profile";
    }

}
