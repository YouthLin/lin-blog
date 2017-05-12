<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="status" type="java.lang.String"--%>
<%--@elvariable id="categoryList" type="java.util.List"--%>
<%--@elvariable id="category" type="com.youthlin.blog.model.bo.Category"--%>
<%--@elvariable id="postPage" type="com.youthlin.blog.model.bo.Pageable"--%>
<%--@elvariable id="post" type="com.youthlin.blog.model.po.Post"--%>
<%@ page import="static com.youthlin.utils.i18n.Translation.__" %><%--
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
    $(".menu-parent-post").addClass("active").click();
    $(".menu-item-all-post").addClass("active");
    $('#a-${status}').css('font-weight', 'bold').css('color', '#000').addClass("btn-info");
    $('#datetimepicker').datetimepicker({
        format: 'YYYY-MM',
        dayViewHeaderFormat: 'YYYY-MM',
        locale: 'zh-cn',
        showClear: true
    });
});</script>
<h1><%=__("All Post")%></h1>
<div class="status-selector margin-top-bottom-p1">
    <%--@elvariable id="allCount" type="java.lang.Long"--%>
    <%--@elvariable id="publishedCount" type="java.lang.Long"--%>
    <%--@elvariable id="allCoudraftCountnt" type="java.lang.Long"--%>
    <%--@elvariable id="draftCount" type="java.lang.Long"--%>
    <%--@elvariable id="pendingCount" type="java.lang.Long"--%>
    <%--@elvariable id="trashCount" type="java.lang.Long"--%>
    <div class="btn-group" role="group" aria-label="...">
        <a class="btn btn-default" id="a-all" href="<c:url value="/admin/post/all"/>">
            <%=__("All")%><span class="badge">${allCount}</span></a>
        <a class="btn btn-default" id="a-published" href="<c:url value="/admin/post/published"/>">
            <%=__("Published")%><span class="badge">${publishedCount}</span></a>
        <%-- 暂时没有这个功能 --%>
        <a class="btn btn-default sr-only" id="a-pending" href="<c:url value="/admin/post/pending"/>">
            <%=__("Pending")%><span class="badge">${pendingCount}</span></a>
        <a class="btn btn-default" id="a-draft" href="<c:url value="/admin/post/draft"/>">
            <%=__("Draft")%><span class="badge">${draftCount}</span></a>
        <a class="btn btn-default" id="a-trash" href="<c:url value="/admin/post/trash"/>">
            <%=__("Trash")%><span class="badge">${trashCount}</span></a>
    </div>
</div>
<form action="" method="get" class="form-inline margin-top-bottom-p1">
    <div class="form-group">
        <label class="sr-only" for="post-date"><%=__("Publish Datetime:")%></label>
        <div class="input-group">
            <div class='input-group date' id="datetimepicker">
                <c:set var="dateValue" value=""/>
                <%--@elvariable id="date" type="java.lang.String"--%>
                <c:if test="${not empty date}">
                    <c:set var="dateValue" value="${date}"/>
                </c:if>
                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                <input id="post-date" type='text' class="form-control" name="date"
                       value="${date}" placeholder=<%=__("\"All Date\"")%>/>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label class="sr-only" for="select-category"><%=__("Select Category:")%></label>
        <select class="form-control" id="select-category" name="category">
            <option value="0"><%=__("All Category")%></option>
            <c:forEach items="${categoryList}" var="category">
                <c:set var="select" value=""/>
                <%--@elvariable id="categoryId" type="java.lang.String"--%>
                <c:if test="${category.taxonomyId eq categoryId}">
                    <c:set var="select" value="selected"/>
                </c:if>
                <option ${select} value="${category.taxonomyId}">${category.name}</option>
            </c:forEach>
        </select>
    </div>
    <button type="submit" class="btn btn-default"><%=__("Filter")%></button>
</form>
<div class="table-responsive">
    <table class="table table-striped table-hover border-ccc">
        <thead>
        <tr>
            <th>
                <label class="no-bottom-margin">
                    <span class="sr-only"><%=__("Select All")%></span>
                    <input type="checkbox">
                </label>
            </th>
            <th><%=__("Title")%></th>
            <th><%=__("Author")%></th>
            <th><%=__("Category")%></th>
            <th><%=__("Tag")%></th>
            <th title="<%=__("Comment Count")%>"><span class="glyphicon glyphicon-comment" aria-hidden="true"></span>
                <span class="sr-only"><%=__("Comment Count")%></span></th>
            <th><%=__("Date")%></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${postPage.list}" var="post">
            <%--@elvariable id="queryString" type="java.lang.String"--%>
            <%--@elvariable id="authorMap" type="java.util.Map"--%>
            <tr>
                <td>
                    <label>
                        <span class="sr-only"><%=__("Select")%></span>
                        <input type="checkbox" name="ids" value="${post.postId}">
                    </label>
                </td>
                <td><strong>${post.postTitle}</strong><br>
                <span class="operation operation-${post.postId}">
                    <c:choose>
                        <c:when test="${status eq 'trash'}">
                            <a href="<c:url value="#"/>"><%=__("UnTrash")%></a> |
                            <a class="text-danger" href="#"><%=__("Delete")%></a> |
                        </c:when>
                        <c:otherwise>
                            <a href="<c:url value="/admin/post/edit?postId=${post.postId}"/>"><%=__("Edit")%></a> |
                            <a class="text-danger" href="#"><%=__("Move To Trash")%></a> |
                        </c:otherwise>
                    </c:choose>
                    <a href="#"><%=__("View")%></a>
                </span></td>
                <td><a href="?${queryString}&author=${post.postAuthorId}">${authorMap[post.postAuthorId]}</a></td>
                <td>
                    <%--@elvariable id="taxonomyMap" type="java.util.Map"--%>
                    <%--@elvariable id="taxonomyCat" type="com.youthlin.blog.model.po.Taxonomy"--%>
                    <c:forEach items="${taxonomyMap[post.postId]}" var="taxonomyCat">
                        <c:if test="${taxonomyCat.taxonomy eq 'category'}">
                            <a href="?category=${taxonomyCat.taxonomyId}">
                                <span class="label label-default">${taxonomyCat.name}</span></a>
                        </c:if>
                    </c:forEach>
                </td>
                <td>
                    <%--@elvariable id="tag" type="com.youthlin.blog.model.po.Taxonomy"--%>
                    <c:forEach items="${taxonomyMap[post.postId]}" var="tag">
                        <c:if test="${tag.taxonomy eq 'tag'}">
                            <a href="?tag=${tag.name}"><span class="label label-default">${tag.name}</span></a>
                        </c:if>
                    </c:forEach>
                </td>
                <td><span class="label label-default no-padding">
                        <span class="sr-only"><%=__("Comment Count:")%></span>
                        <span class="badge">${post.commentCount}</span>
                </span></td>
                <td>
                    <c:choose>
                        <c:when test="${post.postStatus.code eq 0}">
                            <%=__("Published")%><br>
                            <abbr title="<fmt:formatDate value="${post.postDate}" pattern="YYYY-MM-dd HH:mm:ss"/>">
                                <fmt:formatDate value="${post.postDate}" pattern="YYYY-MM-dd"/></abbr>
                        </c:when>
                        <c:otherwise>
                            <%=__("Last Modified")%><br>
                            <abbr title="<fmt:formatDate value="${post.postModified}" pattern="YYYY-MM-dd HH:mm:ss"/>">
                                <fmt:formatDate value="${post.postModified}" pattern="YYYY-MM-dd"/></abbr>
                        </c:otherwise>
                    </c:choose>
                    </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="/WEB-INF/pages/common/admin/footer.jsp" %>
