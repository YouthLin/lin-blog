package com.youthlin.blog.dao;

import com.youthlin.blog.model.po.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 创建： lin
 * 时间： 2017-05-04 09:41
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/app.xml"})
public class UserDaoTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoTest.class);

    @Resource
    private UserDao userDao;

    private static final String USER = "lin";
    private static final String PASS = "12345678901234567890123456789012";

    // @Test
    public void save() throws Exception {
        User user = new User();
        user.setUserLogin(USER)
                .setUserPass(PASS)
                .setUserEmail("948310204@qq.com")
                .setUserUrl("http://youthlin.com")
                .setDisplayName("YouthLin Chen");
        userDao.save(user);
        LOGGER.info("saved user: {}", user);
    }

    @Test
    public void findByUserName() throws Exception {
        //save();
        User lin = userDao.findByUserName("lin");
        LOGGER.info("lin = {}", lin);
    }

    @Test
    public void findByUserNameAndPassword() throws Exception {
        //save();
        User lin = userDao.findByUserNameAndPassword(USER, PASS);
        LOGGER.info("lin = {}", lin);
    }

}
