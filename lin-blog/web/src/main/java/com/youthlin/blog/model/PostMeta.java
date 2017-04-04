package com.youthlin.blog.model;

/**
 * 创建者： youthlin.chen 日期： 2017-04-04 21:03.
 * <pre>DROP TABLE IF EXISTS `post_meta`;
 * CREATE TABLE `post_meta` (
 * `meta_id`    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT
 * COMMENT '自增主键',
 * `post_id`    BIGINT UNSIGNED NOT NULL DEFAULT '0'
 * COMMENT 'post id',
 * `meta_key`   VARCHAR(255)             DEFAULT NULL,
 * `meta_value` LONGTEXT,
 * PRIMARY KEY (`meta_id`),
 * KEY `post_id` (`post_id`),
 * KEY `meta_key` (`meta_key`(191))
 * )
 * ENGINE = InnoDB
 * DEFAULT CHARSET = utf8mb4
 * COLLATE = utf8mb4_unicode_ci
 * COMMENT 'post 元数据';</pre>
 */
public class PostMeta extends BaseMeta {
    private Long postId;

    @Override
    public String toString() {
        return "PostMeta{" +
                "metaId=" + metaId +
                ", metaKey='" + metaKey + '\'' +
                ", metaValue='" + metaValue + '\'' +
                ", postId=" + postId +
                '}';
    }

    //region getter setter
    public Long getPostId() {
        return postId;
    }

    public PostMeta setPostId(Long postId) {
        this.postId = postId;
        return this;
    }

    //endregion
}
