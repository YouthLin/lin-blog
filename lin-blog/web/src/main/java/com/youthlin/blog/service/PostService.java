package com.youthlin.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.youthlin.blog.dao.MetaDao;
import com.youthlin.blog.dao.PostDao;
import com.youthlin.blog.dao.TaxonomyDao;
import com.youthlin.blog.model.bo.Category;
import com.youthlin.blog.model.bo.Page;
import com.youthlin.blog.model.bo.Pageable;
import com.youthlin.blog.model.bo.Tag;
import com.youthlin.blog.model.enums.PostStatus;
import com.youthlin.blog.model.po.Post;
import com.youthlin.blog.model.po.PostMeta;
import com.youthlin.blog.model.po.Taxonomy;
import com.youthlin.blog.model.po.TaxonomyRelationships;
import com.youthlin.blog.support.GlobalInfo;
import com.youthlin.blog.util.Constant;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    private MetaDao metaDao;
    @Resource
    private TaxonomyDao taxonomyDao;
    @Resource
    private CategoryService categoryService;
    @Resource
    private GlobalInfo<String, List<Category>> globalInfo;

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
        log.info("已保存 post {}", post);
        List<Taxonomy> categoryToSave = getCategoryFromIds(categoryIdList);
        saveRelationships(post.getPostId(), categoryToSave);
        processTagList(post.getPostId(), tagList);
        saveMarkdownContent(post.getPostId(), markdownContent);
        globalInfo.set(Constant.O_ALL_CATEGORIES, null);//clear cache
        return post;
    }

    private List<Taxonomy> getCategoryFromIds(List<Long> categoryIdList) {
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
        return categoryToSave;
    }

    /* 如果包含新的标签，先保存 tag. 然后保存所有 tag 与 post 的关联关系 */
    private void processTagList(Long postId, List<String> tagList) {
        List<Taxonomy> tags = taxonomyDao.findByTaxonomyAndNameIn(Taxonomy.TAXONOMY_TAG, tagList);
        Map<String, Taxonomy> allTag = Maps.newHashMap();
        for (Taxonomy tag : tags) {
            allTag.put(tag.getName(), tag);
        }

        List<String> tagToSave = Lists.newArrayList();
        for (String tagName : tagList) {
            if (!allTag.containsKey(tagName)) {
                tagToSave.add(tagName);
            }
        }
        saveNewTag(tagToSave);
        tags = taxonomyDao.findByTaxonomyAndNameIn(Taxonomy.TAXONOMY_TAG, tagList);//reload
        saveRelationships(postId, tags);
    }

    private void saveMarkdownContent(Long postId, String mdContent) {
        if (!StringUtils.hasText(mdContent)) {
            return;
        }
        PostMeta postMeta = new PostMeta();
        postMeta.setPostId(postId)
                .setMetaKey(Constant.K_MD_SOURCE)
                .setMetaValue(mdContent);
        metaDao.save(postMeta);
        log.info("已保存 md 原内容: {}", postMeta);
    }

    public Post findById(Long id) {
        return postDao.findById(id);
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
        log.info("已保存新标签：{}", tagList);
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
            log.info("已更新 {} count 值: {}", taxonomy.getName(), taxonomy.getCount());
        }
        taxonomyDao.saveTaxonomyRelationships(relationshipsList);
        log.info("已保存 post 与 taxonomy 关系: postId={}, taxonomy = {}", postId, taxonomyList);
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
    public Pageable<Post> findByPageAndStatusAndDateAndCategoryAndTagAndAuthorId
    (int pageIndex, int pageSize, PostStatus status, Date yearMonth, Long categoryId, String tagName, Long authorId) {
        Date start = null, end = null;
        if (yearMonth != null) {
            DateTime dateTime = new DateTime(yearMonth).withDayOfMonth(1).withMillisOfDay(0);
            start = dateTime.toDate();
            end = dateTime.plusMonths(1).toDate();
        }
        final Date finalStart = start;
        final Date finalEnd = end;
        if (categoryId != null && categoryId < 1) {
            categoryId = null;
        }
        final Long finalCategoryId = categoryId;
        PageInfo<Post> pageInfo = PageHelper.startPage(pageIndex, pageSize).doSelectPageInfo(
                () -> postDao.findByStatusAndDateAndCategoryIdAndTagAndAuthorId
                        (status, finalStart, finalEnd, finalCategoryId, tagName, authorId)
        );
        Pageable<Post> pageable = new Page<>(pageInfo);
        log.debug("post page = {}", pageable);
        return pageable;
    }

    public long countByStatus(PostStatus postStatus) {
        return postDao.countByStatus(postStatus);
    }

    public Multimap<Long, Taxonomy> findTaxonomyByPostId(Long... postIds) {
        Multimap<Long, Taxonomy> multimap = HashMultimap.create();
        List<Taxonomy> taxonomies = taxonomyDao.findByPostId(postIds);
        Map<Long, Taxonomy> taxonomyMap = Maps.newHashMap();
        for (Taxonomy taxonomy : taxonomies) {
            taxonomyMap.put(taxonomy.getTaxonomyId(), taxonomy);
        }
        List<TaxonomyRelationships> relationships = taxonomyDao.findRelationshipsByPostId(postIds);
        for (TaxonomyRelationships relationship : relationships) {
            Long postId = relationship.getPostId();
            Long taxonomyId = relationship.getTaxonomyId();
            Taxonomy taxonomy = taxonomyMap.get(taxonomyId);
            multimap.put(postId, taxonomy);
        }
        return multimap;
    }

    public List<PostMeta> findPostMetaByPostId(Long postId) {
        return metaDao.findPostMetaByPostId(postId);
    }

    private Page<Post> findPublishedPostByPage(int pageIndex, int pageSize) {
        PageInfo<Post> pageInfo = PageHelper.startPage(pageIndex, pageSize).doSelectPageInfo(
                () -> postDao.queryByTaxonomySlugKindAndDate(null, PostStatus.PUBLISHED, null, null)
        );
        Page<Post> postPage = new Page<>(pageInfo);
        log.debug("分页查询已发布文章：{}", postPage);
        return postPage;
    }

    public Page<Post> findPublishedPostByTaxonomyByPage(Taxonomy taxonomy, int pageIndex, int pageSize) {
        if (taxonomy == null) {
            return findPublishedPostByPage(pageIndex, pageSize);
        }
        PageInfo<Post> pageInfo = PageHelper.startPage(pageIndex, pageSize).doSelectPageInfo(
                () -> postDao.queryByTaxonomySlugKindAndDate(Lists.newArrayList(taxonomy), PostStatus.PUBLISHED, null, null)
        );
        Page<Post> postPage = new Page<>(pageInfo);
        log.debug("按 Taxonomy 分页查询已发布文章：{}", postPage);
        return postPage;
    }

    public Page<Post> findPublishedPostByDateByPage(Date start, Date end, int pageIndex, int pageSize) {
        PageInfo<Post> pageInfo = PageHelper.startPage(pageIndex, pageSize).doSelectPageInfo(
                () -> postDao.queryByTaxonomySlugKindAndDate(null, PostStatus.PUBLISHED, start, end)
        );
        Page<Post> postPage = new Page<>(pageInfo);
        log.debug("按时间分页查询已发布文章：{}", postPage);
        return postPage;
    }

    @Transactional
    public void update(Post post, List<Long> categoryIds, List<String> tagList, String md) {
        postDao.update(post);
        Long postId = post.getPostId();
        Multimap<Long, Taxonomy> multimap = findTaxonomyByPostId(postId);
        Collection<Taxonomy> taxonomies = multimap.get(postId);
        // remove relationship
        removeRelationship(postId, taxonomies);
        // save new ship
        List<Taxonomy> categoriesToSave = getCategoryFromIds(categoryIds);
        saveRelationships(postId, categoriesToSave);
        processTagList(postId, tagList);
        //md meta
        List<PostMeta> postMetaList = metaDao.findPostMetaByPostId(postId);
        PostMeta mdMeta = null;
        for (PostMeta meta : postMetaList) {
            if (meta.getMetaKey().equals(Constant.K_MD_SOURCE)) {
                mdMeta = meta;
                break;
            }
        }
        if (mdMeta == null) {
            saveMarkdownContent(postId, md);//原来没有
        } else {
            metaDao.update(mdMeta);
        }
        globalInfo.set(Constant.O_ALL_CATEGORIES, null);//clear cache
    }

    private void removeRelationship(Long postId, Collection<Taxonomy> taxonomies) {
        if (taxonomies == null || taxonomies.isEmpty()) {
            return;
        }
        List<Long> ids = Lists.newArrayListWithExpectedSize(taxonomies.size());
        for (Taxonomy taxonomy : taxonomies) {
            taxonomy.setCount(taxonomy.getCount() - 1);
            taxonomyDao.update(taxonomy);
            log.debug("已更新 {} 数量={} ({})", taxonomy.getName(), taxonomy.getCount(), taxonomy);
            ids.add(taxonomy.getTaxonomyId());
        }
        taxonomyDao.deleteRelationships(postId, ids);
        log.debug("已移除 post - taxonomy 关系: {} <-> {}", postId, taxonomies);
        globalInfo.set(Constant.O_ALL_CATEGORIES, null);// clear cache
    }

    /* 更新 post,  如 comment count */
    public void update(Post post) {
        postDao.update(post);
    }

    public Post findNextOrPrevious(Long postId, boolean next) {
        com.github.pagehelper.Page<Post> page = PageHelper.startPage(1, 1).doSelectPage(
                () -> postDao.findIdGreaterOrLessThen(postId, next)
        );
        if (page != null) {
            List<Post> result = page.getResult();
            if (result != null && !result.isEmpty()) {
                return result.get(0);
            }
        }
        return null;
    }

    /**
     * 返回 YYYY-MM  对应相应月份的文章篇数
     */
    public LinkedHashMultiset<String> archiveCount() {
        List<Date> dates = postDao.listAllDateByStatus(PostStatus.PUBLISHED);
        LinkedHashMultiset<String> multiset = LinkedHashMultiset.create();
        for (Date date : dates) {
            multiset.add(new DateTime(date).toString(Constant.YYYY_MM));
        }
        return multiset;
    }
}
