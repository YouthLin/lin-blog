<%@ page import="org.joda.time.DateTime" %><%--
  Created by IntelliJ IDEA.
  User: lin
  Date: 17-5-5
  Time: 上午10:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<div id="copyright">
    <p>&copy; 2017-<%=DateTime.now().getYear()%> LinBlog</p>
    <p>Powered By <a href="http://youthlin.com/" target="_blank">YouthLin Chen</a></p>
</div>
<%@ include file="/WEB-INF/pages/common/footer.jsp" %>
