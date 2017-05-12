package com.youthlin.blog.web.front;

import com.google.common.collect.Lists;
import com.youthlin.blog.model.bo.Category;
import com.youthlin.blog.model.bo.Page;
import com.youthlin.blog.model.bo.Tag;
import com.youthlin.blog.model.po.Post;
import com.youthlin.blog.model.po.Taxonomy;
import com.youthlin.blog.service.CategoryService;
import com.youthlin.blog.service.PostService;
import com.youthlin.blog.service.TaxonomyService;
import com.youthlin.blog.util.PostTaxonomyHelper;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 创建： lin
 * 时间： 2017-04-03 15:03
 */
@Controller
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    private static final String DATE_TO_STRING = "YYYY-MM-dd HH:mm";
    @Resource
    private PostService postService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private TaxonomyService taxonomyService;

    @RequestMapping(path = {
            "/", "/category/{categorySlug}", "/tag/{tagSlug}", "/date/{year}", "/date/{year}/{month}",
    })
    public String home(@RequestParam(required = false, name = "page") String pageIndex,
                       @RequestParam(required = false, name = "size") String pageSize,
                       @PathVariable(required = false) String categorySlug,
                       @PathVariable(required = false) String tagSlug,
                       @PathVariable(required = false) String year,
                       @PathVariable(required = false) String month, Model model) {
        log.debug("param: pageIndex = {}, categorySlug = {}, tagSlug = {}, year = {}, month = {}",
                pageIndex, categorySlug, tagSlug, year, month);
        int pageNum = parsePageNum(pageIndex);
        int size = parsePageNum(pageSize);
        Taxonomy taxonomy = parseTaxonomy(categorySlug, tagSlug, model);
        DateTime[] dateTimes = parseDate(year, month, model);
        DateTime start = dateTimes[0];
        DateTime end = dateTimes[1];
        log.info("参数：页码{} 分类法{} 日期{} -> {}", pageNum, taxonomy, toString(start), toString(end));
        Page<Post> postPage = getPostPage(pageNum, size, taxonomy, start, end);
        model.addAttribute("postPage", postPage);
        log.info("post page = {}", postPage);
        PostTaxonomyHelper.fetchTaxonomyRelationships(postPage.getList(), model, postService);
        return "index";
    }

    private int parsePageNum(String pageIndex) {
        int pageNum = 0;
        if (StringUtils.hasText(pageIndex)) {
            try {
                pageNum = Integer.parseInt(pageIndex);
            } catch (NumberFormatException ignore) {
                log.debug("页码错误:{}", pageIndex);
            }
        }
        return pageNum;
    }

    private Taxonomy parseTaxonomy(String cat, String tag, Model model) {
        Taxonomy taxonomy = null;
        if (StringUtils.hasText(cat)) {
            taxonomy = new Category().setSlug(cat);
            List<Category> categories = categoryService.listCategoriesNoPrefix();
            Category current = null;
            for (Category category : categories) {
                if (category.getSlug().equals(cat)) {
                    current = category;
                }
            }
            if (current != null) {
                List<Taxonomy> taxonomyList = Lists.newArrayList(current);
                while (current.getParentCategory() != null) {
                    taxonomyList.add(current.getParentCategory());
                    current = current.getParentCategory();
                }
                model.addAttribute("taxonomyList", Lists.reverse(taxonomyList));
            }
        }
        if (StringUtils.hasText(tag)) {
            taxonomy = new Tag().setSlug(tag);
            Taxonomy savedTag = taxonomyService.findBySlugAndTaxonomy(tag, Taxonomy.TAXONOMY_TAG);
            if (savedTag != null) {
                List<Taxonomy> taxonomyList = Collections.singletonList(savedTag);
                model.addAttribute("taxonomyList", taxonomyList);
            }
        }
        return taxonomy;
    }

    private DateTime[] parseDate(String year, String month, Model model) {
        DateTime start = null, end = null;
        if (StringUtils.hasText(year)) {
            try {
                int y = Integer.parseInt(year);
                start = new DateTime(y, 1, 1, 0, 0);
                end = start.plusYears(1);
                model.addAttribute("year", String.valueOf(y));
            } catch (NumberFormatException ignore) {
                log.debug("年份不能识别:{}", year);
            }
        }
        if (StringUtils.hasText(month) && start != null && end != null) {
            try {
                int m = Integer.parseInt(month);
                if (m > 0 && m < 13) {
                    start = start.withMonthOfYear(m);
                    end = start.plusMonths(1);
                    model.addAttribute("month", String.valueOf(m));
                }
            } catch (NumberFormatException ignore) {
                log.debug("月份不能识别:{}", month);
            }
        }
        return new DateTime[]{start, end};
    }

    private Page<Post> getPostPage(int pageNum, int pageSize, Taxonomy taxonomy, DateTime start, DateTime end) {
        if (start != null && end != null) {
            return postService.findPublishedPostByDateByPage(start.toDate(), end.toDate(), pageNum, pageSize);
        }
        return postService.findPublishedPostByTaxonomyByPage(taxonomy, pageNum, pageSize);
    }

    private String toString(DateTime dateTime) {
        if (dateTime != null) {
            return dateTime.toString(DATE_TO_STRING);
        }
        return null;
    }

}
