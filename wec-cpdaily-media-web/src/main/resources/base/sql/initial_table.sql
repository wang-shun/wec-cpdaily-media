
-- 自动回复设置表

drop table if exists `t_cpdaily_media_auto_msg`;

CREATE TABLE `t_cpdaily_media_auto_msg` (
  `media_id` varchar(36) NOT NULL COMMENT '媒体号编号',
  `title` varchar(200) DEFAULT NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `original_link` varchar(256) DEFAULT NULL COMMENT '原文链接',
  `img` varchar(256) DEFAULT NULL COMMENT '封面',
  `summary` varchar(500) DEFAULT NULL COMMENT '摘要',
  `local_url` varchar(256) DEFAULT NULL COMMENT '生成文章的链接',
  `owner_id` varchar(36) DEFAULT NULL COMMENT '所有者id',
  `tenant_id` varchar(36) DEFAULT NULL COMMENT '租户编号',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`media_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 用户标签表

drop table if exists `t_cpdaily_media_user_tag`;

CREATE TABLE `t_cpdaily_media_user_tag` (
  `tag_id` varchar(36) NOT NULL,
  `tag_name` varchar(200) NOT NULL,
  `media_id` varchar(36) NOT NULL,
  `owner_id` varchar(36) NOT NULL,
  `tenant_id` varchar(36) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

drop table if exists `t_cpdaily_media_user_tag_relation`;

CREATE TABLE `t_cpdaily_media_user_tag_relation` (
  `tag_id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `media_id` varchar(36) NOT NULL,
  `user_name` varchar(36) NOT NULL,
  `img_url` varchar(256),
  `create_time` datetime NOT NULL,
  UNIQUE KEY `uk_taguser_id` (`tag_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 媒体号增加类型 学生组织（CLUB）
ALTER TABLE `t_cpdaily_campus_media` MODIFY COLUMN `MEDIA_TYPE` enum('PERSONAL','OFFICIAL','COMPANY','CLUB');

-- 媒体号增加列
ALTER TABLE `t_cpdaily_campus_media` add COLUMN `auth_status` enum('UN_AUTH','AUTHING','AUTHED','AUTH_FAIL') default 'UN_AUTH';
ALTER TABLE `t_cpdaily_campus_media` add COLUMN `auth_materials` varchar(2000);
ALTER TABLE `t_cpdaily_campus_media` modify COLUMN `ICON` varchar(256);

-- 初始化原始数据为认证通过
update t_cpdaily_campus_media set auth_status = 'AUTHED';

INSERT INTO `t_cpdaily_syscodes` (`VALUE`,`DISPLAY`,`TYPE_ID`,`TYPE_NAME`,`SORT_NO`,`DESCRIPTION`) VALUES ('CLUB','学生组织','MEDIA_TYPE','校园号类型',0,NULL);

-- 媒体号申请限制 

drop table if exists `t_cpdaily_media_type_limit`;

CREATE TABLE `t_cpdaily_media_type_limit` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `tenant_id` varchar(36) NOT NULL,
  `user_type` enum('STUDENT','TEACHER') NOT NULL COMMENT '用户类型',
  `type_id` enum('PERSONAL','OFFICIAL','COMPANY','CLUB') NOT NULL COMMENT '媒体号类型',
  `limit_number` int(11) NOT NULL COMMENT '限制数量',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_user_type` (`tenant_id`,`user_type`,`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- 群发消息
drop table if exists `t_cpdaily_media_msg`;

CREATE TABLE `t_cpdaily_media_msg` (
  `wid` varchar(36) NOT NULL COMMENT '主键',
  `not_send_user_ids` varchar(4000) DEFAULT NULL COMMENT '屏蔽发送的用户编号',
  `title` varchar(200) DEFAULT NULL COMMENT '标题',
  `content` text,
  `summary` varchar(500) DEFAULT NULL COMMENT '摘要',
  `attachments` varchar(2000) DEFAULT '' COMMENT '附件地址_多',
  `img` varchar(256) DEFAULT NULL COMMENT '封面图',
  `original_link` varchar(512) DEFAULT '' COMMENT '原文连接',
  `local_url` varchar(200) DEFAULT '',
  `media_id` varchar(36) DEFAULT NULL COMMENT '发送媒体号的编号',
  `tenant_id` varchar(36) DEFAULT NULL COMMENT '租户编号',
  `c_user_id` varchar(36) DEFAULT NULL COMMENT '创建用户编号',
  `c_time` timestamp NULL DEFAULT NULL,
  `u_time` timestamp NULL DEFAULT NULL COMMENT '编辑时间',
  `send_count` int(11) DEFAULT '0',
  `receive_count` int(11) DEFAULT '0',
  `read_count` int(11) DEFAULT '0',
  `status` varchar(36) DEFAULT 'SEND' COMMENT '发送状态  SEND 未发送  SEND_IN  发送中 SEND_END 发送成功 SEND_ERROR 发送失败',
  `is_delete` int(11) DEFAULT '0' COMMENT '是否刪除0 未删除 1删除',
  PRIMARY KEY (`wid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

drop table if exists `t_cpdaily_media_msg_error`;

CREATE TABLE `t_cpdaily_media_msg_error` (
  `msg_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `user_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`msg_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

drop table if exists `t_cpdaily_media_msg_send_groups`;

CREATE TABLE `t_cpdaily_media_msg_send_groups` (
  `msg_id` varchar(36) NOT NULL,
  `group_id` varchar(36) NOT NULL,
  `group_type` varchar(36) NOT NULL COMMENT '组类型   院系专业 部门  标签',
  `group_name` varchar(200) DEFAULT NULL COMMENT '组名称',
  PRIMARY KEY (`msg_id`,`group_id`,`group_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



