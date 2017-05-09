package com.youthlin.blog.dao;

import com.youthlin.blog.model.bo.Category;
import com.youthlin.blog.model.po.Taxonomy;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-08 22:18.
 */
@Repository
public interface TaxonomyDao {
    void save(Taxonomy taxonomy);

    Taxonomy findByNameAndTaxonomy(@Param("name") String name, @Param("taxonomy") String taxonomy);

    List<Taxonomy> findByTaxonomy(String taxonomy);

    Taxonomy findById(long id);

    void update(Taxonomy taxonomy);

    void delete(List<Long> ids);

    void resetPostCategory(List<Long> taxonomyId);
}
