package com.youthlin.blog.service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.youthlin.blog.dao.TaxonomyDao;
import com.youthlin.blog.model.bo.Category;
import com.youthlin.blog.model.po.Taxonomy;
import com.youthlin.blog.support.GlobalInfo;
import com.youthlin.blog.util.Constant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-08 22:45.
 */
@Service
public class CategoryService {
    @Resource
    private TaxonomyDao taxonomyDao;
    @Resource
    private GlobalInfo<String, List<Category>> globalInfo;

    public List<Category> listCategories() {
        return globalInfo.get(Constant.O_ALL_CATEGORIES, () -> {
            // 按 id 排序的
            List<Taxonomy> categories = taxonomyDao.findByTaxonomy(Taxonomy.TAXONOMY_CATEGORY);
            List<Category> categoryList = fromTaxonomyList(categories);
            Map<Long, Category> categoryMap = Maps.newHashMap();
            for (Category category : categoryList) {
                categoryMap.put(category.getTaxonomyId(), category);
                Long parentId = category.getParent();
                if (0 != parentId) {
                    Category parent = categoryMap.get(parentId);
                    parent.getChildren().add(category);
                    category.setParentCategory(parent)
                            .setDepth(parent.getDepth() + 1);
                    category.setName(Strings.repeat(Constant.LONG_DASH, category.getDepth()) + category.getName());
                }
            }
            return categoryList;
        });
    }

    private List<Category> fromTaxonomyList(List<Taxonomy> taxonomyList) {
        List<Category> categoryList = Lists.newArrayListWithExpectedSize(taxonomyList.size());
        for (Taxonomy taxonomy : taxonomyList) {
            categoryList.add(fromTaxonomy(taxonomy));
        }
        return categoryList;
    }

    private Category fromTaxonomy(Taxonomy taxonomy) {
        if (!Taxonomy.TAXONOMY_CATEGORY.equals(taxonomy.getTaxonomy())) {
            return null;
        }
        Category category = new Category();
        category.setName(taxonomy.getName())
                .setSlug(taxonomy.getSlug())
                .setDescription(taxonomy.getDescription())
                .setParent(taxonomy.getParent())
                .setCount(taxonomy.getCount())
                .setTaxonomyId(taxonomy.getTaxonomyId());
        return category;
    }

}
