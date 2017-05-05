package com.youthlin.blog.util;

/**
 * 常量
 * <p>
 * 创建： lin
 * 时间： 2017-05-04 19:08
 */
public class Constant {
    // servlet
    public static final String UA = "User-Agent";
    public static final String TOKEN = "token";

    // user meta
    public static final String K_LOGIN_INFO = "login_info";
    public static final String K_NEXT_LOGIN_ID = "next_login_id";
    public static final String K_ROLE = "role";

    // options
    public static final String O_INSTALLED = "installed";
    public static final String O_BLOG_TITLE = "blog_title";

    // model
    public static final String MSG = "msg";
    public static final String ERROR = "error";

    public static final int RAND_LEN = 8;
    public static final int MD5_LEN = 32;
    public static final int PASS_LEN = RAND_LEN + MD5_LEN;

    public static final int DEFAULT_EXPIRE_DAYS_7 = 7;
}
