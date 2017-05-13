package com.youthlin.blog.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.youthlin.blog.dao.CommentDao;
import com.youthlin.blog.model.bo.CommentNode;
import com.youthlin.blog.model.po.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-13 00:11.
 */
@Service
public class CommentService {
    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);
    @Resource
    private CommentDao commentDao;

    public List<CommentNode> getTopLevelCommentOfPost(Long postId) {
        List<CommentNode> list = Lists.newArrayList();
        List<Comment> commentList = commentDao.findByPostId(postId);
        Map<Long, CommentNode> commentNodeMap = Maps.newHashMap();
        CommentNode current = null, next = null;
        for (Comment comment : commentList) {
            Long parentId = comment.getCommentParent();
            if (parentId == 0L) {
                if (current == null) {
                    current = new CommentNode().setComment(comment);
                    commentNodeMap.put(comment.getCommentId(), current);
                    list.add(current);
                } else {
                    next = new CommentNode().setComment(comment);
                    commentNodeMap.put(comment.getCommentId(), next);
                    list.add(next);
                    current.setNext(next);
                    current = next;
                }
            } else {
                CommentNode parentNode = commentNodeMap.get(parentId);
                CommentNode node = new CommentNode()
                        .setComment(comment)
                        .setParent(parentNode)
                        .setLevel(parentNode.getLevel() + 1);
                int size = parentNode.getChildren().size();
                if (size > 0) {
                    CommentNode pre = parentNode.getChildren().get(size - 1);
                    pre.setNext(node);
                }
                parentNode.getChildren().add(node);
            }
        }
        return list;
    }
}
