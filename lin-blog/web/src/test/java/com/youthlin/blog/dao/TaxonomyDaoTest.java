package com.youthlin.blog.dao;

import com.google.common.collect.Lists;
import com.youthlin.blog.model.bo.Tag;
import com.youthlin.blog.model.po.Taxonomy;
import com.youthlin.blog.model.po.TaxonomyRelationships;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-10 22:18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/app.xml"})
public class TaxonomyDaoTest {
    private static final Logger log = LoggerFactory.getLogger(TaxonomyDaoTest.class);
    @Resource
    private TaxonomyDao taxonomyDao;

    @Test
    public void saveList() {
        List<Taxonomy> tags = Lists.newArrayList(new Tag().setName("tag1"), new Tag().setName("tag2"));
        taxonomyDao.saveList(tags);
    }

    @Test
    public void findByTaxonomyAndNameIn() throws Exception {
        List<Taxonomy> tags = taxonomyDao.findByTaxonomyAndNameIn(Taxonomy.TAXONOMY_TAG,
                Lists.newArrayList("标签1", "tag2"));
        log.info("tags = {}", tags);
        tags = taxonomyDao.findByTaxonomyAndNameIn(Taxonomy.TAXONOMY_TAG, Lists.newArrayList());
        log.info("tags = {}", tags);
    }

    @Test
    public void saveRelationship() {
        taxonomyDao.saveTaxonomyRelationships(Lists.newArrayList(
                new TaxonomyRelationships().setPostId(0L).setTaxonomyId(0L)
                , new TaxonomyRelationships().setPostId(1L).setTaxonomyId(1L)

        ));
    }

    @Test
    public void testFindByPostId() {
        List<Taxonomy> byPostId = taxonomyDao.findByPostId(1L, 2L, 9L);
        for (Taxonomy taxonomy : byPostId) {
            log.debug("{}", taxonomy);
        }
        log.debug("");
        List<TaxonomyRelationships> relationships = taxonomyDao.findRelationshipsByPostId(1L, 2L, 9L);
        for (TaxonomyRelationships t : relationships) {
            log.debug("{}", t);
        }
    }

}