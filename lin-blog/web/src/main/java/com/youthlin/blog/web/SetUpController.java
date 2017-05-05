package com.youthlin.blog.web;

import com.youthlin.blog.model.User;
import com.youthlin.blog.model.enums.UserStatus;
import com.youthlin.blog.service.OptionService;
import com.youthlin.blog.service.UserService;
import com.youthlin.blog.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

import static com.youthlin.utils.i18n.Translation.__;
import static com.youthlin.utils.i18n.Translation._f;

/**
 * 安装
 * <p>
 * 创建： lin
 * 时间： 2017-05-04 19:01
 */
@Controller
public class SetUpController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SetUpController.class);
    @Resource
    private OptionService optionService;
    @Resource
    private UserService userService;

    @RequestMapping("install")
    public String setup() {
        if (optionService.installed()) {
            LOGGER.info("已安装");
            return "installed";
        }
        LOGGER.info("未安装");
        return "install";
    }

    @RequestMapping(path = {"install.do"}, method = {RequestMethod.POST})
    public String install(@RequestParam(name = "user", required = false, defaultValue = "") String user,
                          @RequestParam(name = "pass", required = false, defaultValue = "") String pass,
                          @RequestParam(name = "email", required = false, defaultValue = "") String email,
                          @RequestParam(name = "title", required = false, defaultValue = "") String title,
                          Model model) {
        if (!StringUtils.hasText(user) || !StringUtils.hasText(pass) || !StringUtils.hasText(email)) {
            model.addAttribute(Constant.MSG, __("Username, password, email are all required."));
            return "redirect:install";
        }
        if (!StringUtils.hasText(title)) {
            /*TRANSLATORS: 0: username*/
            title = _f("{0}'s Blog", user);
        }
        LOGGER.info("Setup Blog: title = {}, user = {}, password length = {}", title, user, pass.length());
        User admin = new User()
                .setUserLogin(user)
                .setUserPass(pass)
                .setUserEmail(email)
                .setDisplayName(user);
        userService.save(admin);
        optionService.install(title);
        return "redirect:login";
    }
}