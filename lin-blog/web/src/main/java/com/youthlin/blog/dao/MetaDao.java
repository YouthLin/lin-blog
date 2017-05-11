package com.youthlin.blog.dao;

import com.youthlin.blog.model.po.PostMeta;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-11 16:30.
 */
@Repository
public interface MetaDao {
    void save(PostMeta postMeta);

    List<PostMeta> findPostMetaByPostId(Long id);

}
