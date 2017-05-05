package com.youthlin.blog.model.po;

/**
 * 创建者： youthlin.chen 日期： 2017-04-04 21:32.
 * <pre>DROP TABLE IF EXISTS `taxonomy_relationships`;
 * CREATE TABLE `taxonomy_relationships` (
 * `post_id`     BIGINT UNSIGNED NOT NULL DEFAULT '0'
 * COMMENT 'post ID',
 * `taxonomy_id` BIGINT UNSIGNED NOT NULL DEFAULT '0'
 * COMMENT '分类 ID',
 * PRIMARY KEY (`post_id`, `taxonomy_id`),
 * KEY `term_taxonomy_id` (`taxonomy_id`)
 * )
 * ENGINE = InnoDB
 * DEFAULT CHARSET = utf8mb4
 * COLLATE = utf8mb4_unicode_ci
 * COMMENT 'post - 分类 关联表';</pre>
 */
public class TaxonomyRelationships {
    private Long postId;
    private Long taxonomyId;

    @Override
    public String toString() {
        return "TaxonomyRelationships{" +
                "postId=" + postId +
                ", taxonomyId=" + taxonomyId +
                '}';
    }

    public Long getPostId() {
        return postId;
    }

    public TaxonomyRelationships setPostId(Long postId) {
        this.postId = postId;
        return this;
    }

    public Long getTaxonomyId() {
        return taxonomyId;
    }

    public TaxonomyRelationships setTaxonomyId(Long taxonomyId) {
        this.taxonomyId = taxonomyId;
        return this;
    }
}
