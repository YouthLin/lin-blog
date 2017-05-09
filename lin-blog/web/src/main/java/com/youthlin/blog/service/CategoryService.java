package com.youthlin.blog.service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.youthlin.blog.dao.TaxonomyDao;
import com.youthlin.blog.model.bo.Category;
import com.youthlin.blog.model.po.Taxonomy;
import com.youthlin.blog.support.GlobalInfo;
import com.youthlin.blog.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);
    @Resource
    private TaxonomyDao taxonomyDao;
    @Resource
    private GlobalInfo<String, List<Category>> globalInfo;

    public Category save(Category category) {
        taxonomyDao.save(category);
        globalInfo.set(Constant.O_ALL_CATEGORIES, null);
        log.info("保存分类目录，重置缓存");
        return category;
    }

    public List<Category> listCategoriesByOrder() {
        List<Category> topLevelCategories = listTopLevelCategories();
        List<Category> categoryList = Lists.newArrayList();
        for (Category topLevel : topLevelCategories) {
            categoryList.add(topLevel);
            addChildren(categoryList, topLevel);
        }
        return categoryList;
    }

    private void addChildren(List<Category> categoryList, Category category) {
        if (category == null || category.getChildren().size() == 0) {
            return;
        }
        List<Category> children = category.getChildren();
        for (Category category1 : children) {
            categoryList.add(category1);
            addChildren(categoryList, category1);
        }
    }

    @SuppressWarnings("WeakerAccess")
    public List<Category> listTopLevelCategories() {
        List<Category> topLevel = Lists.newArrayList();
        List<Category> categoryList = listCategories();
        for (Category category : categoryList) {
            if (category.getParentCategory() == null) {
                topLevel.add(category);
            }
        }
        return topLevel;
    }

    public Category findById(long id) {
        List<Category> categoryList = listCategoriesByOrder();
        for (Category category : categoryList) {
            if (category.getTaxonomyId() == id) {
                Category find = new Category(category);
                String prefix = Strings.repeat(Constant.DASH, find.getDepth());
                if (find.getDepth() != 0) {
                    String name = find.getName();
                    name = name.substring(prefix.length());
                    find.setName(name);
                }
                return find;
            }
        }
        return null;
    }

    public Category update(Category category) {
        taxonomyDao.update(category);
        globalInfo.set(Constant.O_ALL_CATEGORIES, null);
        return category;
    }

    private List<Category> listCategories() {
        return globalInfo.get(Constant.O_ALL_CATEGORIES, () -> {
            log.info("获取分类目录未命中缓存，查询数据库");
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
                    category.setName(Strings.repeat(Constant.DASH, category.getDepth()) + category.getName());
                }
            }
            globalInfo.set(Constant.O_ALL_CATEGORIES, categoryList);
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