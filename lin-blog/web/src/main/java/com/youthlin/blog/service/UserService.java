package com.youthlin.blog.service;

import com.youthlin.blog.dao.UserDao;
import com.youthlin.blog.model.User;
import com.youthlin.blog.util.Constant;
import com.youthlin.blog.util.MD5Util;
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

    public boolean login(String username, String md5ByUserNameAndPassword) {
        User user = userDao.findByUserName(username);
        if (user != null) {
            String userPass = user.getUserPass();
            if (userPass.length() == Constant.PASS_LEN) {
                String rand = userPass.substring(0, Constant.RAND_LEN);
                String md5WithRand = rand + MD5Util.md5(rand + md5ByUserNameAndPassword);
                return md5WithRand.equalsIgnoreCase(userPass);
            }
        }
        return false;
    }
}
