<%--suppress ELValidationInJSP --%>
<%@ page import="static com.youthlin.utils.i18n.Translation._f" %>
<%@ page import="com.youthlin.blog.util.ServletUtil" %>
<%@ page import="com.youthlin.blog.model.po.Comment" %>

<%--
  Created by IntelliJ IDEA.
  User: youthlin.chen
  Date: 2017/5/11
  Time: 21:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<aside id="page-sidebar" class="col-sm-3">
    <div class="sidebar">
        <div class="panel panel-default panel-comment">
            <div class="panel-heading">
                <h5 class="panel-title"><%=__("Recent Comments")%></h5>
            </div>
            <div class="panel-body">
                <ul class="recent-comment-list">
                    <%--@elvariable id="recentCommentMap" type="java.util.LinkedHashMap"--%>
                    <%--@elvariable id="comment" type="com.youthlin.blog.model.po.Comment"--%>
                    <%--@elvariable id="post" type="com.youthlin.blog.model.po.Post"--%>
                    <c:forEach items="${recentCommentMap}" var="entry">
                        <c:set var="comment" value="${entry.key}"/>
                        <c:set var="post" value="${entry.value}"/>
                        <li class="recent-comment" id="recent-coment-${comment.commentId}">
                            <div class="media">
                                <div class="media-left">
                                    <img class="media-object" width="40" height="40" alt="Gravatar"
                                         src=<%="'"+ServletUtil.getGravatarUrl(
                                                 ((Comment)pageContext.getAttribute("comment")).getCommentAuthorEmail()
                                                 )+"'"%>>
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
                                    </strong>
                                    <c:set var="content" value="${comment.commentContent}"/>
                                    <c:if test="${(not empty content) and(content.length()>50) }">
                                        <c:set var="content" value="${content.substring(0,50)} [...]"/>
                                    </c:if>
                                    <a href="<c:url value="/post/${post.postId}#comment-${comment.commentId}"/>"
                                       title='${post.postTitle} at <fmt:formatDate value="${comment.commentDate}" pattern="YYYY-MM-dd HH:mm:ss"/>'>${content}</a>
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div class="panel panel-default panel-category">
            <div class="panel-heading">
                <h5 class="panel-title"><%=__("Categories")%></h5>
            </div>
            <div class="panel-body">
                <ul class="category-list">
                    <%--@elvariable id="categoryList" type="java.util.List"--%>
                    <%--@elvariable id="category" type="com.youthlin.blog.model.bo.Category"--%>
                    <c:forEach items="${categoryList}" var="category">
                        <li class="category category-${category.taxonomyId}">
                            <a href="<c:url value="/category/${category.slug}"/>">${category.name} (${category.count})</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div class="panel panel-default panel-tag">
            <div class="panel-heading">
                <h5 class="panel-title"><%=__("Tags")%></h5>
            </div>
            <div class="panel-body">
                <%--@elvariable id="tagList" type="java.util.List"--%>
                <%--@elvariable id="tag" type="com.youthlin.blog.model.bo.Tag"--%>
                <c:forEach items="${tagList}" var="tag">
                    <a href="<c:url value="/tag/${tag.slug}"/>" class="label label-info" data-count="${tag.count}">
                       ${tag.name}<span class="badge">${tag.count}</span></a>
                </c:forEach>
            </div>
        </div>
        <div class="panel panel-default panel-date">
            <div class="panel-heading">
                <h5 class="panel-title"><%=__("Archive")%></h5>
            </div>
            <div class="panel-body">
                <ul class="month-list">
                    <%--@elvariable id="months" type="com.google.common.collect.LinkedHashMultiset"--%>
                    <%--@elvariable id="entry" type="com.google.common.collect.Multiset.Entry"--%>
                    <%--@elvariable id="yearMonth" type="java.lang.String"--%>
                    <c:forEach items="${months.entrySet()}" var="entry">
                        <c:set var="yearMonth" value="${entry.element}"/>
                        <c:set var="arr" value="${yearMonth.split('-')}"/>
                        <li class="month month-${yearMonth}">
                            <a href="<c:url value="/date/${arr[0]}/${arr[1]}"/>">${yearMonth} <span> (${entry.count})</span></a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div class="panel panel-default panel-date">
            <div class="panel-heading">
                <h5 class="panel-title"><%=__("Meta")%></h5>
            </div>
            <div class="panel-body">
                <ul>
                    <li><a href="<c:url value="/admin/"/>"><%=__("Dashboard")%></a></li>
                    <li><a href="#"><abbr title="Really Simple Syndication">RSS</abbr></a></li>
                    <li><a href="http://youthlin.com/" target="_blank"><%=_f("Powered By {0}", "LinBlog")%></a></li>
                </ul>
            </div>
        </div>
    </div>
</aside>
