package com.youthlin.blog.model.bo;

import com.youthlin.blog.util.Constant;
import com.youthlin.blog.util.ServletUtil;

import javax.servlet.http.HttpServletRequest;
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

    public String toCookieValue() {
        return id + ":" + userName + ":" + token + ":" + expire.getTime();
    }

    public static LoginInfo fromRequest(HttpServletRequest request) {
        String loginToken = ServletUtil.getCookieValue(request, Constant.TOKEN);
        if (loginToken == null) {
            return null;
        }
        String[] split = loginToken.split(":");
        if (split.length != 4) {
            return null;
        }
        String ip = ServletUtil.getRemoteIP(request);
        String ua = request.getHeader(Constant.UA);
        try {
            int id = Integer.parseInt(split[0]);
            String userName = split[1];
            String token = split[2];
            long time = Long.parseLong(split[3]);
            Date expire = new Date(time);
            return new LoginInfo()
                    .setId(id)
                    .setUserName(userName)
                    .setUserAgent(ua)
                    .setUserIp(ip)
                    .setToken(token)
                    .setExpire(expire);
        } catch (NumberFormatException e) {
            return null;
        }
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginInfo loginInfo = (LoginInfo) o;

        if (id != loginInfo.id) return false;
        if (!userName.equals(loginInfo.userName)) return false;
        if (!userAgent.equals(loginInfo.userAgent)) return false;
        if (!userIp.equals(loginInfo.userIp)) return false;
        //noinspection SimplifiableIfStatement
        if (!token.equals(loginInfo.token)) return false;
        return expire.equals(loginInfo.expire);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userName.hashCode();
        result = 31 * result + userAgent.hashCode();
        result = 31 * result + userIp.hashCode();
        result = 31 * result + token.hashCode();
        result = 31 * result + expire.hashCode();
        return result;
    }
}
