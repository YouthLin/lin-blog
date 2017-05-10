<%--@elvariable id="page" type="com.youthlin.blog.model.bo.Pageable"--%>
<%--@elvariable id="tag" type="com.youthlin.blog.model.bo.Tag"--%>
<%@ page import="static com.youthlin.utils.i18n.Translation.__" %>
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
    $(".menu-parent-post").addClass("active").click();
    $(".menu-item-tag").addClass("active");
});</script>
<h1><%=__("Tag")%></h1>
<div>
    <div class="add-tag col-sm-3">
        <%--@elvariable id="error" type="java.lang.String"--%>
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        <form action="<c:url value="/admin/post/tag/add"/>" method="post">
            <h4><%=__("Add Tag")%></h4>
            <div class="form-group">
                <label for="name"><%=__("Name:")%></label>
                <input type="text" class="form-control" id="name" name="name" required autofocus>
                <span class="help-block"><%=__("This will be show on the site.")%></span>
            </div>
            <div class="form-group">
                <label for="slug"><%=__("Slug:")%></label>
                <input type="text" class="form-control" id="slug" name="slug">
                <span class="help-block"><%=__("Slug is used to show at URL. It only contains alphabetic or dash('-').")%></span>
            </div>
            <div class="form-group">
                <label for="description"><%=__("Description:")%></label>
                <input type="text" class="form-control" id="description" name="description">
                <span class="help-block"><%=__("And you may write some description about this tag...")%></span>
            </div>
            <button type="submit" class="btn btn-primary"><%=__("Add Tag")%></button>
        </form>
    </div>
    <div class="all-tag col-sm-9">

        <table class="table table-striped table-hover border-ccc">
            <thead>
            <tr>
                    <th>
                        <label class="no-bottom-margin">
                            <span class="sr-only"><%=__("Select All")%></span>
                            <input type="checkbox">
                        </label>
                    </th>
                    <th><%=__("Name")%></th>
                    <th><%=__("Slug")%></th>
                    <th><%=__("Description")%></th>
                    <th><%=__("Count")%></th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.list}" var="tag">
                <tr id="row-${tag.taxonomyId}" class="action-row">
                    <td><label>
                        <span class="sr-only"><%=__("Select")%></span>
                        <input type="checkbox" name="selected" value="${tag.taxonomyId}">
                    </label></td>
                    <td>
                        <strong>${tag.name}</strong><br>
                        <span class="operation operation-${tag.taxonomyId}">
                                <a href="#"><%=__("Edit")%></a> |
                                <a href="#" class="text-danger confirm"><%=__("Delete")%></a> |
                                <a href="#"><%=__("View")%></a>
                            </span>
                    </td>
                    <td>${tag.slug}</td>
                    <td>${tag.description}</td>
                    <td>${tag.count}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="/WEB-INF/pages/common/admin/footer.jsp" %>
