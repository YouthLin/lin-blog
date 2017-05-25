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
import com.youthlin.blog.util.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    @RequestMapping(path = "/comment/edit")
    public String editComment(@RequestParam Map<String, String> param, HttpServletRequest request, Model model) {
        String die = checkEditParam(param, request, model);
        if (die != null) {
            return die;
        }
        model.addAttribute("title", __("Edit Comment"));
        return "admin/comment-edit";
    }

    private String checkEditParam(Map<String, String> param, HttpServletRequest request, Model model) {
        String idStr = param.get("id");
        long id = -1;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException ignore) {
        }
        if (id < 0) {
            model.addAttribute(Constant.ERROR, __("Invalid param."));
            return "admin/die";
        }
        Comment comment = commentService.findById(id);
        User user = (User) request.getAttribute(Constant.USER);
        Role role = (Role) request.getAttribute(Constant.K_ROLE);
        if (comment == null) {
            model.addAttribute(Constant.ERROR, __("No such comment."));
            return "admin/die";
        }
        if (role.getCode() < Role.Editor.getCode() && !Objects.equals(comment.getUserId(), user.getUserId())) {
            model.addAttribute(Constant.ERROR, __("Sorry, but you can not edit this comment."));
            return "admin/die";
        }
        String content = comment.getCommentContent();
        comment.setCommentContent(content.replaceAll("\\n<br>", "\n"));
        model.addAttribute("comment", comment);
        return null;
    }

    @RequestMapping(path = "comment/edit", method = RequestMethod.POST)
    public String editComment(@RequestParam Map<String, String> param, Model model, HttpServletRequest request) {
        String die = checkEditParam(param, request, model);
        if (die != null) {
            return die;
        }
        String author = param.get("author");
        String email = param.get("email");
        String url = param.get("url");
        String content = param.get("content");
        if (!StringUtils.hasText(author) || !StringUtils.hasText(email) || !StringUtils.hasText(content)) {
            model.addAttribute(Constant.ERROR, __("Name, email, and comment content are required."));
            return "admin/comment-edit";
        }
        if (!StringUtils.hasText(url)) {
            url = "";
        }
        url = ServletUtil.filterHtml(url);
        author = ServletUtil.filterHtml(author);
        email = ServletUtil.filterHtml(email);
        url = ServletUtil.filterHtml(url);
        content = content.replaceAll("(\\r\\n|\\n)", "\n<br>");
        content = ServletUtil.filterXss(content);
        Comment comment = (Comment) model.asMap().get("comment");

        if (comment != null) {
            comment.setCommentAuthor(author)
                    .setCommentAuthorEmail(email)
                    .setCommentAuthorUrl(url)
                    .setCommentContent(content);
            Role role = (Role) request.getAttribute("role");
            if (role.getCode() >= Role.Editor.getCode()) {
                String statusStr = param.get("status");
                CommentStatus status = CommentStatus.nameOf(statusStr);
                if (status != null) {
                    comment.setCommentStatus(status);
                }
            }
            commentService.update(comment);
            comment.setCommentContent(content.replaceAll("\\n<br>", "\n"));
            model.addAttribute("comment", comment);
            model.addAttribute(Constant.MSG, __("Comment updated."));
        }
        return "admin/comment-edit";
    }
}
