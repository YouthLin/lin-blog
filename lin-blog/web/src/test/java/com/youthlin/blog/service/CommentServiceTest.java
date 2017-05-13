package com.youthlin.blog.service;

import com.youthlin.blog.model.bo.CommentNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-13 00:48.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/app.xml"})
public class CommentServiceTest {
    @Resource
    private CommentService commentService;

    @Test
    public void getTopLevelCommentOfPost() throws Exception {
        List<CommentNode> commentNodeList = commentService.getTopLevelCommentOfPost(1L);
        System.out.println(commentNodeList);
    }

}