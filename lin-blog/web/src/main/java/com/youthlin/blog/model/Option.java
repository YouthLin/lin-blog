package com.youthlin.blog.model;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 创建者： youthlin.chen 日期： 2017-04-04 21:33.
 * <pre>DROP TABLE IF EXISTS `options`;
 * CREATE TABLE `options` (
 * `option_id`    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT
 * COMMENT '自增主键',
 * `option_name`  VARCHAR(191)    NOT NULL DEFAULT ''
 * COMMENT '设置项',
 * `option_value` LONGTEXT        NOT NULL
 * COMMENT '设置项内容',
 * PRIMARY KEY (`option_id`),
 * UNIQUE KEY `option_name` (`option_name`)
 * )
 * ENGINE = InnoDB
 * DEFAULT CHARSET = utf8mb4
 * COLLATE = utf8mb4_unicode_ci
 * COMMENT '设置表';</pre>
 */
public class Option {
    public static final Map<String, String> options = Maps.newConcurrentMap();
    private Long optionId;
    private String optionName;
    private String optionValue;

    @Override
    public String toString() {
        return "Option{" +
                "optionId=" + optionId +
                ", optionName='" + optionName + '\'' +
                ", optionValue='" + optionValue + '\'' +
                '}';
    }

    public Long getOptionId() {
        return optionId;
    }

    public Option setOptionId(Long optionId) {
        this.optionId = optionId;
        return this;
    }

    public String getOptionName() {
        return optionName;
    }

    public Option setOptionName(String optionName) {
        this.optionName = optionName;
        return this;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public Option setOptionValue(String optionValue) {
        this.optionValue = optionValue;
        return this;
    }

}
