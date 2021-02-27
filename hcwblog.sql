/*创建数据库*/
CREATE DATABASE hcwblog CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

/*使用数据库*/
USE hcwblog;

/*创建文章分类表*/
CREATE TABLE `m_category`
(
    `id`               BIGINT(32)                              NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`             VARCHAR(512) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
    `content`          TEXT COLLATE utf8mb4_unicode_ci COMMENT '内容描述',
    `summary`          TEXT COLLATE utf8mb4_unicode_ci COMMENT '概要',
    `icon`             VARCHAR(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图标',
    `post_count`       INT(11) UNSIGNED                        DEFAULT '0' COMMENT '该分类的内容数量',
    `order_num`        INT(11)                                 DEFAULT NULL COMMENT '排序编码',
    `parent_id`        BIGINT(32) UNSIGNED                     DEFAULT NULL COMMENT '父级分类的ID',
    `meta_keywords`    VARCHAR(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'SEO关键字',
    `meta_description` VARCHAR(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'SEO描述内容',
    `created`          DATETIME                                DEFAULT NULL COMMENT '创建时间',
    `modified`         DATETIME                                DEFAULT NULL COMMENT '最后修改时间',
    `status`           TINYINT(2)                              DEFAULT NULL COMMENT '文章状态',
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = DYNAMIC;

INSERT INTO `m_category`(`id`, `name`, `content`, `summary`, `icon`, `post_count`, `order_num`, `parent_id`,
                         `meta_keywords`, `meta_description`, `created`, `modified`, `status`)
VALUES (1, '提问', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, '2020-04-28 22:36:48', NULL, 0);
INSERT INTO `m_category`(`id`, `name`, `content`, `summary`, `icon`, `post_count`, `order_num`, `parent_id`,
                         `meta_keywords`, `meta_description`, `created`, `modified`, `status`)
VALUES (2, '分享', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, '2020-04-28 22:36:48', NULL, 0);
INSERT INTO `m_category`(`id`, `name`, `content`, `summary`, `icon`, `post_count`, `order_num`, `parent_id`,
                         `meta_keywords`, `meta_description`, `created`, `modified`, `status`)
VALUES (3, '讨论', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, '2020-04-28 22:36:48', NULL, 0);
INSERT INTO `m_category`(`id`, `name`, `content`, `summary`, `icon`, `post_count`, `order_num`, `parent_id`,
                         `meta_keywords`, `meta_description`, `created`, `modified`, `status`)
VALUES (4, '建议', NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, '2020-04-28 22:36:48', NULL, 0);


/*创建文章评论表*/
CREATE TABLE `m_comment`
(
    `id`        BIGINT(32)                          NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `content`   LONGTEXT COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '评论的内容',
    `parent_id` BIGINT(32)                                   DEFAULT NULL COMMENT '回复的评论ID',
    `post_id`   BIGINT(32)                          NOT NULL COMMENT '评论的内容ID',
    `user_id`   BIGINT(32)                          NOT NULL COMMENT '评论的用户ID',
    `vote_up`   INT(11) UNSIGNED                    NOT NULL DEFAULT '0' COMMENT '“顶”的数量',
    `vote_down` INT(11) UNSIGNED                    NOT NULL DEFAULT '0' COMMENT '“踩”的数量',
    `level`     TINYINT(2) UNSIGNED                 NOT NULL DEFAULT '0' COMMENT '置顶等级',
    `status`    TINYINT(2)                                   DEFAULT NULL COMMENT '评论的状态',
    `created`   DATETIME                            NOT NULL COMMENT '评论的时间',
    `modified`  DATETIME                                     DEFAULT NULL COMMENT '评论的更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_post_id` (`post_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_post_user_id` (`post_id`, `user_id`)
) ENGINE = INNODB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = DYNAMIC;


/*创建文章表*/
CREATE TABLE `m_post`
(
    `id`            BIGINT(32)                              NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `title`         VARCHAR(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
    `content`       LONGTEXT COLLATE utf8mb4_unicode_ci     NOT NULL COMMENT '内容',
    `edit_mode`     VARCHAR(32) COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT '0' COMMENT '编辑模式：html可视化，markdown ..',
    `category_id`   BIGINT(32)                                       DEFAULT NULL,
    `user_id`       BIGINT(32)                                       DEFAULT NULL COMMENT '用户ID',
    `vote_up`       INT(11) UNSIGNED                        NOT NULL DEFAULT '0' COMMENT '支持人数',
    `vote_down`     INT(11) UNSIGNED                        NOT NULL DEFAULT '0' COMMENT '反对人数',
    `view_count`    INT(11) UNSIGNED                        NOT NULL DEFAULT '0' COMMENT '访问量',
    `comment_count` INT(11)                                 NOT NULL DEFAULT '0' COMMENT '评论数量',
    `recommend`     TINYINT(1)                                       DEFAULT '0' COMMENT '是否为精华',
    `level`         TINYINT(2)                              NOT NULL DEFAULT '0' COMMENT '置顶等级',
    `status`        TINYINT(2)                                       DEFAULT NULL COMMENT '状态',
    `created`       DATETIME                                         DEFAULT NULL COMMENT '创建日期',
    `modified`      DATETIME                                         DEFAULT NULL COMMENT '最后更新日期',
    PRIMARY KEY (`id`),
    KEY `idx_post_user_date` (`user_id`, `created`)
) ENGINE = INNODB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = DYNAMIC;


/*创建用户表*/
CREATE TABLE `m_user`
(
    `id`            BIGINT(32)                              NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `username`      VARCHAR(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '昵称',
    `password`      VARCHAR(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
    `email`         VARCHAR(64) COLLATE utf8mb4_unicode_ci           DEFAULT NULL COMMENT '邮件',
    `mobile`        VARCHAR(32) COLLATE utf8mb4_unicode_ci           DEFAULT NULL COMMENT '手机电话',
    `point`         INT(11) UNSIGNED                        NOT NULL DEFAULT '0' COMMENT '积分',
    `sign`          VARCHAR(255) COLLATE utf8mb4_unicode_ci          DEFAULT NULL COMMENT '个性签名',
    `gender`        INT(2)                                           DEFAULT '0' COMMENT '性别',
    `wechat`        VARCHAR(32) COLLATE utf8mb4_unicode_ci           DEFAULT NULL COMMENT '微信号',
    `vip_level`     INT(32)                                          DEFAULT NULL COMMENT 'vip等级',
    `birthday`      DATETIME                                         DEFAULT NULL COMMENT '生日',
    `avatar`        VARCHAR(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '头像',
    `post_count`    INT(11) UNSIGNED                        NOT NULL DEFAULT '0' COMMENT '内容数量',
    `comment_count` INT(11) UNSIGNED                        NOT NULL DEFAULT '0' COMMENT '评论数量',
    `status`        TINYINT(2)                              NOT NULL DEFAULT '0' COMMENT '状态',
    `lasted`        DATETIME                                         DEFAULT NULL COMMENT '最后的登陆时间',
    `created`       DATETIME                                NOT NULL COMMENT '创建时间',
    `modified`      DATETIME                                         DEFAULT NULL COMMENT '最后修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `username` (`username`) USING BTREE,
    UNIQUE KEY `email` (`email`) USING BTREE,
    KEY `idx_login_check` (`email`, `username`)
) ENGINE = INNODB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = DYNAMIC;

INSERT INTO `m_user`(`id`, `username`, `password`, `email`, `mobile`, `point`, `sign`, `gender`, `wechat`, `vip_level`,
                     `birthday`, `avatar`, `post_count`, `comment_count`, `status`, `lasted`, `created`, `modified`)
VALUES (1, '超级管理员', '6ec5c2edfd2e01c422ed38ed66a64b7ed240368bffea71edd46bd794b26642e7', 'admin@hcworld.xyz', NULL, 0,
        '', 1, NULL, 8, NULL, 'https://img.hcworld.xyz/code/duck/2021-02-03-b5188e9862ad4f61885984500a3921ee.jpg', 0, 0,
        100, '2021-02-27 12:04:55', '2021-01-15 16:23:53', NULL);


/*创建用户动作表*/
CREATE TABLE `m_user_action`
(
    `id`         BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`    VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户ID',
    `action`     VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '动作类型',
    `point`      INT(11)                                DEFAULT NULL COMMENT '得分',
    `post_id`    VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '关联的帖子ID',
    `comment_id` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '关联的评论ID',
    `created`    DATETIME                               DEFAULT NULL COMMENT '创建时间',
    `modified`   DATETIME                               DEFAULT NULL COMMENT '最后修改时间',
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = DYNAMIC;


/*创建文章收藏表*/
CREATE TABLE `m_user_collection`
(
    `id`           BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`      BIGINT(20) NOT NULL COMMENT '要收藏文章的用户ID',
    `post_id`      BIGINT(20) NOT NULL COMMENT '文章ID',
    `post_user_id` BIGINT(20) NOT NULL COMMENT '发表文章的用户ID',
    `created`      DATETIME   NOT NULL COMMENT '创建时间',
    `modified`     DATETIME   NOT NULL COMMENT '最后修改时间',
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = DYNAMIC;


/*创建用户消息表*/
CREATE TABLE `m_user_message`
(
    `id`           BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `from_user_id` BIGINT(20) NOT NULL COMMENT '发送消息的用户ID',
    `to_user_id`   BIGINT(20) NOT NULL COMMENT '接收消息的用户ID',
    `post_id`      BIGINT(20) DEFAULT NULL COMMENT '消息可能关联的帖子',
    `comment_id`   BIGINT(20) DEFAULT NULL COMMENT '消息可能关联的评论',
    `content`      TEXT COLLATE utf8mb4_unicode_ci,
    `type`         TINYINT(2) DEFAULT NULL COMMENT '消息类型',
    `created`      DATETIME   NOT NULL COMMENT '创建时间',
    `modified`     DATETIME   DEFAULT NULL COMMENT '最后修改时间',
    `status`       INT(11)    DEFAULT '0' COMMENT '消息类型0系统消息，1评论文章，2评论评论',
    PRIMARY KEY (`id`),
    KEY `idx_to_user_msg` (`to_user_id`),
    KEY `idx_comment_id` (`comment_id`)
) ENGINE = INNODB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = DYNAMIC;
