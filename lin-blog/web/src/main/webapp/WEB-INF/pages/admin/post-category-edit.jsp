<%--@elvariable id="category" type="com.youthlin.blog.model.bo.Category"--%>
<%--@elvariable id="categoryList" type="java.util.List"--%>
<%--@elvariable id="item" type="com.youthlin.blog.model.bo.Category"--%>
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
<h1><%=__("Edit Category")%></h1>
<%--@elvariable id="error" type="java.lang.String"--%>
<c:if test="${not empty error}">
    <div class="error">${error}</div>
</c:if>
<c:if test="${empty error}">
    <form action="<c:url value="/admin/post/category/edit"/>" method="post" class="form-horizontal">
        <input type="hidden" name="id" value="${category.taxonomyId}">
        <div class="form-group">
            <label for="name" class="col-sm-2 control-label"><%=__("Name:")%></label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="name" name="name" value="${category.name}">
                <span class="help-block"><%=__("This will be show on the site.")%></span>
            </div>
        </div>
        <div class="form-group">
            <label for="slug" class="col-sm-2 control-label"><%=__("Slug:")%></label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="slug" name="slug" value="${category.slug}">
                <span class="help-block"><%=__("Slug is used to show at URL. It only contains alphabetic or dash('-').")%></span>
            </div>
        </div>

        <div class="form-group">
            <label for="parent" class="col-sm-2 control-label"><%=__("Parent:")%></label>
            <div class="col-sm-10">
                <select class="form-control" id="parent" name="parent">
                    <option selected value="0"><%=__("None")%></option>
                    <c:forEach items="${categoryList}" var="item">
                        <c:if test="${item.taxonomyId ne category.taxonomyId}">
                            <c:set var="select" value=""/>
                            <c:if test="${item.taxonomyId eq category.parent}">
                                <c:set var="select" value="selected"/>
                            </c:if>
                            <option value="${item.taxonomyId}" ${select}>${item.name}</option>
                        </c:if>
                    </c:forEach>
                </select>
                <span class="help-block"><%=__("Unlike tag, category can have a parent category.")%></span>
            </div>
        </div>
        <div class="form-group">
            <label for="description" class="col-sm-2 control-label"><%=__("Description:")%></label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="description" name="description"
                       value="${category.description}">
                <span class="help-block"><%=__("And you may write some description about this category...")%></span>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-primary"><%=__("Update")%></button>
            </div>
        </div>
    </form>
</c:if>
<%@ include file="/WEB-INF/pages/common/admin/footer.jsp" %>
