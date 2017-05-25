package com.youthlin.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.youthlin.blog.dao.CommentDao;
import com.youthlin.blog.dao.PostDao;
import com.youthlin.blog.model.bo.CommentNode;
import com.youthlin.blog.model.bo.Page;
import com.youthlin.blog.model.enums.CommentStatus;
import com.youthlin.blog.model.po.Comment;
import com.youthlin.blog.model.po.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
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
    @Resource
    private PostDao postDao;

    public Comment save(Comment comment) {
        commentDao.save(comment);
        return comment;
    }

    public List<CommentNode> getTopLevelCommentOfPost(Long postId) {
        List<CommentNode> list = Lists.newArrayList();
        List<Comment> commentList = commentDao.findByPostId(postId);
        Map<Long, CommentNode> commentNodeMap = Maps.newHashMap();
        CommentNode current = null, next;
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
                commentNodeMap.put(comment.getCommentId(), node);
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

    public Comment findById(long id) {
        return commentDao.findById(id);
    }

    public LinkedHashMap<Comment, Post> getRecentComment(int count) {
        List<Comment> comments = commentDao.listRecent(count);
        List<Long> postIds = Lists.newArrayList();
        for (Comment comment : comments) {
            postIds.add(comment.getCommentPostId());
        }
        List<Post> posts = postDao.listPost(postIds);
        Map<Long, Post> postMap = Maps.newHashMapWithExpectedSize(posts.size());
        for (Post post : posts) {
            postMap.put(post.getPostId(), post);
        }
        LinkedHashMap<Comment, Post> map = Maps.newLinkedHashMapWithExpectedSize(comments.size());
        for (Comment comment : comments) {
            map.put(comment, postMap.get(comment.getCommentPostId()));
        }
        return map;
    }

    /**
     * 评论总数
     */
    public long count() {
        return commentDao.count();
    }

    public long countByStatus(CommentStatus status) {
        return commentDao.countByStatus(status);
    }

    /**
     * @param status 不为 null 时，直查该状态的评论。为 null 查所有状态的评论
     */
    public Page<Comment> listPageByStatus(int pageIndex, int pageSize, CommentStatus status, Long userId) {
        PageInfo<Comment> pageInfo = PageHelper.startPage(pageIndex, pageSize).doSelectPageInfo(() -> commentDao.listByStatus(status, userId));
        Page<Comment> page = new Page<>(pageInfo);
        log.debug("list comments: {}", page);
        return page;
    }

    public boolean changeStatus(Long commentId, CommentStatus newStatus) {
        return commentDao.updateStatus(commentId, newStatus) == 1;
    }

    public boolean update(Comment comment) {
        return commentDao.update(comment) == 1;
    }

}
