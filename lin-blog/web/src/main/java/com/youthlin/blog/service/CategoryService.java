package com.youthlin.blog.service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.youthlin.blog.dao.TaxonomyDao;
import com.youthlin.blog.model.bo.Category;
import com.youthlin.blog.model.po.Taxonomy;
import com.youthlin.blog.support.GlobalInfo;
import com.youthlin.blog.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-08 22:45.
 */
@Service
public class CategoryService extends TaxonomyService {
    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);
    @Resource
    private TaxonomyDao taxonomyDao;
    @Resource
    private GlobalInfo<String, List<Category>> globalInfo;

    @Override
    public String save(Taxonomy category) {
        String errMsg = super.save(category);
        if (StringUtils.hasText(errMsg)) {
            return errMsg;
        }
        globalInfo.set(Constant.O_ALL_CATEGORIES, null);
        log.info("保存分类目录，重置缓存");
        return null;
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

    public List<Category> listCategoriesNoPrefix() {
        List<Category> categoryList = listCategoriesByOrder();
        List<Category> categories = Lists.newArrayListWithExpectedSize(categoryList.size());
        for (Category category : categoryList) {
            Category find = removePrefix(category);
            categories.add(find);
        }
        return categories;
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
                return removePrefix(category);
            }
        }
        return null;
    }

    private Category removePrefix(Category category) {
        Category find = new Category(category);
        String prefix = Strings.repeat(Constant.DASH, find.getDepth());
        if (find.getDepth() != 0) {
            String name = find.getName();
            name = name.substring(prefix.length());
            find.setName(name);
        }
        return find;
    }

    public Category update(Category category) {
        taxonomyDao.update(category);
        globalInfo.set(Constant.O_ALL_CATEGORIES, null);
        return category;
    }

    @Transactional
    public void delete(Long... ids) {
        if (ids == null) {
            return;
        }
        Category unCategory = null;
        List<Category> categoryList = listCategories();
        Set<Long> allId = Sets.newHashSet();
        for (Category category : categoryList) {
            if (category.getTaxonomyId() == 1) {
                unCategory = category;
            }
            allId.add(category.getTaxonomyId());
        }
        ArrayList<Long> idList = Lists.newArrayList();
        for (Long id : ids) {
            if (allId.contains(id)) {
                idList.add(id);
            }
        }
        if (idList.isEmpty()) {
            return;
        }
        taxonomyDao.delete(idList);
        int deleteCount = taxonomyDao.resetPostCategory(idList);
        if (unCategory != null) {
            unCategory.setCount(unCategory.getCount() + deleteCount);
        }
        taxonomyDao.update(unCategory);
        globalInfo.set(Constant.O_ALL_CATEGORIES, null);
    }

    private List<Category> listCategories() {
        return globalInfo.get(Constant.O_ALL_CATEGORIES, () -> {
            log.info("获取分类目录未命中缓存，查询数据库");
            // 按 id 排序的
            List<Taxonomy> categories = taxonomyDao.findByTaxonomy(Taxonomy.TAXONOMY_CATEGORY);
            // 按 parent id 排序的
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
        categoryList.sort(Comparator.comparingLong(Taxonomy::getParent));
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
