<%@ page import="static com.youthlin.utils.i18n.Translation.__" %>
<%--@elvariable id="categoryList" type="java.util.List"--%>
<%--@elvariable id="category" type="com.youthlin.blog.model.bo.Category"--%>
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
    $(".menu-item-category").addClass("active");
});</script>
<h1><%=__("Category")%></h1>
<div>
    <div class="add-category col-sm-3">
        <%--@elvariable id="error" type="java.lang.String"--%>
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        <form action="<c:url value="/admin/post/category/add"/>" method="post">
            <h4><%=__("Add Category")%></h4>
            <div class="form-group">
                <label for="category-name"><%=__("Name:")%></label>
                <input type="text" class="form-control" id="category-name" name="name" required autofocus>
                <span class="help-block"><%=__("This will be show on the site.")%></span>
            </div>
            <div class="form-group">
                <label for="category-slug"><%=__("Slug:")%></label>
                <input type="text" class="form-control" id="category-slug" name="slug">
                <span class="help-block"><%=__("Slug is used to show at URL. It only contains alphabetic or dash('-').")%></span>
            </div>
            <div class="form-group">
                <label for="category-parent"><%=__("Parent:")%></label>
                <select class="form-control" id="category-parent" name="parent">
                    <option selected value="0"><%=__("None")%></option>
                    <c:forEach items="${categoryList}" var="category">
                        <option value="${category.taxonomyId}">${category.name}</option>
                    </c:forEach>
                </select>
                <span class="help-block"><%=__("Unlike tag, category can have a parent category.")%></span>
            </div>
            <div class="form-group">
                <label for="category-description"><%=__("Description:")%></label>
                <input type="text" class="form-control" id="category-description" name="description">
                <span class="help-block"><%=__("And you may write some description about this category...")%></span>
            </div>
            <button type="submit" class="btn btn-primary"><%=__("Add Category")%></button>
        </form>
    </div>
    <div class="all-category col-sm-9">
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
                    <th><%=__("Name")%></th>
                    <th><%=__("Slug")%></th>
                    <th><%=__("Description")%></th>
                    <th><%=__("Count")%></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${categoryList}" var="category">
                    <tr id="row-${category.taxonomyId}" class="action-row">
                        <td><c:if test="${category.taxonomyId ne 1}">
                            <label>
                                <span class="sr-only"><%=__("Select")%></span>
                                <input type="checkbox" name="selected" value="${category.taxonomyId}">
                            </label>
                        </c:if></td>
                        <td>
                            <strong>${category.name}</strong><br>
                            <span class="operation operation-${category.taxonomyId}">
                                <a href="<c:url value="/admin/post/category/edit?id=${category.taxonomyId}"/>"><%=__("Edit")%></a> |
                                <c:if test="${category.taxonomyId ne 1}">
                                <a href="<c:url value="/admin/post/category/delete?id=${category.taxonomyId}"/>"
                                   class="text-danger confirm"><%=__("Delete")%></a> |
                                </c:if>
                                <a href="<c:url value="/category/${category.slug}"/>"
                                   target="_blank"><%=__("View")%></a>
                            </span>&nbsp;
                        </td>
                        <td>${category.slug}</td>
                        <td>${category.description}</td>
                        <td>${category.count}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/pages/common/admin/footer.jsp" %>
