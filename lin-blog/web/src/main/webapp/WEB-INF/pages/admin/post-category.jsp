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
    $(".menu-item-category").addClass("active");
});</script>
<h1><%=__("Category")%></h1>
<div>
    <div class="add-category col-sm-3">
        <form action="">
            <h4><%=__("Add Category")%></h4>
            <div class="form-group">
                <label for="category-name"><%=__("Name:")%></label>
                <input type="text" class="form-control" id="category-name">
                <span class="help-block"><%=__("This will be show on the site.")%></span>
            </div>
            <div class="form-group">
                <label for="category-slug"><%=__("Slug:")%></label>
                <input type="text" class="form-control" id="category-slug">
                <span class="help-block"><%=__("Slug is used to show at URL. It only contains alphabetic or dash('-').")%></span>
            </div>
            <div class="form-group">
                <label for="category-parent"><%=__("Parent:")%></label>
                <input type="text" class="form-control" id="category-parent">
                <span class="help-block"><%=__("Unlike tag, category can have a parent category.")%></span>
            </div>
            <div class="form-group">
                <label for="category-description"><%=__("Description:")%></label>
                <input type="text" class="form-control" id="category-description">
                <span class="help-block"><%=__("And you may write some description about this category...")%></span>
            </div>
            <button class="btn btn-primary"><%=__("Add Category")%></button>
        </form>
    </div>
    <div class="all-category col-sm-9">
        <div class="table-responsive">
            <table class="table table-striped table-hover">
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
                    <th><%=__("Operation")%></th>
                </tr>
                </thead>
                <tbody>
                <%--@elvariable id="categoryList" type="java.util.List"--%>
                <%--@elvariable id="category" type="com.youthlin.blog.model.bo.Category"--%>
                <c:forEach items="${categoryList}" var="category">
                    <tr>
                        <td><label><span class="sr-only"><%=__("Select")%></span>
                            <input type="checkbox" name="" id="select-${category.taxonomyId}">
                        </label></td>
                        <td>${category.name}</td>
                        <td>${category.slug}</td>
                        <td>${category.description}</td>
                        <td>${category.count}</td>
                        <td></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/pages/common/admin/footer.jsp" %>
