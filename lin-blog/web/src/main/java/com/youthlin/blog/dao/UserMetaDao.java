package com.youthlin.blog.dao;

import com.youthlin.blog.model.po.UserMeta;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 创建： lin
 * 时间： 2017-05-05 16:34
 */
@Repository
public interface UserMetaDao {
    void save(UserMeta userMeta);

    int updateValue(UserMeta userMeta);

    UserMeta findByUserIdAndMetaKey(@Param("userId") Long userId, @Param("metaKey") String metaKey);

    UserMeta findByUserNameAndMetaKey(@Param("name") String username, @Param("key") String metaKey);

    List<UserMeta> listByMetaKey(String metaKey);
}
