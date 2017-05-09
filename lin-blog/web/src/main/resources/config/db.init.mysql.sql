/*DROP TABLE IF EXISTS `users`;*/
CREATE TABLE IF NOT EXISTS `users` (
  `ID`              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '自增主键',
  `user_login`      VARCHAR(32)     NOT NULL DEFAULT '' UNIQUE
  COMMENT '用户名，字母或数字或下划线，字母开头，最多 32 字符',
  `user_pass`       CHAR(40)        NOT NULL DEFAULT ''
  COMMENT '密码，MD5 加密后的密码，32 位+8 rand. user_pass = rand+md5(rand+md5(user+pass))',
  `user_email`      VARCHAR(64)     NOT NULL DEFAULT ''
  COMMENT '邮件地址，用于显示头像,最多 64 个字符',
  `user_url`        VARCHAR(128)    NOT NULL DEFAULT ''
  COMMENT '用户评论的显示名字链接到这个 URL 地址',
  `user_registered` DATETIME        NOT NULL DEFAULT now()
  COMMENT '注册时间',
  `user_status`     TINYINT         NOT NULL DEFAULT '0'
  COMMENT '账号状态，0正常，1锁定',
  `display_name`    VARCHAR(250)    NOT NULL DEFAULT ''
  COMMENT '显示名',
  PRIMARY KEY (`ID`),
  KEY `user_login_key` (`user_login`),
  KEY `user_email` (`user_email`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  COMMENT '用户表';

/*DROP TABLE IF EXISTS `user_meta`;*/
CREATE TABLE IF NOT EXISTS `user_meta` (
  `meta_id`    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '自增主键',
  `user_id`    BIGINT UNSIGNED NOT NULL DEFAULT '0'
  COMMENT '用户 ID',
  `meta_key`   VARCHAR(255)             DEFAULT NULL
  COMMENT '元数据 key',
  `meta_value` LONGTEXT COMMENT '元数据 value',
  PRIMARY KEY (`meta_id`),
  KEY `user_id` (`user_id`),
  KEY `meta_key` (`meta_key`(191))
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  COMMENT '用户元数据';

/*DROP TABLE IF EXISTS `posts`;*/
CREATE TABLE IF NOT EXISTS `posts` (
  `ID`                BIGINT UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '自增主键',
  `post_author`       BIGINT UNSIGNED NOT NULL DEFAULT '0'
  COMMENT '作者 ID',
  `post_date`         DATETIME        NOT NULL DEFAULT now()
  COMMENT '发表时间',
  `post_date_utc`     DATETIME        NOT NULL DEFAULT now()
  COMMENT 'UTC 发表时间 (UTC_TIMESTAMP: http://stackoverflow.com/a/19112227)',
  `post_content`      LONGTEXT        NOT NULL
  COMMENT '文章/页面内容',
  `post_title`        TEXT            NOT NULL
  COMMENT '标题',
  `post_excerpt`      TEXT            NOT NULL
  COMMENT '摘要',
  `post_status`       TINYINT         NOT NULL DEFAULT 0
  COMMENT '状态， 0 发表， 1草稿， ',
  `comment_open`      TINYINT         NOT NULL DEFAULT 1
  COMMENT '是否可评论 1是 0否',
  `ping_open`         TINYINT         NOT NULL DEFAULT 1
  COMMENT '是否允许 ping, 1是 0否',
  `post_password`     VARCHAR(20)     NOT NULL DEFAULT ''
  COMMENT '密码',
  `post_name`         VARCHAR(200)    NOT NULL DEFAULT ''
  COMMENT '显示在 URL 中，通常是标题经过 URL 编码后的字符串',
  `post_modified`     DATETIME        NOT NULL DEFAULT now()
  COMMENT '修改时间',
  `post_modified_utc` DATETIME        NOT NULL DEFAULT now()
  COMMENT '修改时间 UTC',
  `post_parent`       BIGINT UNSIGNED NOT NULL DEFAULT '0'
  COMMENT '父 post ，如页面可以有父级页面',
  `post_type`         INT             NOT NULL DEFAULT '0'
  COMMENT '类型 0post/1page/2attachment/...',
  `post_mime_type`    VARCHAR(100)    NOT NULL DEFAULT ''
  COMMENT '附件的 MIME 类型',
  `comment_count`     BIGINT          NOT NULL DEFAULT '0'
  COMMENT '评论数量',
  PRIMARY KEY (`ID`),
  KEY `post_name` (`post_name`(191)),
  KEY `type_status_date` (`post_type`, `post_status`, `post_date`, `ID`),
  KEY `post_parent` (`post_parent`),
  KEY `post_author` (`post_author`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  COMMENT '文章，页面，附件等 post ';

/*DROP TABLE IF EXISTS `post_meta`;*/
CREATE TABLE IF NOT EXISTS `post_meta` (
  `meta_id`    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '自增主键',
  `post_id`    BIGINT UNSIGNED NOT NULL DEFAULT '0'
  COMMENT 'post id',
  `meta_key`   VARCHAR(255)             DEFAULT NULL,
  `meta_value` LONGTEXT,
  PRIMARY KEY (`meta_id`),
  KEY `post_id` (`post_id`),
  KEY `meta_key` (`meta_key`(191))
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  COMMENT 'post 元数据';

/*DROP TABLE IF EXISTS `comments`;*/
CREATE TABLE IF NOT EXISTS `comments` (
  `comment_ID`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '自增主键',
  `comment_post_ID`      BIGINT UNSIGNED NOT NULL DEFAULT '0'
  COMMENT 'post id',
  `comment_author`       TINYTEXT        NOT NULL
  COMMENT '评论者姓名',
  `comment_author_email` VARCHAR(64)     NOT NULL DEFAULT ''
  COMMENT '评论者邮件',
  `comment_author_url`   VARCHAR(128)    NOT NULL DEFAULT ''
  COMMENT '评论者URL',
  `comment_author_IP`    INT             NOT NULL DEFAULT '0'
  COMMENT 'inet_aton("10.10.10.10")=168430090, inet_ntoa(168430090)="10.10.10.10"',
  `comment_author_IPv6`  BINARY(16)      NOT NULL DEFAULT '0'
  COMMENT 'inet6_aton("FE80:0000:0000:0000:0202:B3FF:FE1E:8329")=0xFE800000000000000202B3FFFE1E8329, inet6_ntoa()',
  `comment_date`         DATETIME        NOT NULL DEFAULT now()
  COMMENT '评论时间',
  `comment_date_utc`     DATETIME        NOT NULL DEFAULT now()
  COMMENT 'UTC 时间',
  `comment_content`      TEXT            NOT NULL
  COMMENT '评论内容',
  `comment_status`       TINYINT         NOT NULL DEFAULT '0'
  COMMENT '评论状态，0正常，1垃圾评论',
  `comment_agent`        VARCHAR(255)    NOT NULL DEFAULT ''
  COMMENT '评论者浏览器 UA',
  `comment_type`         TINYINT         NOT NULL DEFAULT '0'
  COMMENT '评论类型，0-正常评论，1-pingback',
  `comment_parent`       BIGINT UNSIGNED NOT NULL DEFAULT '0'
  COMMENT '回复给哪条评论',
  `user_id`              BIGINT UNSIGNED NOT NULL DEFAULT '0'
  COMMENT '用户ID，游客则为0',
  PRIMARY KEY (`comment_ID`),
  KEY `comment_post_ID` (`comment_post_ID`),
  KEY `comment_approved_date_utc` (`comment_status`, `comment_date_utc`),
  KEY `comment_date_utc` (`comment_date_utc`),
  KEY `comment_parent` (`comment_parent`),
  KEY `comment_author_email` (`comment_author_email`(10))
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  COMMENT '评论表';

/*DROP TABLE IF EXISTS `comment_meta`;*/
CREATE TABLE IF NOT EXISTS `comment_meta` (
  `meta_id`    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `comment_id` BIGINT UNSIGNED NOT NULL DEFAULT '0',
  `meta_key`   VARCHAR(255)             DEFAULT NULL,
  `meta_value` LONGTEXT,
  PRIMARY KEY (`meta_id`),
  KEY `comment_id` (`comment_id`),
  KEY `meta_key` (`meta_key`(191))
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  COMMENT '评论元数据';

/*DROP TABLE IF EXISTS `taxonomy`;*/
CREATE TABLE IF NOT EXISTS `taxonomy` (
  `taxonomy_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '自增主键',
  `name`        VARCHAR(200)    NOT NULL DEFAULT ''
  COMMENT '分类名',
  `slug`        VARCHAR(200)    NOT NULL DEFAULT ''
  COMMENT '分类URL',
  `taxonomy`    VARCHAR(32)     NOT NULL DEFAULT ''
  COMMENT '分类法，如 category / tag / format ...',
  `description` LONGTEXT        NOT NULL
  COMMENT '描述',
  `parent`      BIGINT UNSIGNED NOT NULL DEFAULT '0'
  COMMENT '父级分类',
  `count`       BIGINT          NOT NULL DEFAULT '0'
  COMMENT '该分类下 post 数量',
  PRIMARY KEY (`taxonomy_id`),
  UNIQUE KEY `uniq_name_taxonomy` (`name`, `taxonomy`),
  KEY `taxonomy` (`taxonomy`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  COMMENT '分类信息表';

/*DROP TABLE IF EXISTS `taxonomy_relationships`;*/
CREATE TABLE IF NOT EXISTS `taxonomy_relationships` (
  `ID`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `post_id`     BIGINT UNSIGNED NOT NULL DEFAULT '0'
  COMMENT 'post ID',
  `taxonomy_id` BIGINT UNSIGNED NOT NULL DEFAULT '0'
  COMMENT '分类 ID',
  PRIMARY KEY (`ID`),
  KEY (`post_id`, `taxonomy_id`),
  KEY `term_taxonomy_id` (`taxonomy_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  COMMENT 'post - 分类 关联表';

/*DROP TABLE IF EXISTS `options`;*/
CREATE TABLE IF NOT EXISTS `options` (
  `option_id`    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '自增主键',
  `option_name`  VARCHAR(191)    NOT NULL DEFAULT ''
  COMMENT '设置项',
  `option_value` LONGTEXT        NOT NULL
  COMMENT '设置项内容',
  PRIMARY KEY (`option_id`),
  UNIQUE KEY `option_name` (`option_name`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  COMMENT '设置表';
