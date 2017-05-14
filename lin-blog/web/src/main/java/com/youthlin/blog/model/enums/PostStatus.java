package com.youthlin.blog.model.enums;

import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;

/**
 * 创建者： youthlin.chen 日期： 2017-04-04 20:46.
 */
@SuppressWarnings("unused")
public enum PostStatus {
    TRASH(-1, "回收站"), PUBLISHED(0, "已发布"), DRAFT(1, "草稿"), PENDING(2, "待审"),;
    private final int code;
    private final String describe;
    private static final Map<Integer, PostStatus> map;
    private static final Map<String, PostStatus> nameMap;

    static {
        Map<Integer, PostStatus> all = Maps.newHashMap();
        Map<String, PostStatus> allName = Maps.newHashMap();
        PostStatus[] values = PostStatus.values();
        for (PostStatus v : values) {
            all.put(v.code, v);
            allName.put(v.name().toLowerCase(), v);
        }
        map = Collections.unmodifiableMap(all);
        nameMap = Collections.unmodifiableMap(allName);
    }

    PostStatus(int code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    public int getCode() {
        return code;
    }

    public String getDescribe() {
        return describe;
    }

    public static PostStatus codeOf(int code) {
        return map.get(code);
    }

    public static PostStatus nameOf(String name) {
        if (name == null) {
            return null;
        }
        return nameMap.get(name.toLowerCase());
    }
}
