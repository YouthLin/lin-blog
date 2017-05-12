<%@ page import="static com.youthlin.utils.i18n.Translation.__" %><%--
  Created by IntelliJ IDEA.
  User: youthlin.chen
  Date: 2017/5/12
  Time: 22:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="common/head.jsp" %>
    <title>404</title>
</head>
<body>
<div id="wrap">
    <div class="page container-fluid">
        <%@ include file="include/header.jsp" %>
        <ol class="breadcrumb">
            <li><a href="<c:url value="/"/>"><%=__("Home")%></a></li>
            <li class="active">404</li>
        </ol>
        <div class="content-wrap row">
            <main id="page-main" class="col-sm-9">
                <div class="main-content">
                    <article id="404" class="article article-not-found border-ccc margin-padding-p1">
                        <h2><%=__("404 Not Found")%></h2>
                        <p><%=__("We are sorry, but the page is not found. ")%></p>
                    </article>
                </div>
            </main>
            <%@ include file="include/sidebar.jsp" %>
        </div>
        <%--.row--%>
    </div>
    <%--/.page--%>
    <div id="end"></div>
</div>
<%@ include file="common/login/footer.jsp" %>
</body>
</html>