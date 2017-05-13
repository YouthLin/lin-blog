package com.youthlin.blog.web.front;

import com.youthlin.blog.model.bo.CommentNode;
import com.youthlin.blog.model.po.Post;
import com.youthlin.blog.service.CommentService;
import com.youthlin.blog.service.PostService;
import com.youthlin.blog.util.PostTaxonomyHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-12 22:41.
 */
@Controller
public class SinglePostController {
    @Resource
    private PostService postService;
    @Resource
    private CommentService commentService;

    @RequestMapping(path = {"/{id}"}, method = {RequestMethod.GET})
    public String view(@PathVariable(required = false, name = "id") String id, Model model) {
        long postId = 0;
        try {
            postId = Long.parseLong(id);
        } catch (NumberFormatException ignore) {
        }
        Post post = postService.findById(postId);
        if (post == null) {
            return "404";
        }
        model.addAttribute("post", post);
        PostTaxonomyHelper.fetchTaxonomyRelationships(Collections.singletonList(post), model, postService);
        fetchComment(post, model);
        return "post";
    }

    private void fetchComment(Post post, Model model) {
        List<CommentNode> topLevelCommentNodeList = commentService.getTopLevelCommentOfPost(post.getPostId());
        model.addAttribute("topLevelCommentNodeList", topLevelCommentNodeList);
    }
}
