package com.youthlin.blog.web.back;

import com.youthlin.blog.model.bo.Page;
import com.youthlin.blog.model.enums.CommentStatus;
import com.youthlin.blog.model.enums.Role;
import com.youthlin.blog.model.po.Comment;
import com.youthlin.blog.service.CommentService;
import com.youthlin.blog.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.Map;

import static com.youthlin.utils.i18n.Translation.__;

/**
 * 创建： lin
 * 时间： 2017-05-06 21:53
 */
@Controller
@RequestMapping("/admin")
public class CommentController {
    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);
    @Resource
    private CommentService commentService;

    @RequestMapping(path = {"/comment", "/comment/{status}"})
    public String commentList(Model model, HttpServletRequest request,
                              @PathVariable(required = false, name = "status") String statusStr,
                              @RequestParam Map<String, String> param) {
        Role role = (Role) request.getAttribute(Constant.K_ROLE);
        if (role != null && role.getCode() < Role.Editor.getCode()) {
            return Constant.REDIRECT_TO_PROFILE;
        }
        log.debug("list all comments, params = {}", param);
        CommentStatus status = parseStatus(statusStr, model);

        String pageIndexStr = param.get("page");
        int pageIndex = 1;
        try {
            pageIndex = Integer.parseInt(pageIndexStr);
        } catch (NumberFormatException ignore) {
        }
        Page<Comment> commentPage = commentService.listPageByStatus(pageIndex, Constant.DEFAULT_PAGE_SIZE, status);
        CommentStatus[] commentStatuses = CommentStatus.values();
        long sum = 0;
        for (CommentStatus commentStatus : commentStatuses) {
            long count = commentService.countByStatus(commentStatus);
            sum += count;
            model.addAttribute(commentStatus.name(), count);
        }
        model.addAttribute("allCount", sum);
        model.addAttribute("commentPage", commentPage);
        model.addAttribute("title", __("All Comments"));
        return "admin/comment-all";
    }

    private CommentStatus parseStatus(String statusStr, Model model) {
        CommentStatus status = null;
        if (StringUtils.hasText(statusStr)) {
            status = CommentStatus.nameOf(statusStr.toUpperCase());
        }
        if (status == null) {
            model.addAttribute("status", "all");
        } else {
            model.addAttribute("status", status.name().toLowerCase());
        }
        return status;
    }

    @RequestMapping("/comment/my")
    public String myComments(Model model) {
        model.addAttribute("title", __("My Comments"));
        return "admin/comment-my";
    }
}
