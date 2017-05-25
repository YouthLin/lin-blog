package com.youthlin.blog.dao;

import com.youthlin.blog.model.po.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-13 00:43.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/app.xml"})
public class CommentDaoTest {
    @Resource
    private CommentDao commentDao;

   // @Test
    public void save() throws Exception {
        Comment comment = new Comment();
        comment.setCommentPostId(1L)
                .setCommentContent("Comment on post 1")
                .setCommentAuthor("Author1 ")
                .setCommentAuthorEmail("1@1.cn")
        //.setCommentParent(1L
        ;
        commentDao.save(comment);
        System.out.println("saved: " + comment);
    }

    @Test
    public void findByPostId() throws Exception {
    }

}
