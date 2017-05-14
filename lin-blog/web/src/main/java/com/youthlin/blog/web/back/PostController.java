package com.youthlin.blog.web.back;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.youthlin.blog.model.bo.Category;
import com.youthlin.blog.model.bo.Pageable;
import com.youthlin.blog.model.enums.PostStatus;
import com.youthlin.blog.model.po.Post;
import com.youthlin.blog.model.po.PostMeta;
import com.youthlin.blog.model.po.User;
import com.youthlin.blog.service.CategoryService;
import com.youthlin.blog.service.PostService;
import com.youthlin.blog.service.UserService;
import com.youthlin.blog.support.GlobalInfo;
import com.youthlin.blog.util.Constant;
import com.youthlin.blog.util.PostTaxonomyHelper;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.youthlin.utils.i18n.Translation.__;

/**
 * 创建： lin
 * 时间： 2017-05-06 21:07
 */
@SuppressWarnings("unused")
@Controller
@RequestMapping("/admin")
public class PostController {
    private static final Logger log = LoggerFactory.getLogger(PostController.class);
    private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm");
    private static final DateTimeFormatter FORMATTER_YM = DateTimeFormat.forPattern("YYYY-MM");
    private static final Splitter SPLITTER = Splitter.on(",").trimResults().omitEmptyStrings();
    @Resource
    private CategoryService categoryService;
    @Resource
    private PostService postService;
    @Resource
    private UserService userService;
    @Resource
    private GlobalInfo<String, Long> globalInfo;//status-count
    @Resource
    private GlobalInfo<Long, String> globalInfoUserIdNameMap;


    // region //list all post
    @RequestMapping(path = {"/post", "/post/{status}"})
    public String allPostPage(@PathVariable(required = false) String status, @RequestParam Map<String, String> param,
                              HttpServletRequest request, Model model) {
        PostStatus postStatus = parseStatus(status, model);
        Date yearMonth = parseDate(param, model);
        Long categoryId = parseCategoryId(param, model);
        int pageIndex = parsePageIndex(param, model);
        String tagName = param.get("tag");
        long authorId = parseAuthorId(param, model);

        Pageable<Post> postPage = postService.findByPageAndStatusAndDateAndCategoryAndTagAndAuthorId
                (pageIndex, Constant.DEFAULT_PAGE_SIZE, postStatus, yearMonth, categoryId, tagName, authorId);
        fetchAuthorInfo(postPage.getList(), model);
        PostTaxonomyHelper.fetchTaxonomyRelationships(postPage.getList(), model, postService);
        model.addAttribute("postPage", postPage);
        fetchCategoryInfo(model);
        model.addAttribute("queryString", request.getQueryString());
        model.addAttribute("title", __("All Post"));
        return "admin/post-all";
    }

    private PostStatus parseStatus(String status, Model model) {
        PostStatus postStatus = PostStatus.nameOf(status);
        log.debug("post status = {}", postStatus);
        if (postStatus != null) {
            model.addAttribute("status", postStatus.name().toLowerCase());
        } else {
            model.addAttribute("status", "all");
        }
        count(model);
        return postStatus;
    }

    private void count(Model model) {
        long allCount = getCount(null);
        long publishedCount = getCount(PostStatus.PUBLISHED);
        long draftCount = getCount(PostStatus.DRAFT);
        long pendingCount = getCount(PostStatus.PENDING);
        long trashCount = getCount(PostStatus.TRASH);
        model.addAttribute("allCount", allCount);
        model.addAttribute("publishedCount", publishedCount);
        model.addAttribute("draftCount", draftCount);
        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("trashCount", trashCount);
    }

    private long getCount(PostStatus status) {
        if (status == null) {
            return globalInfo.get(Constant.PostStatus_ALL, () -> {
                long count = postService.countByStatus(null);
                globalInfo.set(Constant.PostStatus_ALL, count);
                return count;
            });
        }
        return globalInfo.get(status.name(), () -> {
            long count = postService.countByStatus(status);
            globalInfo.set(status.name(), count);
            return count;
        });
    }

    private Date parseDate(Map<String, String> param, Model model) {
        Date yearMonth = null;
        String date = param.get("date");
        if (StringUtils.hasText(date)) {
            try {
                yearMonth = DateTime.parse(date, FORMATTER_YM).toDate();
                model.addAttribute("date", date);
            } catch (Exception ignore) {
                log.warn("日期格式错误:{}", date);
            }
        }
        return yearMonth;
    }

    private Long parseCategoryId(Map<String, String> param, Model model) {
        Long categoryId = null;
        String category = param.get("category");
        if (category != null) {
            try {
                categoryId = Long.parseLong(category);
                model.addAttribute("categoryId", category);
            } catch (NumberFormatException ignore) {
                log.warn("分类目录 id 数字格式有误:{}", category);
            }
        }
        return categoryId;
    }

    private int parsePageIndex(Map<String, String> param, Model model) {
        int pageIndex = 1;
        String pageIndexStr = param.get("page");
        if (StringUtils.hasText(pageIndexStr)) {
            try {
                pageIndex = Integer.parseInt(pageIndexStr);
            } catch (NumberFormatException ignore) {
                log.warn("页码错误:{}", pageIndexStr);
            }
        }
        return pageIndex;
    }

    private long parseAuthorId(Map<String, String> param, Model model) {
        String author = param.get("author");
        long id = 1L;
        if (author != null) {
            try {
                id = Long.parseLong(author);
            } catch (NumberFormatException ignore) {
                log.warn("作者 id 转换数字失败: {}", author);
            }
        }
        return id;
    }

    private void fetchAuthorInfo(List<Post> posts, Model model) {
        Map<Long, String> authorMap = Maps.newHashMap();
        for (Post post : posts) {
            Long authorId = post.getPostAuthorId();
            String name = globalInfoUserIdNameMap.get(authorId, () -> {
                User user = userService.findById(authorId);
                Long id = user.getUserId();
                String displayName = user.getDisplayName();
                globalInfoUserIdNameMap.set(id, displayName);
                return displayName;
            });
            authorMap.put(authorId, name);
        }
        model.addAttribute("authorMap", authorMap);
    }

    private void fetchCategoryInfo(Model model) {
        List<Category> categoryList = categoryService.listCategoriesByOrder();
        model.addAttribute("categoryList", categoryList);
        categoryList = categoryService.listCategoriesNoPrefix();
        Map<Long, String> categoryIdNameMap = Maps.newHashMap();
        for (Category category : categoryList) {
            categoryIdNameMap.put(category.getTaxonomyId(), category.getName());
        }
        model.addAttribute("categoryIdNameMap", categoryIdNameMap);
    }

    // endregion

    @RequestMapping("/post/new")
    public String newPostPage(Model model) {
        model.addAttribute("title", __("Write Post"));
        model.addAttribute("editor", true);

        List<Category> categoryList = categoryService.listCategoriesByOrder();
        model.addAttribute("categoryList", categoryList);
        return "admin/post-write";
    }

    @RequestMapping(path = {"/post/add"}, method = {RequestMethod.POST})
    public String addPost(@RequestParam Map<String, String> param, HttpServletRequest request) {
        String title = param.get("title");
        String content = param.get("content");
        String markdownContent = param.get("md-content");
        String excerpt = param.get("excerpt");
        String date = param.get("date");
        String[] categories = request.getParameterValues("category");
        String tags = param.get("tags");
        String commentOpenStr = param.get("commentOpen");
        String pingOpenStr = param.get("pingOpen");
        String password = param.get("password");
        String postName = param.get("postName");
        String draft = param.get("draft");
        log.info("param = {}", param);

        DateTime dateTime = DateTime.now();
        try {
            dateTime = DateTime.parse(date, FORMATTER);
        } catch (Exception e) {
            log.warn("日期转化异常：{}", date, e);
        }

        List<Long> categoryList = parseCategory(categories);

        List<String> tagList = SPLITTER.splitToList(tags);
        boolean commentOpen = true;
        if (!"on".equals(commentOpenStr)) {
            commentOpen = false;
        }
        boolean pingOpen = true;
        if (!"on".equals(pingOpenStr)) {
            pingOpen = false;
        }

        PostStatus status = PostStatus.PUBLISHED;
        if (StringUtils.hasText(draft)) {
            status = PostStatus.DRAFT;
        }

        User user = (User) request.getAttribute(Constant.USER);//CheckLoginInterceptor
        Post post = new Post();
        post.setPostAuthorId(user.getUserId())
                .setPostDate(dateTime.toDate())
                .setPostContent(content)
                .setPostTitle(title)
                .setPostExcerpt(excerpt)
                .setPostStatus(status)
                .setCommentOpen(commentOpen)
                .setPingOpen(pingOpen)
                .setPostPassword(password)
                .setPostName(postName);
        postService.save(post, categoryList, tagList, markdownContent);
        clearStatusCount();
        return "redirect:/admin/post";
    }

    private List<Long> parseCategory(String... categories) {
        List<String> categoriesStr = Lists.newArrayList(categories);
        List<Long> categoryList = Lists.newArrayList();
        for (String id : categoriesStr) {
            try {
                long categoryId = Long.parseLong(id);
                categoryList.add(categoryId);
            } catch (NumberFormatException e) {
                log.warn("数字转化失败，分类目录编号。{}", id, e);
            }
        }
        return categoryList;
    }

    @RequestMapping(path = {"/post/edit"}, method = {RequestMethod.GET})
    public String edit(@RequestParam Map<String, String> param, Model model) {
        Long postId = parsePostId(param);
        if (postId == null) {
            model.addAttribute(Constant.ERROR, __("Illegal param: postId."));
            return "admin/die";
        }
        Post post = postService.findById(postId);
        if (post == null) {
            model.addAttribute(Constant.ERROR, __("Illegal param: postId does not exist."));
            return "admin/die";
        }
        model.addAttribute("post", post);
        List<PostMeta> postMetaList = postService.findPostMetaByPostId(postId);
        String mdSource = null;
        for (PostMeta postMeta : postMetaList) {
            if (postMeta.getMetaKey().equals(Constant.K_MD_SOURCE)) {
                mdSource = postMeta.getMetaValue();
                break;
            }
        }
        if (StringUtils.hasText(mdSource)) {
            model.addAttribute("md", mdSource);
        }
        PostTaxonomyHelper.fetchTaxonomyRelationships(Lists.newArrayList(post), model, postService);
        model.addAttribute("title", __("Edit Post"));
        model.addAttribute("editor", true);
        List<Category> categoryList = categoryService.listCategoriesByOrder();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("status", PostStatus.values());
        return "admin/post-edit";
    }

    private Long parsePostId(Map<String, String> param) {
        String postIdStr = param.get("postId");
        Long postId = null;
        try {
            postId = Long.parseLong(postIdStr);
        } catch (NumberFormatException ignore) {
        }
        return postId;
    }

    @RequestMapping(path = {"/post/edit"}, method = {RequestMethod.POST})
    public String editPost(@RequestParam Map<String, String> param, Model model, HttpServletRequest request) {
        log.debug("param = {}", param);
        Long postId = parsePostId(param);
        if (postId == null) {
            model.addAttribute(Constant.ERROR, __("Illegal param: postId."));
            return "admin/die";
        }
        Post post = postService.findById(postId);
        if (post == null) {
            model.addAttribute(Constant.ERROR, __("Illegal param: postId does not exist."));
            return "admin/die";
        }
        String title = param.get("title");
        String content = param.get("content");
        String markdownContent = param.get("md-content");
        String excerpt = param.get("excerpt");
        String date = param.get("date");
        String status = param.get("status");
        String[] categories = request.getParameterValues("category");
        String tags = param.get("tags");
        String commentOpenStr = param.get("commentOpen");
        String pingOpenStr = param.get("pingOpen");
        String password = param.get("password");
        String postName = param.get("postName");

        post.setPostTitle(title);
        post.setPostContent(content);
        post.setPostExcerpt(excerpt);
        try {
            DateTime dateTime = DateTime.parse(date, FORMATTER);
            post.setPostDate(dateTime.toDate());
        } catch (Exception ignore) {
            log.warn("日期格式错误: {}", date);
        }
        PostStatus postStatus = PostStatus.nameOf(status);
        if (postStatus != null) {
            post.setPostStatus(postStatus);
        }
        if (StringUtils.hasText(commentOpenStr)) {
            post.setCommentOpen(commentOpenStr.equals("on"));
        }
        if (StringUtils.hasText(pingOpenStr)) {
            post.setPingOpen(pingOpenStr.equals("on"));
        }
        post.setPostPassword(password);
        post.setPostName(postName);
        post.setPostModified(new Date());

        // md, cat, tag
        List<Long> categoryList = parseCategory(categories);
        List<String> tagList = SPLITTER.splitToList(tags);
        postService.update(post, categoryList, tagList, markdownContent);
        clearStatusCount();
        return "redirect:/admin/post";
    }

    private void clearStatusCount() {
        globalInfo.set(Constant.PostStatus_ALL, null);
        PostStatus[] values = PostStatus.values();
        for (PostStatus status : values) {
            globalInfo.set(status.name(), null);
        }
        log.info("clear status count cache");
    }

}
