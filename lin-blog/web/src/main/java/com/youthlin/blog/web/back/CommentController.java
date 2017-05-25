package com.youthlin.blog.web.back;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.youthlin.blog.model.bo.Page;
import com.youthlin.blog.model.enums.CommentStatus;
import com.youthlin.blog.model.enums.Role;
import com.youthlin.blog.model.po.Comment;
import com.youthlin.blog.model.po.Post;
import com.youthlin.blog.model.po.User;
import com.youthlin.blog.service.CommentService;
import com.youthlin.blog.service.PostService;
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

import java.util.List;
import java.util.Map;
import java.util.Set;

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
    @Resource
    private PostService postService;

    @RequestMapping(path = {"/comment", "/comment/{status}"})
    public String commentList(Model model, HttpServletRequest request,
                              @PathVariable(required = false, name = "status") String statusStr,
                              @RequestParam Map<String, String> param) {
        Role role = (Role) request.getAttribute(Constant.K_ROLE);
        if (role != null && role.getCode() < Role.Editor.getCode()) {
            return Constant.REDIRECT_TO_PROFILE;
        }
        log.debug("list all comments, params = {}", param);
        processAction(param);

        CommentStatus status = parseStatus(statusStr, model);
        processCommentPage(param, model, status, null);
        processStatusCount(model);

        model.addAttribute("title", __("All Comments"));
        return "admin/comment-all";
    }

    private void processAction(Map<String, String> param) {
        String action = param.get("action");
        String idStr = param.get("id");
        long id = -1;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException ignore) {
        }
        if (!StringUtils.hasText(action) || id < 0) {
            return;
        }
        CommentStatus status = CommentStatus.nameOf(action.toUpperCase());
        if (status != null) {
            commentService.changeStatus(id, status);
        }
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

    //param: pageIndex; model: add attribute; status: comment status, null -> all; userId: comment user id, null -> all
    private void processCommentPage(Map<String, String> param, Model model, CommentStatus status, Long userId) {
        String pageIndexStr = param.get("page");
        int pageIndex = 1;
        try {
            pageIndex = Integer.parseInt(pageIndexStr);
        } catch (NumberFormatException ignore) {
        }
        Page<Comment> commentPage = commentService.listPageByStatus(pageIndex, Constant.DEFAULT_PAGE_SIZE, status, userId);
        model.addAttribute("commentPage", commentPage);
        List<Comment> commentList = commentPage.getList();
        Set<Long> postIds = Sets.newHashSet();
        for (Comment comment : commentList) {
            postIds.add(comment.getCommentPostId());
        }
        List<Post> posts = postService.listByPostIds(postIds);
        Map<Long, Post> postMap = Maps.newHashMap();
        for (Post post : posts) {
            postMap.put(post.getPostId(), post);
        }
        model.addAttribute("postMap", postMap);
    }

    private void processStatusCount(Model model) {
        CommentStatus[] commentStatuses = CommentStatus.values();
        long sum = 0;
        for (CommentStatus commentStatus : commentStatuses) {
            long count = commentService.countByStatus(commentStatus);
            sum += count;
            model.addAttribute(commentStatus.name(), count);
        }
        model.addAttribute("allCount", sum);
    }


    @RequestMapping("/comment/my")
    public String myComments(@RequestParam Map<String, String> param, HttpServletRequest request, Model model) {
        User user = (User) request.getAttribute(Constant.USER);
        processCommentPage(param, model, null, user.getUserId());
        model.addAttribute("title", __("My Comments"));
        return "admin/comment-my";
    }
}
