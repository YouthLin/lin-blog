package com.youthlin.blog.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.youthlin.blog.model.po.Post;
import com.youthlin.blog.model.po.Taxonomy;
import com.youthlin.blog.service.PostService;
import org.springframework.ui.Model;

import java.util.List;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-12 16:07.
 */
public class PostTaxonomyHelper {
    public static void fetchTaxonomyRelationships(List<Post> posts, Model model, PostService postService) {
        List<Long> ids = Lists.newArrayListWithExpectedSize(posts.size());
        for (Post post : posts) {
            ids.add(post.getPostId());
        }
        Long[] postIds = ids.toArray(new Long[0]);
        Multimap<Long, Taxonomy> postIdTaxonomyMultimap = postService.findTaxonomyByPostId(postIds);
        model.addAttribute("taxonomyMap", postIdTaxonomyMultimap.asMap());
    }
}
