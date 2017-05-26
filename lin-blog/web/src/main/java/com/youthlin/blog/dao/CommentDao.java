package com.youthlin.blog.dao;

import com.youthlin.blog.model.enums.CommentStatus;
import com.youthlin.blog.model.po.Comment;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 获取最近评论(已发布文章的)
     */
    List<Comment> listRecent(int count);

    /**
     * 评论总数
     */
    long count();

    List<Comment> listByStatus(@Param("status") CommentStatus status, @Param("userId") Long userId);

    /**
     * 获取各种状态评论的数量
     */
    long countByStatus(CommentStatus status);

    int updateStatus(@Param("id") long commentId, @Param("status") CommentStatus status);

    int update(Comment comment);
}
