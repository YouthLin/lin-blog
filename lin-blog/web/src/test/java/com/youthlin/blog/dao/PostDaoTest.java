package com.youthlin.blog.dao;

import com.youthlin.blog.model.enums.PostStatus;
import com.youthlin.blog.model.po.Post;
import org.apache.ibatis.session.RowBounds;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

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

    @Test
    public void findByStatusAndDateAndCategoryIdAndTag() throws Exception {
        List<Post> posts = postDao.findByStatusAndDateAndCategoryIdAndTagAndAuthorId
                (null, null, null, null, null,1L);
        System.out.println("所有文章");
        for (Post post : posts) {
            System.out.println(post.getPostId() + ":" + post.getPostTitle());
        }

        posts = postDao.findByStatusAndDateAndCategoryIdAndTagAndAuthorId
                (PostStatus.PUBLISHED, null, null, null, null, 1L,new RowBounds(1, 2));
        System.out.println("已发布文章：限 2 条");
        for (Post post : posts) {
            System.out.println(post.getPostId() + ":" + post.getPostTitle());
        }

        posts = postDao.findByStatusAndDateAndCategoryIdAndTagAndAuthorId
                (null, null, null, 1L, null,1L, new RowBounds(1, 2));
        System.out.println("未分类文章：限 2 条");
        for (Post post : posts) {
            System.out.println(post.getPostId() + ":" + post.getPostTitle());
        }

        posts = postDao.findByStatusAndDateAndCategoryIdAndTagAndAuthorId
                (null, null, null, null, "标签1", 1L,new RowBounds(1, 2));
        System.out.println("标签 1 文章：限 2 条");
        for (Post post : posts) {
            System.out.println(post.getPostId() + ":" + post.getPostTitle());
        }


    }


}