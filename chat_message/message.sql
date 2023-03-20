DROP TABLE IF EXISTS `chat_message`;
CREATE TABLE `chat_message`
(
    `id`           bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `status`       varchar(1)   DEFAULT NULL COMMENT '状态',
    `content`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '消息内容',
    `type`         tinyint(1) DEFAULT NULL COMMENT '是否显示时间 0：否 1:是',
    `content_type` tinyint(1) DEFAULT NULL COMMENT '内容类型 0文字1图片2视频',
    `is_read`      varchar(1)   DEFAULT NULL COMMENT '是否已读',
    `sender`       bigint       DEFAULT NULL COMMENT '发送人',
    `receiver`     bigint       DEFAULT NULL COMMENT '接收人',
    `open_id`      varchar(64)  DEFAULT NULL COMMENT '请求人', ,
    `is_last`      tinyint(1) DEFAULT NULL COMMENT '是否是最后一条消息 0:否 1:是',
    `remarks`      varchar(200) DEFAULT NULL COMMENT '备注',
    `created_by`   varchar(100) DEFAULT NULL COMMENT '创建人',
    `created_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `updated_by`   varchar(100) DEFAULT NULL COMMENT '更新人',
    `updated_time` datetime     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=205 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='业务消息表';


DROP TABLE IF EXISTS `chat_user`;
CREATE TABLE `chat_user`
(
    `id`           bigint NOT NULL AUTO_INCREMENT,
    `name`         varchar(255) COLLATE utf8mb4_general_ci  DEFAULT NULL,
    `avatar`       varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `open_id`      varchar(64)                              DEFAULT NULL,
    `union_id`     varchar(64)                              DEFAULT NULL,
    `my_sign`      varchar(255) COLLATE utf8mb4_general_ci  DEFAULT NULL,
    `gender`       varchar(2)                               DEFAULT NULL,
    `remarks`      varchar(200)                             DEFAULT NULL COMMENT '备注',
    `created_by`   varchar(100)                             DEFAULT NULL COMMENT '创建人',
    `created_time` datetime                                 DEFAULT NULL COMMENT '创建时间',
    `updated_by`   varchar(100)                             DEFAULT NULL COMMENT '更新人',
    `updated_time` datetime                                 DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
