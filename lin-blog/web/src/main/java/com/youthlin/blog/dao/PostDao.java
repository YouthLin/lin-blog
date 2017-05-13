package com.youthlin.blog.dao;

import com.youthlin.blog.model.enums.PostStatus;
import com.youthlin.blog.model.po.Post;
import com.youthlin.blog.model.po.Taxonomy;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-10 20:34.
 */
@Repository
public interface PostDao {
    void save(Post post);

    void update(Post post);

    List<Post> findByStatusAndDateAndCategoryIdAndTagAndAuthorId
            (@Param("status") PostStatus status, @Param("start") Date start, @Param("end") Date end,
             @Param("categoryId") Long categoryId, @Param("tagName") String tagName, @Param("authorId") Long authorId);

    @SuppressWarnings("SameParameterValue")
    List<Post> findByStatusAndDateAndCategoryIdAndTagAndAuthorId
            (@Param("status") PostStatus status, @Param("start") Date start, @Param("end") Date end,
             @Param("categoryId") Long categoryId, @Param("tagName") String tagName, @Param("authorId") Long authorId,
             RowBounds rowBounds);

    long countByStatus(@Param("status") PostStatus status);

    Post findById(Long id);

    /**
     * 按 taxonomy, post status 与时间，查询文章.
     * taxonomy 不为空时才会按 taxonomy 查，否则不限 taxonomy。
     * status 为空时表示不限状态。
     * start 与 end 同时不为 null 才会按时间查，否则不限时间。
     *
     * @param taxonomies 分类目录或标签等，使用其 name 和 taxonomy 属性查询。
     * @param status     PostStatus
     * @param start      开始时间
     * @param end        结束时间
     * @return 按时间倒序的文章列表(新的在前)
     */
    List<Post> queryByTaxonomySlugKindAndDate
    (@Param("taxonomies") List<Taxonomy> taxonomies, @Param("status") PostStatus status,
     @Param("start") Date start, @Param("end") Date end);

    List<Post> findIdGreaterOrLessThen(@Param("id") long id, @Param("grater") boolean greater);
}
