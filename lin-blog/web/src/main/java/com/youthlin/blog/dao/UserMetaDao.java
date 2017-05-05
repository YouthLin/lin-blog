package com.youthlin.blog.dao;

import com.youthlin.blog.model.po.UserMeta;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 创建： lin
 * 时间： 2017-05-05 16:34
 */
@Repository
public interface UserMetaDao {
    void save(UserMeta userMeta);

    void updateValue(UserMeta userMeta);

    UserMeta findByUserIdAndMetaKey(@Param("userId") Long userId, @Param("metaKey") String metaKey);
}
