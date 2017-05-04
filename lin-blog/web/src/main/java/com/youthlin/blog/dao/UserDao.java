package com.youthlin.blog.dao;

import com.youthlin.blog.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 创建： lin
 * 时间： 2017-05-03 16:30
 */
@Repository
public interface UserDao {
    void save(User user);

    User findByUserName(String username);

    User findByUserNameAndPassword(@Param("username") String username, @Param("password") String password);
}