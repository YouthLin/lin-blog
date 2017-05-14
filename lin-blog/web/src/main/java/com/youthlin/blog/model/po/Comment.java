package com.youthlin.blog.model.po;

import com.youthlin.blog.model.enums.CommentStatus;
import com.youthlin.blog.model.enums.CommentType;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Date;

/**
 * 创建者： youthlin.chen 日期： 2017-04-04 21:04.
 * <pre>
 * DROP TABLE IF EXISTS `comments`;
 * CREATE TABLE `comments` (
 * `comment_ID`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT
 * COMMENT '自增主键',
 * `comment_post_ID`      BIGINT UNSIGNED NOT NULL DEFAULT '0'
 * COMMENT 'post id',
 * `comment_author`       TINYTEXT        NOT NULL
 * COMMENT '评论者姓名',
 * `comment_author_email` VARCHAR(64)     NOT NULL DEFAULT ''
 * COMMENT '评论者邮件',
 * `comment_author_url`   VARCHAR(128)    NOT NULL DEFAULT ''
 * COMMENT '评论者URL',
 * `comment_author_IP`    INT             NOT NULL DEFAULT '0'
 * COMMENT 'inet_aton("10.10.10.10")=168430090, inet_ntoa(168430090)="10.10.10.10"',
 * `comment_author_IPv6`  BINARY(16)      NOT NULL DEFAULT '0'
 * COMMENT 'inet6_aton("FE80:0000:0000:0000:0202:B3FF:FE1E:8329")=0xFE800000000000000202B3FFFE1E8329, inet6_ntoa()',
 * `comment_date`         DATETIME        NOT NULL DEFAULT now()
 * COMMENT '评论时间',
 * `comment_date_utc`     DATETIME        NOT NULL DEFAULT now()
 * COMMENT 'UTC 时间',
 * `comment_content`      TEXT            NOT NULL
 * COMMENT '评论内容',
 * `comment_status`       TINYINT         NOT NULL DEFAULT '0'
 * COMMENT '评论状态，0正常，1垃圾评论',
 * `comment_agent`        VARCHAR(255)    NOT NULL DEFAULT ''
 * COMMENT '评论者浏览器 UA',
 * `comment_type`         TINYINT         NOT NULL DEFAULT '0'
 * COMMENT '评论类型，0-正常评论，1-pingback',
 * `comment_parent`       BIGINT UNSIGNED NOT NULL DEFAULT '0'
 * COMMENT '回复给哪条评论',
 * `user_id`              BIGINT UNSIGNED NOT NULL DEFAULT '0'
 * COMMENT '用户ID，游客则为0',
 * PRIMARY KEY (`comment_ID`),
 * KEY `comment_post_ID` (`comment_post_ID`),
 * KEY `comment_approved_date_utc` (`comment_status`, `comment_date_utc`),
 * KEY `comment_date_utc` (`comment_date_utc`),
 * KEY `comment_parent` (`comment_parent`),
 * KEY `comment_author_email` (`comment_author_email`(10))
 * )
 * ENGINE = InnoDB
 * DEFAULT CHARSET = utf8mb4
 * COLLATE = utf8mb4_unicode_ci
 * COMMENT '评论表';</pre>
 */
@SuppressWarnings("unused")
public class Comment {
    private Long commentId;
    private Long commentPostId;
    private String commentAuthor;
    private String commentAuthorEmail;
    private String commentAuthorUrl = "";
    private String commentAuthorIp = "";
    private Date commentDate = new Date();
    private Date commentDateUtc = new DateTime(new DateTime(commentDate), DateTimeZone.UTC).toLocalDateTime().toDate();
    private String commentContent;
    private CommentStatus commentStatus = CommentStatus.NORMAL;
    private String commentAgent = "";
    private CommentType commentType = CommentType.COMMENT;
    private Long commentParent = 0L;
    private Long userId = 0L;

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", commentPostId=" + commentPostId +
                ", commentAuthor='" + commentAuthor + '\'' +
                ", commentAuthorEmail='" + commentAuthorEmail + '\'' +
                ", commentAuthorUrl='" + commentAuthorUrl + '\'' +
                ", commentAuthorIp=" + commentAuthorIp +
                ", commentDate=" + commentDate +
                ", commentDateUtc=" + commentDateUtc +
                ", commentContent='" + commentContent + '\'' +
                ", commentStatus=" + commentStatus +
                ", commentAgent='" + commentAgent + '\'' +
                ", commentType=" + commentType +
                ", commentParent=" + commentParent +
                ", userId=" + userId +
                '}';
    }

    //region getter setter
    public Long getCommentId() {
        return commentId;
    }

    public Comment setCommentId(Long commentId) {
        this.commentId = commentId;
        return this;
    }

    public Long getCommentPostId() {
        return commentPostId;
    }

    public Comment setCommentPostId(Long commentPostId) {
        this.commentPostId = commentPostId;
        return this;
    }

    public String getCommentAuthor() {
        return commentAuthor;
    }

    public Comment setCommentAuthor(String commentAuthor) {
        this.commentAuthor = commentAuthor;
        return this;
    }

    public String getCommentAuthorEmail() {
        return commentAuthorEmail;
    }

    public Comment setCommentAuthorEmail(String commentAuthorEmail) {
        this.commentAuthorEmail = commentAuthorEmail;
        return this;
    }

    public String getCommentAuthorUrl() {
        return commentAuthorUrl;
    }

    public Comment setCommentAuthorUrl(String commentAuthorUrl) {
        this.commentAuthorUrl = commentAuthorUrl;
        return this;
    }

    public String getCommentAuthorIp() {
        return commentAuthorIp;
    }

    public Comment setCommentAuthorIp(String commentAuthorIp) {
        this.commentAuthorIp = commentAuthorIp;
        return this;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public Comment setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
        this.commentDateUtc = new DateTime(new DateTime(commentDate), DateTimeZone.UTC).toLocalDateTime().toDate();
        return this;
    }

    public Date getCommentDateUtc() {
        return commentDateUtc;
    }

    public Comment setCommentDateUtc(Date commentDateUtc) {
        this.commentDateUtc = commentDateUtc;
        return this;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public Comment setCommentContent(String commentContent) {
        this.commentContent = commentContent;
        return this;
    }

    public CommentStatus getCommentStatus() {
        return commentStatus;
    }

    public Comment setCommentStatus(CommentStatus commentStatus) {
        this.commentStatus = commentStatus;
        return this;
    }

    public String getCommentAgent() {
        return commentAgent;
    }

    public Comment setCommentAgent(String commentAgent) {
        this.commentAgent = commentAgent;
        return this;
    }

    public CommentType getCommentType() {
        return commentType;
    }

    public Comment setCommentType(CommentType commentType) {
        this.commentType = commentType;
        return this;
    }

    public Long getCommentParent() {
        return commentParent;
    }

    public Comment setCommentParent(Long commentParent) {
        this.commentParent = commentParent;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public Comment setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    //endregion

}
