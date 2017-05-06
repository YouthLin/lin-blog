<%@ page import="static com.youthlin.utils.i18n.Translation._f" %>
<%@ page import="static com.youthlin.utils.i18n.Translation._x" %>
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
    <%@ include file="/WEB-INF/pages/common/head.jsp" %>
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
                <a class="navbar-brand" href="#">${blog_title}</a>
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
                    <li><a href="#"><%=__("Log out")%></a></li>
                </ul>
            </div>
        </div>
    </header>
    <aside class="collapse in navbar-collapse" id="sidebar">
        <div id="menu" class="col-xs-4 col-sm-3 col-md-2 bg">
            <div class="panel panel-menu bd ">
                <ul>
                    <li><a class="menu-item-a menu-item-overview" href="<c:url value="/admin/"/>">
                            <%=__("Overview")%>
                    </a></li>
                </ul>
            </div>

            <a href="#post" data-toggle="collapse" data-parent="#menu" class="parent menu-parent-post"
               aria-expanded="false" aria-controls="post"><%=__("Post")%></a>
            <div class="panel panel-menu bd ">
                <ul id="post" class="collapse">
                    <li><a class="menu-item-a menu-item-all-post" href="#"><%=__("All Post")%></a></li>
                    <li><a class="menu-item-a menu-item-new-post" href=" #"><%=__("New Post")%></a></li>
                    <li><a class="menu-item-a menu-item-category" href="#"><%=__("Categories")%></a></li>
                    <li><a class="menu-item-a menu-item-tag" href="#"><%=__("Tag")%></a></li>
                </ul>
            </div>

            <a href="#page" data-toggle="collapse" data-parent="#menu" class="parent menu-parent-page"
               aria-expanded="false" aria-controls="page"><%=__("Page")%></a>
            <div class="panel panel-menu bd ">
                <ul id="page" class="collapse">
                    <li><a class="menu-item-a menu-item-all-page" href="#"><%=__("All Page")%></a></li>
                    <li><a class="menu-item-a menu-item-new-page" href="#"><%=__("New Page")%></a></li>
                </ul>
            </div>

            <div class="panel panel-menu bd ">
                <ul>
                    <li><a class="menu-item-a menu-item-comment" href="#"><%=__("Comments")%></a></li>
                </ul>
            </div>

            <a href="#settings" data-toggle="collapse" data-parent="#menu" class="parent menu-parent-settings"
               aria-expanded="false" aria-controls="settings"><%=__("Settings")%></a>
            <div class="panel panel-menu bd ">
                <ul id="settings" class="collapse">
                    <li><a class="menu-item-a menu-item-settings-general" href="#">
                        <%=_x("General", "Settings")%>
                    </a></li>
                    <li><a class="menu-item-a menu-item-settings-post" href="#"><%=_x("Post", "Settings")%></a></li>
                    <li><a class="menu-item-a menu-item-settings-comment" href="#"><%=_x("Comment", "Settings")%></a>
                    </li>
                </ul>
            </div>

        </div><!-- /#menu -->
    </aside>
    <section class="col-xs-offset-4 col-sm-offset-3 col-md-offset-2" id="main">
        <main class="container">
