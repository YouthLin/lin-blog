<%@ taglib prefix="g" uri="http://youthlin.com/linblog/tag/comment" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="static com.youthlin.utils.i18n.Translation.__" %>
<%@ page import="com.youthlin.blog.model.po.Comment" %>
<%@ page import="com.youthlin.blog.model.bo.Page" %>
<%--
  Created by IntelliJ IDEA.
  User: lin
  Date: 17-5-6
  Time: 下午9:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/pages/common/admin/header.jsp" %>
<script>$(document).ready(function () {
    $(".menu-parent-comment").addClass("active").click();
    $(".menu-item-comment-my").addClass("active");
});</script>
<h1><%=__("My Comments")%></h1>
<div class="table-responsive">
    <%--@elvariable id="commentPage" type="com.youthlin.blog.model.bo.Page"--%>
    <c:if test="${not empty commentPage and commentPage.totalPage>0}">
        <div class="border-pager pull-right">
            <%
                Page<Comment> commentPage = (Page<Comment>) request.getAttribute("commentPage");
            %>
            <c:set var="totalRow" value="${commentPage.totalRow}"/>
            <span class="table-meta table-meta-count"><%=_f("{0} Items", commentPage.getTotalRow())%></span>
            <c:set var="disabled" value=""/>
            <c:if test="${commentPage.currentPage==1}">
                <c:set var="disabled" value="disabled"/>
            </c:if>
            <a class="btn btn-sm btn-default ${disabled}" href="?page=1">
                <span class="sr-only"><%=__("First page")%></span>&laquo;</a>
            <a class="btn btn-sm btn-default ${disabled}" href="?page=${commentPage.currentPage-1}">
                <span class="sr-only"><%=__("Previous page")%></span>&lsaquo;</a>
            <form action="" class="form-inline" style="display: inline-block;">
                <label><input type="text" name="page" class="form-control" value="${commentPage.currentPage}"
                              style="width: 34px;"> / ${commentPage.totalPage}</label>
            </form>
            <c:set var="disabled" value=""/>
            <c:if test="${commentPage.currentPage==commentPage.totalPage}">
                <c:set var="disabled" value="disabled"/>
            </c:if>
            <a class="btn btn-sm btn-default ${disabled}" href="?page=${commentPage.currentPage+1}">
                 <span class="sr-only"><%=__("Next page")%></span>&rsaquo;</a>
            <a class="btn btn-sm btn-default ${disabled}" href="?page=${commentPage.totalPage}">
                 <span class="sr-only"><%=__("Last page")%></span>&raquo;</a>
        </div>
    </c:if>
    <table class="table table-striped table-hover border-ccc">
        <thead>
        <tr>
            <th>
                <label class="no-bottom-margin">
                    <span class="sr-only"><%=__("Select All")%></span>
                    <input type="checkbox">
                </label>
            </th>
            <th class="col-sm-2"><%=__("Author")%></th>
            <th class="col-sm-5"><%=__("Comment")%></th>
            <th class="col-sm-2"><%=__("Post")%></th>
            <th class="col-sm-2"><%=__("Date")%></th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <%--@elvariable id="commentPage" type="com.youthlin.blog.model.bo.Page"--%>
            <c:when test="${not empty commentPage and commentPage.list.size()>0}">
                <%--@elvariable id="comment" type="com.youthlin.blog.model.po.Comment"--%>
                <c:forEach items="${commentPage.list}" var="comment">
                    <c:set var="trClass" value=""/>
                    <c:choose>
                        <c:when test="${comment.commentStatus.code eq 1}">
                            <c:set var="trClass" value="warning"/>
                        </c:when>
                        <c:when test="${comment.commentStatus.code eq 2}">
                            <c:set var="trClass" value="danger"/>
                        </c:when>
                        <c:when test="${comment.commentStatus.code eq 3}">
                            <c:set var="trClass" value="danger"/>
                        </c:when>
                    </c:choose>
                    <tr class="${trClass}">
                         <td>
                             <label><span class="sr-only"><%=__("Select")%></span>
                            <input type="checkbox" name="ids" value="${comment.commentId}"></label>
                         </td>
                        <td>
                            <img src="${g:img(comment.commentAuthorEmail)}" alt="Avatar" height="40" width="40">
                            <span class="comment-info comment-info-author">${comment.commentAuthor}</span><br>
                            <span class="comment-info comment-info-url">${comment.commentAuthorUrl}</span><br>
                            <span class="comment-info comment-info-email">${comment.commentAuthorEmail}</span><br>
                            <span class="comment-info comment-info-ip">${comment.commentAuthorIp}</span><br>
                        </td>
                        <td class="relative">
                            <div class="comment-info comment-info-content">${comment.commentContent}</div>
                            <div class="comment-info comment-info-action absolute-bottom">
                                <a href="<c:url value="/admin/comment/edit?id=${comment.commentId}"/>"><%=__("Edit")%></a>
                                | <a href="<c:url value="/post/${comment.commentPostId}#comment-${comment.commentId}"/>"
                                     target="_blank"><%=__("View")%></a>
                                | <span>${g:__(comment.commentStatus.name())}</span>
                            </div>
                        </td>
                        <td>
                            <%--@elvariable id="postMap" type="java.util.Map"--%>
                            <%--@elvariable id="post" type="com.youthlin.blog.model.po.Post"--%>
                            <a href="<c:url value="/post/${comment.commentPostId}"/>" target="_blank">
                                <c:set var="post" value="${postMap[comment.commentPostId]}"/>
                                ${post.postTitle}
                            </a>
                        </td>
                        <td><fmt:formatDate value="${comment.commentDate}" pattern="YYYY-MM-dd HH:mm"/></td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr><td colspan="5"><%=__("No comments")%></td></tr>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
    <c:if test="${not empty commentPage and commentPage.totalPage>0}">
        <div class="border-pager pull-right">
            <%
                Page<Comment> commentPage = (Page<Comment>) request.getAttribute("commentPage");
            %>
            <c:set var="totalRow" value="${commentPage.totalRow}"/>
            <span class="table-meta table-meta-count"><%=_f("{0} Items", commentPage.getTotalRow())%></span>
            <c:set var="disabled" value=""/>
            <c:if test="${commentPage.currentPage==1}">
                <c:set var="disabled" value="disabled"/>
            </c:if>
            <a class="btn btn-sm btn-default ${disabled}" href="?page=1">
                <span class="sr-only"><%=__("First page")%></span>&laquo;</a>
            <a class="btn btn-sm btn-default ${disabled}" href="?page=${commentPage.currentPage-1}">
                <span class="sr-only"><%=__("Previous page")%></span>&lsaquo;</a>
            <form action="" class="form-inline" style="display: inline-block;">
                <label><input type="text" name="page" class="form-control" value="${commentPage.currentPage}"
                              style="width: 34px;"> / ${commentPage.totalPage}</label>
            </form>
            <c:set var="disabled" value=""/>
            <c:if test="${commentPage.currentPage==commentPage.totalPage}">
                <c:set var="disabled" value="disabled"/>
            </c:if>
            <a class="btn btn-sm btn-default ${disabled}" href="?page=${commentPage.currentPage+1}">
                 <span class="sr-only"><%=__("Next page")%></span>&rsaquo;</a>
            <a class="btn btn-sm btn-default ${disabled}" href="?page=${commentPage.totalPage}">
                 <span class="sr-only"><%=__("Last page")%></span>&raquo;</a>
        </div>
    </c:if>

</div>
<%@ include file="/WEB-INF/pages/common/admin/footer.jsp" %>
