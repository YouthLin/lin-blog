<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tr" uri="http://youthlin.com/linblog/tag/comment" %>
<%@ taglib prefix="g" uri="http://youthlin.com/linblog/tag/comment" %>
<%@ page import="static com.youthlin.utils.i18n.Translation.__" %>
<%@ page import="com.youthlin.blog.model.po.User" %>
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
    $(".menu-parent-users").addClass("active").click();
    $(".menu-item-all-user").addClass("active");
});</script>
<h1><%=__("All Users")%></h1>
<div class="table-responsive">
    <%--@elvariable id="userPage" type="com.youthlin.blog.model.bo.Page"--%>
    <c:if test="${not empty userPage and userPage.totalPage>1}">
        <div class="border-pager pull-right">
            <%
                Page<User> userPage = (Page<User>) request.getAttribute("userPage");
            %>
            <c:set var="totalRow" value="${userPage.totalRow}"/>
            <span class="table-meta table-meta-count"><%=_f("{0} Items", userPage.getTotalRow())%></span>
            <c:set var="disabled" value=""/>
            <c:if test="${userPage.currentPage==1}">
                <c:set var="disabled" value="disabled"/>
            </c:if>
            <a class="btn btn-sm btn-default ${disabled}" href="?page=1">
                <span class="sr-only"><%=__("First page")%></span>&laquo;</a>
            <a class="btn btn-sm btn-default ${disabled}" href="?page=${userPage.currentPage-1}">
                <span class="sr-only"><%=__("Previous page")%></span>&lsaquo;</a>
            <form action="" class="form-inline" style="display: inline-block;">
                <label><input type="text" name="page" class="form-control" value="${userPage.currentPage}"
                              style="width: 34px;"> / ${userPage.totalPage}</label>
            </form>
            <c:set var="disabled" value=""/>
            <c:if test="${userPage.currentPage==userPage.totalPage}">
                <c:set var="disabled" value="disabled"/>
            </c:if>
            <a class="btn btn-sm btn-default ${disabled}" href="?page=${userPage.currentPage+1}">
                 <span class="sr-only"><%=__("Next page")%></span>&rsaquo;</a>
            <a class="btn btn-sm btn-default ${disabled}" href="?page=${userPage.totalPage}">
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
            <th><%=__("Username")%></th>
            <th><%=__("Nickname")%></th>
            <th><%=__("Email")%></th>
            <th><%=__("Role")%></th>
            <th><%=__("Register date")%></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${userPage.list}" var="aUser">
            <tr>
                <td>
                    <label>
                        <span class="sr-only"><%=__("Select")%></span>
                        <input type="checkbox" name="ids" value="${aUser.userId}">
                    </label>
                </td>
                <td>
                    <img src="${g:img(aUser.userEmail)}" alt="Avatar" width="40" height="40">
                        ${aUser.userLogin}&nbsp;
                    <a href="<c:url value="/admin/users/edit?id=${aUser.userId}"/>" class="pull-right">
                        <%=__("Edit")%></a></td>
                <td>
                    <c:choose>
                        <c:when test="${not empty aUser.userUrl}">
                            <a href="${aUser.userUrl}" target="_blank">${aUser.displayName}</a>
                        </c:when>
                        <c:otherwise>${aUser.displayName}</c:otherwise>
                    </c:choose>
                      </td>
                <td>${aUser.userEmail}</td>
                <td>
            <%--@elvariable id="roleMetaMap" type="java.util.Map"--%>
                    <span class="split">${tr:__(roleMetaMap[aUser.userId].metaValue)}</span>
               </td>
               <td><fmt:formatDate value="${aUser.userRegistered}" pattern="YYYY-MM-dd HH:mm"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <%--@elvariable id="userPage" type="com.youthlin.blog.model.bo.Page"--%>
    <c:if test="${not empty userPage and userPage.totalPage>1}">
        <div class="border-pager pull-right">
            <%
                Page<User> userPage = (Page<User>) request.getAttribute("userPage");
            %>
            <c:set var="totalRow" value="${userPage.totalRow}"/>
            <span class="table-meta table-meta-count"><%=_f("{0} Items", userPage.getTotalRow())%></span>
            <c:set var="disabled" value=""/>
            <c:if test="${userPage.currentPage==1}">
                <c:set var="disabled" value="disabled"/>
            </c:if>
            <a class="btn btn-sm btn-default ${disabled}" href="?page=1">
                <span class="sr-only"><%=__("First page")%></span>&laquo;</a>
            <a class="btn btn-sm btn-default ${disabled}" href="?page=${userPage.currentPage-1}">
                <span class="sr-only"><%=__("Previous page")%></span>&lsaquo;</a>
            <form action="" class="form-inline" style="display: inline-block;">
                <label><input type="text" name="page" class="form-control" value="${userPage.currentPage}"
                              style="width: 34px;"> / ${userPage.totalPage}</label>
            </form>
            <c:set var="disabled" value=""/>
            <c:if test="${userPage.currentPage==userPage.totalPage}">
                <c:set var="disabled" value="disabled"/>
            </c:if>
            <a class="btn btn-sm btn-default ${disabled}" href="?page=${userPage.currentPage+1}">
                 <span class="sr-only"><%=__("Next page")%></span>&rsaquo;</a>
            <a class="btn btn-sm btn-default ${disabled}" href="?page=${userPage.totalPage}">
                 <span class="sr-only"><%=__("Last page")%></span>&raquo;</a>
        </div>
    </c:if>

</div>
<%@ include file="/WEB-INF/pages/common/admin/footer.jsp" %>
