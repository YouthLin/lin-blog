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
    $(".menu-parent-settings").addClass("active").click();
    $(".menu-item-settings-general").addClass("active");
});</script>
<h1><%=__("General Settings")%></h1>
<form class="form-horizontal" method="post">
    <div class="form-group">
        <label for="title" class="col-sm-2 control-label"><%=__("Site Title")%></label>
        <div class="col-sm-10">
            <%--@elvariable id="blog_title" type="java.lang.String"--%>
            <input type="text" class="form-control" id="title" name="title" value="${blog_title}" required>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-primary"><%=__("Update")%></button>
        </div>
    </div>
</form>
<%@ include file="/WEB-INF/pages/common/admin/footer.jsp" %>
