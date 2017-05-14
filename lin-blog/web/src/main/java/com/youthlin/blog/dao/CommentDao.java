package com.youthlin.blog.dao;

import com.youthlin.blog.model.po.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-12 23:43.
 */
@Repository
public interface CommentDao {
    void save(Comment comment);

    List<Comment> findByPostId(Long postId);

    Comment findById(Long id);

    List<Comment> listRecent(int count);

    long count();
}
