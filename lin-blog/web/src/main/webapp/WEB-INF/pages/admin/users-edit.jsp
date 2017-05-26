<%@ taglib prefix="blog" uri="http://youthlin.com/linblog/tag/blog" %>
<%--@elvariable id="userEdit" type="com.youthlin.blog.model.po.User"--%>
<%--@elvariable id="user" type="com.youthlin.blog.model.po.User"--%>
<%@ page import="static com.youthlin.utils.i18n.Translation.__" %>
<%@ page import="com.youthlin.blog.model.enums.Role" %>
<%@ page import="com.youthlin.blog.model.po.User" %>
<%@ page import="com.youthlin.blog.model.po.UserMeta" %><%--
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
    $(".gravatar").popover({html: true});
});</script>
<form class="form-horizontal" id="edit-user-form" method="post">
    <h1><%=__("Edit User")%></h1>
    <%--@elvariable id="msg" type="java.lang.String"--%>
    <%--@elvariable id="error" type="java.lang.String"--%>
    <c:if test="${not empty msg}">
        <div class="message">${msg}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    <div class="form-group">
        <input type="hidden" name="id" value="${userEdit.userId}">
        <label class="col-sm-2 control-label"><span class="sr-only"><%=__("Avatar")%></span></label>
        <div class="col-sm-10">
            <p class="form-control-static">
                <a tabindex="0" class="gravatar" role="button" data-toggle="popover" data-trigger="focus"
                   title=<%=__("'How to change my avatar?'")%> data-content=<%=__("'You can change your avatar image at <a href=\"http://cn.gravatar.com\" target=\"_blank\">Gravatar.com</a>'")%>>
                    <img src="<%=Gravatar.withEmail(((User)request.getAttribute("userEdit")).getUserEmail()).defaults(Gravatar.DefaultType.MONSTERID).getUrl()%>"
                         height="80" width="80" alt="Avatar"></a>
            </p>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label"><%=__("Username")%></label>
        <div class="col-sm-10">
            <p class="form-control-static" id="user" data-user="${userEdit.userLogin}">${userEdit.userLogin}
             &nbsp;<small class="help-text"><%=__("Username can not be modified.")%></small>
            </p>
        </div>
    </div>
    <div class="form-group">
        <label for="role" class="col-sm-2 control-label"><%=__("Role")%></label>
        <div class="col-sm-10">
            <c:choose>
                <c:when test="${(userEdit.userId eq user.userId) or (userEdit.userId eq 1)}">
                    <p class="form-control-static" id="role">
                        <%--@elvariable id="role" type="com.youthlin.blog.model.enums.Role"--%>
                        <c:if test="${not empty role}">
                            ${blog:__(role.name())}
                        </c:if>
                    &nbsp;<small class="help-text"><%=__("You can not edit yourself or super admin's role.")%></small>
                    </p>
                </c:when>
                <c:otherwise>
                    <select name="role" id="role" class="form-control">
                        <%
                            UserMeta roleMeta = (UserMeta) request.getAttribute("roleMeta");
                            for (Role role : Role.values()) {
                                if (role.name().equalsIgnoreCase(roleMeta.getMetaValue())) {
                                    out.print("<option selected value=\"" + role.name() + "\">" + __(role.name()) + "</option>\n");
                                } else {
                                    out.print("<option value=\"" + role.name() + "\">" + __(role.name()) + "</option>\n");
                                }
                            }
                        %>
                    </select>
                </c:otherwise>
            </c:choose>

        </div>
    </div>

    <div class="form-group">
        <label for="email" class="col-sm-2 control-label"><%=__("Email")%></label>
        <div class="col-sm-10">
            <input type="email" class="form-control" id="email" name="email" value="${userEdit.userEmail}">
        </div>
    </div>
    <div class="form-group">
        <label for="url" class="col-sm-2 control-label"><%=__("Url")%></label>
        <div class="col-sm-10">
            <input type="url" class="form-control" id="url" name="url" value="${userEdit.userUrl}">
        </div>
    </div>
    <div class="form-group">
        <label for="name" class="col-sm-2 control-label"><%=__("Display name")%></label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="name" name="name" value="${userEdit.displayName}">
        </div>
    </div>


    <div class="form-group">
        <label for="new-pass" class="col-sm-2 control-label"><%=__("New password")%></label>
        <div class="col-sm-10">
            <input type="password" class="form-control" id="new-pass">
            <input type="hidden" name="newPass" id="newPass">
            <p class="help-block"><%=__("Leave this filed blank if you does't need change the user's password.")%></p>
        </div>
    </div>


    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-primary"><%=__("Update")%></button>
        </div>
    </div>
</form>

<%@ include file="/WEB-INF/pages/common/admin/footer.jsp" %>
