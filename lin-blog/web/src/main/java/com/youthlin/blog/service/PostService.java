package com.youthlin.blog.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.youthlin.blog.dao.PostDao;
import com.youthlin.blog.dao.TaxonomyDao;
import com.youthlin.blog.model.bo.Category;
import com.youthlin.blog.model.bo.Pageable;
import com.youthlin.blog.model.bo.Tag;
import com.youthlin.blog.model.enums.PostStatus;
import com.youthlin.blog.model.po.Post;
import com.youthlin.blog.model.po.Taxonomy;
import com.youthlin.blog.model.po.TaxonomyRelationships;
import com.youthlin.blog.support.GlobalInfo;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-10 20:26.
 */
@Service
public class PostService {
    private static final Logger log = LoggerFactory.getLogger(PostService.class);
    @Resource
    private PostDao postDao;
    @Resource
    private TaxonomyDao taxonomyDao;
    @Resource
    private CategoryService categoryService;

    /**
     * 保存 post, 分类目录，标签。
     *
     * @param post            post to save
     * @param categoryIdList  分类目录
     * @param tagList         标签
     * @param markdownContent 对应的 markdown 内容，当有内容时存为 post meta
     */
    @Transactional
    public Post save(Post post, List<Long> categoryIdList, List<String> tagList, String markdownContent) {
        postDao.save(post);

        List<Category> categoryList = categoryService.listCategoriesByOrder();
        Map<Long, Category> allCategory = Maps.newHashMap();
        for (Category category : categoryList) {
            allCategory.put(category.getTaxonomyId(), category);
        }
        List<Taxonomy> categoryToSave = Lists.newArrayList();
        for (Long id : categoryIdList) {
            if (allCategory.containsKey(id)) {
                categoryToSave.add(allCategory.get(id));
            }
        }

        saveRelationships(post.getPostId(), categoryToSave);

        List<Taxonomy> tags = taxonomyDao.findByTaxonomyAndNameIn(Taxonomy.TAXONOMY_TAG, tagList);
        Map<String, Taxonomy> allTag = Maps.newHashMap();
        for (Taxonomy tag : tags) {
            allTag.put(tag.getName(), tag);
        }

        List<String> tagTosave = Lists.newArrayList();
        for (String tagName : tagList) {
            if (!allTag.containsKey(tagName)) {
                tagTosave.add(tagName);
            }
        }
        saveNewTag(tagTosave);

        saveRelationships(post.getPostId(), tags);

        return post;
    }

    private void saveNewTag(List<String> tags) {
        if (tags.isEmpty()) {
            return;
        }
        List<Taxonomy> tagList = Lists.newArrayList();
        for (String tag : tags) {
            tagList.add(new Tag().setName(tag));
        }
        taxonomyDao.saveList(tagList);
    }

    private void saveRelationships(Long postId, List<Taxonomy> taxonomyList) {
        if (taxonomyList.isEmpty()) {
            return;
        }
        List<TaxonomyRelationships> relationshipsList = Lists.newArrayList();
        for (Taxonomy taxonomy : taxonomyList) {
            TaxonomyRelationships relationships = new TaxonomyRelationships();
            relationships.setPostId(postId).setTaxonomyId(taxonomy.getTaxonomyId());
            relationshipsList.add(relationships);
            taxonomy.setCount(taxonomy.getCount() + 1);
            taxonomyDao.update(taxonomy);
        }
        taxonomyDao.saveTaxonomyRelationships(relationshipsList);
    }

    /**
     * 查询 post.
     *
     * @param pageIndex  第几页 从 1 开始记
     * @param pageSize   每页几条
     * @param status     文章状态，若为 null 表示查询所有
     * @param yearMonth  指定月份内的文章, 若为 null 表示查询所有日期
     * @param categoryId 指定分类目录 若为 null 表示查询所有分类
     * @param tagName    指定标签，若为 null 表示查询所有标签
     * @return Post 分页对象
     */
    public Pageable<Post> findByPageAndStatusAndDateAndCategoryAndTag
    (long pageIndex, long pageSize, PostStatus status, Date yearMonth, Long categoryId, String tagName) {
        Date start, end;
        if (yearMonth != null) {
            DateTime dateTime = new DateTime(yearMonth).withDayOfMonth(0).withMillisOfDay(0);
            start = dateTime.toDate();
            end = dateTime.plusMonths(1).toDate();
        }

        return null;

    }

}
