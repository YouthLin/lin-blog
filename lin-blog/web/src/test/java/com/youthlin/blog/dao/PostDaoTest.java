package com.youthlin.blog.dao;

import com.youthlin.blog.model.enums.PostStatus;
import com.youthlin.blog.model.po.Post;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-10 20:47.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/app.xml"})
public class PostDaoTest {
    @Resource
    private PostDao postDao;

    @Test
    public void save() throws Exception {
        Post post = new Post()
                .setPostTitle("Test")
                .setPostContent("Test Content")
                .setPostStatus(PostStatus.DRAFT);
        System.out.println(post);
        postDao.save(post);
    }

    @Test
    public void timezone() {
        DateTime now = DateTime.now();
        System.out.println(now);
        DateTime utc = new DateTime(now, DateTimeZone.UTC);
        System.out.println(utc);
        System.out.println(utc.toLocalDateTime().toDate());
        System.out.println(now.toDate());
    }

}