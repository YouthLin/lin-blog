<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="static com.youthlin.utils.i18n.Translation._f" %>
<%@ page import="com.youthlin.blog.util.Constant" %>
<%@ page import="com.youthlin.blog.util.Gravatar" %>
<%--@elvariable id="title" type="java.lang.String"--%>
<%--@elvariable id="blog_title" type="java.lang.String"--%>
<%--
  Created by IntelliJ IDEA.
  User: lin
  Date: 17-5-5
  Time: 下午9:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html>
<head>
    <%--@elvariable id="editor" type="java.lang.Boolean"--%>
    <c:if test="${editor}">
        <link href="<c:url value="/static/editor/css/wangEditor.min.css"/> " rel="stylesheet">
        <link href="<c:url value="/static/editor.simple.md/simplemde.min.css"/> " rel="stylesheet">
        <script src="//cdn.bootcss.com/highlight.js/9.11.0/highlight.min.js"></script>
    </c:if>
    <%@ include file="/WEB-INF/pages/common/head.jsp" %>
    <link href="<c:url value="/static/css/bootstrap-datetimepicker.min.css"/> " rel="stylesheet">
    <title>${title}</title>
</head>
<body class="nav-fixed">
<div id="wrap">
    <header>
        <nav class="navbar navbar-inverse navbar-fixed-top bg bd">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle pull-left" data-toggle="collapse" data-target="#sidebar"
                        aria-expanded="true" aria-controls="navbar" id="toggle-btn">
                    <span class="sr-only"><%=__("Toggle Navigation")%></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="<c:url value="/"/>">${blog_title}</a>
                <div class="pull-right nav-user">
                    <a href="#user-menu" aria-expanded="false" aria-controls="user-menu" data-toggle="collapse">
                        <%=/*TRANSLATORS: 0 username*/_f("Hi, {0}", request.getAttribute(Constant.NAME))%>
                        <img src="<%=Gravatar.getUrlWithEmail((String) request.getAttribute(Constant.EMAIL))%>"
                             width="30" height="30">
                    </a>
                </div>
            </div>
        </nav>
        <div id="user-menu" class="bg collapse">
            <div class="pull-right">
                <img src="<%=Gravatar.getUrlWithEmail((String) request.getAttribute(Constant.EMAIL))%>"
                     width=60 height=60 class="pull-left">
                <ul class="pull-right">
                    <li><a href="#"><%=__("Profile")%></a></li>
                    <li><a href="<c:url value="/login.out"/>"><%=__("Log out")%></a></li>
                </ul>
            </div>
        </div>
    </header>
    <aside class="collapse in navbar-collapse" id="sidebar">
        <div id="menu" class="col-xs-4 col-sm-2 bg">
           <a href="#overview" data-toggle="collapse" data-parent="#menu" class="parent menu-parent-overview"
              aria-expanded="false" aria-controls="post"><%=__("DashBoard")%></a>
            <div class="panel panel-menu bd">
                <ul id="overview" class="collapse">
                    <li><a class="menu-item-a menu-item-overview" href="<c:url value="/admin/"/>">
                            <%=__("Overview")%>
                    </a></li>
                </ul>
            </div>

            <a href="#post" data-toggle="collapse" data-parent="#menu" class="parent menu-parent-post"
               aria-expanded="false" aria-controls="post"><%=__("Post")%></a>
            <div class="panel panel-menu bd ">
                <ul id="post" class="collapse">
                    <li><a class="menu-item-a menu-item-all-post" href="<c:url value="/admin/post"/>">
                        <%=__("All Post")%>
                    </a></li>
                    <li><a class="menu-item-a menu-item-new-post" href="<c:url value="/admin/post/new"/>">
                        <%=__("New Post")%>
                    </a></li>
                    <li><a class="menu-item-a menu-item-category" href="<c:url value="/admin/post/category"/>">
                        <%=__("Categories")%>
                    </a></li>
                    <li><a class="menu-item-a menu-item-tag" href="<c:url value="/admin/post/tag"/>">
                        <%=__("Tag")%>
                    </a></li>
                </ul>
            </div>

            <a href="#page" data-toggle="collapse" data-parent="#menu" class="parent menu-parent-page"
               aria-expanded="false" aria-controls="page"><%=__("Page")%></a>
            <div class="panel panel-menu bd ">
                <ul id="page" class="collapse">
                    <li><a class="menu-item-a menu-item-all-page" href="<c:url value="/admin/page/all"/>">
                        <%=__("All Page")%>
                    </a></li>
                    <li><a class="menu-item-a menu-item-new-page" href="<c:url value="/admin/page/new"/>">
                        <%=__("Write New Page")%>
                    </a></li>
                </ul>
            </div>

            <a href="#comment" data-toggle="collapse" data-parent="#menu" class="parent menu-parent-comment"
               aria-expanded="false" aria-controls="post"><%=__("Comments")%></a>
            <div class="panel panel-menu bd ">
                <ul id="comment" class="collapse">
                    <li><a class="menu-item-a menu-item-comment" href="<c:url value="/admin/comment/all"/>">
                        <%=__("All Comments")%>
                    </a></li>
                </ul>
            </div>

            <a href="#users" data-toggle="collapse" data-parent="#menu" class="parent menu-parent-users"
               aria-expanded="false" aria-controls="post"><%=__("Users")%></a>
            <div class="panel panel-menu bd ">
                <ul id="users" class="collapse">
                    <li><a class="menu-item-a menu-item-all-user" href="<c:url value="/admin/users/all"/>">
                        <%=__("All Users")%>
                    </a></li>
                    <li><a class="menu-item-a menu-item-my-profile" href="<c:url value="/admin/users/my"/>">
                        <%=__("My Profile")%>
                    </a></li>
                </ul>
            </div>

            <a href="#tools" data-toggle="collapse" data-parent="#menu" class="parent menu-parent-tools"
               aria-expanded="false" aria-controls="post"><%=__("Tools")%></a>
            <div class="panel panel-menu bd ">
                <ul id="tools" class="collapse">
                    <li><a class="menu-item-a menu-item-tool-import" href="<c:url value="/admin/tools/import"/>">
                        <%=__("Import")%>
                    </a></li>
                    <li><a class="menu-item-a menu-item-tool-export" href="<c:url value="/admin/tools/export"/>">
                        <%=__("Export")%>
                    </a></li>
                </ul>
            </div>

            <a href="#settings" data-toggle="collapse" data-parent="#menu" class="parent menu-parent-settings"
               aria-expanded="false" aria-controls="settings"><%=__("Settings")%></a>
            <div class="panel panel-menu bd ">
                <ul id="settings" class="collapse">
                    <li>
                        <a class="menu-item-a menu-item-settings-general"
                           href="<c:url value="/admin/settings/general"/>"><%=__("General Settings")%></a>
                    </li>
                    <li><a class="menu-item-a menu-item-settings-post" href="<c:url value="/admin/settings/post"/>">
                        <%=__("Post Settings")%>
                    </a></li>
                    <li><a class="menu-item-a menu-item-settings-comment"
                           href="<c:url value="/admin/settings/comment"/>"><%=__("Comment Settings")%>
                    </a></li>
                </ul>
            </div>

        </div><!-- /#menu -->
    </aside>
    <section class="col-xs-offset-4 col-sm-offset-2 col-xs-8 col-sm-10" id="main">
        <main>
