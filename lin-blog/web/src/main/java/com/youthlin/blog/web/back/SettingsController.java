package com.youthlin.blog.web.back;

import com.youthlin.blog.dao.OptionDao;
import com.youthlin.blog.model.enums.Role;
import com.youthlin.blog.model.po.Option;
import com.youthlin.blog.support.GlobalInfo;
import com.youthlin.blog.util.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.Map;

import static com.youthlin.utils.i18n.Translation.__;

/**
 * 创建： lin
 * 时间： 2017-05-06 21:54
 */
@Controller
@RequestMapping("/admin")
public class SettingsController {
    @Resource
    private OptionDao optionDao;
    @Resource
    private GlobalInfo<String, Object> globalInfo;

    @RequestMapping(path = "/settings/general", method = RequestMethod.GET)
    public String generalSettings(Model model, HttpServletRequest request) {
        Role role = (Role) request.getAttribute(Constant.K_ROLE);
        if (role != null && role.getCode() < Role.Administrator.getCode()) {
            return Constant.REDIRECT_TO_PROFILE;
        }
        model.addAttribute("title", __("General Settings"));
        return "admin/settings-general";
    }

    @RequestMapping(path = "/settings/general", method = RequestMethod.POST)
    public String generalSettings(HttpServletRequest request, Model model, @RequestParam Map<String, String> params) {
        Role role = (Role) request.getAttribute(Constant.K_ROLE);
        if (role != null && role.getCode() < Role.Administrator.getCode()) {
            return Constant.REDIRECT_TO_PROFILE;
        }
        String title = params.get("title");
        if (StringUtils.hasText(title)) {
            Option siteTitle = optionDao.findByName(Constant.O_BLOG_TITLE);
            siteTitle.setOptionValue(title);
            optionDao.update(siteTitle);
            globalInfo.set(Constant.O_BLOG_TITLE, title);
            request.setAttribute(Constant.O_BLOG_TITLE, title);
        }
        model.addAttribute("title", __("General Settings"));
        return "admin/settings-general";
    }

    @RequestMapping("/settings/post")
    public String postSettings(Model model, HttpServletRequest request) {
        Role role = (Role) request.getAttribute(Constant.K_ROLE);
        if (role != null && role.getCode() < Role.Administrator.getCode()) {
            return Constant.REDIRECT_TO_PROFILE;
        }
        model.addAttribute("title", __("Post Settings"));
        return "admin/settings-post";
    }

    @RequestMapping("/settings/comment")
    public String commentSettings(Model model, HttpServletRequest request) {
        Role role = (Role) request.getAttribute(Constant.K_ROLE);
        if (role != null && role.getCode() < Role.Administrator.getCode()) {
            return Constant.REDIRECT_TO_PROFILE;
        }
        model.addAttribute("title", __("Comment Settings"));
        return "admin/settings-comment";
    }

}
