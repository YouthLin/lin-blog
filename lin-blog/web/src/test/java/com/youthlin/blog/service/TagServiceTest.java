package com.youthlin.blog.service;

import com.youthlin.blog.model.bo.Pageable;
import com.youthlin.blog.model.po.Taxonomy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-10 10:45.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/app.xml"})
public class TagServiceTest {
    private static final Logger log = LoggerFactory.getLogger(TagServiceTest.class);
    @Resource
    private TagService tagService;

    @Test
    public void findByPage() throws Exception {
        Pageable<Taxonomy> page = tagService.findByPage(1, 5);
        log.info("page ={}", page);
    }

}