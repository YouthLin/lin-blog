<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="static com.youthlin.utils.i18n.Translation.__" %>
<%--
  Created by IntelliJ IDEA.
  User: youthlin.chen
  Date: 2017/5/12
  Time: 22:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="common/head.jsp" %>
    <%--@elvariable id="title" type="java.lang.String"--%>
    <title>${title}</title>
</head>
<body>
<div id="wrap">
    <%--@elvariable id="post" type="com.youthlin.blog.model.po.Post"--%>
    <div class="page container-fluid">
        <%@ include file="include/header.jsp" %>
        <ol class="breadcrumb">
            <li><a href="<c:url value="/"/>"><%=__("Home")%></a></li>
            <li class="active">${post.postTitle}</li>
        </ol>
        <div class="content-wrap row">
            <main id="page-main" class="col-sm-9">
                <div class="main-content">
                    <article id="post-${post.postId}"
                             class="article article-${post.postId} border-ccc margin-padding-p1">
                        <header class="post-meta post-header">
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
                                </a>
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
                            <h3><a href="<c:url value="/${post.postId}"/>">${post.postTitle}</a></h3>
                        </header>
                        <div class="post-content"> ${post.postContent} </div>

                    </article>
                    <div id="comments" class="border-ccc margin-padding-p1">
                        <h4 class="margin-padding-p1">${post.commentCount} Comments On 《${post.postTitle}》</h4>
                        <ol class="media-list comments-list">
                            <li class="media border-ccc margin-padding-p1 comment-item comment-1 depth-1"
                                id="comment-li-1">
                                <div class="media-left">
                                    <img class="media-object"
                                         src="http://2.gravatar.com/avatar/89081eaa9951596df4914597cdbb1204?s=40&d=monsterid&r=g"
                                         alt="Gravatar">
                                </div>
                                <div class="media-body">
                                    <article id="comment-article-1">
                                        <header class="media-heading comment-meta comment-meta-header">
                                            <div><a href="#">YouthLin</a><span class="sr-only">Says:</span></div>
                                            <div>
                                                <time><a href="#">2017-05-13 11:05:12</a></time>
                                            </div>
                                        </header>
                                        <div class="comment-cotent">
                                            Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque
                                            ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at,
                                            tempus viverra turpis.
                                        </div>
                                        <footer class="comment-meta comment-meta-footer">
                                            <span><a href="#">Reply</a></span>
                                        </footer>
                                    </article>
                                    <ol class="media-list comments-list comments=list-children">
                                        <li class="media border-ccc margin-padding-p1 comment-item comment-1 depth-1"
                                            id="comment-li-3">
                                            <div class="media-left">
                                                <img class="media-object"
                                                     src="http://2.gravatar.com/avatar/89081eaa9951596df4914597cdbb1204?s=40&d=monsterid&r=g"
                                                     alt="Gravatar">
                                            </div>
                                            <div class="media-body">
                                                <article id="comment-article-3">
                                                    <header class="media-heading comment-meta comment-meta-header">
                                                        <div><a href="#">YouthLin</a><span class="sr-only">Says:</span>
                                                        </div>
                                                        <div>
                                                            <time><a href="#">2017-05-13 11:05:12</a></time>
                                                        </div>
                                                    </header>
                                                    <div class="comment-cotent">
                                                        Cras sit amet nibh libero, in gravida nulla. Nulla vel metus
                                                        scelerisque
                                                        ante sollicitudin commodo. Cras purus odio, vestibulum in
                                                        vulputate at,
                                                        tempus viverra turpis.
                                                    </div>
                                                    <footer class="comment-meta comment-meta-footer">
                                                        <span><a href="#">Reply</a></span>
                                                    </footer>
                                                </article>
                                            </div>
                                        </li>
                                        <li class="media border-ccc margin-padding-p1 comment-item comment-1 depth-1"
                                            id="comment-li-4">
                                            <div class="media-left">
                                                <img class="media-object"
                                                     src="http://2.gravatar.com/avatar/89081eaa9951596df4914597cdbb1204?s=40&d=monsterid&r=g"
                                                     alt="Gravatar">
                                            </div>
                                            <div class="media-body">
                                                <article id="comment-article-4">
                                                    <header class="media-heading comment-meta comment-meta-header">
                                                        <div><a href="#">YouthLin</a><span class="sr-only">Says:</span>
                                                        </div>
                                                        <div>
                                                            <time><a href="#">2017-05-13 11:05:12</a></time>
                                                        </div>
                                                    </header>
                                                    <div class="comment-cotent">
                                                        Cras sit amet nibh libero, in gravida nulla. Nulla vel metus
                                                        scelerisque
                                                        ante sollicitudin commodo. Cras purus odio, vestibulum in
                                                        vulputate at,
                                                        tempus viverra turpis.
                                                    </div>
                                                    <footer class="comment-meta comment-meta-footer">
                                                        <span><a href="#">Reply</a></span>
                                                    </footer>
                                                </article>
                                            </div>
                                        </li>
                                    </ol>
                                </div>
                            </li>
                            <li class="media border-ccc margin-padding-p1 comment-item comment-1 depth-1"
                                id="comment-li-2">
                                <div class="media-left">
                                    <img class="media-object"
                                         src="http://2.gravatar.com/avatar/89081eaa9951596df4914597cdbb1204?s=40&d=monsterid&r=g"
                                         alt="Gravatar">
                                </div>
                                <div class="media-body">
                                    <article id="comment-article-2">
                                        <header class="media-heading comment-meta comment-meta-header">
                                            <div><a href="#">YouthLin</a><span class="sr-only">Says:</span></div>
                                            <div>
                                                <time><a href="#">2017-05-13 11:05:12</a></time>
                                            </div>
                                        </header>
                                        <div class="comment-cotent">
                                            Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque
                                            ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at,
                                            tempus viverra turpis.
                                        </div>
                                        <footer class="comment-meta comment-meta-footer">
                                            <span><a href="#">Reply</a></span>
                                        </footer>
                                    </article>
                                </div>
                            </li>
                        </ol>
                    </div>
                    <div id="respond">
                        <c:choose>
                            <c:when test="${post.commentOpen}">
                                <form class="form-horizontal border-ccc margin-padding-p1" id="commentform">
                                    <h4><%=__("Leave your comment")%></h4>

                                    <div class="form-group">
                                        <label for="author" class="col-sm-2 control-label">Name
                                            <span class="star">*</span> </label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="author" name="author"
                                                   required placeholder="Name">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="email" class="col-sm-2 control-label">Email
                                            <span class="star">*</span> </label>
                                        <div class="col-sm-10">
                                            <input type="email" class="form-control" id="email" name="email"
                                                   required placeholder="Email will never public">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="url" class="col-sm-2 control-label">URL &nbsp;</label>
                                        <div class="col-sm-10">
                                            <input type="url" class="form-control" id="url" name="url"
                                                   placeholder="URL is optional.">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="content" class="col-sm-2 control-label">Content
                                            <span class="star">*</span> </label>
                                        <div class="col-sm-10">
                                                <textarea class="form-control" name="content" id="content"
                                                          required rows="10"></textarea>
                                        </div>
                                    </div>


                                    <div class="form-group">
                                        <div class="col-sm-offset-2 col-sm-10">
                                            <button type="submit" class="btn btn-default">Post Comment</button>
                                        </div>
                                    </div>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <%=__("Comment are closed.")%>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <%-- paging --%>
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
