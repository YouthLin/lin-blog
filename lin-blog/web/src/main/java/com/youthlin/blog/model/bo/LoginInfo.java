package com.youthlin.blog.model.bo;

import java.util.Date;

/**
 * 用户 Cookie
 * <p>
 * 创建： lin
 * 时间： 2017-05-05 16:30
 */
public class LoginInfo {
    private int id;
    private String userName;
    private String userAgent;
    private String userIp;
    private String token;
    private Date expire;

    @Override
    public String toString() {
        return "LoginInfo{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", userIp='" + userIp + '\'' +
                ", token='" + token + '\'' +
                ", expire=" + expire +
                '}';
    }

    public int getId() {
        return id;
    }

    public LoginInfo setId(int id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public LoginInfo setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public LoginInfo setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public String getUserIp() {
        return userIp;
    }

    public LoginInfo setUserIp(String userIp) {
        this.userIp = userIp;
        return this;
    }

    public String getToken() {
        return token;
    }

    public LoginInfo setToken(String token) {
        this.token = token;
        return this;
    }

    public Date getExpire() {
        return expire;
    }

    public LoginInfo setExpire(Date expire) {
        this.expire = expire;
        return this;
    }
}
