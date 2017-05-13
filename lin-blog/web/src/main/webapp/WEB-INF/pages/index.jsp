<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="static com.youthlin.utils.i18n.Translation.__" %>
<%@ page import="com.youthlin.blog.model.bo.Pageable" %><%--
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
    <div class="page container-fluid">
        <%@ include file="include/header.jsp" %>
        <ol class="breadcrumb">
            <li><a href="<c:url value="/"/>"><%=__("Home")%></a></li>
            <%--@elvariable id="taxonomyList" type="java.util.List"--%>
            <%--@elvariable id="taxonomy" type="com.youthlin.blog.model.po.Taxonomy"--%>
            <c:forEach items="${taxonomyList}" var="taxonomy">
                <li><a href="<c:url value="/${taxonomy.taxonomy}/${taxonomy.slug}"/>">${taxonomy.name}</a></li>
            </c:forEach>
            <%--@elvariable id="year" type="java.lang.String"--%>
            <c:if test="${not empty year}">
                <li><a href="<c:url value="/date/${year}"/>">${year}</a></li>
            </c:if>
            <%--@elvariable id="month" type="java.lang.String"--%>
            <c:if test="${not empty month}">
                <li><a href="<c:url value="/date/${year}/${month}"/>">${year}-${month}</a></li>
            </c:if>
            <c:if test="${postPage.totalPage > 1}">
                <li class="active"><%=_f("Page {0} ", ((Pageable) request.getAttribute("postPage")).getCurrentPage())%>
                </li>
            </c:if>
        </ol>
        <div class="content-wrap row">
            <main id="page-main" class="col-sm-9">
                <div class="main-content">
                    <%--@elvariable id="postPage" type="com.youthlin.blog.model.bo.Page"--%>
                    <%--@elvariable id="post" type="com.youthlin.blog.model.po.Post"--%>
                    <c:forEach items="${postPage.list}" var="post">
                        <article id="post-${post.postId}"
                                 class="article article-${post.postId} border-ccc margin-padding-p1">
                            <header class="post-meta post-header">
                                <h3><a href="<c:url value="/${post.postId}"/>">${post.postTitle}</a></h3>
                                <span class="meta-info meta-info-date">
                                    <span class="glyphicon glyphicon-time"
                                          aria-level=<%=__("\"Published Date:\"")%> aria-hidden="true"></span>
                                    <a class="meta-link" href="<c:url value="/${post.postId}"/>">
                                        <time datetime="${post.postDate}"
                                              title="<fmt:formatDate value="${post.postDate}" pattern="YYYY-MM-dd HH:mm"/>">
                                    <fmt:formatDate value="${post.postDate}" pattern="YYYY-MM-dd"/>
                                    </time>
                                </a>
                                </span>
                                <span class="meta-info meta-info-comment">
                                    <span class="glyphicon glyphicon-comment"
                                          aria-level=<%=__("\"Comment Count:\"")%> aria-hidden="true"></span>
                                    <a href="<c:url value="/${post.postId}#comments"/>">${post.commentCount}</a>
                                </span>
                                <span class="meta-info meta-info-category">
                                    <span class="glyphicon glyphicon-folder-open"
                                          aria-level=<%=__("\"Category:\"")%> aria-hidden="true"></span>
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
                                          aria-level=<%=__("\"Tags:\"")%> aria-hidden="true"></span>
                                <c:forEach items="${taxonomyMap[post.postId]}" var="taxonomyCat">
                                    <c:if test="${taxonomyCat.taxonomy eq 'tag'}">
                                        <a class="meta-link" href="<c:url value="/tag/${taxonomyCat.slug}"/>">
                                                ${taxonomyCat.name}</a>
                                    </c:if>
                                </c:forEach>
                                </span>

                            </header>
                            <div class="post-content">
                                <c:choose>
                                    <c:when test="${not empty post.postExcerpt}">${post.postExcerpt}</c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${post.postContent.length() > 300}">
                                                ${post.postContent.substring(0,300)}
                                            </c:when>
                                            <c:otherwise>${post.postContent}</c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <footer class="post-meta post-footer">
                                <a href="<c:url value="/${post.postId}"/>"><%=__("Read More")%></a>
                            </footer>
                        </article>
                    </c:forEach>
                </div>
                <%-- paging --%>
                <c:if test="${postPage.totalPage > 1}">
                    <nav aria-label="Navigation">
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
