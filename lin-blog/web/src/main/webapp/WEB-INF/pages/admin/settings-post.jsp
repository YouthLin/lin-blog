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
    $(".menu-item-settings-post").addClass("active");
});</script>
<h1><%=__("Post Settings")%></h1>
<p><%=__("This feature is developing, it will coming soon.")%></p>
<p>默认编辑器设置等。</p>
<%@ include file="/WEB-INF/pages/common/admin/footer.jsp" %>
