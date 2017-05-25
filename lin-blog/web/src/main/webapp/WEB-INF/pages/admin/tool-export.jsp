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
    $(".menu-parent-tools").addClass("active").click();
    $(".menu-item-tool-export").addClass("active");
});</script>
<h1><%=__("Export")%></h1>
<p><%=__("This feature is developing, it will coming soon.")%></p>
<%@ include file="/WEB-INF/pages/common/admin/footer.jsp" %>
