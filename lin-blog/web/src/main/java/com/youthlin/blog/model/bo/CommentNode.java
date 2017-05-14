package com.youthlin.blog.model.bo;

import com.google.common.collect.Lists;
import com.youthlin.blog.model.po.Comment;

import java.util.List;

/**
 * 评论节点.
 * 创建： youthlin.chen
 * 时间： 2017-05-12 23:38.
 */
public class CommentNode {
    private CommentNode parent;
    private int level = 1;
    private Comment comment;
    private CommentNode next;
    private List<CommentNode> children = Lists.newLinkedList();

    @Override
    public String toString() {
        Long p = null;
        if (parent != null && parent.getComment() != null) {
            p = parent.getComment().getCommentId();
        }
        Long n = null;
        if (next != null && next.getComment() != null) {
            n = next.getComment().getCommentId();
        }
        return "CommentNode{" +
                "comment.id=" + (comment != null ? comment.getCommentId() : "null") +
                ", parent.comment.id=" + p +
                ", level=" + level +
                ", next.comment.id=" + n +
                ", comment=" + comment +
                ", children.size=" + (children == null ? "null" : children.size()) +
                '}';
    }

    public CommentNode getParent() {
        return parent;
    }

    public CommentNode setParent(CommentNode parent) {
        this.parent = parent;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public CommentNode setLevel(int level) {
        this.level = level;
        return this;
    }

    public Comment getComment() {
        return comment;
    }

    public CommentNode setComment(Comment comment) {
        this.comment = comment;
        return this;
    }

    public CommentNode getNext() {
        return next;
    }

    public CommentNode setNext(CommentNode next) {
        this.next = next;
        return this;
    }

    public List<CommentNode> getChildren() {
        return children;
    }

    public CommentNode setChildren(List<CommentNode> children) {
        this.children = children;
        return this;
    }
}
