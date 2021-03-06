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
    <title><%=__("Install")%>
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
            <h1><%=__("Welcome")%>
            </h1>
            <p><%=__("Welcome to the installation process! Just fill in the information below and you'll be on your way to using this blog.")%>
            </p>
            <h2><%=__("Information needed")%>
            </h2>
            <p><%=__("Please provide the following information. Don't worry, you can always change these settings later.")%>
            </p>
            <%--@elvariable id="msg" type="String"--%>
            <c:if test="${not empty msg}">
                <p class="error">${msg}</p>
            </c:if>
            <form class="form-horizontal" action="<c:url value="/install"/>" method="post" id="login-form">
                <div class="form-group">
                    <label for="title" class="col-sm-2 control-label"><%=__("Site Title")%>
                    </label>
                    <div class="col-sm-10">
                        <input class="form-control" name="title" id="title">
                    </div>
                </div>
                <div class="form-group">
                    <label for="user" class="col-sm-2 control-label"><%=__("Username")%>
                    </label>
                    <div class="col-sm-10">
                        <input class="form-control" name="user" id="user" required>
                        <span class="help-block small"><%=__("Username can have only alphanumeric characters, and can't changed later.")%></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="plain" class="col-sm-2 control-label"><%=__("Password")%>
                    </label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control" id="plain" required>
                        <input type="hidden" name="pass" id="pass">
                        <span class="help-block small"><%=__("<strong>Important:</strong>You will need this password to log in. Please store it in a secure location.")%></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="email" class="col-sm-2 control-label"><%=__("Your Email")%>
                    </label>
                    <div class="col-sm-10">
                        <input type="email" class="form-control" id="email" name="email" required>
                        <span class="help-block small"><%=__("Double-check your email address before continuing.")%></span>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-primary"><%=__("Next")%>
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div id="end"></div>
</div>
<%@ include file="common/login/footer.jsp" %>
</body>
</html>
