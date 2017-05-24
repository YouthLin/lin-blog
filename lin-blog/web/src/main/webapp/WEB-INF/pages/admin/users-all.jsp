<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    $(".menu-parent-users").addClass("active").click();
    $(".menu-item-all-user").addClass("active");
});</script>
<h1><%=__("All Users")%></h1>
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
            <th><%=__("Username")%></th>
            <th><%=__("Nickname")%></th>
            <th><%=__("Email")%></th>
            <th><%=__("Role")%></th>
            <th><%=__("Register date")%></th>
        </tr>
        </thead>
        <tbody>
        <%--@elvariable id="allUser" type="java.util.List"--%>
        <%--@elvariable id="aUser" type="com.youthlin.blog.model.po.User"--%>
        <c:forEach items="${allUser}" var="aUser">
            <tr>
                <td>
                    <label>
                        <span class="sr-only"><%=__("Select")%></span>
                        <input type="checkbox" name="ids" value="${aUser.userId}">
                    </label>
                </td>
                <td>${aUser.userLogin}</td>
                <td>${aUser.displayName}</td>
                <td>${aUser.userEmail}</td>
                <td>
                <%--@elvariable id="allRole" type="java.util.List"--%>
                <%--@elvariable id="role" type="com.youthlin.blog.model.po.UserMeta"--%>
                <c:forEach items="${allRole}" var="role">
                    <c:if test="${role.userId eq aUser.userId}">
                        <span class="split">${role.metaValue}</span>
                    </c:if>
                </c:forEach>
               </td>
               <td><fmt:formatDate value="${aUser.userRegistered}" pattern="YYYY-MM-dd HH:mm"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="/WEB-INF/pages/common/admin/footer.jsp" %>
