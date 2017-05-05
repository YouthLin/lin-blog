package com.youthlin.blog.dao;

import com.youthlin.blog.model.po.Option;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 创建： lin
 * 时间： 2017-05-05 12:07
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/app.xml"})
public class OptionDaoTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(OptionDaoTest.class);
    @Resource
    private OptionDao optionDao;

    @Test
    public void test() throws Exception {
        Option option = new Option().setOptionName("test_name").setOptionValue("test_value");
        optionDao.save(option);
        LOGGER.info("saved: {}", option);
        Option byName = optionDao.findByName("test_name");
        LOGGER.info("find: {}", byName);
    }
}
