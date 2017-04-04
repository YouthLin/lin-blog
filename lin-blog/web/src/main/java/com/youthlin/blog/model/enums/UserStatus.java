package com.youthlin.blog.model.enums;

import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;

/**
 * 创建者： youthlin.chen 日期： 17-4-4.
 */
public enum UserStatus {
    NORMAL(0, "正常"), LOCKED(1, "锁定");

    private final int code;
    private final String describe;
    private static final Map<Integer, UserStatus> map;

    static {
        Map<Integer, UserStatus> all = Maps.newHashMap();
        UserStatus[] values = UserStatus.values();
        for (UserStatus v : values) {
            all.put(v.code, v);
        }
        map = Collections.unmodifiableMap(all);
    }

    UserStatus(int code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    public int getCode() {
        return code;
    }

    public String getDescribe() {
        return describe;
    }

    public static UserStatus codeOf(int code) {
        return map.get(code);
    }
}
