package com.youthlin.blog.model.bo;

import com.youthlin.blog.model.po.Taxonomy;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-10 10:53.
 */
public class Tag extends Taxonomy {
    public Tag() {
        super.setTaxonomy(Taxonomy.TAXONOMY_TAG);
        super.setParent(0L);
    }

    @Override
    public Tag setTaxonomy(String taxonomy) {
        super.setTaxonomy(Taxonomy.TAXONOMY_TAG);
        return this;
    }

    @Override
    public Tag setParent(Long parent) {
        super.setParent(0L);
        return this;
    }
}
