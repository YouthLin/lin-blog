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
 * 时间： 2017-05-06 21:53
 */
@Controller
@RequestMapping("/admin")
public class CommentController {
    @RequestMapping("/comment/all")
    public String commentList(Model model, HttpServletRequest request) {
        Role role = (Role) request.getAttribute(Constant.K_ROLE);
        if (role != null && role.getCode() < Role.Editor.getCode()) {
            return Constant.REDIRECT_TO_PROFILE;
        }
        model.addAttribute("title", __("All Comments"));
        return "admin/comment-all";
    }

    @RequestMapping("/comment/my")
    public String myComments(Model model) {
        model.addAttribute("title", __("My Comments"));
        return "admin/comment-my";
    }
}
