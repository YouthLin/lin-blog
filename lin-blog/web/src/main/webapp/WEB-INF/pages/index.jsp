<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="static com.youthlin.utils.i18n.Translation.__" %><%--
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
        <div class="content-wrap row">
            <main id="page-main" class="col-sm-9">
                <div class="main-content">
                    <%--@elvariable id="postPage" type="com.youthlin.blog.model.bo.Page"--%>
                    <%--@elvariable id="post" type="com.youthlin.blog.model.po.Post"--%>
                    <c:forEach items="${postPage.list}" var="post">
                        <article class="article article-1 border-ccc margin-padding-p1">
                            <header class="post-meta post-header">
                                <h3><a href="#">${post.postTitle}</a></h3>
                                <span class="category">
                                    <span class="glyphicon glyphicon-folder-open"
                                          aria-level=<%=__("\"Category:\"")%> aria-hidden="true"></span>
<%--@elvariable id="taxonomyMap" type="java.util.Map"--%>
<%--@elvariable id="taxonomyCat" type="com.youthlin.blog.model.po.Taxonomy"--%>
                                <c:forEach items="${taxonomyMap[post.postId]}" var="taxonomyCat">
                                    <c:if test="${taxonomyCat.taxonomy eq 'category'}">
                                        <a href="<c:url value="/category/${taxonomyCat.name}"/>">${taxonomyCat.name}</a>
                                    </c:if>
                                </c:forEach>
                                </span>
                                <span class="tag">
                                    <span class="glyphicon glyphicon-tags"
                                          aria-level=<%=__("\"Tags:\"")%> aria-hidden="true"></span>
                                <c:forEach items="${taxonomyMap[post.postId]}" var="taxonomyCat">
                                    <c:if test="${taxonomyCat.taxonomy eq 'tag'}">
                                        <a href="<c:url value="/tag/${taxonomyCat.name}"/>">${taxonomyCat.name}</a>
                                    </c:if>
                                </c:forEach>
                                </span>
                                <span class="date">
                                    <span class="glyphicon glyphicon-time"
                                          aria-level=<%=__("\"Published Date:\"")%> aria-hidden="true"></span>
                                    <a href="#">
                                        <time datetime="${post.postDate}"
                                              title="<fmt:formatDate value="${post.postDate}" pattern="YYYY-MM-dd HH:mm"/>">
                                    <fmt:formatDate value="${post.postDate}" pattern="YYYY-MM-dd"/>
                                    </time>
                                </a>
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
                                <a href="#"><%=__("Read More")%></a>
                            </footer>
                        </article>
                    </c:forEach>
                </div>
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
