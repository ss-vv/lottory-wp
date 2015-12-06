/*
Navicat MySQL Data Transfer

Source Server         : 大V彩
Source Server Version : 50152
Source Host           : 10.97.0.102:3306
Source Database       : db_account

Target Server Type    : MYSQL
Target Server Version : 50152
File Encoding         : 65001

Date: 2012-02-10 16:16:11
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `lt_account`
-- ----------------------------
DROP TABLE IF EXISTS `lt_account`;
CREATE TABLE `lt_account` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(32) DEFAULT NULL,
  `fund` decimal(12,2) unsigned NOT NULL DEFAULT '0.00',
  `_grant` decimal(12,2) unsigned NOT NULL DEFAULT '0.00',
  `free` decimal(12,2) unsigned NOT NULL DEFAULT '0.00',
  `frozen_fund` decimal(12,2) unsigned NOT NULL DEFAULT '0.00',
  `frozen_grant` decimal(12,2) unsigned NOT NULL DEFAULT '0.00',
  `total_recharge` decimal(12,2) unsigned NOT NULL DEFAULT '0.00',
  `total_withdraw` decimal(12,2) unsigned NOT NULL DEFAULT '0.00',
  `total_bet` decimal(12,2) unsigned NOT NULL DEFAULT '0.00',
  `total_award` decimal(12,2) unsigned NOT NULL DEFAULT '0.00',
  `province` varchar(16) DEFAULT NULL,
  `city` varchar(16) DEFAULT NULL,
  `bank` varchar(128) DEFAULT NULL,
  `account_number` varchar(32) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `verify_code` varchar(32) NOT NULL,
  `checked_time` datetime DEFAULT NULL,
  `status` smallint(5) unsigned NOT NULL DEFAULT '0',
  `version` int(11) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `lt_order`
-- ----------------------------
DROP TABLE IF EXISTS `lt_order`;
CREATE TABLE `lt_order` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `amount` decimal(12,2) unsigned NOT NULL DEFAULT '0.00',
  `deduction` decimal(12,2) unsigned NOT NULL DEFAULT '0.00',
  `fund_amount` decimal(12,2) unsigned NOT NULL DEFAULT '0.00',
  `grant_amount` decimal(12,2) unsigned NOT NULL DEFAULT '0.00',
  `fee` decimal(12,2) unsigned NOT NULL DEFAULT '0.00',
  `operator_id` int(11) unsigned NOT NULL DEFAULT '0',
  `created_time` datetime NOT NULL,
  `type` smallint(5) unsigned NOT NULL,
  `status` smallint(5) unsigned NOT NULL DEFAULT '1',
  `origin_id` bigint(20) DEFAULT NULL,
  `related_id` bigint(20) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=958 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `lt_recharge`
-- ----------------------------
DROP TABLE IF EXISTS `lt_recharge`;
CREATE TABLE `lt_recharge` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL,
  `channel_id` int(11) unsigned NOT NULL,
  `real_name` varchar(32) NOT NULL,
  `ip` varchar(32) NOT NULL,
  `amount` decimal(12,2) unsigned NOT NULL DEFAULT '0.00',
  `total_fee` decimal(12,2) unsigned NOT NULL DEFAULT '0.00',
  `channel_fee` decimal(12,2) unsigned NOT NULL DEFAULT '0.00',
  `pay_amount` decimal(12,2) unsigned NOT NULL DEFAULT '0.00',
  `bank_order` varchar(255) DEFAULT NULL,
  `channel_code` varchar(128) NOT NULL,
  `pay_time` datetime DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `status` smallint(5) unsigned NOT NULL DEFAULT '0',
  `created_time` datetime NOT NULL,
  `audit_time` datetime DEFAULT NULL,
  `audit_id` int(11) unsigned NOT NULL DEFAULT '0',
  `confirm_time` datetime DEFAULT NULL,
  `confirm_id` int(11) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `lt_withdraw`
-- ----------------------------
DROP TABLE IF EXISTS `lt_withdraw`;
CREATE TABLE `lt_withdraw` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL,
  `channel_id` int(11) unsigned NOT NULL,
  `real_name` varchar(32) NOT NULL,
  `ip` varchar(32) NOT NULL,
  `bank` varchar(128) NOT NULL,
  `bank_account` varchar(32) NOT NULL,
  `amount` decimal(12,2) unsigned NOT NULL DEFAULT '0.00',
  `total_fee` decimal(12,2) unsigned NOT NULL DEFAULT '0.00',
  `bank_fee` decimal(12,2) unsigned NOT NULL DEFAULT '0.00',
  `pay_amount` decimal(12,2) unsigned NOT NULL DEFAULT '0.00',
  `bank_order` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `status` smallint(5) unsigned NOT NULL DEFAULT '0',
  `created_time` datetime NOT NULL,
  `audit_time` datetime DEFAULT NULL,
  `audit_id` int(11) unsigned NOT NULL DEFAULT '0',
  `confirm_time` datetime DEFAULT NULL,
  `confirm_id` int(11) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of lt_withdraw
-- ----------------------------
