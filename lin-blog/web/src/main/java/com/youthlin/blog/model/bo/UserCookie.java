package com.youthlin.blog.model.bo;

/**
 * 用户 Cookie
 * <p>
 * 创建： lin
 * 时间： 2017-05-05 16:30
 */
public class UserCookie {
    private int id;
    private String userName;
    private String userAgent;
    private String userIp;
    private String token;

    @Override
    public String toString() {
        return "UserCookie{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", userIp='" + userIp + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public UserCookie setId(int id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserCookie setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public UserCookie setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public String getUserIp() {
        return userIp;
    }

    public UserCookie setUserIp(String userIp) {
        this.userIp = userIp;
        return this;
    }

    public String getToken() {
        return token;
    }

    public UserCookie setToken(String token) {
        this.token = token;
        return this;
    }
}
