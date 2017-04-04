package com.youthlin.blog.model.enums;

import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;

/**
 * 创建者： youthlin.chen 日期： 2017-04-04 20:53.
 */
public enum PostType {
    POST(0, "文章"), PAGE(1, "页面"), ATTACHMENT(2, "附件");
    private final int code;
    private final String describe;
    private static final Map<Integer, PostType> map;

    static {
        Map<Integer, PostType> all = Maps.newHashMap();
        PostType[] values = PostType.values();
        for (PostType v : values) {
            all.put(v.code, v);
        }
        map = Collections.unmodifiableMap(all);
    }

    PostType(int code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    public int getCode() {
        return code;
    }

    public String getDescribe() {
        return describe;
    }

    public static PostType codeOf(int code) {
        return map.get(code);
    }
}
