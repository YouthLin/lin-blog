<%@ page import="static com.youthlin.utils.i18n.Translation.*" %><%--
  Created by IntelliJ IDEA.
  User: lin
  Date: 17-5-4
  Time: 下午8:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="common/login/head.jsp" %>
    <title><%=__("Already Installed")%>
    </title>
</head>
<body>
<div id="wrap">
    <div class="container">
        <h1 id="logo">
            <a href="<c:url value="/"/>">
                <img src="<c:url value="/static/img/logo.png"/>" alt="Logo" width="84" height="84">
            </a>
        </h1>
        <div class="panel blog-panel">
            <h1><%=__("Already Installed")%>
            </h1>
            <p><%=__("You appear to have already installed. To reinstall please clear your old database tables first and then restart the web container.")%>
            </p>
            <p><a href="<c:url value="/"/>" class="btn btn-primary"><%=__("Home")%></a>
            <a href="<c:url value="/login"/>" class="btn btn-default"><%=__("Log In")%></a></p>
        </div><!--/.panel-->
    </div><!--/.container-->
    <div id="end"></div>
</div>
<%@ include file="common/login/footer.jsp" %>
</body>
</html>
