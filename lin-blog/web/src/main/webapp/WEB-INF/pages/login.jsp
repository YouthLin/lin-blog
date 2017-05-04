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
    <%@ include file="common/head.jsp" %>
    <title><%=__("Login")%>
    </title>
    <%--suppress CssOverwrittenProperties --%>
    <style>
        html, body {
            height: 100%;
        }

        body {
            background: #f1f1f1 url("http://cn.bing.com/ImageResolution.aspx?w=1920&h=1200") no-repeat;
            background-size: cover;
        }

        #wrap {
            min-height: 100%;
            height: auto !important;
            height: 100%;
            margin: 0 auto -70px;
        }

        .message {
            border-left: 4px solid #00a0d2;
            padding: 12px;
            margin-left: 0;
            background-color: #fff;
            -webkit-box-shadow: 0 1px 1px 0 rgba(0, 0, 0, .1);
            box-shadow: 0 1px 1px 0 rgba(0, 0, 0, .1);
        }

        .error {
            border-left: 4px solid #dc3232;
            padding: 12px;
            margin-left: 0;
            background-color: #fbeaea;
            -webkit-box-shadow: 0 1px 1px 0 rgba(0, 0, 0, .1);
            box-shadow: 0 1px 1px 0 rgba(0, 0, 0, .1);
        }

        #login {
            min-width: 320px;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
        }

        #login h1 {
            text-align: center;
        }

        #login-form {
            background: rgba(255, 255, 255, 0.5);
            padding: 26px;
            -webkit-box-shadow: 0 1px 3px rgba(0, 0, 0, .13);
            box-shadow: 0 1px 3px rgba(0, 0, 0, .13);
        }

        .login-text {
            font-size: 13px;
            margin: 1% 0;
        }

        .clear {
            clear: both;
        }

        #end {
            clear: both;
            height: 70px;
        }

        #copyright {
            height: 70px;
            overflow: hidden;
            padding: 10px 0;
            text-align: center;
            color: #c7c7c7;
            background-color: rgba(255, 255, 255, .2);
        }

        #copyright p:last-child {
            margin-bottom: 0;
        }
    </style>
</head>
<body>
<div id="wrap">
    <div id="login">
        <h1><a href="<c:url value="/"/>">
            <img src="<c:url value="/static/img/logo.png"/>" alt="Logo" width="84" height="84">
        </a></h1>
        <c:if test="${not empty msg}">
            <div class="message">${msg}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        <form id="login-form" action="" method="post">
            <div class="form-group">
                <label for="user_login"><%=__("Username or Email Address")%>
                </label>
                <input type="text" class="form-control" id="user_login" name="login" required>
            </div>
            <div class="form-group">
                <label for="user_pass"><%=__("Password")%>
                </label>
                <input type="password" class="form-control" id="user_pass" name="pass" required>
            </div>
            <div class="checkbox pull-left">
                <label>
                    <input type="checkbox" id="remember-me" value="forever" name="remember">
                    <%=__("Remember Me")%>
                </label>
            </div>
            <div class="submit pull-right">
                <button class="btn btn-primary"><%=__("Log In")%>
                </button>
            </div>
            <div class="clear"></div>
            <p class="login-text"><a href=""><%=__("Lost your password?")%>
            </a></p>
            <p class="login-text"><a href=""><%=/*TRANSLATORS: 0. site name.*/_f("&larr; Back to {0}", "LinBlog")%>
            </a></p>
        </form>
    </div>
    <div id="end"></div>
</div>
<div id="copyright">
    <p>&copy; 2017-<%=DateTime.now().getYear()%> LinBlog</p>
    <p>YouthLin Chen</p>
</div>
<%@ include file="common/footer.jsp" %>
</body>
</html>
