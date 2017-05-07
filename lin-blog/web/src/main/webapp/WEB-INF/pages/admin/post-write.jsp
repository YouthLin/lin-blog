<%@ page import="static com.youthlin.utils.i18n.Translation.__" %>
<%@ page import="static com.youthlin.utils.i18n.Translation._x" %><%--
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
    $(".menu-item-new-post").addClass("active");
});</script>
<h1><%=__("Write Post")%></h1>
<form>
    <div class="main-content col-sm-9">

        <div class="form-group">
            <label for="post-title"><span class="sr-only"><%=__("Post Title:")%></span>            </label>
            <input type="text" class="form-control" name="post-title" id="post-title"
                   placeholder=<%=__("\"Write Post Title Here\"")%>>
        </div>

        <div>
            <!-- Nav tabs -->
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active">
                    <a href="#rich" aria-controls="rich" role="tab" data-toggle="tab"><%=__("Rich Editor")%></a>
                </li>
                <li role="presentation">
                    <a href="#markdown" aria-controls="markdown" role="tab" data-toggle="tab"><%=__("Markdown")%></a>
                </li>
            </ul>
            <!-- Tab panes -->
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="rich">
                    <div id="editor-container">
                        <label for="editor" class="sr-only"><%=__("Post Content:")%></label>
                        <textarea class="form-control" id="editor" name="post-content" rows="20"></textarea>
                    </div>
                </div>
                <div role="tabpanel" class="tab-pane" id="markdown">

                </div>
            </div><!-- /.Tab panes -->

        </div>
    </div>
    <div class="control-sidebar col-sm-3">

        <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="heading-publish">
                    <h4 class="panel-title">
                        <a role="button" data-toggle="collapse" href="#collapse-publish" aria-expanded="true"
                           aria-controls="collapse-publish"><%=__("Publish")%></a>
                    </h4>
                </div>
                <div id="collapse-publish" class="panel-collapse collapse in" role="tabpanel"
                     aria-labelledby="heading-publish">
                    <div class="panel-body">
                        Publish Settings
                    </div>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="heading-category">
                    <h4 class="panel-title">
                        <a role="button" data-toggle="collapse" href="#collapse-category" aria-expanded="true"
                           aria-controls="collapse-category"><%=__("Category")%></a>
                    </h4>
                </div>
                <div id="collapse-category" class="panel-collapse collapse in" role="tabpanel"
                     aria-labelledby="heading-category">
                    <div class="panel-body">
                        category Settings
                    </div>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="heading-tag">
                    <h4 class="panel-title">
                        <a role="button" data-toggle="collapse" href="#collapse-tag" aria-expanded="true"
                           aria-controls="collapse-tag"><%=__("Tag")%></a>
                    </h4>
                </div>
                <div id="collapse-tag" class="panel-collapse collapse in" role="tabpanel"
                     aria-labelledby="heading-tag">
                    <div class="panel-body">
                        tag Settings
                    </div>
                </div>
            </div>
        </div>


        <button type="submit" class="btn btn-default">Submit</button>

    </div>
    <div class="clear"></div>
</form>


<%--suppress JSPotentiallyInvalidConstructorUsage --%>
<script type="text/javascript">
    $(document).ready(function () {
        // 为页面所有的editor配置全局的密钥
        wangEditor.config.mapAk = 'BBe1ff531f9d8c7f526568d5390e9200';  // 此处换成自己申请的密钥
        var editor = new wangEditor('editor');
        editor.create();
        $('#post-title').focus();
    });
</script>
<%@ include file="/WEB-INF/pages/common/admin/footer.jsp" %>
