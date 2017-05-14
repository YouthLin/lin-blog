package com.youthlin.blog.service;

import com.youthlin.blog.dao.CommentDao;
import com.youthlin.blog.dao.OptionDao;
import com.youthlin.blog.dao.PostDao;
import com.youthlin.blog.dao.TaxonomyDao;
import com.youthlin.blog.dao.UserDao;
import com.youthlin.blog.dao.UserMetaDao;
import com.youthlin.blog.model.bo.Category;
import com.youthlin.blog.model.enums.PostStatus;
import com.youthlin.blog.model.enums.Role;
import com.youthlin.blog.model.po.Comment;
import com.youthlin.blog.model.po.Option;
import com.youthlin.blog.model.po.Post;
import com.youthlin.blog.model.po.TaxonomyRelationships;
import com.youthlin.blog.model.po.User;
import com.youthlin.blog.model.po.UserMeta;
import com.youthlin.blog.support.GlobalInfo;
import com.youthlin.blog.util.Constant;
import com.youthlin.blog.util.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.UUID;

import static com.youthlin.utils.i18n.Translation.__;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-08 22:05.
 */
@Service
public class SetupService {
    @Resource
    private UserDao userDao;
    @Resource
    private UserMetaDao userMetaDao;
    @Resource
    private OptionDao optionDao;
    @Resource
    private TaxonomyDao taxonomyDao;
    @Resource
    private GlobalInfo<String, Object> globalInfo;
    @Resource
    private PostDao postDao;
    @Resource
    private CommentDao commentDao;

    @Transactional
    public void setup(String pass, String user, String email, String title) {
        saveAdmin(user, pass, email);
        saveTitle(title);
        createCategory();
        createPost();
    }

    private void saveAdmin(String user, String pass, String email) {
        String rand = UUID.randomUUID().toString().substring(0, 8);
        if (rand.length() != 8) {
            throw new AssertionError();//won't happen
        }
        pass = rand + MD5Util.md5(rand + pass);
        User admin = new User()
                .setUserLogin(user)
                .setUserPass(pass)
                .setUserEmail(email)
                .setDisplayName(user);
        userDao.save(admin);
        UserMeta roleInfo = new UserMeta();
        roleInfo.setUserId(admin.getUserId())
                .setMetaKey(Constant.K_ROLE)
                .setMetaValue(Role.Administrator.name());
        userMetaDao.save(roleInfo);
    }

    private void saveTitle(String title) {
        Option option = new Option()
                .setOptionName(Constant.O_BLOG_TITLE)
                .setOptionValue(title);
        optionDao.save(option);
        globalInfo.set(Constant.O_BLOG_TITLE, title);
    }

    private void createCategory() {
        Category uncategorized = new Category();
        uncategorized
                .setName(__("Un Categorized"))
                .setSlug("uncategorized")
                .setDescription("")
                .setParent(0L)
                .setCount(1L);
        taxonomyDao.save(uncategorized);
    }

    private void createPost() {
        Post post = new Post();
        post.setPostTitle(__("Hello, World"))
                .setPostContent(__("Welcome to use LinBlog. This post is generate by the blog system. Edit or delete this post, and then going to start your blog!"))
                .setPostStatus(PostStatus.PUBLISHED)
                .setPostAuthorId(1L)
                .setCommentCount(1L);
        postDao.save(post);
        TaxonomyRelationships relationships = new TaxonomyRelationships();
        relationships.setPostId(post.getPostId())
                .setTaxonomyId(1L);// un categorised
        taxonomyDao.saveTaxonomyRelationships(Collections.singletonList(relationships));
        Comment comment = new Comment()
                .setCommentPostId(post.getPostId())
                .setCommentAuthor("Youth．霖")
                .setCommentAuthorUrl("http://youthlin.com/")
                .setCommentAuthorEmail("yulinlin1995@gmail.com")
                .setCommentContent(__("Hello, this is a comment. You can view or delete some comments at dashboard once you have login."));
        commentDao.save(comment);
    }

}
