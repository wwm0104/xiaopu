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

 Date: 11/10/2016 16:53:34 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `user_vr_activitys`;
CREATE TABLE `user_vr_activitys` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `appointment_id` int(11) DEFAULT NULL,
  `appointment_date` varchar(12) DEFAULT NULL COMMENT '预约时间(2016-11-10)',
  `appointment_time` varchar(20) DEFAULT NULL COMMENT '预约时间段(9:00-10:00)',
  `appointment_code` varchar(6) DEFAULT NULL COMMENT '预约码',
  `activity_cnt` int(11) DEFAULT NULL COMMENT '活动体验次数',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `vr_activitys`
-- ----------------------------
DROP TABLE IF EXISTS `vr_activitys`;
CREATE TABLE `vr_activitys` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appointment_date` varchar(12) NOT NULL,
  `appointment_time` varchar(12) NOT NULL,
  `appointment_cnt` int(10) NOT NULL COMMENT '预约次数',
  `appointment_max_cnt` int(10) NOT NULL DEFAULT '10' COMMENT '预约最大次数',
  `available` tinyint(1) DEFAULT '1' COMMENT '是否启用；0：未启用，1：启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
