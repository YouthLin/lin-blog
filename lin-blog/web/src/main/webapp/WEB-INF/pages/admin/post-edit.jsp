<%--@elvariable id="taxonomyMap" type="java.util.Map"--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="post" type="com.youthlin.blog.model.po.Post"--%>
<%@ page import="static com.youthlin.utils.i18n.Translation.__" %>
<%@ page import="static com.youthlin.utils.i18n.Translation._x" %>
<%--@elvariable id="categoryList" type="java.util.List"--%>
<%--@elvariable id="category" type="com.youthlin.blog.model.bo.Category"--%>
<%--@elvariable id="md" type="java.lang.String"--%>
<%--
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
    <c:choose>
    <c:when test="${not empty md}">
    $('#li-md').addClass('active');
    $('#markdown').addClass('active in');
    </c:when>
    <c:otherwise>
    $('#li-rich').addClass('active');
    $('#rich').addClass('active in');
    </c:otherwise>
    </c:choose>
});</script>
<h1><%=__("Edit Post")%>
</h1>
<form action="<c:url value="/admin/post/edit"/>" method="post" id="edit-post-form">
    <div class="main-content col-sm-9">
        <input type="hidden" name="postId" value="${post.postId}">
        <div class="form-group">
            <label for="post-title"><%=__("Title:")%></label>
            <input type="text" class="form-control" name="title" id="post-title" value="${post.postTitle}">
        </div>
        <div class="form-group">
            <label for="content"><%=__("Content:")%></label>
            <textarea class="hide" name="content" id="content" rows="20">
                <c:if test="${ empty md}">
                    ${post.postContent}
                </c:if>
            </textarea>
        </div>
        <div id="editors">
            <!-- Nav tabs -->
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" id="li-rich">
                    <a href="#rich" data-textarea="#editor" aria-controls="rich" role="tab" data-toggle="tab">
                        <%=__("Rich Editor")%>
                    </a>
                </li>
                <li role="presentation" id="li-md">
                    <a href="#markdown" data-textarea="#md-editor" aria-controls="markdown" role="tab"
                       data-toggle="tab"><%=__("Markdown")%></a>
                </li>
            </ul>
            <!-- Tab panes -->
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane fade" id="rich">
                    <div id="editor-container" class="editor-container">
                        <label for="editor" class="sr-only"><%=__("Content:")%></label>
                        <textarea class="form-control content-editor" id="editor"
                                  rows="20">${post.postContent}</textarea>
                    </div>
                </div>
                <div role="tabpanel" class="tab-pane fade" id="markdown">
                    <label for="md-editor" class="sr-only"><%=__("Content:")%></label>
                    <div id="md-editor-container" class="editor-container">
                        <textarea class="form-control content-editor" id="md-editor" name="md-content" rows="20"
                                  style="display:none;">${md}</textarea>
                    </div>
                </div>
            </div><!-- /.Tab panes -->
        </div>
        <div class="form-group">
            <label for="post-excerpt"><%=__("Excerpt:")%></label>
            <textarea class="form-control" name="excerpt" id="post-excerpt" rows="3">${post.postExcerpt}</textarea>
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
                                <div class='input-group date' id="datetimepicker">
                                   <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                     </span>
                                    <input id="post-date" type='text' class="form-control" name="date"
                                           value="<fmt:formatDate value="${post.postDate}" pattern="YYYY-MM-dd HH:mm"/>"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="post-status"><%=__("Status:")%></label>
                            <select class="form-control" id="post-status" name="status">
                                <%--@elvariable id="status" type="com.youthlin.blog.model.enums.PostStatus[]"--%>
                                <c:forEach items="${status}" var="s">
                                    <c:set var="select" value=""/>
                                    <c:if test="${post.postStatus eq s}">
                                        <c:set var="select" value="selected"/>
                                    </c:if>
                                    <option ${select} value="${s.name()}">${s.describe}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="panel-footer">
                        <button type="submit" class="btn btn-primary"><%=__("Update")%></button>
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
                        <c:forEach items="${categoryList}" var="category">
                            <c:set var="checked" value=""/>
                            <%--@elvariable id="cat" type="com.youthlin.blog.model.po.Taxonomy"--%>
                            <c:forEach items="${taxonomyMap[post.postId]}" var="cat">
                                <c:if test="${cat.taxonomyId eq category.taxonomyId}">
                                    <c:set var="checked" value="checked"/>
                                </c:if>
                            </c:forEach>
                            <div class="checkbox">
                                <label class="full-width">
                                    <input type="checkbox" name="category" value="${category.taxonomyId}" ${checked}>
                                    ${category.name}
                                </label>
                            </div>
                        </c:forEach>
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
                        <input type="hidden" name="tags" id="tags">
                        <div>
                            <div class="tags">
                                <%-- 别忘了还有 js 里的数组 --%>
                                <%--@elvariable id="tag" type="com.youthlin.blog.model.po.Taxonomy"--%>
                                <c:forEach items="${taxonomyMap[post.postId]}" var="tag">
                                    <c:if test="${tag.taxonomy eq 'tag'}">
                                        <span class="label label-primary">${tag.name}
                                        <a href="javascript:void(0);" data-tag="${tag.name}" class="badge remove-tag"
                                           aria-label="Remove"><span aria-hidden="true">×</span></a></span>
                                    </c:if>
                                </c:forEach>
                            </div>&nbsp;
                        </div>
                        <div class="form-group">
                            <label for="add-tag"><span class="sr-only"><%=__("Attach Tag:")%></span></label>
                            <input type="text" class="form-control" id="add-tag">
                            <span class="help-block"><%=__("You can use comma(,) to split tags.")%></span>
                        </div>
                        <a id="add-tag-btn" href="javascript:void(0);" class="btn btn-default"><%=__("Add")%></a>
                    </div>
                    <div class="panel-footer">
                        <a href="javascript:void(0);"><%=__("Select from frequently-used tags.")%></a>
                    </div>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="heading-settings">
                    <h4 class="panel-title">
                        <a role="button" data-toggle="collapse" href="#collapse-settings" aria-expanded="false"
                           aria-controls="collapse-settings"><%=__("Other Settings")%>
                        </a>
                    </h4>
                </div>
                <div id="collapse-settings" class="panel-collapse collapse" role="tabpanel"
                     aria-labelledby="heading-tag">
                    <div class="panel-body">
                        <div class="checkbox">
                            <c:set value="" var="checkComment"/>
                            <c:if test="${post.commentOpen}">
                                <c:set value="checked" var="checkComment"/>
                            </c:if>
                            <label class="full-width">
                                <input type="checkbox" ${checkComment} name="commentOpen"><%=__("Comments Open")%>
                            </label>
                        </div>
                        <div class="checkbox">
                            <c:set value="" var="pingOpen"/>
                            <c:if test="${post.commentOpen}">
                                <c:set value="checked" var="pingOpen"/>
                            </c:if>
                            <label class="full-width">
                                <input type="checkbox" ${pingOpen} name="pingOpen"><%=__("Pings Open")%></label>
                        </div>
                        <div class="form-group">
                            <div class="input-group">
                                <label for="post-password" class="input-group-addon"><%=__("Password:")%></label>
                                <input type="password" class="form-control" id="post-password" name="password"
                                       value="${post.postPassword}">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="input-group">
                                <label for="post-name" class="input-group-addon"><%=_x("Name:", "post name")%></label>
                                <input type="text" class="form-control" id="post-name" name="postName"
                                       value="${post.postName}">
                            </div>
                        </div>
                    </div>
                    <div class="panel-footer">
                        <small class="help-text">
                            <%=__("When Password has set, visitors must input password to view the post.<br>Post name is used to show in url.")%>
                        </small>
                    </div>
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

        var md = new SimpleMDE({
            element: $("#md-editor")[0],
            autosave: {
                enabled: true,
                uniqueId: "md-eitor",
                delay: 1000
            },
            spellChecker: false,
            forceSync: true,
            renderingConfig: {
                codeSyntaxHighlighting: true
            }
        });

        $('#datetimepicker').datetimepicker({
            format: 'YYYY-MM-DD HH:mm',
            dayViewHeaderFormat: 'YYYY-MM'
        });

        // input  回车不让提交, 如添加标签、密码、postname
        $('input').bind('keypress', function (e) {
            if (event.keyCode === 13) {
                return e.preventDefault();
            }
        });

        //region //add tag
        var addBtn = $("#add-tag-btn");
        var addInput = $('#add-tag');
        var tagList = $('.tags');
        var tagsInput = $('#tags');
        addBtn.click(function () {
            var input = addInput.val();
            var tags = input.split(',');
            $(tags).each(function (index, e) {
                e = e.trim();
                if (e.length > 0) {
                    add(e); // add each
                }
            });
            addInput.val('');//clear input
        });
        addInput.on('keypress', function (e) {
            if (event.keyCode === 13) {
                addBtn.click();
                return e.preventDefault();
            }
        });

        var allTag = [
            <%--@elvariable id="tag" type="com.youthlin.blog.model.po.Taxonomy"--%>
            <c:forEach items="${taxonomyMap[post.postId]}" var="tag">
            <c:if test="${tag.taxonomy eq 'tag'}">
            '${tag.name}',
            </c:if>
            </c:forEach>
        ];
        var toInput = function () {
            tagsInput.val(allTag.join(','));
        };
        toInput();
        var add = function (tag) {
            if (allTag.indexOf(tag) !== -1) {
                // already contains
                return;
            }
            tagList.append(makeTagHtml(tag));
            allTag.push(tag);
            toInput();
        };
        var makeTagHtml = function (tag) {
            return '<span class="label label-primary">' + tag +
                '<a href="javascript:void(0);" data-tag="' + tag + '" class="badge remove-tag" aria-label="Remove">' +
                '<span aria-hidden="true">&times;</span></a></span>';
        };
        var remove = function () {
            var tag = $(this).data('tag');
            var parent = $(this).parent();
            parent.remove();// remove the tag from page
            $(allTag).each(function (index, e) {
                if (e === tag) {
                    allTag.splice(index, 1);//delete 1 item from index
                }
            });
            toInput();
        };

        // $(...).live is not a function
        // http://stackoverflow.com/a/14354091
        $(this).on('click', '.remove-tag', remove);
        //endregion

        // check editor before change editor panel
        $('a[data-toggle="tab"]').on('hide.bs.tab', function (e) {
            var current = e.target;
            var textarea = $($(current).data('textarea'));
            var val = textarea.val();
            if (val.length === 0 || val === '<p><br></p>') {
                // 放行
                $('#content').val('')//clear;
            } else {
                alert(<%=__("\"Current editor is not empty so you can not swith to another editor.\"")%>);
                return e.preventDefault();
            }
        });

        // submit 之前获取真正 content(html)
        $('#edit-post-form').submit(function () {
            var $content = $('#content');
            if ($('#rich').hasClass('active')) {
                var editor = $('#editor');
                var content = editor.val();
                console.log(content);
                $content.val(content);
                $("textarea[name='md-content']").val('');// clear md-content
            } else if ($('#markdown').hasClass('active')) {
                var preview = md.options.previewRender(md.value());
                console.log(preview);
                $content.val(preview);
            }
        });

    });
</script>
<%@ include file="/WEB-INF/pages/common/admin/footer.jsp" %>
