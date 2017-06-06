<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="blog" uri="http://youthlin.com/linblog/tag/blog" %>
<%@ page import="static com.youthlin.utils.i18n.Translation.__" %>
<%@ page import="com.youthlin.blog.model.bo.Pageable" %>
<%--
  Created by IntelliJ IDEA.
  User: lin
  Date: 17-5-5
  Time: 下午3:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="common/head.jsp" %>
    <title><%=__("Home")%></title>
</head>
<body>
<div id="wrap">
    <div class="page">
        <%@ include file="include/header.jsp" %>
        <ol class="breadcrumb">
            <li aria-label=<%=__("\"Home\"")%>>
                <span class="glyphicon glyphicon-home" aria-hidden="true"></span>
                <a href="<c:url value="/"/>"><%=__("Home")%></a></li>
            <%--@elvariable id="author" type="com.youthlin.blog.model.po.User"--%>
            <c:if test="${not empty author}">
                <li aria-label=<%=__("\"Post of author\"")%>>
                    <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                    <a href="<c:url value="/author/${author.userId}"/>">${author.displayName}</a></li>
            </c:if>
            <%--@elvariable id="taxonomyList" type="java.util.List"--%>
            <%--@elvariable id="taxonomy" type="com.youthlin.blog.model.po.Taxonomy"--%>
            <c:forEach items="${taxonomyList}" var="taxonomy">
                <li aria-label=<%=__("\"Post of taxonomy\"")%>>
                    <c:choose>
                        <c:when test="${taxonomy.taxonomy eq 'tag'}">
                            <span class="glyphicon glyphicon-tag" aria-hidden="true"></span>
                        </c:when>
                        <c:otherwise>
                            <span class="glyphicon glyphicon-folder-open" aria-hidden="true"></span>
                        </c:otherwise>
                    </c:choose>
                    <a href="<c:url value="/${taxonomy.taxonomy}/${taxonomy.slug}"/>">${taxonomy.name}</a></li>
            </c:forEach>
            <%--@elvariable id="year" type="java.lang.String"--%>
            <c:if test="${not empty year}">
                <li aria-label=<%=__("\"Post of year\"")%>>
                    <span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
                    <a href="<c:url value="/date/${year}"/>">${year}</a></li>
            </c:if>
            <%--@elvariable id="month" type="java.lang.String"--%>
            <c:if test="${not empty month}">
                <li aria-label=<%=__("\"Post of month\"")%>>
                    <a href="<c:url value="/date/${year}/${month}"/>">${year}-${month}</a>
                </li>
            </c:if>
            <%--@elvariable id="keyWords" type="java.lang.String"--%>
            <c:if test="${not empty keyWords}">
                <li aria-label=<%=__("\"Search Result\"")%>>
                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                    <a href="<c:url value="/s?w=${keyWords}"/>"
                       title=<%=_f("\"Used {0} ms.\"", request.getAttribute("usedMillsTime"))%>>
                        <%=ServletUtil.filterHtml(request.getParameter("w"))%></a>
                </li>
            </c:if>
            <c:if test="${postPage.totalPage > 1}">
                <li class="active"><%=_f("Page {0} ", ((Pageable) request.getAttribute("postPage")).getCurrentPage())%>
                </li>
            </c:if>
        </ol>
        <div class="content-wrap container-fluid">
            <main id="page-main" class="col-sm-9">
                <div class="main-content">
                    <%--@elvariable id="postPage" type="com.youthlin.blog.model.bo.Page"--%>
                    <%--@elvariable id="post" type="com.youthlin.blog.model.po.Post"--%>
                    <c:forEach items="${postPage.list}" var="post">
                        <article id="post-${post.postId}"
                                 class="article article-${post.postId} border-ccc margin-padding-p1">
                            <header class="post-meta post-header">
                                <h3><a href="<c:url value="/post/${post.postId}"/>">${post.postTitle}</a></h3>
                                <span class="meta-info meta-info-date">
                                    <span class="glyphicon glyphicon-time"
                                          aria-label=<%=__("\"Published Date:\"")%> aria-hidden="true"></span>
                                    <a class="meta-link" href="<c:url value="/post/${post.postId}"/>">
                                        <time datetime="${post.postDate}"
                                              title="<fmt:formatDate value="${post.postDate}" pattern="YYYY-MM-dd HH:mm"/>">
                                    <fmt:formatDate value="${post.postDate}" pattern="YYYY-MM-dd"/>
                                    </time>
                                </a>
                                </span>
                                <span class="meta-info meta-info-author">
                                    <span class="glyphicon glyphicon-user"
                                          aria-label=<%=__("\"Author:\"")%> aria-hidden="true"></span>
                                    <%--@elvariable id="userMap" type="java.util.Map"--%>
                                    <c:set var="author" value="${userMap[post.postAuthorId]}"/>
                                    <a href="<c:url value="/author/${post.postAuthorId}"/>">${author.displayName}</a>
                                </span>

                                <span class="meta-info meta-info-comment">
                                    <span class="glyphicon glyphicon-comment"
                                          aria-label=<%=__("\"Comment Count:\"")%> aria-hidden="true"></span>
                                    <a href="<c:url value="/post/${post.postId}#comments"/>">${post.commentCount}</a>
                                </span>
                                <span class="meta-info meta-info-category">
                                    <span class="glyphicon glyphicon-folder-open"
                                          aria-label=<%=__("\"Category:\"")%> aria-hidden="true"></span>
<%--@elvariable id="taxonomyMap" type="java.util.Map"--%>
<%--@elvariable id="taxonomyCat" type="com.youthlin.blog.model.po.Taxonomy"--%>
                                <c:forEach items="${taxonomyMap[post.postId]}" var="taxonomyCat">
                                    <c:if test="${taxonomyCat.taxonomy eq 'category'}">
                                        <a class="meta-link" href="<c:url value="/category/${taxonomyCat.slug}"/>">
                                                ${taxonomyCat.name}</a>
                                    </c:if>
                                </c:forEach>
                                </span>
                                <span class="meta-info meta-info-tag">
                                    <span class="glyphicon glyphicon-tags"
                                          aria-label=<%=__("\"Tags:\"")%> aria-hidden="true"></span>
                                <c:forEach items="${taxonomyMap[post.postId]}" var="taxonomyCat">
                                    <c:if test="${taxonomyCat.taxonomy eq 'tag'}">
                                        <a class="meta-link" href="<c:url value="/tag/${taxonomyCat.slug}"/>">
                                                ${taxonomyCat.name}</a>
                                    </c:if>
                                </c:forEach>
                                </span>

                                    <%--@elvariable id="user" type="com.youthlin.blog.model.po.User"--%>
                                    <%--@elvariable id="role" type="com.youthlin.blog.model.enums.Role"--%>
                                <c:if test="${((not empty user) and (post.postAuthorId eq user.userId)) or ( (not empty role) and role.code ge 30 )}">
                                <span class="meta-info meta-info-tag" aria-label="Edit">
                                    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                    <a href="<c:url value="/admin/post/edit?postId=${post.postId}"/>"><%=__("Edit")%></a>
                                </span>
                                </c:if>
                            </header>
                            <div class="post-content">
                                <c:choose>
                                    <c:when test="${not empty post.postExcerpt}">${post.postExcerpt}</c:when>
                                    <c:otherwise>
                                        ${blog:sub(post.postContent,500)}
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <footer class="post-meta post-footer">
                                <a href="<c:url value="/post/${post.postId}"/>"><%=__("Read More")%></a>
                            </footer>
                        </article>
                    </c:forEach>
                </div>
                <%-- paging --%>
                <c:if test="${postPage.totalPage > 1}">
                    <nav aria-label="Navigation" class="margin-left-right-p1">
                        <ul class="pager">
                            <c:choose>
                                <c:when test="${postPage.currentPage > 1}">
                                    <li class="previous">
                                        <a href="?page=${postPage.currentPage-1}">
                                            <span aria-hidden="true">&larr;</span> <%=__("Previous Page")%></a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="previous disabled">
                                        <a href="javascript:void(0);">
                                            <span aria-hidden="true">&larr;</span> <%=__("Previous Page")%></a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                            <li><span>${postPage.currentPage} / ${postPage.totalPage}</span></li>
                            <c:choose>
                                <c:when test="${postPage.currentPage < postPage.totalPage}">
                                    <li class="next">
                                        <a href="?page=${postPage.currentPage+1}">
                                            <%=__("Next Page")%> <span aria-hidden="true">&rarr;</span></a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="next disabled">
                                        <a href="javascript:void(0);">
                                            <%=__("Next Page")%> <span aria-hidden="true">&rarr;</span></a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </ul>
                    </nav>
                </c:if>
            </main>
            <%@ include file="include/sidebar.jsp" %>
        </div>
        <%--.row--%>
    </div>
    <%--/.page--%>
    <div id="end"></div>
</div>
<%@ include file="common/login/footer.jsp" %>
</body>
</html>
