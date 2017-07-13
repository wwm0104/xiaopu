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

 Date: 11/05/2016 10:50:30 AM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

ALTER TABLE `user_info`
ADD COLUMN `user_sex` TINYINT(1) NULL DEFAULT 1 COMMENT '用户性别，1：男，2：女' AFTER `real_name`;

SET FOREIGN_KEY_CHECKS = 1;
