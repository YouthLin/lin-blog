<%--@elvariable id="status" type="java.lang.String"--%>
<%@ page import="static com.youthlin.utils.i18n.Translation.__" %><%--
  Created by IntelliJ IDEA.
  User: lin
  Date: 17-5-6
  Time: 下午9:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/pages/common/admin/header.jsp" %>
<script>$(document).ready(function () {
    $(".menu-parent-post").addClass("active").click();
    $(".menu-item-all-post").addClass("active");
    $('#a-${status}').css('font-weight', 'bold').css('color', '#000');
});</script>
<h1><%=__("All Post")%></h1>
<div class="status-selector">
    <a id="a-all" href="<c:url value="/admin/post/all"/>"><%=__("All")%></a> |
    <a id="a-published" href="<c:url value="/admin/post/published"/>"><%=__("Published")%></a> |
    <a id="a-draft" href="<c:url value="/admin/post/draft"/>"><%=__("Draft")%></a> |
    <a id="a-pending" href="<c:url value="/admin/post/pending"/>"><%=__("Pending")%></a> |
    <a id="a-trash" href="<c:url value="/admin/post/trash"/>"><%=__("Trash")%></a>
</div>
<div class="table-responsive">
    <table class="table table-striped table-hover border-ccc">

    </table>
</div>
<%@ include file="/WEB-INF/pages/common/admin/footer.jsp" %>
