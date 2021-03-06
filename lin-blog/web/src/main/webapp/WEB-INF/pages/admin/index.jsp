<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="blog" uri="http://youthlin.com/linblog/tag/blog" %>
<%@ page import="static com.youthlin.utils.i18n.Translation.__" %>
<%@ page import="static com.youthlin.utils.i18n.Translation._n" %>
<%--@elvariable id="user" type="com.youthlin.blog.model.po.User"--%>
<%--@elvariable id="role" type="com.youthlin.blog.model.enums.Role"--%>
<%--
  Created by IntelliJ IDEA.
  User: lin
  Date: 17-5-5
  Time: 下午2:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/pages/common/admin/header.jsp" %>
<script>$(document).ready(function () {
    $(".menu-parent-overview").addClass("active").click();
    $(".menu-item-overview").addClass("active");
});</script>
<h1><%=__("Overview")%></h1>
<div>
    <div class="panel-group col-sm-6" id="accordion-right" role="tablist" aria-multiselectable="true">
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="panel-recent-published">
                <h4 class="panel-title">
                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#panel-recent-published-body"
                   aria-expanded="true" aria-controls="collapseOne"><%=__("Recent Published")%></a>
                </h4>
            </div>
            <div id="panel-recent-published-body" class="panel-collapse collapse in" role="tabpanel"
                 aria-labelledby="panel-recent-published">
                <ul class="list-group">
                    <%--@elvariable id="posts" type="java.util.List"--%>
                    <%--@elvariable id="post" type="com.youthlin.blog.model.po.Post"--%>
                    <c:forEach items="${posts}" var="post">
                        <li class="list-group-item" id="post-${post.postId}">
                            <div class="row">
                                <div class="col-sm-5">
                                    <fmt:formatDate value="${post.postDate}" pattern="YYYY-MM-dd HH:mm"/>
                                </div>
                                <div class="col-sm-7">
                                    <strong>
                                        <a target="_blank"
                                           href="<c:url value="/post/${post.postId}"/>">${post.postTitle}</a></strong>
                                    <c:if test="${post.postAuthorId eq user.userId or role.code ge 30}">
                                        <small>
                                            | <a
                                                href="<c:url value="/admin/post/edit?postId=${post.postId}"/>"><%=__("Edit")%></a>
                                        </small>
                                    </c:if>

                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="panel-recent-comment">
                <h4 class="panel-title">
                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#panel-recent-comment-body"
                   aria-expanded="true" aria-controls="collapseOne"><%=__("Recent Comments")%></a>
                </h4>
            </div>
            <div id="panel-recent-comment-body" class="panel-collapse collapse in" role="tabpanel"
                 aria-labelledby="panel-recent-comment">
                <ul class="list-group">
                    <%--@elvariable id="recentComments" type="java.util.LinkedHashMap"--%>
                    <%--@elvariable id="comment" type="com.youthlin.blog.model.po.Comment"--%>
                    <%--@elvariable id="post" type="com.youthlin.blog.model.po.Post"--%>
                    <c:forEach items="${recentComments}" var="entry">
                        <c:set var="comment" value="${entry.key}"/>
                        <c:set var="post" value="${entry.value}"/>
                        <li class="list-group-item" id="recent-comment-${comment.commentId}">
                            <div class="media">
                                <div class="media-left">
                                    <img class="media-object" width="40" height="40" alt="Gravatar"
                                         src="${blog:img(comment.commentAuthorEmail,40)}">
                                </div>
                                <div class="media-body">
                                    <strong class="media-heading">
                                        <c:choose>
                                            <c:when test="${ not empty comment.commentAuthorUrl}">
                                                <a href="${comment.commentAuthorUrl}" target="_blank" rel="nofollow">
                                                        ${comment.commentAuthor}</a>
                                            </c:when>
                                            <c:otherwise>
                                                ${comment.commentAuthor}
                                            </c:otherwise>
                                        </c:choose> :
                                        《 <a href="<c:url value="/post/${post.postId}"/>"
                                             target="_blank">${post.postTitle}</a> 》
                                    </strong>
                                    <div>
<a href="<c:url value="/post/${post.postId}#comment-${comment.commentId}"/>" target="_blank"
   title='${post.postTitle} at <fmt:formatDate value="${comment.commentDate}" pattern="YYYY-MM-dd HH:mm:ss"/>'>
        ${blog:sub(comment.commentContent, 50)}</a>
                                        <br>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>

    </div>

    <div class="panel-group col-sm-6" id="accordion-left" role="tablist" aria-multiselectable="true">
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="panel-overview">
                <h4 class="panel-title">
                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#panel-overview-body"
                   aria-expanded="true" aria-controls="collapseOne"><%=__("Overview")%></a>
                </h4>
            </div>
            <div id="panel-overview-body" class="panel-collapse collapse in" role="tabpanel"
                 aria-labelledby="panel-overview">
                <div class="panel-body">
                    <%
                        long postCount = ((Number) request.getAttribute("postCount")).longValue();
                        long categoryCount = ((Number) request.getAttribute("categoryCount")).longValue();
                        long commentCount = ((Number) request.getAttribute("commentCount")).longValue();
                        long tagCount = ((Number) request.getAttribute("tagCount")).longValue();
                    %>
                    <div class="col-sm-6 count count-posts">
                        <span class="glyphicon glyphicon-book" aria-hidden="true"></span>&nbsp;
                        <c:choose>
                            <c:when test="${role.code ge 20}">
                                <a href="<c:url value="/admin/post"/>"><%=_n("One Post", "{0} Posts", postCount, postCount)%></a>
                            </c:when>
                            <c:otherwise>
                                <%=_n("One Post", "{0} Posts", postCount, postCount)%>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-sm-6 count count-comments">
                        <span class="glyphicon glyphicon-comment" aria-hidden="true"></span>&nbsp;
                        <c:choose>
                            <c:when test="${role.code ge 30}">
                                <a href="<c:url value="/admin/comment/all"/>">
                                    <%=_n("One Comment", "{0} Comments", commentCount, commentCount)%></a>
                            </c:when>
                            <c:otherwise>
                                <%=_n("One Comment", "{0} Comments", commentCount, commentCount)%>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-sm-6 count count-categories">
                        <span class="glyphicon glyphicon-folder-open" aria-hidden="true"></span>&nbsp;
                        <c:choose>
                            <c:when test="${role.code ge 40}">
                                <a href="<c:url value="/admin/post/category" />">
                                    <%=_n("One Category", "{0} Categories", categoryCount, categoryCount)%></a>
                            </c:when>
                            <c:otherwise>
                                <%=_n("One Category", "{0} Categories", categoryCount, categoryCount)%>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-sm-6 count count-tags">
                        <span class="glyphicon glyphicon-tags" aria-hidden="true"></span>&nbsp;
                        <c:choose>
                            <c:when test="${role.code ge 40}">
                                <a href="<c:url value="/admin/post/tag"/>"><%=_n("One Tag", "{0} Tags", tagCount, tagCount)%></a>
                            </c:when>
                            <c:otherwise>
                                <%=_n("One Tag", "{0} Tags", tagCount, tagCount)%>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="panel-draft">
                <h4 class="panel-title">
                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#panel-draft-body"
                   aria-expanded="true" aria-controls="collapseOne"><%=__("Quickly Draft")%></a>
                </h4>
            </div>
            <div id="panel-draft-body" class="panel-collapse collapse in" role="tabpanel"
                 aria-labelledby="panel-draft">
                <div class="panel-body">
                    <form action="<c:url value="/admin/post/add"/>" method="post">
                        <div class="form-group">
                            <label for="post-title"><%=__("Title:")%></label>
                            <input type="text" class="form-control" name="title" id="post-title">
                        </div>
                        <div class="form-group">
                            <label for="content"><%=__("Content:")%></label>
                            <textarea class="form-control" name="content" id="content" rows="10"></textarea>
                        </div>
                        <button type="submit" name="draft" value="draft" class="btn btn-default">
                            <%=__("Save Draft")%>
                        </button>
                    </form>
                </div>
            </div>
        </div>

    </div>
</div>
<%@ include file="/WEB-INF/pages/common/admin/footer.jsp" %>
