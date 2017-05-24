<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="static com.youthlin.utils.i18n.Translation.__" %>
<%@ page import="com.youthlin.blog.model.enums.Role" %><%--
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
    $(".menu-item-add-user").addClass("active");
});</script>
<form class="form-horizontal" id="profile-form" method="post">
    <h1><%=__("Add User")%></h1>
    <%--@elvariable id="msg" type="java.lang.String"--%>
    <%--@elvariable id="error" type="java.lang.String"--%>
    <c:if test="${not empty msg}">
        <div class="message">${msg}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    <div class="form-group">
        <label for="username" class="col-sm-2 control-label"><%=__("Username")%>&nbsp;<span
                class="star">*</span></label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="username" name="username" required>
            <span class="help-block"><%=__("Username can not change after user added.")%></span>
        </div>
    </div>
    <div class="form-group">
        <label for="email" class="col-sm-2 control-label"><%=__("Email")%>&nbsp;<span class="star">*</span></label>
        <div class="col-sm-10">
            <input type="email" class="form-control" id="email" name="email" required>
        </div>
    </div>
    <div class="form-group">
        <label for="pass" class="col-sm-2 control-label"><%=__("Password")%>&nbsp;<span class="star">*</span></label>
        <div class="col-sm-10">
            <input type="password" class="form-control" id="pass" required>
            <input type="hidden" id="password" name="password">
        </div>
    </div>

    <div class="form-group">
        <label for="role" class="col-sm-2 control-label"><%=__("Role")%>&nbsp;<span class="star">*</span></label>
        <div class="col-sm-10">
            <select name="role" id="role" class="form-control"  readonly>
                <%
                    for (Role role : Role.values()) {
                        if (role.equals(Role.Author)) {
                            out.print("<option selected value=\"" + role.name() + "\">" + __(role.name()) + "</option>\n");
                        } else {
                            out.print("<option value=\"" + role.name() + "\">" + __(role.name()) + "</option>\n");
                        }

                    }
                %>
            </select>
        </div>
    </div>

    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-primary"><%=__("Add User")%></button>
        </div>
    </div>
</form>
<%@ include file="/WEB-INF/pages/common/admin/footer.jsp" %>
