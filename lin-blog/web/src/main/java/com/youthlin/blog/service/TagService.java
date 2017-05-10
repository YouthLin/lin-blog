package com.youthlin.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.youthlin.blog.dao.TaxonomyDao;
import com.youthlin.blog.model.bo.Page;
import com.youthlin.blog.model.bo.Pageable;
import com.youthlin.blog.model.po.Taxonomy;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-09 23:38.
 */
@Service
public class TagService extends TaxonomyService {
    private static final Logger log = LoggerFactory.getLogger(TagService.class);
    @Resource
    private TaxonomyDao taxonomyDao;

    public Pageable<Taxonomy> findByPage(int pageIndex, int pageSize) {
        PageInfo<Taxonomy> pageInfo = PageHelper.startPage(pageIndex, pageSize)
                .doSelectPageInfo(() -> taxonomyDao.findByPage(Taxonomy.TAXONOMY_TAG, new RowBounds(pageIndex, pageSize)));
        Page<Taxonomy> page = new Page<>(pageInfo);
        log.debug("page = {}", page);
        return page;
    }
}
