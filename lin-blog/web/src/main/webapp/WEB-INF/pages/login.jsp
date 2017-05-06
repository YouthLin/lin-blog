<%@page import="static com.youthlin.utils.i18n.Translation.*" %>
<%@ page import="org.joda.time.DateTime" %>
<%--@elvariable id="msg" type="String"--%>
<%--@elvariable id="error" type="String"--%>
<%--
  Created by IntelliJ IDEA.
  User: lin
  Date: 17-5-4
  Time: 上午11:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="common/login/head.jsp" %>
    <title><%=__("Login")%>
    </title>
    <style>
        body {
            background: #f1f1f1 url("http://cn.bing.com/ImageResolution.aspx?w=1920&h=1200") no-repeat;
            background-size: cover;
        }

        .login-panel {
            background: rgba(255, 255, 255, .5);
        }

        .blog-panel-footer {
            padding-top: 20px;
        }
    </style>
</head>
<body>
<div id="wrap">
    <div class="container">
        <h1 id="logo">
            <a href="<c:url value="/"/>">
                <img src="<c:url value="/static/img/logo.png"/>" alt="Logo" width="84" height="84">
            </a>
        </h1>
        <div class="panel blog-panel login-panel">
            <c:if test="${not empty msg}">
                <div class="message">${msg}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>
            <form id="login-form" action="<c:url value="/login.do"/> " method="post">
                <div class="form-group">
                    <label for="user"><%=__("Username")%>
                    </label>
                    <input type="text" class="form-control" id="user" name="user" required>
                </div>
                <div class="form-group">
                    <label for="plain"><%=__("Password")%>
                    </label>
                    <input type="password" class="form-control" id="plain" required>
                    <input type="hidden" id="pass" name="pass">
                </div>
                <div class="checkbox pull-left">
                    <label>
                        <input type="checkbox" id="remember-me" value="forever" name="remember">
                        <%=__("Remember Me")%>
                    </label>
                </div>
                <div class="submit pull-right">
                    <button type="submit" class="btn btn-primary"><%=__("Log In")%>
                    </button>
                </div>
            </form>
            <div class="blog-panel-footer clear">
                <p><a href=""><%=__("Lost your password?")%>
                </a></p>
                <p><a href=""><%=/*TRANSLATORS: 0. site name.*/_f("&larr; Back to {0}", "LinBlog")%>
                </a></p>
            </div>
        </div>

    </div>
    <div id="end"></div>
</div>
<%@ include file="common/login/footer.jsp" %>
</body>
</html>
