package com.youthlin.blog.support;

import com.google.common.collect.Lists;
import com.rometools.rome.feed.rss.Category;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Content;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Guid;
import com.rometools.rome.feed.rss.Item;
import com.youthlin.blog.model.po.Post;
import com.youthlin.blog.model.po.Taxonomy;
import com.youthlin.blog.model.po.User;
import com.youthlin.blog.util.Constant;
import com.youthlin.blog.util.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.youthlin.utils.i18n.Translation.__;
import static com.youthlin.utils.i18n.Translation._x;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-26 18:31.
 */
@Component
public class RssFeedView extends AbstractRssFeedView {
    private static final Logger log = LoggerFactory.getLogger(RssFeedView.class);
    public static final String POSTS = "posts";
    public static final String USER_MAP = "userMap";
    public static final String TAXONOMY_Map = "taxonomyMap";
    @Resource
    private GlobalInfo<String, String> globalInfo;

    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Channel feed, HttpServletRequest request) {
        super.buildFeedMetadata(model, feed, request);
        String url = ServletUtil.getHostLink(request);
        feed.setLink(url + "/feed");
        String title = globalInfo.get(Constant.O_BLOG_TITLE);
        feed.setTitle(title);
        feed.setDescription(title);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected List<Item> buildFeedItems(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Post> posts = (List<Post>) model.get(POSTS);
        Map<Long, User> userMap = (Map<Long, User>) model.get(USER_MAP);
        Map<Long, Collection<Taxonomy>> taxonomyMap = (Map<Long, Collection<Taxonomy>>) model.get(TAXONOMY_Map);
        String link = ServletUtil.getHostLink(request);
        List<Item> items = Lists.newArrayList();
        for (Post post : posts) {
            Item item = new Item();
            item.setTitle(post.getPostTitle());
            item.setAuthor(_x("Unknown", "author name"));// 佚名
            User user = userMap.get(post.getPostAuthorId());
            if (user != null) {
                item.setAuthor(user.getDisplayName());
            }
            Description description = new Description();
            description.setType("text/plain");
            String postExcerpt = post.getPostExcerpt();
            if (StringUtils.hasText(postExcerpt)) {
                description.setValue(ServletUtil.substringHtml(postExcerpt, 300));
            } else {
                description.setValue(ServletUtil.substringHtml(post.getPostContent(), 300));
            }
            item.setDescription(description);
            Content content = new Content();
            content.setType("text/html");
            content.setValue(post.getPostContent());
            item.setContent(content);
            //item.setComments("comments");
            Collection<Taxonomy> taxonomies = taxonomyMap.get(post.getPostId());
            List<Category> categoryList = Lists.newArrayList();
            for (Taxonomy taxonomy : taxonomies) {
                if (taxonomy.getTaxonomy().equalsIgnoreCase(Taxonomy.TAXONOMY_CATEGORY)) {
                    Category category = new Category();
                    //category.setDomain("");
                    category.setValue(taxonomy.getName());
                    categoryList.add(category);
                }
            }
            item.setCategories(categoryList);
            String postLink = link + "/post/" + post.getPostId();
            item.setLink(postLink);
            Guid guid = new Guid();
            guid.setPermaLink(true);
            guid.setValue(postLink);
            item.setPubDate(post.getPostDate());
            items.add(item);
        }

        return items;
    }
}
