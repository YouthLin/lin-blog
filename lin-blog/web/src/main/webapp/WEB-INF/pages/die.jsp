<%@ page import="static com.youthlin.utils.i18n.Translation.__" %><%--
  Created by IntelliJ IDEA.
  User: youthlin.chen
  Date: 2017/5/13
  Time: 15:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="common/head.jsp" %>
    <title><%=__("Error")%></title>
</head>
<body>
<div id="wrap">
    <div class="page container-fluid">
        <%@ include file="include/header.jsp" %>
        <ol class="breadcrumb">
            <li><a href="<c:url value="/"/>"><%=__("Home")%></a></li>
            <li class="active">
                <%=__("Error")%>
            </li>
        </ol>
        <div class="content-wrap row">
            <h1><%=__("Error")%></h1>
            <%--@elvariable id="error" type="java.lang.String"--%>
            <div class="error">${error}</div>
        </div>
        <%--.row--%>
    </div>
    <%--/.page--%>
    <div id="end"></div>
</div>
<%@ include file="common/login/footer.jsp" %>
</body>
</html>
