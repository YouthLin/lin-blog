<%@ page import="static com.youthlin.utils.i18n.Translation.__" %>
<%@ page import="static com.youthlin.utils.i18n.Translation._x" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Date" %><%--
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
<h1><%=__("Write Post")%>
</h1>
<form>
    <div class="main-content col-sm-9">
        <div class="form-group">
            <label for="post-title"><%=__("Title:")%></label>
            <input type="text" class="form-control" name="post-title" id="post-title">
        </div>
        <div class="form-group">
            <label><%=__("Content:")%></label>
        </div>
        <div>
            <!-- Nav tabs -->
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active">
                    <a href="#rich" aria-controls="rich" role="tab" data-toggle="tab"><%=__("Rich Editor")%>
                    </a>
                </li>
                <li role="presentation">
                    <a href="#markdown" aria-controls="markdown" role="tab" data-toggle="tab"><%=__("Markdown")%>
                    </a>
                </li>
            </ul>
            <!-- Tab panes -->
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="rich">
                    <div id="editor-container" class="editor-container">
                        <label for="editor" class="sr-only"><%=__("Content:")%></label>
                        <textarea class="form-control" id="editor" name="post-content" rows="20"></textarea>
                    </div>
                </div>
                <div role="tabpanel" class="tab-pane" id="markdown">
                    <label for="md-editor" class="sr-only"><%=__("Content:")%></label>
                    <div id="md-editor-container" class="editor-container">
                        <textarea class="form-control" id="md-editor" name="md-post-content" rows="20"
                                  style="display:none;"></textarea>
                    </div>
                </div>
            </div><!-- /.Tab panes -->

            <div class="form-group">
                <label for="post-excerpt"><%=__("Excerpt:")%></label>
                <textarea class="form-control" name="post-excerpt" id="post-excerpt" rows="3"></textarea>
            </div>
        </div>
    </div>

    <div class="control-sidebar col-sm-3">
        <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="heading-publish">
                    <h4 class="panel-title">
                        <a role="button" data-toggle="collapse" href="#collapse-publish" aria-expanded="true"
                           aria-controls="collapse-publish"><%=__("Publish")%>
                        </a>
                    </h4>
                </div>
                <div id="collapse-publish" class="panel-collapse collapse in" role="tabpanel"
                     aria-labelledby="heading-publish">
                    <div class="panel-body">
                        <div class="form-group">
                            <label for="post-date"><%=__("Publish Datetime:")%></label>
                            <div class="input-group">
                                <span class="input-group-addon sr-only"><%=__("Publish Datetime:")%></span>
                                <div class='input-group date' id="datetimepicker">
                                   <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                     </span>
                                    <input id="post-date" type='text' class="form-control"
                                           value="<%=DateTime.now().toString("YYYY-MM-dd HH:mm")%>"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel-footer">
                        <button type="submit" class="btn btn-default "><%=__("Save Draft")%></button>
                        <button type="submit" class="btn btn-primary pull-right"><%=__("Publish")%></button>
                    </div>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="heading-category">
                    <h4 class="panel-title">
                        <a role="button" data-toggle="collapse" href="#collapse-category" aria-expanded="true"
                           aria-controls="collapse-category"><%=__("Category")%>
                        </a>
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
                           aria-controls="collapse-tag"><%=__("Tag")%>
                        </a>
                    </h4>
                </div>
                <div id="collapse-tag" class="panel-collapse collapse in" role="tabpanel"
                     aria-labelledby="heading-tag">
                    <div class="panel-body">
                        tag Settings
                    </div>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="heading-settings">
                    <h4 class="panel-title">
                        <a role="button" data-toggle="collapse" href="#collapse-settings" aria-expanded="true"
                           aria-controls="collapse-settings"><%=__("Other Settings")%>
                        </a>
                    </h4>
                </div>
                <div id="collapse-settings" class="panel-collapse collapse in" role="tabpanel"
                     aria-labelledby="heading-tag">
                    <div class="panel-body">
                        <div class="checkbox">
                            <label><input type="checkbox" checked><%=__("Comments Open")%></label>
                        </div>
                        <div class="checkbox">
                            <label><input type="checkbox" checked><%=__("Pings Open")%></label>
                        </div>
                        <div class="form-group">
                            <div class="input-group">
                                <label for="post-password" class="input-group-addon"><%=__("Password:")%></label>
                                <input type="text" class="form-control" id="post-password">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="input-group">
                                <label for="post-name" class="input-group-addon"><%=_x("Name:", "post name")%></label>
                                <input type="text" class="form-control" id="post-name">
                            </div>
                        </div>
                    </div>
                    <div class="panel-footer"><small class="help-text">
                        <%=__("When Password has set, visitors must input password to view the post.<br>Post name is used to show in url.")%>
                    </small></div>
                </div>

                <%-- Author: 可设置为其他作者（以后有需求再做） --%>
            </div>

        </div><!-- ./panel-group -->

    </div>
    <div class="clear"></div>
</form>


<%--suppress JSPotentiallyInvalidConstructorUsage, JSUnresolvedFunction --%>
<script type="text/javascript">
    $(document).ready(function () {
        // 为页面所有的editor配置全局的密钥
        wangEditor.config.mapAk = 'BBe1ff531f9d8c7f526568d5390e9200';  // 此处换成自己申请的密钥
        var editor = new wangEditor('editor');
        editor.create();
        $('#post-title').focus();

        new SimpleMDE({
            element: $("#md-editor")[0],
            autosave: {
                enabled: true,
                uniqueId: "md-eitor",
                delay: 1000
            }
        });

        $('#datetimepicker').datetimepicker({});
    });
</script>
<%@ include file="/WEB-INF/pages/common/admin/footer.jsp" %>
