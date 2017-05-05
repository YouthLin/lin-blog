package com.youthlin.blog.model.enums;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 创建： lin
 * 时间： 2017-05-05 21:11
 */
public enum Role {
    Administrator(0), Editor(1), Author(2), Contributor(3), Subscriber(4),;
    private static final Map<Integer, Role> map;
    private final int code;

    Role(int code) {
        this.code = code;
    }

    static {
        Map<Integer, Role> roleMap = Maps.newHashMap();
        Role[] values = Role.values();
        for (Role r : values) {
            roleMap.put(r.code, r);
        }
        map = ImmutableMap.copyOf(roleMap);
    }

    public int getCode() {
        return code;
    }

    public static Role codeOf(int code) {
        return map.get(code);
    }
}
