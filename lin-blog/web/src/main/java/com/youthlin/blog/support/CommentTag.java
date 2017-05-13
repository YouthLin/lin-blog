package com.youthlin.blog.support;

import com.youthlin.blog.model.bo.CommentNode;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;

/**
 * 创建： youthlin.chen
 * 时间： 2017-05-13 10:48.
 */
public class CommentTag extends SimpleTagSupport {
    private CommentNode commentNode;
    private List<CommentNode> commentNodeChain;

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();

    }
}
