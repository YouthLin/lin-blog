package com.youthlin.blog.web.front;

import com.youthlin.blog.model.bo.CommentNode;
import com.youthlin.blog.model.enums.PostStatus;
import com.youthlin.blog.model.enums.Role;
import com.youthlin.blog.model.po.Comment;
import com.youthlin.blog.model.po.Post;
import com.youthlin.blog.model.po.User;
import com.youthlin.blog.service.CommentService;
import com.youthlin.blog.service.PostService;
import com.youthlin.blog.util.Constant;
import com.youthlin.blog.util.PostTaxonomyHelper;
import com.youthlin.blog.util.ServletUtil;
import org.joda.time.DateTime;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.youthlin.utils.i18n.Translation.__;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-12 22:41.
 */
@Controller
public class SinglePostController {
    private static final Logger log = LoggerFactory.getLogger(SinglePostController.class);
    @Resource
    private PostService postService;
    @Resource
    private CommentService commentService;

    @SuppressWarnings("StatementWithEmptyBody")
    @RequestMapping(path = {"/post/{id}"}, method = {RequestMethod.GET})
    public String view(@PathVariable(required = false, name = "id") String id, Model model, HttpServletRequest request) {
        long postId = 0;
        try {
            postId = Long.parseLong(id);
        } catch (NumberFormatException ignore) {
        }
        Post post = postService.findById(postId);
        if (post == null || new DateTime(post.getPostDate()).isAfterNow()) {
            return "404";
        }
        User user = (User) request.getAttribute(Constant.USER);
        Role role = (Role) request.getAttribute(Constant.K_ROLE);
        if (post.getPostStatus() != PostStatus.PUBLISHED) {//对于未发布文章
            //编辑+ 可查看、文章作者可查看
            if (role != null && role.getCode() >= Role.Editor.getCode()) {
            } else if (user != null && post.getPostAuthorId().equals(user.getUserId())) {
            } else {
                return "404";            //否则 404
            }
        }
        model.addAttribute("post", post);
        PostTaxonomyHelper.fetchTaxonomyRelationships(Collections.singletonList(post), model, postService);
        fetchComment(post, model);
        Post next = postService.findNextOrPrevious(postId, true);
        Post previous = postService.findNextOrPrevious(postId, false);
        model.addAttribute("next", next);
        model.addAttribute("previous", previous);
        return "post";
    }

    private void fetchComment(Post post, Model model) {
        List<CommentNode> topLevelCommentNodeList = commentService.getTopLevelCommentOfPost(post.getPostId());
        model.addAttribute("topLevelCommentNodeList", topLevelCommentNodeList);
    }

    @RequestMapping(path = {"/post/{id}"}, method = {RequestMethod.POST})
    public String comment(@RequestParam Map<String, String> param, HttpServletRequest request,
                          @PathVariable(name = "id", required = false) String id, Model model) {
        long postId;
        try {
            postId = Long.parseLong(id);
        } catch (NumberFormatException ignore) {
            model.addAttribute(Constant.ERROR, __("No such post."));
            return "die";
        }
        Post post = postService.findById(postId);
        if (post == null) {
            model.addAttribute(Constant.ERROR, __("No such post."));
            return "die";
        }
        Boolean commentOpen = post.getCommentOpen();
        if (commentOpen == null || !commentOpen) {
            model.addAttribute(Constant.ERROR, __("Comments are closed on this post."));
            return "die";
        }
        String author = param.get("author");
        String email = param.get("email");
        String url = param.get("url");
        String content = param.get("content");
        if (!StringUtils.hasText(author) || !StringUtils.hasText(email) || !StringUtils.hasText(content)) {
            model.addAttribute(Constant.ERROR, __("Name, email, and comment content are required."));
            return "die";
        }
        if (!StringUtils.hasText(url)) {
            url = "";
        }
        url = ServletUtil.filterHtml(url);
        String to = param.get("to");
        long parent = 0;
        try {
            parent = Long.parseLong(to);
        } catch (NumberFormatException ignore) {
        }
        if (parent != 0) {
            Comment parentComment = commentService.findById(parent);
            if (parentComment == null) {
                model.addAttribute(Constant.ERROR, __("Parent comment does not exist."));
                return "die";
            }
            if (!parentComment.getCommentPostId().equals(postId)) {
                model.addAttribute(Constant.ERROR, __("This post does not contains the parent comment."));
                return "die";
            }
        }
        log.info("param:post id = {}, author = {}, email = {}, url = {}, content = {}", postId, author, email, url, content);
        author = ServletUtil.filterHtml(author);
        email = ServletUtil.filterHtml(email);
        url = ServletUtil.filterHtml(url);
        content = ServletUtil.filterXss(content.replaceAll("(\\r\\n|\\n)", "<br>\n"));
        Comment comment = new Comment()
                .setCommentPostId(postId)
                .setCommentAuthor(author)
                .setCommentAuthorEmail(email)
                .setCommentAuthorUrl(url)
                .setCommentAgent(request.getHeader(Constant.UA))
                .setCommentAuthorIp(ServletUtil.getRemoteIP(request))
                .setCommentContent(content)
                //.setCommentStatus(CommentStatus.NORMAL)
                .setCommentParent(parent);
        commentService.save(comment);
        Long commentCount = post.getCommentCount();
        if (commentCount == null) {
            commentCount = 0L;
        }
        post.setCommentCount(commentCount + 1);
        postService.update(post);
        return "redirect:/post/" + postId + "#comment-" + comment.getCommentId();
    }
}
