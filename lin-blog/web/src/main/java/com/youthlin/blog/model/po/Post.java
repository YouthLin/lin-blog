package com.youthlin.blog.model.po;

import com.youthlin.blog.model.enums.PostStatus;
import com.youthlin.blog.model.enums.PostType;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.util.StringUtils;

import java.util.Date;

import static com.youthlin.utils.i18n.Translation.__;

/**
 * 创建者： youthlin.chen 日期： 2017-04-04 20:36.
 * <pre>
 * DROP TABLE IF EXISTS `posts`;
 * CREATE TABLE `posts` (
 * `ID`                BIGINT UNSIGNED NOT NULL AUTO_INCREMENT
 * COMMENT '自增主键',
 * `post_author`       BIGINT UNSIGNED NOT NULL DEFAULT '0'
 * COMMENT '作者 ID',
 * `post_date`         DATETIME        NOT NULL DEFAULT now()
 * COMMENT '发表时间',
 * `post_date_utc`     DATETIME        NOT NULL DEFAULT now()
 * COMMENT 'UTC 发表时间 (UTC_TIMESTAMP: http://stackoverflow.com/a/19112227)',
 * `post_content`      LONGTEXT        NOT NULL
 * COMMENT '文章/页面内容',
 * `post_title`        TEXT            NOT NULL
 * COMMENT '标题',
 * `post_excerpt`      TEXT            NOT NULL
 * COMMENT '摘要',
 * `post_status`       TINYINT         NOT NULL DEFAULT 0
 * COMMENT '状态， 0 发表， 1草稿， ',
 * `comment_open`      TINYINT         NOT NULL DEFAULT 1
 * COMMENT '是否可评论 1是 0否',
 * `ping_open`         TINYINT         NOT NULL DEFAULT 1
 * COMMENT '是否允许 ping, 1是 0否',
 * `post_password`     VARCHAR(20)     NOT NULL DEFAULT ''
 * COMMENT '密码',
 * `post_name`         VARCHAR(200)    NOT NULL DEFAULT ''
 * COMMENT '显示在 URL 中，通常是标题经过 URL 编码后的字符串',
 * `post_modified`     DATETIME        NOT NULL DEFAULT now()
 * COMMENT '修改时间',
 * `post_modified_utc` DATETIME        NOT NULL DEFAULT now()
 * COMMENT '修改时间 UTC',
 * `post_parent`       BIGINT UNSIGNED NOT NULL DEFAULT '0'
 * COMMENT '父 post ，如页面可以有父级页面',
 * `post_type`         INT             NOT NULL DEFAULT '0'
 * COMMENT '类型 0post/1page/2attachment/...',
 * `post_mime_type`    VARCHAR(100)    NOT NULL DEFAULT ''
 * COMMENT '附件的 MIME 类型',
 * `comment_count`     BIGINT          NOT NULL DEFAULT '0'
 * COMMENT '评论数量',
 * PRIMARY KEY (`ID`),
 * KEY `post_name` (`post_name`(191)),
 * KEY `type_status_date` (`post_type`, `post_status`, `post_date`, `ID`),
 * KEY `post_parent` (`post_parent`),
 * KEY `post_author` (`post_author`)
 * )
 * ENGINE = InnoDB
 * DEFAULT CHARSET = utf8mb4
 * COLLATE = utf8mb4_unicode_ci
 * COMMENT '文章，页面，附件等 post ';</pre>
 */
@SuppressWarnings("WeakerAccess")
public class Post {
    private Long postId;
    private Long postAuthorId = 0L;
    private Date postDate = new Date();
    private Date postDateUtc = new DateTime(new DateTime(postDate), DateTimeZone.UTC).toLocalDateTime().toDate();
    private String postContent;
    private String postTitle;
    private String postExcerpt = "";
    private PostStatus postStatus = PostStatus.DRAFT;
    private Boolean commentOpen = true;
    private Boolean pingOpen = true;
    private String postPassword = "";
    private String postName;
    private Date postModified = new Date();
    private Date postModifiedUtc = new DateTime(new DateTime(postModified), DateTimeZone.UTC).toLocalDateTime().toDate();
    private Long postParent = 0L;
    private PostType postType = PostType.POST;
    private String postMimeType = "";
    private Long commentCount = 0L;

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", postAuthorId=" + postAuthorId +
                ", postDate=" + postDate +
                ", postDateUtc=" + postDateUtc +
                ", postContent='" + postContent + '\'' +
                ", postTitle='" + postTitle + '\'' +
                ", postExcerpt='" + postExcerpt + '\'' +
                ", postStatus=" + postStatus +
                ", commentOpen=" + commentOpen +
                ", pingOpen=" + pingOpen +
                ", postPassword='" + postPassword + '\'' +
                ", postName='" + postName + '\'' +
                ", postModified=" + postModified +
                ", postModifiedUtc=" + postModifiedUtc +
                ", postParent=" + postParent +
                ", postType=" + postType +
                ", postMimeType='" + postMimeType + '\'' +
                ", commentCount=" + commentCount +
                '}';
    }

    //region getter setter
    public Long getPostId() {
        return postId;
    }

    public Post setPostId(Long postId) {
        this.postId = postId;
        return this;
    }

    public Long getPostAuthorId() {
        return postAuthorId;
    }

    public Post setPostAuthorId(Long postAuthorId) {
        this.postAuthorId = postAuthorId;
        return this;
    }

    public Date getPostDate() {
        return postDate;
    }

    public Post setPostDate(Date postDate) {
        this.postDate = postDate;
        this.postDateUtc = new DateTime(new DateTime(postDate), DateTimeZone.UTC).toLocalDateTime().toDate();
        return this;
    }

    public Date getPostDateUtc() {
        return postDateUtc;
    }

    public Post setPostDateUtc(Date postDateUtc) {
        this.postDateUtc = postDateUtc;
        return this;
    }

    public String getPostContent() {
        return postContent;
    }

    public Post setPostContent(String postContent) {
        if (!StringUtils.hasText(postContent)) {
            postContent = "";
        }
        this.postContent = postContent;
        return this;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public Post setPostTitle(String postTitle) {
        if (!StringUtils.hasText(postTitle)) {
            postTitle = __("Untitled");
        }
        this.postTitle = postTitle;
        if (!StringUtils.hasText(postName)) {
            postName = postTitle;
        }
        return this;
    }

    public String getPostExcerpt() {
        return postExcerpt;
    }

    public Post setPostExcerpt(String postExcerpt) {
        if (!StringUtils.hasText(postExcerpt)) {
            postExcerpt = "";
        }
        this.postExcerpt = postExcerpt;
        return this;
    }

    public PostStatus getPostStatus() {
        return postStatus;
    }

    public Post setPostStatus(PostStatus postStatus) {
        this.postStatus = postStatus;
        return this;
    }

    public Boolean getCommentOpen() {
        return commentOpen;
    }

    public Post setCommentOpen(Boolean commentOpen) {
        this.commentOpen = commentOpen;
        return this;
    }

    public Boolean getPingOpen() {
        return pingOpen;
    }

    public Post setPingOpen(Boolean pingOpen) {
        this.pingOpen = pingOpen;
        return this;
    }

    public String getPostPassword() {
        return postPassword;
    }

    public Post setPostPassword(String postPassword) {
        if (!StringUtils.hasText(postPassword)) {
            postPassword = "";
        }
        this.postPassword = postPassword;
        return this;
    }

    public String getPostName() {
        return postName;
    }

    @SuppressWarnings("UnusedReturnValue")
    public Post setPostName(String postName) {
        if (!StringUtils.hasText(postName)) {
            postName = getPostTitle();
        }
        this.postName = postName;
        return this;
    }

    public Date getPostModified() {
        return postModified;
    }

    public Post setPostModified(Date postModified) {
        this.postModified = postModified;
        this.postModifiedUtc = new DateTime(new DateTime(postModified), DateTimeZone.UTC).toLocalDateTime().toDate();
        return this;
    }

    public Date getPostModifiedUtc() {
        return postModifiedUtc;
    }

    public Post setPostModifiedUtc(Date postModifiedUtc) {
        this.postModifiedUtc = postModifiedUtc;
        return this;
    }

    public Long getPostParent() {
        return postParent;
    }

    public Post setPostParent(Long postParent) {
        this.postParent = postParent;
        return this;
    }

    public PostType getPostType() {
        return postType;
    }

    public Post setPostType(PostType postType) {
        this.postType = postType;
        return this;
    }

    public String getPostMimeType() {
        return postMimeType;
    }

    public Post setPostMimeType(String postMimeType) {
        this.postMimeType = postMimeType;
        return this;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public Post setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
        return this;
    }
    //endregion

}
