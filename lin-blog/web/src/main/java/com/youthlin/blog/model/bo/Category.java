package com.youthlin.blog.model.bo;

import com.google.common.collect.Lists;
import com.youthlin.blog.model.po.Taxonomy;

import java.util.List;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-08 22:32.
 */
@SuppressWarnings("UnusedReturnValue")
public class Category extends Taxonomy {
    private int depth;
    private Category parentCategory = null;
    private List<Category> children = Lists.newArrayList();

    public Category() {
        setTaxonomy(Taxonomy.TAXONOMY_CATEGORY);
    }

    public Category(Category another) {
        setTaxonomyId(another.getTaxonomyId());
        setName(another.getName());
        setSlug(another.getSlug());
        setTaxonomy(Taxonomy.TAXONOMY_CATEGORY);
        setDescription(another.getDescription());
        setParent(another.getParent());
        setCount(another.getCount());
        setDepth(another.getDepth());
        setParentCategory(another.getParentCategory());
        setChildren(another.getChildren());
    }

    @Override
    public String toString() {
        return "Category{" +
                "depth=" + depth +
                ", parentCategory=" + (parentCategory == null ? "null" : parentCategory.getName()) +
                ", children=" + children +
                "} " + super.toString();
    }

    @Override
    public Taxonomy setTaxonomy(String taxonomy) {
        return super.setTaxonomy(Taxonomy.TAXONOMY_CATEGORY);
    }

    public int getDepth() {
        return depth;
    }

    public Category setDepth(int depth) {
        this.depth = depth;
        return this;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public Category setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
        return this;
    }

    public List<Category> getChildren() {
        return children;
    }

    public Category setChildren(List<Category> children) {
        this.children = children;
        return this;
    }
}
