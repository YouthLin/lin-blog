package com.youthlin.blog.model.enums;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Map;

import static com.youthlin.utils.i18n.Translation.__;

/**
 * 创建： lin
 * 时间： 2017-05-05 21:11
 */
@SuppressWarnings("unused")
public enum Role {
    Administrator(40),//设置、工具

    Editor(30),//页面、可以编辑其他人文章、分类、标签

    Author(20),//list自己的文章、发文章

    Contributor(10),//能看到概览、list 自己的文章、写草稿

    Subscriber(0),;//能看到自己的评论

    private static final Map<Integer, Role> map;
    private static final Map<String, Role> nameMap;
    private final int code;

    Role(int code) {
        this.code = code;
    }

    static {
        Map<Integer, Role> roleMap = Maps.newHashMap();
        Map<String, Role> stringRoleMap = Maps.newHashMap();
        Role[] values = Role.values();
        for (Role r : values) {
            roleMap.put(r.code, r);
            stringRoleMap.put(r.name(), r);
        }
        map = ImmutableMap.copyOf(roleMap);
        nameMap = ImmutableMap.copyOf(stringRoleMap);
        __("Administrator");
        __("Editor");
        __("Author");
        __("Contributor");
        __("Subscriber");
    }

    public int getCode() {
        return code;
    }

    public static Role codeOf(int code) {
        return map.get(code);
    }

    public static Role nameOf(String name) {
        return nameMap.get(name);
    }
}
