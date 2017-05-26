<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="static com.youthlin.utils.i18n.Translation.__" %>
<%@ page import="com.youthlin.blog.model.enums.CommentStatus" %>
<%@ page import="com.youthlin.blog.model.po.Comment" %>
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
    $(".menu-parent-comment").addClass("active").click();
});</script>
<h1><%=__("Edit Comment")%></h1>
<%--@elvariable id="comment" type="com.youthlin.blog.model.po.Comment"--%>
<form class="form-horizontal" method="post">
    <%--@elvariable id="msg" type="java.lang.String"--%>
    <%--@elvariable id="error" type="java.lang.String"--%>
    <c:if test="${not empty msg}">
        <div class="message">${msg}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    <div class="form-group">
        <label for="author" class="col-sm-2 control-label"><%=__("Name")%> <span class="star">*</span> </label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="author" name="author" value="${comment.commentAuthor}" required
                   placeholder=<%=__("\"Display Name\"")%>>
        </div>
    </div>
    <div class="form-group">
        <label for="email" class="col-sm-2 control-label"><%=__("Email")%> <span class="star">*</span> </label>
        <div class="col-sm-10">
            <input type="email" class="form-control" id="email" name="email"
                   required value="${comment.commentAuthorEmail}"
                   placeholder=<%=__("\"Email will never public\"")%>>
        </div>
    </div>
    <div class="form-group">
        <label for="url" class="col-sm-2 control-label"><%=__("URL")%> &nbsp;</label>
        <div class="col-sm-10">
            <input type="url" class="form-control" id="url" name="url" value="${comment.commentAuthorUrl}"
                   placeholder=<%=__("\"URL is optional\"")%>>
        </div>
    </div>

    <div class="form-group">
        <label for="content" class="col-sm-2 control-label"><%=__("Content")%><span class="star">*</span> </label>
        <div class="col-sm-10">
            <textarea class="form-control" name="content" id="content" required
                      rows="10">${comment.commentContent}</textarea>
        </div>
    </div>

    <div class="form-group">
        <label for="status" class="col-sm-2 control-label"><%=__("Status")%>&nbsp;</label>
        <div class="col-sm-10">
            <%
                Comment comment = (Comment) request.getAttribute("comment");
            %>
            <c:choose>
                <%--@elvariable id="role" type="com.youthlin.blog.model.enums.Role"--%>
                <c:when test="${role.code ge 30}">
                    <select name="status" id="status" class="form-control">
                        <%
                            for (CommentStatus status : CommentStatus.values()) {
                                if (status.equals(comment.getCommentStatus())) {
                                    out.print("<option selected value=\"" + status.name() + "\">" + __(status.name()) + "</option>\n");
                                } else {
                                    out.print("<option value=\"" + status.name() + "\">" + __(status.name()) + "</option>\n");
                                }

                            }
                        %>
                    </select>
                </c:when>
                <c:otherwise>
                    <p id="status" class="form-control-static"><%=__(comment.getCommentStatus().name())%></p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <input type="hidden" name="id" value="${comment.commentId}">
            <button type="submit" class="btn btn-primary"><%=__("Update")%></button>
        </div>
    </div>
</form>
</form>
<%@ include file="/WEB-INF/pages/common/admin/footer.jsp" %>
