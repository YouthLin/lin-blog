package com.youthlin.blog.dao;

import com.youthlin.blog.model.po.Post;
import org.springframework.stereotype.Repository;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-10 20:34.
 */
@Repository
public interface PostDao {
    void save(Post post);
}
