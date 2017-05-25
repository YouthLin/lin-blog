package com.youthlin.blog.web.back;

import com.youthlin.blog.model.enums.Role;
import com.youthlin.blog.util.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static com.youthlin.utils.i18n.Translation.__;

/**
 * 创建： lin
 * 时间： 2017-05-07 14:56
 */
@Controller
@RequestMapping("/admin")
public class ToolController {
    @RequestMapping("/tools/import")
    public String importPost(Model model, HttpServletRequest request) {
        Role role = (Role) request.getAttribute(Constant.K_ROLE);
        if (role != null && role.getCode() < Role.Administrator.getCode()) {
            return Constant.REDIRECT_TO_PROFILE;
        }
        model.addAttribute("title", __("Import"));
        return "admin/tool-import";
    }

    @RequestMapping("/tools/export")
    public String exportPost(Model model, HttpServletRequest request) {
        Role role = (Role) request.getAttribute(Constant.K_ROLE);
        if (role != null && role.getCode() < Role.Administrator.getCode()) {
            return Constant.REDIRECT_TO_PROFILE;
        }
        model.addAttribute("title", __("Export"));
        return "admin/tool-export";
    }

}
