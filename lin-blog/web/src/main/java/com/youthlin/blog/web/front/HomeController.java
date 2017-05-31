package com.youthlin.blog.web.front;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.youthlin.blog.model.bo.Category;
import com.youthlin.blog.model.bo.Page;
import com.youthlin.blog.model.bo.Tag;
import com.youthlin.blog.model.po.Post;
import com.youthlin.blog.model.po.Taxonomy;
import com.youthlin.blog.model.po.User;
import com.youthlin.blog.service.CategoryService;
import com.youthlin.blog.service.PostService;
import com.youthlin.blog.service.TaxonomyService;
import com.youthlin.blog.service.UserService;
import com.youthlin.blog.util.PostTaxonomyHelper;
import com.youthlin.blog.util.ServletUtil;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    @Resource
    private UserService userService;

    @RequestMapping(path = {
            "/", "/category/{categorySlug}", "/tag/{tagSlug}", "/date/{year}", "/date/{year}/{month}",
            "/author/{authorId}"
    })
    public String home(@RequestParam(required = false, name = "page") String pageIndex,
                       @RequestParam(required = false, name = "size") String pageSize,
                       @PathVariable(required = false) String categorySlug,
                       @PathVariable(required = false) String tagSlug,
                       @PathVariable(required = false) String year,
                       @PathVariable(required = false) String month, Model model,
                       @PathVariable(required = false) String authorId) {
        log.debug("param: pageIndex = {}, categorySlug = {}, tagSlug = {}, year = {}, month = {}, authorId str = {}",
                pageIndex, categorySlug, tagSlug, year, month, authorId);
        int pageNum = parsePageNum(pageIndex);
        int size = parsePageNum(pageSize);
        Taxonomy taxonomy = parseTaxonomy(categorySlug, tagSlug, model);
        User user = parseUser(authorId, model);
        DateTime[] dateTimes = parseDate(year, month, model);
        DateTime start = dateTimes[0];
        DateTime end = dateTimes[1];
        log.info("参数：页码{} 分类法{} 日期{} -> {}", pageNum, taxonomy, toString(start), toString(end));
        Page<Post> postPage = getPostPage(pageNum, size, taxonomy, user, start, end);
        injectPostInfo(postPage, model);
        return "index";
    }

    private void injectPostInfo(Page<Post> postPage, Model model) {
        model.addAttribute("postPage", postPage);
        log.info("post page = {}", postPage);
        PostTaxonomyHelper.fetchTaxonomyRelationships(postPage.getList(), model, postService);
        fetchAuthorInfo(postPage.getList(), model);
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

    private User parseUser(String authorIdStr, Model model) {
        if (!StringUtils.hasText(authorIdStr)) {
            return null;
        }
        long id = -1;
        try {
            id = Long.parseLong(authorIdStr);
        } catch (NumberFormatException ignore) {
        }
        if (id < 0) {
            return null;
        }
        User user = userService.findById(id);
        model.addAttribute("author", user);
        log.debug("user = {}", user);
        return user;
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

    private Page<Post> getPostPage(int pageNum, int pageSize, Taxonomy taxonomy, User author, DateTime start, DateTime end) {
        if (pageSize == 0) {
            pageSize = 3;
        }
        if (start != null && end != null) {
            return postService.findPublishedPostByDateByPage(start.toDate(), end.toDate(), pageNum, pageSize);
        }
        return postService.findPublishedPostByTaxonomyByPage(taxonomy, author, pageNum, pageSize);
    }

    private String toString(DateTime dateTime) {
        if (dateTime != null) {
            return dateTime.toString(DATE_TO_STRING);
        }
        return null;
    }

    private void fetchAuthorInfo(List<Post> postList, Model model) {
        if (postList == null) {
            return;
        }
        Set<Long> userIds = Sets.newHashSet();
        for (Post post : postList) {
            userIds.add(post.getPostAuthorId());
        }
        List<User> users = userService.listById(userIds);
        Map<Long, User> userMap = Maps.newHashMap();
        for (User user : users) {
            userMap.put(user.getUserId(), user);
        }
        model.addAttribute("userMap", userMap);
    }

    @RequestMapping(path = "/s", method = RequestMethod.GET)
    public String search(@RequestParam Map<String, String> params, Model model) {
        String keyWords = params.get("w");
        if (!StringUtils.hasText(keyWords)) {
            return "redirect:/";
        }
        keyWords = ServletUtil.filterHtml(keyWords);
        keyWords = ServletUtil.filterXss(keyWords);
        int[] indexAndSize = ServletUtil.parsePageIndexAndSize(params);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Page<Post> postPage = postService.search(indexAndSize[0], indexAndSize[1], keyWords);
        injectPostInfo(postPage, model);
        stopWatch.stop();
        model.addAttribute("keyWords", keyWords);
        model.addAttribute("usedMillsTime", stopWatch.getTotalTimeMillis());
        return "index";
    }
}
