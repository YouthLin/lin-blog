package com.youthlin.blog.dao;

import com.youthlin.blog.model.po.Taxonomy;
import com.youthlin.blog.model.po.TaxonomyRelationships;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-08 22:18.
 */
@Repository
public interface TaxonomyDao {
    void save(Taxonomy taxonomy);

    void saveList(List<Taxonomy> taxonomyList);

    void saveTaxonomyRelationships(List<TaxonomyRelationships> relationships);

    int deleteRelationships(@Param("postId") Long postId, @Param("taxonomyId") List<Long> ids);

    List<Taxonomy> findByPostId(Long... postIds);

    List<TaxonomyRelationships> findRelationshipsByPostId(Long... postIds);

    Taxonomy findByNameAndTaxonomy(@Param("name") String name, @Param("taxonomy") String taxonomy);

    List<Taxonomy> findByTaxonomy(String taxonomy);

    Taxonomy findById(long id);

    void update(Taxonomy taxonomy);

    void delete(List<Long> ids);

    void resetPostCategory(List<Long> taxonomyId);

    Taxonomy findBySlugAndTaxonomy(@Param("slug") String slug, @Param("taxonomy") String taxonomy);

    List<Taxonomy> findByPage(String taxonomy, RowBounds rowBounds);

    List<Taxonomy> findByTaxonomyAndNameIn(@Param("taxonomy") String taxonomy, @Param("nameList") List<String> nameList);
}
