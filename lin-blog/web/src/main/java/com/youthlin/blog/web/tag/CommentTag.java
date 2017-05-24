package com.youthlin.blog.web.tag;

import com.google.common.collect.Lists;
import com.youthlin.blog.model.bo.CommentNode;
import com.youthlin.blog.model.po.Comment;
import com.youthlin.blog.model.po.Post;
import com.youthlin.blog.util.ServletUtil;
import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;

import static com.youthlin.utils.i18n.Translation.__;
import static com.youthlin.utils.i18n.Translation._n;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-13 10:48.
 */
@SuppressWarnings("unused")
public class CommentTag extends SimpleTagSupport {
    private Post post;
    private CommentNode commentNode;
    private List<CommentNode> topLevelCommentNodeList = Lists.newLinkedList();
    private String dateFormat = "YYYY-MM-dd HH:mm:ss";

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        out.println("<div id=\"comments\" class=\"border-ccc margin-padding-p1 well\">");
        out.println("  <h4 class=\"margin-padding-p1\">"
                /*TRANSLATORS: 0, comment count; 1, post title. */
                + _n("One Comment On {1} ", "{0} Comments On {1} ",
                post.getCommentCount(), post.getCommentCount(), post.getPostTitle()) + "</h4>");
        if (!topLevelCommentNodeList.isEmpty()) {
            commentNode = topLevelCommentNodeList.get(0);
        }
        if (commentNode != null) {
            out.println("  <ol class=\"media-list comments-list\">");
            while (commentNode != null) {
                printCommentItem(commentNode);
                commentNode = commentNode.getNext();
            }
            out.println("  </ol>");
        } else {
            out.println("<div class='margin-padding-p1'>" + __("No Comments.") + "</div>");
        }
        out.println("</div>");
    }

    private void printCommentItem(CommentNode node) throws IOException {
        JspWriter out = getJspContext().getOut();
        Comment comment = node.getComment();
        if (comment == null) {
            return;
        }

        switch (comment.getCommentStatus()) {
            case NORMAL:
                break;
            case PENDING:
                out.println("    <li class=\"media border-ccc margin-padding-p1 comment-item comment-" + comment.getCommentId()
                        + " depth-" + node.getLevel() + "\" id=\"comment-" + comment.getCommentId() + "\">");
                out.println(__("This comment is pending to show."));
                processChildrenComment(node);
                out.println("    </li>\n");
                return;
            default:
                out.println("    <li class=\"media border-ccc margin-padding-p1 comment-item comment-" + comment.getCommentId()
                        + " depth-" + node.getLevel() + "\" id=\"comment-" + comment.getCommentId() + "\">");
                out.println(__("This comment has been deleted."));
                processChildrenComment(node);
                out.println("    </li>\n");
                return;
        }


        out.println("    <li class=\"media border-ccc margin-padding-p1 comment-item comment-" + comment.getCommentId()
                + " depth-" + node.getLevel() + "\" id=\"comment-" + comment.getCommentId() + "\">");
        out.println("      <div class=\"media-left\">");
        out.println("        <img class=\"media-object\" src='" + ServletUtil.getGravatarUrl(comment.getCommentAuthorEmail())
                + "' alt=\"Gravatar\" width=\"40\" height=\"40\">");
        out.println("      </div>");
        out.println("      <div class=\"media-body\">");
        // region article
        out.println("        <article id=\"comment-article-" + comment.getCommentId() + "\">");
        out.println("          <header class=\"media-heading comment-meta comment-meta-header\">");
        String url = comment.getCommentAuthorUrl();
        if (StringUtils.hasText(url)) {
            out.println("            <div class=\"meta-info-author\"><a href=\"" + ServletUtil.filterHtml(url) + "\" target='_blank'>"
                    + comment.getCommentAuthor() + "</a><span class=\"sr-only\">" + __("Says:") + "</span></div>");
        } else {
            out.println("            <div class=\"meta-info-author\"><span>" + ServletUtil.filterHtml(comment.getCommentAuthor())
                    + "</span><span class=\"sr-only\">" + __("Says:") + "</span></div>");
        }
        out.println("          <div class=\"meta-info-author\"><time><a href=\"#comment-" + comment.getCommentId()
                + "\">" + new DateTime(comment.getCommentDate()).toString(dateFormat) + "</a></time></div>");
        out.println("          </header>");
        out.println("          <div class=\"comment-cotent\">" + ServletUtil.filterXss(comment.getCommentContent()
                .replaceAll("(\\n)?<br>(\\n)?", "\n").replaceAll("(\\r\\n|\\n)", "<br>")) + "</div>");
        out.println("          <footer class=\"comment-meta comment-meta-footer\">");
        out.println("            <span><a href=\"#commentform\" data-to='" + comment.getCommentId() + "' class='replay'>"
                + __("Replay") + "</a></span>");
        out.println("          </footer>");
        out.println("        </article>");
        // endregion article
        processChildrenComment(node);
        out.println("      </div>");
        out.println("    </li>\n");
    }

    private void processChildrenComment(CommentNode node) throws IOException {
        JspWriter out = getJspContext().getOut();
        List<CommentNode> children = node.getChildren();
        if (children != null && !children.isEmpty()) {
            out.println("          <ol class=\"media-list comments-list comments=list-children\">");
            for (CommentNode child : children) {
                printCommentItem(child);
            }
            out.println("          </ol>");

        }
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public CommentNode getCommentNode() {
        return commentNode;
    }

    public void setCommentNode(CommentNode commentNode) {
        this.commentNode = commentNode;
    }

    public List<CommentNode> getTopLevelCommentNodeList() {
        return topLevelCommentNodeList;
    }

    public void setTopLevelCommentNodeList(List<CommentNode> topLevelCommentNodeList) {
        this.topLevelCommentNodeList = topLevelCommentNodeList;
    }

}
