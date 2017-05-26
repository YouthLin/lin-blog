<%--@elvariable id="user" type="com.youthlin.blog.model.po.User"--%>

<%@ page import="static com.youthlin.utils.i18n.Translation.__" %>
<%@ page import="com.youthlin.blog.model.po.User" %>
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
<script src="<c:url value="/static/js/jquery.md5.min.js"/>"></script>
<script>$(document).ready(function () {
    $(".menu-parent-users").addClass("active").click();
    $(".menu-item-my-profile").addClass("active");
    $(".gravatar").popover({html: true});
});</script>
<form class="form-horizontal" id="profile-form" method="post">
    <h1><%=__("My Profile")%></h1>
    <%--@elvariable id="msg" type="java.lang.String"--%>
    <%--@elvariable id="error" type="java.lang.String"--%>
    <c:if test="${not empty msg}">
        <div class="message">${msg}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    <div class="form-group">
        <label class="col-sm-2 control-label"><span class="sr-only"><%=__("Avatar")%></span></label>
        <div class="col-sm-10">
            <p class="form-control-static">
                <a tabindex="0" class="gravatar" role="button" data-toggle="popover" data-trigger="focus"
                   title=<%=__("'How to change my avatar?'")%> data-content=<%=__("'You can change your avatar image at <a href=\"http://cn.gravatar.com\" target=\"_blank\">Gravatar.com</a>'")%>>
                    <img src="<%=Gravatar.getUrlWithEmail(((User)request.getAttribute(Constant.USER)).getUserEmail())%>"
                         height="80" width="80" alt="Avatar"></a>
            </p>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label"><%=__("Username")%></label>
        <div class="col-sm-10">
            <p class="form-control-static" id="user" data-user="${user.userLogin}">${user.userLogin}
                &nbsp;<small class="help-text"><%=__("Username can not be modified.")%></small>
            </p>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label"><%=__("Role")%></label>
        <div class="col-sm-10">
            <p class="form-control-static" id="role"><%=__(((Role) request.getAttribute(Constant.K_ROLE)).name())%>
                &nbsp;<small class="help-text"><%=__("You can't edit your role.")%></small>
            </p>
        </div>
    </div>
    <fieldset>
        <legend><%=__("General")%></legend>
        <div class="form-group">
            <label for="email" class="col-sm-2 control-label"><%=__("Email")%><span class="star">&nbsp;*</span></label>
            <div class="col-sm-10">
                <input type="email" class="form-control" id="email" name="email" value="${user.userEmail}" required>
            </div>
        </div>
        <div class="form-group">
            <label for="url" class="col-sm-2 control-label"><%=__("Url")%><span>&nbsp;&nbsp;</span></label>
            <div class="col-sm-10">
                <input type="url" class="form-control" id="url" name="url" value="${user.userUrl}">
            </div>
        </div>
        <div class="form-group">
            <label for="name" class="col-sm-2 control-label"><%=__("Display name")%><span class="star">&nbsp;*</span></label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="name" name="name" value="${user.displayName}" required>
            </div>
        </div>
    </fieldset>
    <fieldset>
        <legend><%=__("Password")%>&nbsp;<small><%=__("If you don't want change password, please remain the password filed blank below.")%></small></legend>
        <div class="form-group">
            <label for="old-pass" class="col-sm-2 control-label"><%=__("Old password")%></label>
            <div class="col-sm-10">
                <input type="password" class="form-control" id="old-pass">
                <input type="hidden" name="oldPass" id="oldPass">
            </div>
        </div>
        <div class="form-group">
            <label for="new-pass" class="col-sm-2 control-label"><%=__("New password")%></label>
            <div class="col-sm-10">
                <input type="password" class="form-control" id="new-pass">
                <input type="hidden" name="newPass" id="newPass">
            </div>
        </div>
    </fieldset>

    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-primary"><%=__("Update")%></button>
        </div>
    </div>
</form>

<%@ include file="/WEB-INF/pages/common/admin/footer.jsp" %>
