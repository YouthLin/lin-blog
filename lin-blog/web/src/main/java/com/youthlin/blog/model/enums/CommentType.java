package com.youthlin.blog.model.enums;

import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;

/**
 * 创建者： youthlin.chen 日期： 2017-04-04 21:22.
 */
@SuppressWarnings("unused")
public enum CommentType {
    COMMENT(0, "评论"), PINGBACK(1, "PingBack");
    private final int code;
    private final String describe;
    private static final Map<Integer, CommentType> map;

    static {
        Map<Integer, CommentType> all = Maps.newHashMap();
        CommentType[] values = CommentType.values();
        for (CommentType v : values) {
            all.put(v.code, v);
        }
        map = Collections.unmodifiableMap(all);
    }

    CommentType(int code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    public int getCode() {
        return code;
    }

    public String getDescribe() {
        return describe;
    }

    public static CommentType codeOf(int code) {
        return map.get(code);
    }
}
