package com.youthlin.blog.util;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

/**
 * 创建： lin
 * 时间： 2017-05-05 14:35
 */
public class MD5Util {
    public static String md5(String source) {
        return Hashing.md5().hashString(source, Charsets.UTF_8).toString();
    }

}
