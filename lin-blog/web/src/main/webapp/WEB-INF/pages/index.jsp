<%@ page import="static com.youthlin.utils.i18n.Translation.__" %><%--
  Created by IntelliJ IDEA.
  User: lin
  Date: 17-5-5
  Time: 下午3:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="common/login/head.jsp" %>
    <title><%=__("Home")%></title>
</head>
<body>
<div id="wrap">
    <div class="page">
        <%@ include file="include/header.jsp" %>
        <main class="col-sm-9">
            <article>
                <header>
                    <h2>Title</h2>
                </header>
                <div class="content">
                    Content
                </div>
                <footer>

                </footer>
            </article>
        </main>
        <%@ include file="include/sidebar.jsp" %>
    </div>
    <div id="end"></div>
</div>
<%@ include file="common/login/footer.jsp" %>
</body>
</html>
