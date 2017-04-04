package com.youthlin.blog.model.enums;

import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;

/**
 * 创建者： youthlin.chen 日期： 2017-04-04 21:08.
 */
public enum CommentStatus {
    NORMAL(0, "正常"), PENDING(1, "待审"), SPAM(2, "垃圾评论"), TRASH(3, "回收站");

    private final int code;
    private final String describe;
    private static final Map<Integer, CommentStatus> map;

    static {
        Map<Integer, CommentStatus> all = Maps.newHashMap();
        CommentStatus[] values = CommentStatus.values();
        for (CommentStatus v : values) {
            all.put(v.code, v);
        }
        map = Collections.unmodifiableMap(all);
    }

    CommentStatus(int code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    public int getCode() {
        return code;
    }

    public String getDescribe() {
        return describe;
    }

    public static CommentStatus codeOf(int code) {
        return map.get(code);
    }

}
