package com.youthlin.blog.model.po;

/**
 * 创建者： youthlin.chen 日期： 2017-04-04 21:25.
 * <pre>DROP TABLE IF EXISTS `comment_meta`;
 * CREATE TABLE `comment_meta` (
 * `meta_id`    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
 * `comment_id` BIGINT UNSIGNED NOT NULL DEFAULT '0',
 * `meta_key`   VARCHAR(255)             DEFAULT NULL,
 * `meta_value` LONGTEXT,
 * PRIMARY KEY (`meta_id`),
 * KEY `comment_id` (`comment_id`),
 * KEY `meta_key` (`meta_key`(191))
 * )
 * ENGINE = InnoDB
 * DEFAULT CHARSET = utf8mb4
 * COLLATE = utf8mb4_unicode_ci
 * COMMENT '评论元数据';</pre>
 */
public class CommentMeta extends BaseMeta {
    private Long commentId;

    @Override
    public String toString() {
        return "CommentMeta{" +
                "metaId=" + metaId +
                ", metaKey='" + metaKey + '\'' +
                ", metaValue='" + metaValue + '\'' +
                ", commentId=" + commentId +
                '}';
    }

    public Long getCommentId() {
        return commentId;
    }

    public CommentMeta setCommentId(Long commentId) {
        this.commentId = commentId;
        return this;
    }
}
