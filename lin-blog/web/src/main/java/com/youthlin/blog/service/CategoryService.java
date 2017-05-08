package com.youthlin.blog.service;

import com.youthlin.blog.dao.TaxonomyDao;
import com.youthlin.blog.model.bo.Category;
import com.youthlin.blog.model.po.Taxonomy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-08 22:45.
 */
@Service
public class CategoryService {
    @Resource
    private TaxonomyDao taxonomyDao;

    public List<Category> listCategories() {
        List<Taxonomy> categories = taxonomyDao.findByTaxonomy(Taxonomy.TAXONOMY_CATEGORY);

        return null;
    }

}
