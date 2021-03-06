package com.youthlin.blog.model.bo;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.youthlin.blog.util.Constant;
import com.youthlin.blog.util.ServletUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 用户 Cookie
 * <p>
 * 创建： lin
 * 时间： 2017-05-05 16:30
 */
@SuppressWarnings("unused")
public class LoginInfo {
    private static final String SPLITTER_STR = ":";
    private static final Splitter SPLITTER = Splitter.on(SPLITTER_STR);
    private static final Joiner JOINER = Joiner.on(SPLITTER_STR);
    private String  id;
    private String userName;
    private String userAgent;
    private String userIp;
    private String token;
    private Date expire;

    public String toCookieValue() {
        String s = JOINER.join(id, userName, token, expire.getTime());
        return ServletUtil.base64Encode(s);
    }

    public static LoginInfo fromRequest(HttpServletRequest request) {
        String loginToken = ServletUtil.getCookieValue(request, Constant.TOKEN);
        if (loginToken == null) {
            return null;
        }
        loginToken = ServletUtil.base64Decode(loginToken);
        List<String> split = SPLITTER.splitToList(loginToken);
        if (split.size() != 4) {
            return null;
        }
        String ip = ServletUtil.getRemoteIP(request);
        String ua = request.getHeader(Constant.UA);
        try {
            String id = split.get(0);
            String userName = split.get(1);
            String token = split.get(2);
            long time = Long.parseLong(split.get(3));
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

    public String getId() {
        return id;
    }

    public LoginInfo setId(String id) {
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

        if (!id.equals(loginInfo.id)) return false;
        if (!userName.equals(loginInfo.userName)) return false;
        if (!userAgent.equals(loginInfo.userAgent)) return false;
        if (!userIp.equals(loginInfo.userIp)) return false;
        //noinspection SimplifiableIfStatement
        if (!token.equals(loginInfo.token)) return false;
        return expire.equals(loginInfo.expire);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + userName.hashCode();
        result = 31 * result + userAgent.hashCode();
        result = 31 * result + userIp.hashCode();
        result = 31 * result + token.hashCode();
        result = 31 * result + expire.hashCode();
        return result;
    }
}
