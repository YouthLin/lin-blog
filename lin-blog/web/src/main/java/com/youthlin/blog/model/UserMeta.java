package com.youthlin.blog.model;

/**
 * 创建者： youthlin.chen 日期： 2017-04-04 20:34.
 * <pre>
 * DROP TABLE IF EXISTS `user_meta`;
 * CREATE TABLE `user_meta` (
 * `meta_id`    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT
 * COMMENT '自增主键',
 * `user_id`    BIGINT UNSIGNED NOT NULL DEFAULT '0'
 * COMMENT '用户 ID',
 * `meta_key`   VARCHAR(255)             DEFAULT NULL
 * COMMENT '元数据 key',
 * `meta_value` LONGTEXT COMMENT '元数据 value',
 * PRIMARY KEY (`meta_id`),
 * KEY `user_id` (`user_id`),
 * KEY `meta_key` (`meta_key`(191))
 * )
 * ENGINE = InnoDB
 * AUTO_INCREMENT = 18
 * DEFAULT CHARSET = utf8mb4
 * COLLATE = utf8mb4_unicode_ci
 * COMMENT '用户元数据';
 * </pre>
 */
public class UserMeta extends BaseMeta {
    private Long userId;

    @Override
    public String toString() {
        return "UserMeta{" +
                "metaId=" + metaId +
                ", metaKey='" + metaKey + '\'' +
                ", metaValue='" + metaValue + '\'' +
                ", userId=" + userId +
                '}';
    }

    //region getter setter
    public Long getUserId() {
        return userId;
    }

    public UserMeta setUserId(Long userId) {
        this.userId = userId;
        return this;
    }
    //endregion
}
