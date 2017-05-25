package com.youthlin.blog.dao;

import com.youthlin.blog.model.po.UserMeta;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * 创建： lin
 * 时间： 2017-05-05 16:41
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/app.xml"})
public class UserMetaDaoTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserMetaDaoTest.class);

    @Resource
    private UserMetaDao userMetaDao;

    @Test
    public void te() {
    }

    //@Test
    public void test() throws Exception {
        UserMeta userMeta = new UserMeta();
        userMeta.setUserId(1L)
                .setMetaKey("cookie")
                .setMetaValue("COOKIE");
        userMetaDao.save(userMeta);
        LOGGER.info("saved: {}", userMeta);
        UserMeta meta = userMetaDao.findByUserIdAndMetaKey(1L, "cookie");
        LOGGER.info("find: {}", meta);
    }


}
