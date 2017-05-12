<%@ page import="static com.youthlin.utils.i18n.Translation._f" %><%--
  Created by IntelliJ IDEA.
  User: youthlin.chen
  Date: 2017/5/11
  Time: 21:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<aside id="page-sidebar" class="col-sm-3">
    <div class="sidebar">
        <div class="panel panel-default panel-comment">
            <div class="panel-heading">
                <h5 class="panel-title">Panel title</h5>
            </div>
            <div class="panel-body">
                Panel content
            </div>
        </div>
        <div class="panel panel-default panel-category">
            <div class="panel-heading">
                <h5 class="panel-title">Panel title</h5>
            </div>
            <div class="panel-body">
                Panel content
            </div>
        </div>
        <div class="panel panel-default panel-tag">
            <div class="panel-heading">
                <h5 class="panel-title">Panel title</h5>
            </div>
            <div class="panel-body">
                Panel content
            </div>
        </div>
        <div class="panel panel-default panel-date">
            <div class="panel-heading">
                <h5 class="panel-title">Panel title</h5>
            </div>
            <div class="panel-body">
                Panel content
            </div>
        </div>
        <div class="panel panel-default panel-date">
            <div class="panel-heading">
                <h5 class="panel-title"><%=__("Meta")%></h5>
            </div>
            <div class="panel-body">
                <ul>
                    <li><a href="<c:url value="/admin/"/>"><%=__("Dashboard")%></a></li>
                    <li><a href="#"><abbr title="Really Simple Syndication">RSS</abbr></a></li>
                    <li><a href="http://youthlin.com/" target="_blank"><%=_f("Powered By {0}", "LinBlog")%></a></li>
                </ul>
            </div>
        </div>
    </div>
</aside>