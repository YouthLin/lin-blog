package com.youthlin.blog.web.front;

import com.youthlin.blog.model.bo.Category;
import com.youthlin.blog.model.bo.Page;
import com.youthlin.blog.model.bo.Tag;
import com.youthlin.blog.model.po.Post;
import com.youthlin.blog.model.po.Taxonomy;
import com.youthlin.blog.service.PostService;
import com.youthlin.blog.util.Constant;
import com.youthlin.blog.util.PostTaxonomyHelper;
import com.youthlin.blog.web.back.PostController;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

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

    @RequestMapping(path = {
            "/", "/page/{pageIndex}",
            "/category/{categoryName}", "/category/{categoryName}/page/{pageIndex}",
            "/tag/{tagName}", "/tag/{tagName}/page/{pageIndex}",
            "/date/{year}", "/date/{year}/page/{pageIndex}",
            "/date/{year}/{month}", "/date/{year}/{month}/page/{pageIndex}"
    })
    public String home(@PathVariable(required = false) String pageIndex,
                       @PathVariable(required = false) String categoryName,
                       @PathVariable(required = false) String tagName,
                       @PathVariable(required = false) String year,
                       @PathVariable(required = false) String month, Model model) {
        log.debug("param: pageIndex = {}, categoryName = {}, tagName = {}, year = {}, month = {}",
                pageIndex, categoryName, tagName, year, month);
        int pageNum = parsePageNum(pageIndex);
        Taxonomy taxonomy = parseTaxonomy(categoryName, tagName);
        DateTime[] dateTimes = parseDate(year, month);
        DateTime start = dateTimes[0];
        DateTime end = dateTimes[1];
        log.info("参数：页码{} 分类法{} 日期{} -> {}", pageNum, taxonomy, toString(start), toString(end));
        Page<Post> postPage = getPostPage(pageNum, taxonomy, start, end);
        model.addAttribute("postPage", postPage);
        log.info("post page = {}", postPage);
        PostTaxonomyHelper.fetchTaxonomyRelationships(postPage.getList(), model, postService);
        return "index";
    }

    private int parsePageNum(String pageIndex) {
        int pageNum = 1;
        if (StringUtils.hasText(pageIndex)) {
            try {
                pageNum = Integer.parseInt(pageIndex);
            } catch (NumberFormatException ignore) {
                log.debug("页码错误:{}", pageIndex);
            }
        }
        return pageNum;
    }

    private Taxonomy parseTaxonomy(String cat, String tag) {
        Taxonomy taxonomy = null;
        if (StringUtils.hasText(cat)) {
            taxonomy = new Category().setName(cat);
        }
        if (StringUtils.hasText(tag)) {
            taxonomy = new Tag().setName(tag);
        }
        return taxonomy;
    }

    private DateTime[] parseDate(String year, String month) {
        DateTime start = null, end = null;
        if (StringUtils.hasText(year)) {
            try {
                int y = Integer.parseInt(year);
                start = new DateTime(y, 1, 1, 0, 0);
                end = start.plusYears(1);
            } catch (NumberFormatException ignore) {
                log.debug("年份不能识别:{}", year);
            }
        }
        if (StringUtils.hasText(month) && start != null && end != null) {
            try {
                int m = Integer.parseInt(month);
                start = start.withMonthOfYear(m);
                end = start.plusMonths(1);
            } catch (NumberFormatException ignore) {
                log.debug("月份不能识别:{}", month);
            }
        }
        return new DateTime[]{start, end};
    }

    private Page<Post> getPostPage(int pageNum, Taxonomy taxonomy, DateTime start, DateTime end) {
        int pageSize = Constant.DEFAULT_PAGE_SIZE_FRONT;
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
