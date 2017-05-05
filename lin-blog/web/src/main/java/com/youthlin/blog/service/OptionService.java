package com.youthlin.blog.service;

import com.youthlin.blog.dao.OptionDao;
import com.youthlin.blog.model.po.Option;
import com.youthlin.blog.support.GlobalInfo;
import com.youthlin.blog.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 创建： lin
 * 时间： 2017-05-05 12:28
 */
@Service
public class OptionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OptionService.class);
    @Resource
    private OptionDao optionDao;
    @Resource
    private GlobalInfo<String, String> globalInfo;

    public boolean installed() {
        String installed = globalInfo.get(Constant.O_INSTALLED, () -> {
            Option option = optionDao.findByName(Constant.O_INSTALLED);
            LOGGER.info("Global Info 无结果，查询数据库：{}", option);
            if (option != null) {
                return option.getOptionValue();
            }
            return null;
        });
        return installed != null;
    }

    public void install(String blogTitle) {
        Option option = new Option()
                .setOptionName(Constant.O_BLOG_TITLE)
                .setOptionValue(blogTitle);
        optionDao.save(option);
        globalInfo.set(Constant.O_INSTALLED, blogTitle);
    }
}
