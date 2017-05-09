package com.youthlin.blog.util;

/**
 * 常量
 * <p>
 * 创建： lin
 * 时间： 2017-05-04 19:08
 */
@SuppressWarnings("WeakerAccess")
public class Constant {
    public static final String DASH = "—";
    // servlet
    public static final String UA = "User-Agent";
    public static final String TOKEN = "token";
    public static final String USER = "user";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final int DEFAULT_EXPIRE_DAYS_7 = 7;
    public static final int DEFAULT_EXPIRE_DAYS_7_SECOND = DEFAULT_EXPIRE_DAYS_7 * 24 * 3600;

    // user meta
    public static final String K_LOGIN_INFO = "login_info";
    public static final String K_NEXT_LOGIN_ID = "next_login_id";
    public static final String K_ROLE = "role";

    // options
    public static final String O_BLOG_TITLE = "blog_title";
    public static final String O_ALL_CATEGORIES = "all_categories";


    // model
    public static final String MSG = "msg";
    public static final String ERROR = "error";

    public static final int RAND_LEN = 8;
    public static final int MD5_LEN = 32;
    public static final int PASS_LEN = RAND_LEN + MD5_LEN;

}
