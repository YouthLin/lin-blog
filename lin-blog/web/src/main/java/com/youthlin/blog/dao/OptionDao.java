package com.youthlin.blog.dao;

import com.youthlin.blog.model.po.Option;
import org.springframework.stereotype.Repository;

/**
 * 博客设置
 * <p>
 * 创建： lin
 * 时间： 2017-05-05 11:48
 */
@Repository
public interface OptionDao {
    void save(Option option);

    Option findByName(String optionName);
}
