package com.youthlin.blog.service;

import com.youthlin.blog.dao.UserDao;
import com.youthlin.blog.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 创建者： youthlin.chen 日期： 17-4-4.
 */
@Service
public class UserService {
    @Resource
    private UserDao userDao;

    public void save(User user) {
        userDao.save(user);
    }

}
