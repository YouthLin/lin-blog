<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cmt" uri="http://youthlin.com/linblog/tag/comment" %>
<%@ page import="static com.youthlin.utils.i18n.Translation.__" %>
<%@ page import="static com.youthlin.utils.i18n.Translation._x" %>
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
    <title>${post.postTitle}</title>
</head>
<body>
<div id="wrap">
    <%--@elvariable id="previous" type="com.youthlin.blog.model.po.Post"--%>
    <%--@elvariable id="next" type="com.youthlin.blog.model.po.Post"--%>
    <%--@elvariable id="post" type="com.youthlin.blog.model.po.Post"--%>
    <%--@elvariable id="topLevelCommentNodeList" type="java.util.List"--%>
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
                             class="article article-${post.postId} border-ccc margin-padding-p1 well">
                        <header class="post-meta post-header">
                            <span class="meta-info meta-info-date">
                                    <span class="glyphicon glyphicon-time"
                                          aria-level=<%=__("\"Published Date:\"")%> aria-hidden="true"></span>
                                    <a class="meta-link" href="<c:url value="/post/${post.postId}"/>">
                                        <time datetime="${post.postDate}"
                                              title="<fmt:formatDate value="${post.postDate}" pattern="YYYY-MM-dd HH:mm"/>">
                                    <fmt:formatDate value="${post.postDate}" pattern="YYYY-MM-dd"/>
                                    </time>
                                </a>
                                </span>
                            <span class="meta-info meta-info-comment">
                                    <span class="glyphicon glyphicon-comment"
                                          aria-level=<%=__("\"Comment Count:\"")%> aria-hidden="true"></span>
                                    <a href="#comments">${post.commentCount}</a>
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
                            <h3><a href="<c:url value="/post/${post.postId}"/>">${post.postTitle}</a></h3>
                        </header>
                        <div class="post-content"> ${post.postContent} </div>

                    </article>
                    <div class="border-ccc margin-padding-p1 well" id="post-nav">
                        <nav aria-label="Navigation">
                            <ul class="pager">
                                <c:choose>
                                    <c:when test="${previous != null}">
                                        <li class="previous"><span class="sr-only"><%=__("Previous post:")%></span>
                                            <a href="<c:url value="/post/${previous.postId}"/>">
                                            <span aria-hidden="true">&larr;&nbsp;</span>${previous.postTitle}</a>
                                        </li>
                                    </c:when>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${next != null}">
                                        <li class="next"><span class="sr-only"><%=__("Next post:")%></span>
                                            <a href="<c:url value="/post/${next.postId}"/>">
                                            ${next.postTitle}<span aria-hidden="true">&nbsp;&rarr;</span></a>
                                        </li>
                                    </c:when>
                                </c:choose>
                            </ul>
                        </nav>
                    </div>
                    <cmt:comments post="${post}" topLevelCommentNodeList="${topLevelCommentNodeList}"/>
                    <div id="respond">
                        <c:choose>
                            <c:when test="${post.commentOpen}">
                                <form class="form-horizontal border-ccc margin-padding-p1 well"
                                      id="commentform" method="post">
                                    <h4 class="margin-padding-p1"><%=__("Leave your comment")%>
                                    <small><a href="javascript:cancel();" class="hide" id="to-cancel">
                                        <%=_x("Cancel", "cancel response.")%></a></small></h4>
                                    <div class="form-group">
                                        <label for="author" class="col-sm-2 control-label"><%=__("Name")%>
                                            <span class="star">*</span> </label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" id="author" name="author"
                                                   required placeholder=<%=__("\"Display Name\"")%>>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="email" class="col-sm-2 control-label"><%=__("Email")%>
                                            <span class="star">*</span> </label>
                                        <div class="col-sm-10">
                                            <input type="email" class="form-control" id="email" name="email"
                                                   required placeholder=<%=__("\"Email will never public\"")%>>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="url" class="col-sm-2 control-label"><%=__("URL")%> &nbsp;</label>
                                        <div class="col-sm-10">
                                            <input type="url" class="form-control" id="url" name="url"
                                                   placeholder=<%=__("\"URL is optional\"")%>>
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
                                            <input type="hidden" name="post" value="${post.postId}">
                                            <input type="hidden" name="to" value="0" id="to">
                                            <button type="submit" class="btn btn-default">
                                                <%=__("Post Comment")%></button>
                                        </div>
                                    </div>
                                </form>
                                <script>
                                    var $to = $('#to');
                                    var $respond = $('#respond');
                                    var $commentform = $('#commentform');
                                    var $cancel = $('#to-cancel');
                                    function cancel() {
                                        $respond.html($commentform);
                                        $to.val(0);
                                        $cancel.addClass('hide');
                                    }
                                    $(document).ready(function () {
                                        $('.replay').click(function () {
                                            var parent = $(this).data('to');
                                            var $toArticle = $('#comment-article-' + parent);
                                            $respond.html('');
                                            $toArticle.append($commentform);
                                            $cancel.removeClass('hide');
                                            $to.val(parent);
                                        });
                                    });
                                </script>
                            </c:when>
                            <c:otherwise>
                                <h4 class="margin-padding-p1 well"><%=__("Comment are closed")%></h4>
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
