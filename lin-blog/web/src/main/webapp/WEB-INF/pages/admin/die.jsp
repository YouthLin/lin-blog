<%@ page import="static com.youthlin.utils.i18n.Translation.__" %>
<%--
  Created by IntelliJ IDEA.
  User: youthlin.chen
  Date: 2017/5/11
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/pages/common/admin/header.jsp" %>
<h1><%=__("Error")%></h1>
<%--@elvariable id="error" type="java.lang.String"--%>
<div class="error">${error}</div>
<%@ include file="/WEB-INF/pages/common/admin/footer.jsp" %>
