package com.youthlin.blog.web.front;

import com.youthlin.blog.model.bo.Category;
import com.youthlin.blog.model.bo.Tag;
import com.youthlin.blog.model.po.Taxonomy;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 创建： lin
 * 时间： 2017-04-03 15:03
 */
@Controller
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

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
                       @PathVariable(required = false) String month) {
        log.info("param: pageIndex = {}, categoryName = {}, tagName = {}, year = {}, month = {}",
                pageIndex, categoryName, tagName, year, month);
        int pageNum = 1;
        DateTime start = null, end = null;
        Taxonomy taxonomy = null;
        if (StringUtils.hasText(pageIndex)) {
            try {
                pageNum = Integer.parseInt(pageIndex);
            } catch (NumberFormatException ignore) {
            }
        }

        if (StringUtils.hasText(categoryName)) {
            taxonomy = new Category().setName(categoryName);
        }
        if (StringUtils.hasText(tagName)) {
            taxonomy = new Tag().setName(tagName);
        }
        if (StringUtils.hasText(year)) {
            try {
                int y = Integer.parseInt(year);
                start = new DateTime(y, 1, 1, 0, 0);
                end = start.plusYears(1);
            } catch (NumberFormatException ignore) {
            }
        }
        if (StringUtils.hasText(month) && start != null && end != null) {
            try {
                int m = Integer.parseInt(month);
                start = start.withMonthOfYear(m);
                end = start.plusMonths(1);
            } catch (NumberFormatException ignore) {
            }
        }
        log.info("参数：页码{} 分类法{} 日期{}-{}", pageNum, taxonomy, start, end);
        return "index";
    }


}
