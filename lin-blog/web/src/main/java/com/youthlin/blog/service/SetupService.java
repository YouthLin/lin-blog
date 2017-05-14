package com.youthlin.blog.service;

import com.youthlin.blog.dao.OptionDao;
import com.youthlin.blog.dao.TaxonomyDao;
import com.youthlin.blog.dao.UserDao;
import com.youthlin.blog.dao.UserMetaDao;
import com.youthlin.blog.model.bo.Category;
import com.youthlin.blog.model.enums.Role;
import com.youthlin.blog.model.po.Option;
import com.youthlin.blog.model.po.User;
import com.youthlin.blog.model.po.UserMeta;
import com.youthlin.blog.support.GlobalInfo;
import com.youthlin.blog.util.Constant;
import com.youthlin.blog.util.MD5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    public void setup(String pass, String user, String email, String title) {
        saveAdmin(user, pass, email);
        saveTitle(title);
        createCategory();
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
                .setCount(0L);
        taxonomyDao.save(uncategorized);
    }
}
