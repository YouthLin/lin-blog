<%@ page import="static com.youthlin.utils.i18n.Translation.__" %><%--
  Created by IntelliJ IDEA.
  User: lin
  Date: 17-5-5
  Time: 下午2:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/pages/common/admin/header.jsp" %>
<script>$(document).ready(function () {
    $(".menu-parent-overview").addClass("active").click();
    $(".menu-item-overview").addClass("active");
});</script>
<h1><%=__("Overview")%></h1>

<%@ include file="/WEB-INF/pages/common/admin/footer.jsp" %>
