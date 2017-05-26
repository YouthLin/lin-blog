package com.youthlin.blog.support;

import com.google.common.collect.Lists;
import com.rometools.rome.feed.atom.Category;
import com.rometools.rome.feed.atom.Content;
import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;
import com.rometools.rome.feed.atom.Generator;
import com.rometools.rome.feed.atom.Link;
import com.rometools.rome.feed.atom.Person;
import com.rometools.rome.feed.synd.SyndPerson;
import com.youthlin.blog.model.po.Post;
import com.youthlin.blog.model.po.Taxonomy;
import com.youthlin.blog.model.po.User;
import com.youthlin.blog.util.Constant;
import com.youthlin.blog.util.ServletUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.feed.AbstractAtomFeedView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-26 20:16.
 */
@Component
public class AtomFeedView extends AbstractAtomFeedView {
    @Resource
    private GlobalInfo<String, String> globalInfo;

    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Feed feed, HttpServletRequest request) {
        super.buildFeedMetadata(model, feed, request);
        String url = ServletUtil.getHostLink(request);
        String title = globalInfo.get(Constant.O_BLOG_TITLE);
        feed.setTitle(title);
        feed.setId(url + "/feed/atom");
        Generator generator = new Generator();
        generator.setUrl(url);
        generator.setVersion("1.0");
        generator.setValue("LinBlog");
        feed.setGenerator(generator);
        feed.setIcon(url + "/static/img/logo.png");
    }

    @SuppressWarnings("unchecked")
    @Override
    protected List<Entry> buildFeedEntries(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Post> posts = (List<Post>) model.get(RssFeedView.POSTS);
        Map<Long, User> userMap = (Map<Long, User>) model.get(RssFeedView.USER_MAP);
        Map<Long, Collection<Taxonomy>> taxonomyMap = (Map<Long, Collection<Taxonomy>>) model.get(RssFeedView.TAXONOMY_Map);
        String link = ServletUtil.getHostLink(request);
        List<Entry> entryList = Lists.newArrayList();
        for (Post post : posts) {
            Entry entry = new Entry();
            Link postLink = new Link();
            postLink.setHref(link + "/post/" + post.getPostId());
            entry.setAlternateLinks(Collections.singletonList(postLink));
            entry.setPublished(post.getPostDate());
            entry.setModified(post.getPostModified());
            entry.setTitle(post.getPostTitle());
            User author = userMap.get(post.getPostAuthorId());
            if (author != null) {
                SyndPerson person = new Person();
                person.setEmail(author.getUserEmail());
                person.setName(author.getDisplayName());
                String userUrl = author.getUserUrl();
                if (StringUtils.hasText(userUrl)) {
                    person.setUri(userUrl);
                }
                entry.setAuthors(Collections.singletonList(person));
            }
            entry.setId(link + "/post/" + post.getPostId());
            entry.setUpdated(post.getPostModified());
            entry.setPublished(post.getPostDate());
            List<Category> categoryList = Lists.newArrayList();
            Collection<Taxonomy> taxonomyCollection = taxonomyMap.get(post.getPostId());
            for (Taxonomy taxonomy : taxonomyCollection) {
                if (taxonomy.getTaxonomy().equalsIgnoreCase(Taxonomy.TAXONOMY_CATEGORY)) {
                    Category category = new Category();
                    category.setTerm(taxonomy.getName());
                    categoryList.add(category);
                }
            }
            entry.setCategories(categoryList);
            Content summary = new Content();
            summary.setType("text/plain");
            String postExcerpt = post.getPostExcerpt();
            if (StringUtils.hasText(postExcerpt)) {
                summary.setValue(ServletUtil.substringHtml(postExcerpt, 300));
            } else {
                summary.setValue(ServletUtil.substringHtml(post.getPostContent(), 300));
            }
            entry.setSummary(summary);
            Content content = new Content();
            content.setType("text/html");
            content.setValue(post.getPostContent());
            entry.setContents(Collections.singletonList(content));
            entryList.add(entry);
        }

        return entryList;
    }
}
