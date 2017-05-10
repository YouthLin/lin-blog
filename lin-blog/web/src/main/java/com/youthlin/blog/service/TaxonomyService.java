package com.youthlin.blog.service;

import com.youthlin.blog.dao.TaxonomyDao;
import com.youthlin.blog.model.po.Taxonomy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.youthlin.utils.i18n.Translation.__;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-10 11:25.
 */
public class TaxonomyService {
    private static final Logger log = LoggerFactory.getLogger(TaxonomyService.class);
    @Resource
    private TaxonomyDao taxonomyDao;

    public String save(Taxonomy taxonomy) {
        Taxonomy saved = taxonomyDao.findByNameAndTaxonomy(taxonomy.getName(), taxonomy.getTaxonomy());
        if (saved != null) {
            log.warn("name 已被使用: {}", taxonomy);
            return __("The name has be used by another item.");
        }
        saved = taxonomyDao.findBySlugAndTaxonomy(taxonomy.getSlug(), taxonomy.getTaxonomy());
        if (saved != null) {
            log.warn("slug 已被使用: {}", taxonomy);
            return __("The slug has be used by another item.");
        }

        taxonomyDao.save(taxonomy);
        return null;
    }
}
