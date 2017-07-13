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

 Date: 11/10/2016 18:33:06 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `award_presens`
-- ----------------------------
DROP TABLE IF EXISTS `award_presens`;
CREATE TABLE `award_presens` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `real_name` varchar(50) NOT NULL COMMENT '发奖人姓名',
  `mobile` bigint(11) NOT NULL COMMENT '发奖人手机',
  `official_name` varchar(50) NOT NULL COMMENT '官方负责人',
  `official_mobile` bigint(11) NOT NULL COMMENT '负责人手机',
  `award_cnt` int(11) DEFAULT '0' COMMENT '发奖次数',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `available` tinyint(1) DEFAULT '1' COMMENT '是否启用；0：未启用，1：启用',
  `remarks` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `dict`
-- ----------------------------
DROP TABLE IF EXISTS `dict`;
CREATE TABLE `dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` int(9) NOT NULL,
  `value` varchar(1000) NOT NULL,
  `sort` int(9) NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',
  `valid` tinyint(1) NOT NULL DEFAULT '1' COMMENT '0:有效；1:无效',
  `parent_id` int(11) DEFAULT NULL,
  `other` varchar(2000) DEFAULT NULL COMMENT '其他，JSON格式存储',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典表';

-- ----------------------------
--  Table structure for `invitation_code`
-- ----------------------------
DROP TABLE IF EXISTS `invitation_code`;
CREATE TABLE `invitation_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(10) DEFAULT NULL,
  `partner_id` int(11) DEFAULT NULL,
  `user_cnt` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合伙人邀请码表';

-- ----------------------------
--  Table structure for `partner_group`
-- ----------------------------
DROP TABLE IF EXISTS `partner_group`;
CREATE TABLE `partner_group` (
  `partner_id` int(11) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='社团合伙人对应社团中间表';

-- ----------------------------
--  Table structure for `pk_channel`
-- ----------------------------
DROP TABLE IF EXISTS `pk_channel`;
CREATE TABLE `pk_channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `slogan` varchar(255) NOT NULL,
  `desc` varchar(1000) NOT NULL,
  `poster_img` varchar(50) DEFAULT NULL,
  `type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1:通用型；2.其他模版待定',
  `create_id` int(11) DEFAULT NULL COMMENT '非官方频道申请人ID',
  `create_realname` varchar(255) DEFAULT NULL COMMENT '非官方频道申请人姓名',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '-1:无效；1：有效；0:待审核',
  `is_official` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1：官方；0：非官方',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `pk_prize_result`
-- ----------------------------
DROP TABLE IF EXISTS `pk_prize_result`;
CREATE TABLE `pk_prize_result` (
  `pk_id` int(11) NOT NULL,
  `code` varchar(36) NOT NULL COMMENT '奖品编码',
  `prize_name` varchar(255) NOT NULL,
  `prize_type` tinyint(1) NOT NULL DEFAULT '0',
  `prize_num` int(5) NOT NULL DEFAULT '0',
  `challenge_topic_id` int(11) NOT NULL,
  `challenge_topic_slogan` varchar(255) DEFAULT NULL,
  `reward_user_id` int(11) NOT NULL COMMENT '得奖者id',
  `reward_user_nickname` varchar(255) DEFAULT NULL COMMENT '得奖者',
  `reward_user_realname` varchar(255) DEFAULT NULL COMMENT '得奖者',
  `reward_user_avatar` varchar(255) DEFAULT NULL COMMENT '得奖者头像',
  `effective_time` datetime NOT NULL,
  `expire_time` datetime DEFAULT NULL COMMENT '奖品失效时间',
  `take_time` datetime NOT NULL,
  `has_take` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0:未领取；1:领取',
  PRIMARY KEY (`pk_id`,`reward_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户最终得到的奖品，奖品发放表';

-- ----------------------------
--  Table structure for `pk_prize_take_log`
-- ----------------------------
DROP TABLE IF EXISTS `pk_prize_take_log`;
CREATE TABLE `pk_prize_take_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `award_user_id` int(11) NOT NULL COMMENT '发放奖品的人id',
  `pk_id` int(11) NOT NULL COMMENT 'PK的ID',
  `reward_user_id` int(11) NOT NULL COMMENT '得奖人Id',
  `take_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预付奖品物资，每增加一个PK，库存出库+1（针对实物类）';

-- ----------------------------
--  Table structure for `pk_result`
-- ----------------------------
DROP TABLE IF EXISTS `pk_result`;
CREATE TABLE `pk_result` (
  `pk_id` int(11) NOT NULL,
  `challenge_topic_id` int(11) NOT NULL,
  `reward_user_id` int(11) NOT NULL COMMENT '得奖者id',
  `reward_user_nickname` varchar(255) DEFAULT NULL COMMENT '得奖者',
  `reward_user_realname` varchar(255) DEFAULT NULL COMMENT '得奖者',
  `reward_user_avatar` varchar(255) DEFAULT NULL COMMENT '得奖者头像',
  `ranking` tinyint(5) NOT NULL DEFAULT '0' COMMENT '排名',
  `vote_cnt` tinyint(9) NOT NULL DEFAULT '0' COMMENT '投票总数',
  `is_finish` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否符合pk规则，达到奖励，达不到不奖励;0:未达到奖励指标；1:达到奖励指标',
  `finish_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '生成结果时间',
  PRIMARY KEY (`pk_id`,`reward_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='PK结果表,奖品物资需要计数出库量';

-- ----------------------------
--  Table structure for `pk_vote_result`
-- ----------------------------
DROP TABLE IF EXISTS `pk_vote_result`;
CREATE TABLE `pk_vote_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pk_id` int(11) NOT NULL,
  `topic_id` int(11) NOT NULL,
  `topic_slogan` varchar(255) NOT NULL COMMENT '冗余',
  `creator_id` int(11) NOT NULL COMMENT '参与人员id',
  `creator_nickname` varchar(255) NOT NULL,
  `vote_cnt` int(9) NOT NULL DEFAULT '0',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`,`pk_id`,`topic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `pk_votes`
-- ----------------------------
DROP TABLE IF EXISTS `pk_votes`;
CREATE TABLE `pk_votes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pk_id` int(11) NOT NULL,
  `topic_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `user_nickname` varchar(255) DEFAULT NULL,
  `user_avatar` varchar(255) DEFAULT NULL,
  `type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1:投；-1:踩',
  `vote_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`,`pk_id`,`user_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='PK投票明细，每PK仅有一次投票（投／踩）插入的同时，更新result表，计算得奖前核实两表数据是否平衡';

-- ----------------------------
--  Table structure for `prizes`
-- ----------------------------
DROP TABLE IF EXISTS `prizes`;
CREATE TABLE `prizes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '奖品名称',
  `prize` varchar(4000) NOT NULL COMMENT '奖品以JSON格式保存，目前包含{ 奖励描述、奖品图片等}',
  `type` tinyint(1) NOT NULL COMMENT '1:现金；2:实物；3:虚拟物品；4:校谱红包',
  `is_public` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0:对外部公开，即pk奖品由官方提供；1:不对外部公开，即非官方提供奖品；2:校谱专用；其他待定',
  `available_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '生效时间',
  `expire_time` datetime NOT NULL COMMENT '失效时间',
  `creater_id` int(11) NOT NULL,
  `creater_realname` varchar(255) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `has_limit` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1有限制【需要操作库存】；0没限制',
  `stock_total` int(6) DEFAULT '0' COMMENT '总库存',
  `stock_out` int(6) DEFAULT '0' COMMENT '已分配出奖品数量',
  `available` tinyint(4) DEFAULT '0' COMMENT '是否启用；0：不启用，1：启用。',
  PRIMARY KEY (`id`,`creater_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='奖品物资';

-- ----------------------------
--  Table structure for `prizes_imprest`
-- ----------------------------
DROP TABLE IF EXISTS `prizes_imprest`;
CREATE TABLE `prizes_imprest` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topic_id` int(11) NOT NULL,
  `pk_id` int(11) NOT NULL,
  `prize_id` int(11) DEFAULT NULL,
  `stock_out` int(6) NOT NULL DEFAULT '0' COMMENT '待分配出奖品数量',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预占位奖品物资，每增加一个PK，库存出库+1（针对实物类）';

-- ----------------------------
--  Table structure for `recommend_event`
-- ----------------------------
DROP TABLE IF EXISTS `recommend_event`;
CREATE TABLE `recommend_event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(11) NOT NULL,
  `organize_id` int(11) NOT NULL,
  `organize_name` varchar(255) NOT NULL,
  `event_subject` varchar(255) NOT NULL,
  `poster_img` varchar(255) NOT NULL,
  `sort` int(5) NOT NULL DEFAULT '0' COMMENT '越大越靠前',
  `recommend_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='推荐活动表';

-- ----------------------------
--  Table structure for `tipoff`
-- ----------------------------
DROP TABLE IF EXISTS `tipoff`;
CREATE TABLE `tipoff` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topic_id` int(11) NOT NULL,
  `desc` varchar(500) NOT NULL,
  `tipoff_user_id` int(11) NOT NULL,
  `tipoff_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='举报记录';

-- ----------------------------
--  Table structure for `topic_comment`
-- ----------------------------
DROP TABLE IF EXISTS `topic_comment`;
CREATE TABLE `topic_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topic_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `user_nickname` varchar(60) NOT NULL,
  `user_avatar_url` varchar(60) DEFAULT NULL COMMENT '用户头像',
  `comment` varchar(2000) NOT NULL COMMENT '评论，JSON,{url:{[],[]}, desc:""}',
  `parent_id` int(11) DEFAULT NULL COMMENT '评论引用，暂不实现',
  `like_cnt` int(6) NOT NULL DEFAULT '0',
  `action_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论';

-- ----------------------------
--  Table structure for `topic_fav`
-- ----------------------------
DROP TABLE IF EXISTS `topic_fav`;
CREATE TABLE `topic_fav` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topic_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `user_nickname` varchar(255) NOT NULL,
  `fav_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '收藏时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户收藏功能';

-- ----------------------------
--  Table structure for `topic_hot`
-- ----------------------------
DROP TABLE IF EXISTS `topic_hot`;
CREATE TABLE `topic_hot` (
  `topic_id` int(11) NOT NULL,
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '1.运营推荐（置顶）??；0.最热，按照点赞数量',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`topic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='热门主题，当天数据，100条这里取';

-- ----------------------------
--  Table structure for `topic_like`
-- ----------------------------
DROP TABLE IF EXISTS `topic_like`;
CREATE TABLE `topic_like` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topic_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `user_nickname` varchar(255) NOT NULL,
  `like_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '点赞时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='点赞无取消点赞功能';

-- ----------------------------
--  Table structure for `topic_new`
-- ----------------------------
DROP TABLE IF EXISTS `topic_new`;
CREATE TABLE `topic_new` (
  `topic_id` int(11) NOT NULL COMMENT '最新话题',
  `type` tinyint(1) NOT NULL COMMENT '0.按时间最新；1.运营推荐；',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`topic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='最新主题,前50条这里取，后续数据从主表取得';

-- ----------------------------
--  Table structure for `topic_pk`
-- ----------------------------
DROP TABLE IF EXISTS `topic_pk`;
CREATE TABLE `topic_pk` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topic_id` int(11) NOT NULL COMMENT '擂主贴ID',
  `period_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1:当天；2:当周；3:当月；4:当季；5当年；0：自定义（目前需要大于12小时）',
  `prize_id` int(11) DEFAULT NULL COMMENT '奖品编号',
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '按period计算最后参与时间',
  `end_time` datetime NOT NULL COMMENT '按period计算最后参与时间',
  `reward_type` tinyint(1) NOT NULL COMMENT '0:无奖品；1:排名最高者得全部(小额／小件／短期)；2:前三得80%（200元以上），第一：60%	，第二：15%	，第三：5%，	其余20%（原则每人均分比例不超过第三的50%，即2.5%）',
  `rule` varchar(4000) NOT NULL COMMENT '规则说明',
  `expire_time` datetime DEFAULT NULL COMMENT 'null:奖品无失效时间（主要为现金）；奖品失效时间，超过奖品无法领取，依据奖品设置计算',
  `is_finish` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'PK奖励结果是否完成：0：没完成，1:完成',
  `further` varchar(4000) DEFAULT NULL COMMENT '扩展字段，JSON格式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='PK定义表';

-- ----------------------------
--  Table structure for `topic_ranking`
-- ----------------------------
DROP TABLE IF EXISTS `topic_ranking`;
CREATE TABLE `topic_ranking` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '0:无效；1:有效',
  `topic_id` int(11) NOT NULL,
  `vote_cnt` int(11) NOT NULL DEFAULT '0',
  `type` int(9) NOT NULL DEFAULT '0' COMMENT '1.图文热度排名；2.PK热度排名；3.图文最新排名；4：频道热度排名',
  `parent_type` int(9) NOT NULL DEFAULT '0' COMMENT 'type=1 or 2(1：日榜；2：周版；3：月榜),type=4(parent_type=具体的频道ID)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1有效；0过期；每次更新榜单把前期数据设置为过期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='热门主题，当天数据，100条这里取';

-- ----------------------------
--  Table structure for `topic_tags`
-- ----------------------------
DROP TABLE IF EXISTS `topic_tags`;
CREATE TABLE `topic_tags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topic_id` int(11) NOT NULL,
  `tag_type` tinyint(1) NOT NULL COMMENT '1:社团；2:活动',
  `target_id` int(11) NOT NULL COMMENT ' 关联对象ID',
  `target_name` varchar(255) DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '待用字段 1:当前有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='topic主表插入JSON同时这里插入明细';

-- ----------------------------
--  Table structure for `topics`
-- ----------------------------
DROP TABLE IF EXISTS `topics`;
CREATE TABLE `topics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creator_id` int(11) NOT NULL,
  `creator_nickname` varchar(255) DEFAULT '无名',
  `creator_avatar` varchar(255) NOT NULL COMMENT '头像，没有使用默认图标',
  `school_id` int(11) DEFAULT NULL,
  `school_name` varchar(255) DEFAULT NULL,
  `challenge_topic_id` int(11) DEFAULT NULL COMMENT '擂主贴',
  `is_challenger` tinyint(1) DEFAULT '-1' COMMENT '-1:非PK贴，1:是擂主；0:挑战者;',
  `challenge_id` int(11) DEFAULT NULL COMMENT '擂主id',
  `challenge_nickname` varchar(255) DEFAULT NULL COMMENT '擂主昵称',
  `challenge_avatar` varchar(255) DEFAULT NULL COMMENT '擂主头像',
  `channel_id` int(11) DEFAULT NULL COMMENT '频道id',
  `channel_name` varchar(100) DEFAULT NULL COMMENT '频道名称',
  `type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1:图文；2:视频；3:直播;4.其他',
  `slogan` varchar(100) DEFAULT NULL COMMENT '擂台口号',
  `content` varchar(4000) NOT NULL COMMENT 'JSON格式存储topic{slogan:"",urls:[{},{}],desc:""}',
  `recommend` tinyint(1) NOT NULL DEFAULT '0' COMMENT '大于0，表示推荐越大越靠前',
  `like_cnt` int(9) NOT NULL DEFAULT '0' COMMENT '点赞总数，每个话题，一个人只有一次点赞',
  `favorite_cnt` int(9) NOT NULL DEFAULT '0' COMMENT '收藏数 ',
  `comment_cnt` int(9) NOT NULL DEFAULT '0' COMMENT '评论数',
  `is_official` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0:非官方；1:官方',
  `is_delete` tinyint(1) NOT NULL DEFAULT '1' COMMENT '0:已删除；1:正常',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间，long',
  `update_time` datetime NOT NULL COMMENT '最后更新时间，计数变化需要更新计数时间',
  `expire_time` datetime DEFAULT NULL COMMENT 'null：无失效时间；（目前仅有PK贴设置失效时间），long',
  `is_pk` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0不是，1是',
  `further` varchar(4000) DEFAULT NULL COMMENT '扩展字段，JSON格式',
  `status` tinyint(1) DEFAULT '1' COMMENT '审核状态。0：待审核，1：审核通过，2：审核未通过',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='内容话题，预期包含图文、短视频、直播一个月前的设置为冷数据，后续实现冷热数据分表';

-- ----------------------------
--  Table structure for `user_invitation_code`
-- ----------------------------
DROP TABLE IF EXISTS `user_invitation_code`;
CREATE TABLE `user_invitation_code` (
  `user_id` int(11) NOT NULL,
  `invitation_code` varchar(10) DEFAULT NULL,
  `user_code` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `user_ranking`
-- ----------------------------
DROP TABLE IF EXISTS `user_ranking`;
CREATE TABLE `user_ranking` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '0:无效；1:有效',
  `user_id` int(11) NOT NULL,
  `user_nickname` varchar(50) DEFAULT NULL,
  `user_avatar_url` varchar(60) DEFAULT NULL,
  `vote_cnt` int(11) NOT NULL DEFAULT '0',
  `type` int(9) NOT NULL DEFAULT '0' COMMENT '1：不服榜，2：达人榜',
  `parent_type` int(9) NOT NULL DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1有效；0过期；每次更新榜单把前期数据设置为过期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户排名';

DROP TABLE IF EXISTS `event_ranking`;
CREATE TABLE `event_ranking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(11) NOT NULL,
  `topic_cnt` int(11) NOT NULL DEFAULT '0',
  `type` int(9) NOT NULL DEFAULT '0' COMMENT '1.活动排名',
  `parent_type` int(9) NOT NULL DEFAULT '0' COMMENT '1：日榜；2：周版；3：月榜',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1有效；0过期；每次更新榜单把前期数据设置为过期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动排名';

ALTER TABLE `partners`
ADD COLUMN `sex` TINYINT(1) NULL DEFAULT 1 COMMENT '性别,1:男，2：女' AFTER `real_name`,
ADD COLUMN `school_id` INT(11) NULL AFTER `sex`,
ADD COLUMN `school_name` VARCHAR(50) NULL AFTER `school_id`,
ADD COLUMN `star_rating` TINYINT(1) NULL COMMENT '审核星级，1-5星' AFTER `school_name`,
ADD COLUMN `remarks` VARCHAR(255) NULL AFTER `check_time`;

SET FOREIGN_KEY_CHECKS = 1;


