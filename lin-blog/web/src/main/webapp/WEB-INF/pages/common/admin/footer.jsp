<%@ page import="org.joda.time.DateTime" %><%--
  Created by IntelliJ IDEA.
  User: lin
  Date: 17-5-6
  Time: 下午4:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
</main>
</section>
<div id="end"></div>
</div><%--#wrap--%>
<footer id="copyright">
    <p>&copy; 2017-<%=DateTime.now().getYear()%> LinBlog</p>
    <p>Powered By <a href="http://youthlin.com/" target="_blank">YouthLin Chen</a></p>
</footer>
<%@ include file="/WEB-INF/pages/common/footer.jsp" %>
<%--@elvariable id="editor" type="java.lang.Boolean"--%>
<c:if test="${editor}">
    <script src="<c:url value="/static/editor/js/wangEditor.min.js"/>"></script>
    <script src="<c:url value="/static/editor.simple.md/simplemde.min.js"/>"></script>
</c:if>
<script src="<c:url value="/static/js/moment-with-locales.min.js"/>"></script>
<script src="<c:url value="/static/js/bootstrap-datetimepicker.min.js"/>"></script>
</body>
</html>
