/*
 Navicat Premium Data Transfer

 Source Server         : xiaopu_dev
 Source Server Type    : MySQL
 Source Server Version : 50715
 Source Host           : 10.25.18.254
 Source Database       : xiaopu

 Target Server Type    : MySQL
 Target Server Version : 50715
 File Encoding         : utf-8

 Date: 12/12/2016 13:13:47 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `anchor_position`
-- ----------------------------
DROP TABLE IF EXISTS `anchor_position`;
CREATE TABLE `anchor_position` (
  `anchor_id` int(11) NOT NULL,
  `position_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `channels`
-- ----------------------------
DROP TABLE IF EXISTS `channels`;
CREATE TABLE `channels` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `slogan` varchar(255) DEFAULT NULL,
  `desc` varchar(1000) DEFAULT NULL,
  `poster_img` varchar(50) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `more` int(1) DEFAULT '0' COMMENT '是否显示更多,0:不显示，1：显示不服来战,2:其他',
  `type` int(1) DEFAULT '1' COMMENT 'type = 1 :图文父频道  type= 2 :音频父频道',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `channel_anchor`
-- ----------------------------
DROP TABLE IF EXISTS `channel_anchor`;
CREATE TABLE `channel_anchor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `channel_id` int(11) NOT NULL COMMENT '频道ID',
  `anchor_id` int(11) NOT NULL COMMENT '主播ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `channel_associated`
-- ----------------------------
DROP TABLE IF EXISTS `channel_associated`;
CREATE TABLE `channel_associated` (
  `p_channel_id` int(11) NOT NULL,
  `channel_id` int(11) NOT NULL,
  `sort` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `event_lottery`
-- ----------------------------
DROP TABLE IF EXISTS `event_lottery`;
CREATE TABLE `event_lottery` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(11) NOT NULL,
  `event_name` varchar(255) DEFAULT NULL,
  `stauts` tinyint(1) DEFAULT '0' COMMENT '抽奖状态，0：未抽奖，1：抽奖结束',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `event_lottery_user`
-- ----------------------------
DROP TABLE IF EXISTS `event_lottery_user`;
CREATE TABLE `event_lottery_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `lottery_id` int(11) NOT NULL,
  `event_id` int(11) NOT NULL,
  `event_name` varchar(255) DEFAULT NULL,
  `round` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '抽奖轮次。',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `event_user_msgs`
-- ----------------------------
DROP TABLE IF EXISTS `event_user_msgs`;
CREATE TABLE `event_user_msgs` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `event_id` int(11) NOT NULL,
  `event_name` varchar(255) DEFAULT NULL,
  `content` varchar(400) NOT NULL,
  `type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '消息类型，1：文字，2：图片',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4mb4;

-- ----------------------------
--  Table structure for `notifys`
-- ----------------------------
DROP TABLE IF EXISTS `notifys`;
CREATE TABLE `notifys` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(1000) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL COMMENT '消息的类型，1: 公告 Announce，2: 提醒 Remind，3：信息 Message',
  `target_id` int(11) DEFAULT NULL COMMENT '目标ID',
  `target_type` varchar(255) DEFAULT NULL COMMENT '目标类型;1:用户，2：活动，3：社团，4：图文，5:奖品',
  `action` int(11) DEFAULT NULL COMMENT '提醒信息的动作类型',
  `sender` int(11) DEFAULT NULL COMMENT '发送者ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `parameter` varchar(255) DEFAULT NULL COMMENT '消息参数,JSON格式',
  `further` varchar(4000) DEFAULT NULL COMMENT '扩展字段 JSON格式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `positions`
-- ----------------------------
DROP TABLE IF EXISTS `positions`;
CREATE TABLE `positions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `position_name` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT '1' COMMENT '类型，暂时不用',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `is_official` tinyint(1) DEFAULT '1' COMMENT '是否官方职位，0:非官方，1：官方',
  `available` tinyint(1) DEFAULT '1' COMMENT '是否启用；0：未启用，1：启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `recommend`
-- ----------------------------
DROP TABLE IF EXISTS `recommend`;
CREATE TABLE `recommend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL,
  `sort` int(5) NOT NULL COMMENT '越大 推荐越靠前',
  `recommend_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `parent_type` int(5) NOT NULL DEFAULT '1' COMMENT '1:频道热门推荐 2:首页图文推荐 3:首页奖品推荐4：音频贴推荐 5:音频置顶',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `tickets`
-- ----------------------------
DROP TABLE IF EXISTS `tickets`;
CREATE TABLE `tickets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ticket_name` varchar(50) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `business_id` int(11) NOT NULL DEFAULT '1' COMMENT '对应类型的ID，1：活动门票(business_id对应为event_id)；其他待定',
  `business_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '门票类型',
  `ticket_cnt` int(11) DEFAULT NULL COMMENT '门票数量',
  `remaining_cnt` int(11) DEFAULT NULL COMMENT '剩余数量',
  `create_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `expire_time` datetime DEFAULT NULL COMMENT '失效时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `topic_play`
-- ----------------------------
DROP TABLE IF EXISTS `topic_play`;
CREATE TABLE `topic_play` (
  `topic_id` int(11) NOT NULL,
  `play_cnt` int(11) DEFAULT '0' COMMENT '播放数量',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`topic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `user_channel_subscribe`
-- ----------------------------
DROP TABLE IF EXISTS `user_channel_subscribe`;
CREATE TABLE `user_channel_subscribe` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `channel_id` int(11) NOT NULL,
  `subscribe_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `user_fans`
-- ----------------------------
DROP TABLE IF EXISTS `user_fans`;
CREATE TABLE `user_fans` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `fans_id` int(11) NOT NULL COMMENT '粉丝ID',
  `is_focus` varchar(255) DEFAULT '0' COMMENT '是否相互关注,0:未相互关注,1:相互关注',
  `foncus_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`,`user_id`,`fans_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `user_focus_anchor`
-- ----------------------------
DROP TABLE IF EXISTS `user_focus_anchor`;
CREATE TABLE `user_focus_anchor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `focus_user_id` int(11) NOT NULL,
  `focus_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `user_notify`
-- ----------------------------
DROP TABLE IF EXISTS `user_notify`;
CREATE TABLE `user_notify` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `is_read` tinyint(255) DEFAULT '0' COMMENT '是否阅读；0：未读，1：已读',
  `user_id` int(11) NOT NULL COMMENT '用户消息所属者',
  `notify_id` int(11) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_delete` tinyint(255) DEFAULT '0' COMMENT '是否删除，0：未删除：1:删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `user_ticket`
-- ----------------------------
DROP TABLE IF EXISTS `user_ticket`;
CREATE TABLE `user_ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `ticket_id` int(11) NOT NULL,
  `qrcode` varchar(255) DEFAULT NULL,
  `business_id` int(11) NOT NULL COMMENT '业务类型，根据type判断所属哪个业务。',
  `business_type` tinyint(1) DEFAULT '1' COMMENT '对应类型的ID，1：活动门票(business_id对应为event_id)；其他待定',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) DEFAULT '0' COMMENT '扫码状态，0：未扫码；1：已扫码; 2：已失效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

insert into `roles` ( `id`, `role_key`, `role_name`, `create_by`, `create_time`, `update_by`, `update_time`, `available`) values ( '4', 'anchor', '主播', '1', '2016-12-07 10:52:20', '1', '2016-12-07 10:52:25', '1');

SET FOREIGN_KEY_CHECKS = 1;
