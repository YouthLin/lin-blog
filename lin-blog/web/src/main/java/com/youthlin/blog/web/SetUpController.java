package com.youthlin.blog.web;

import com.youthlin.blog.model.Option;
import com.youthlin.blog.util.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static com.youthlin.utils.i18n.Translation.__;

/**
 * 安装
 * <p>
 * 创建： lin
 * 时间： 2017-05-04 19:01
 */
@Controller
public class SetUpController {
    @RequestMapping("install")
    public String setup() {
        String installed = Option.options.get(Constant.INSTALLED);
        if (installed != null) {
            return "installed";
        }
        return "install";
    }

    @RequestMapping(path = {"install.do"}, method = {RequestMethod.POST})
    public String install(@RequestParam(name = "user", required = false, defaultValue = "") String user,
                          @RequestParam(name = "pass", required = false, defaultValue = "") String pass,
                          Model model) {
        if (!StringUtils.hasText(user) || !StringUtils.hasText(pass)) {
            model.addAttribute(Constant.MSG, __("Username and Password are required."));
            return "redirect:install";
        }
        Option.options.put(Constant.INSTALLED, "true");
        return "redirect:login";
    }
}
