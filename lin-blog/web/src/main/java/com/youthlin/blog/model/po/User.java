package com.youthlin.blog.model.po;

import com.youthlin.blog.model.enums.UserStatus;

import java.util.Date;

/**
 * <pre>
 * DROP TABLE IF EXISTS `users`;
 * CREATE TABLE `users` (
 * `ID`              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT
 * COMMENT '自增主键',
 * `user_login`      VARCHAR(32)     NOT NULL DEFAULT ''
 * COMMENT '用户名，字母或数字或下划线，字母开头，最多 32 字符',
 * `user_pass`       CHAR(32)        NOT NULL DEFAULT ''
 * COMMENT '密码，MD5 加密后的密码，32 位',
 * `user_email`      VARCHAR(64)     NOT NULL DEFAULT ''
 * COMMENT '邮件地址，用于显示头像,最多 64 个字符',
 * `user_url`        VARCHAR(128)    NOT NULL DEFAULT ''
 * COMMENT '用户评论的显示名字链接到这个 URL 地址',
 * `user_registered` DATETIME        NOT NULL DEFAULT now()
 * COMMENT '注册时间',
 * `user_status`     TINYINT         NOT NULL DEFAULT '0'
 * COMMENT '账号状态，0正常，1锁定',
 * `display_name`    VARCHAR(250)    NOT NULL DEFAULT ''
 * COMMENT '显示名',
 * PRIMARY KEY (`ID`),
 * KEY `user_login_key` (`user_login`),
 * KEY `user_email` (`user_email`)
 * )
 * ENGINE = InnoDB
 * DEFAULT CHARSET = utf8mb4
 * COLLATE = utf8mb4_unicode_ci
 * COMMENT '用户表';
 * </pre>
 */
public class User {
    private Long userId;
    private String userLogin;
    private String userPass;
    private String userEmail = "";
    private String userUrl = "";
    private Date userRegistered = new Date();
    private UserStatus userStatus = UserStatus.NORMAL;
    private String displayName = "";

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userLogin='" + userLogin + '\'' +
                ", userPass='" + userPass + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userUrl='" + userUrl + '\'' +
                ", userRegistered=" + userRegistered +
                ", userStatus=" + userStatus +
                ", displayName='" + displayName + '\'' +
                '}';
    }

    //region getter and setter
    public Long getUserId() {
        return userId;
    }

    public User setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public User setUserLogin(String userLogin) {
        this.userLogin = userLogin;
        return this;
    }

    public String getUserPass() {
        return userPass;
    }

    public User setUserPass(String userPass) {
        this.userPass = userPass;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public User setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public User setUserUrl(String userUrl) {
        this.userUrl = userUrl;
        return this;
    }

    public Date getUserRegistered() {
        return userRegistered;
    }

    public User setUserRegistered(Date userRegistered) {
        this.userRegistered = userRegistered;
        return this;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public User setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public User setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }
    //endregion

}
