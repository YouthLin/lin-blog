package com.youthlin.blog.dao;

import com.youthlin.blog.model.enums.PostStatus;
import com.youthlin.blog.model.po.Post;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-10 20:34.
 */
@Repository
public interface PostDao {
    void save(Post post);

    List<Post> findByStatusAndDateAndCategoryIdAndTag
            (@Param("status") PostStatus status, @Param("start") Date start, @Param("end") Date end,
             @Param("categoryId") Long categoryId, @Param("tagName") String tagName);

    List<Post> findByStatusAndDateAndCategoryIdAndTag
            (@Param("status") PostStatus status, @Param("start") Date start, @Param("end") Date end,
             @Param("categoryId") Long categoryId, @Param("tagName") String tagName, RowBounds rowBounds);

}
