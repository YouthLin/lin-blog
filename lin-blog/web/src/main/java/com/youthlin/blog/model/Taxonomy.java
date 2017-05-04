package com.youthlin.blog.model;

/**
 * 创建者： youthlin.chen 日期： 2017-04-04 21:26.
 * <pre>
 * DROP TABLE IF EXISTS `taxonomy`;
 * CREATE TABLE `taxonomy` (
 * `taxonomy_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT
 * COMMENT '自增主键',
 * `name`        VARCHAR(200)    NOT NULL DEFAULT ''
 * COMMENT '分类名',
 * `slug`        VARCHAR(200)    NOT NULL DEFAULT ''
 * COMMENT '分类URL',
 * `taxonomy`    VARCHAR(32)     NOT NULL DEFAULT ''
 * COMMENT '分类法，如 category / tag / format ...',
 * `description` LONGTEXT        NOT NULL
 * COMMENT '描述',
 * `parent`      BIGINT UNSIGNED NOT NULL DEFAULT '0'
 * COMMENT '父级分类',
 * `count`       BIGINT          NOT NULL DEFAULT '0'
 * COMMENT '该分类下 post 数量',
 * PRIMARY KEY (`taxonomy_id`),
 * UNIQUE KEY `uniq_name_taxonomy` (`name`, `taxonomy`),
 * KEY `taxonomy` (`taxonomy`)
 * )
 * ENGINE = InnoDB
 * DEFAULT CHARSET = utf8mb4
 * COLLATE = utf8mb4_unicode_ci
 * COMMENT '分类信息表';</pre>
 */
public class Taxonomy {
    public static final String TAXONOMY_CATEGORY = "category";
    public static final String TAXONOMY_TAG = "tag";
    private Long taxonomyId;
    private String name;
    private String slug;
    private String taxonomy;
    private String description;
    private Long parent = 0L;
    private Long count = 0L;

    @Override
    public String toString() {
        return "Taxonomy{" +
                "taxonomyId=" + taxonomyId +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                ", taxonomy='" + taxonomy + '\'' +
                ", description='" + description + '\'' +
                ", parent=" + parent +
                ", count=" + count +
                '}';
    }

    //region getter setter
    public Long getTaxonomyId() {
        return taxonomyId;
    }

    public Taxonomy setTaxonomyId(Long taxonomyId) {
        this.taxonomyId = taxonomyId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Taxonomy setName(String name) {
        this.name = name;
        return this;
    }

    public String getSlug() {
        return slug;
    }

    public Taxonomy setSlug(String slug) {
        this.slug = slug;
        return this;
    }

    public String getTaxonomy() {
        return taxonomy;
    }

    public Taxonomy setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Taxonomy setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getParent() {
        return parent;
    }

    public Taxonomy setParent(Long parent) {
        this.parent = parent;
        return this;
    }

    public Long getCount() {
        return count;
    }

    public Taxonomy setCount(Long count) {
        this.count = count;
        return this;
    }
    //endregion

}
