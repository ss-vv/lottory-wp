/*
Navicat MySQL Data Transfer

Source Server         : 大V彩
Source Server Version : 50152
Source Host           : 10.97.0.102:3306
Source Database       : db_lottery

Target Server Type    : MYSQL
Target Server Version : 50152
File Encoding         : 65001

Date: 2012-02-10 16:16:03
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `bb_match`
-- ----------------------------
DROP TABLE IF EXISTS `bb_match`;
CREATE TABLE `bb_match` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `cn_code` varchar(16) NOT NULL,
  `code` varchar(16) NOT NULL,
  `name` varchar(128) NOT NULL,
  `league_id` int(11) NOT NULL DEFAULT '0',
  `league_name` varchar(64) NOT NULL,
  `playing_time` datetime NOT NULL,
  `offtime` datetime NOT NULL,
  `entrust_deadline` datetime NOT NULL,
  `home_team_id` int(11) NOT NULL DEFAULT '0',
  `home_team_name` varchar(64) NOT NULL,
  `guest_team_id` int(11) NOT NULL DEFAULT '0',
  `guest_team_name` varchar(64) NOT NULL,
  `status` smallint(5) NOT NULL DEFAULT '0',
  `quarter1` varchar(16) NOT NULL DEFAULT '0:0',
  `quarter2` varchar(16) NOT NULL DEFAULT '0:0',
  `quarter3` varchar(16) NOT NULL DEFAULT '0:0',
  `quarter4` varchar(16) NOT NULL DEFAULT '0:0',
  `final_score` varchar(16) NOT NULL DEFAULT '0:0',
  `note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `bb_match_play`
-- ----------------------------
DROP TABLE IF EXISTS `bb_match_play`;
CREATE TABLE `bb_match_play` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `match_id` bigint(20) NOT NULL,
  `play_id` varchar(16) DEFAULT NULL,
  `options` varchar(255) DEFAULT NULL,
  `odds` varchar(255) NOT NULL,
  `prior_odds` varchar(255) DEFAULT NULL,
  `status` smallint(5) NOT NULL DEFAULT '0',
  `win_bonus` decimal(5,2) NOT NULL DEFAULT '0.00',
  `win_option` varchar(16) DEFAULT NULL,
  `default_score` float(10,1) NOT NULL DEFAULT '0.0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `fb_match`
-- ----------------------------
DROP TABLE IF EXISTS `fb_match`;
CREATE TABLE `fb_match` (
  `id` bigint(20) unsigned NOT NULL,
  `cn_code` varchar(16) NOT NULL,
  `code` varchar(16) NOT NULL,
  `name` varchar(128) NOT NULL,
  `league_id` int(11) unsigned NOT NULL DEFAULT '0',
  `league_name` varchar(64) NOT NULL,
  `playing_time` datetime NOT NULL,
  `offtime` datetime NOT NULL,
  `entrust_deadline` datetime NOT NULL,
  `home_team_id` int(11) unsigned NOT NULL DEFAULT '0',
  `home_team_name` varchar(64) NOT NULL,
  `guest_team_id` int(11) unsigned NOT NULL DEFAULT '0',
  `guest_team_name` varchar(64) NOT NULL,
  `status` smallint(5) NOT NULL DEFAULT '0',
  `concede_points` smallint(5) NOT NULL DEFAULT '0',
  `half_score` varchar(16) NOT NULL DEFAULT '0:0',
  `score` varchar(16) NOT NULL DEFAULT '0:0',
  `note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `offtime` (`offtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `fb_match_play`
-- ----------------------------
DROP TABLE IF EXISTS `fb_match_play`;
CREATE TABLE `fb_match_play` (
  `id` varchar(32) NOT NULL,
  `match_id` bigint(20) NOT NULL,
  `play_id` varchar(16) NOT NULL,
  `options` varchar(128) NOT NULL,
  `odds` varchar(255) NOT NULL,
  `prior_odds` varchar(255) DEFAULT NULL,
  `status` smallint(5) unsigned NOT NULL DEFAULT '0',
  `win_bonus` decimal(5,2) unsigned NOT NULL DEFAULT '0.00',
  `win_option` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `match_id` (`match_id`),
  KEY `play_id` (`play_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `lt_admin`
-- ----------------------------
DROP TABLE IF EXISTS `lt_admin`;
CREATE TABLE `lt_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL,
  `real_name` varchar(16) NOT NULL,
  `password` varchar(32) NOT NULL,
  `mobile` varchar(16) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `status` int(11) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of lt_admin
-- ----------------------------
INSERT INTO `lt_admin` VALUES ('1', 'root', '超级管理员', '6de4331045e392361641133cb4fdb5cf', null, null, '0');


-- ----------------------------
-- Table structure for `lt_bet_match`
-- ----------------------------
DROP TABLE IF EXISTS `lt_bet_match`;
CREATE TABLE `lt_bet_match` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `scheme_id` bigint(20) unsigned NOT NULL,
  `match_id` int(11) NOT NULL,
  `code` varchar(512) NOT NULL,
  `odds` varchar(1024) NOT NULL,
  `bonus` decimal(12,2) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `scheme_id` (`scheme_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1047 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `lt_bet_partner`
-- ----------------------------
DROP TABLE IF EXISTS `lt_bet_partner`;
CREATE TABLE `lt_bet_partner` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `scheme_id` bigint(20) unsigned NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL,
  `username` varchar(32) NOT NULL DEFAULT ' ',
  `bet_amount` int(11) unsigned NOT NULL DEFAULT '0',
  `win_amount` decimal(12,2) unsigned DEFAULT NULL,
  `created_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `scheme_id` (`scheme_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=382 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `lt_bet_scheme`
-- ----------------------------
DROP TABLE IF EXISTS `lt_bet_scheme`;
CREATE TABLE `lt_bet_scheme` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `sponsor_id` bigint(20) NOT NULL,
  `sponsor` varchar(32) NOT NULL DEFAULT ' ',
  `lottery_id` varchar(16) NOT NULL,
  `play_id` varchar(16) NOT NULL,
  `pass_type_ids` varchar(1024) NOT NULL DEFAULT '',
  `status` smallint(5) unsigned NOT NULL DEFAULT '0',
  `sale_status` smallint(5) unsigned NOT NULL DEFAULT '0',
  `type` smallint(5) unsigned NOT NULL DEFAULT '0',
  `share_ratio` smallint(5) unsigned NOT NULL DEFAULT '0',
  `privacy` smallint(5) unsigned NOT NULL DEFAULT '0',
  `total_amount` int(11) unsigned NOT NULL DEFAULT '0',
  `buy_amount` int(11) unsigned NOT NULL DEFAULT '0',
  `floor_amount` int(11) unsigned NOT NULL DEFAULT '0',
  `purchased_amount` int(11) unsigned NOT NULL DEFAULT '0',
  `bet_note` int(11) unsigned NOT NULL DEFAULT '1',
  `match_number` int(11) unsigned NOT NULL DEFAULT '1',
  `multiple` int(11) unsigned NOT NULL DEFAULT '1',
  `win_note` int(11) unsigned NOT NULL DEFAULT '0',
  `cancel_note` int(11) NOT NULL DEFAULT '0',
  `ticket_note` int(11) NOT NULL DEFAULT '0',
  `ticket_count` int(11) NOT NULL DEFAULT '0',
  `max_bonus` decimal(12,2) NOT NULL DEFAULT '0.00',
  `expect_bonus` decimal(12,2) unsigned NOT NULL DEFAULT '0.00',
  `pre_tax_bonus` decimal(12,2) unsigned NOT NULL DEFAULT '0.00',
  `after_tax_bonus` decimal(12,2) unsigned NOT NULL DEFAULT '0.00',
  `offtime` datetime NOT NULL,
  `created_time` datetime NOT NULL,
  `partner_count` int(5) NOT NULL,
  `version` int(11) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `play_id` (`play_id`),
  KEY `sponsor_id` (`sponsor_id`),
  KEY `created_time` (`created_time`)
) ENGINE=InnoDB AUTO_INCREMENT=383 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `lt_grant`
-- ----------------------------
DROP TABLE IF EXISTS `lt_grant`;
CREATE TABLE `lt_grant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `amount` decimal(12,2) NOT NULL DEFAULT '0.00',
  `created_time` datetime NOT NULL,
  `operator_id` int(11) NOT NULL,
  `audit_id` int(11) DEFAULT NULL,
  `audit_time` datetime DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `status` smallint(5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=89 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `lt_lottery`
-- ----------------------------
DROP TABLE IF EXISTS `lt_lottery`;
CREATE TABLE `lt_lottery` (
  `id` varchar(16) NOT NULL,
  `name` varchar(32) NOT NULL,
  `win_level` smallint(5) NOT NULL DEFAULT '1',
  `help_url` varchar(255) DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of lt_lottery
-- ----------------------------
INSERT INTO `lt_lottery` VALUES ('JCZQ', '竞彩足球', '1', null, '2011-12-07 17:49:57', null);
INSERT INTO `lt_lottery` VALUES ('JCLQ', '竞彩篮球', '1', null, '2011-12-07 17:51:14', null);

-- ----------------------------
-- Table structure for `lt_pass_type`
-- ----------------------------
DROP TABLE IF EXISTS `lt_pass_type`;
CREATE TABLE `lt_pass_type` (
  `id` varchar(16) NOT NULL,
  `name` varchar(16) NOT NULL,
  `note` int(11) unsigned NOT NULL,
  `index` smallint(5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of lt_pass_type
-- ----------------------------
INSERT INTO `lt_pass_type` VALUES ('1@1', '1串1', '1', '1001');
INSERT INTO `lt_pass_type` VALUES ('2@1', '2串1', '1', '2001');
INSERT INTO `lt_pass_type` VALUES ('3@1', '3串1', '1', '3001');
INSERT INTO `lt_pass_type` VALUES ('3@3', '3串3', '3', '3003');
INSERT INTO `lt_pass_type` VALUES ('3@4', '3串4', '4', '3004');
INSERT INTO `lt_pass_type` VALUES ('4@1', '4串1', '1', '4001');
INSERT INTO `lt_pass_type` VALUES ('4@11', '4串11', '11', '4011');
INSERT INTO `lt_pass_type` VALUES ('4@4', '4串4', '4', '4004');
INSERT INTO `lt_pass_type` VALUES ('4@5', '4串5', '5', '4005');
INSERT INTO `lt_pass_type` VALUES ('4@6', '4串6', '6', '4006');
INSERT INTO `lt_pass_type` VALUES ('5@1', '5串1', '1', '5001');
INSERT INTO `lt_pass_type` VALUES ('5@10', '5串10', '10', '5010');
INSERT INTO `lt_pass_type` VALUES ('5@16', '5串16', '16', '5016');
INSERT INTO `lt_pass_type` VALUES ('5@20', '5串20', '20', '5020');
INSERT INTO `lt_pass_type` VALUES ('5@26', '5串26', '26', '5026');
INSERT INTO `lt_pass_type` VALUES ('5@5', '5串5', '5', '5005');
INSERT INTO `lt_pass_type` VALUES ('5@6', '5串6', '6', '5006');
INSERT INTO `lt_pass_type` VALUES ('6@1', '6串1', '1', '6001');
INSERT INTO `lt_pass_type` VALUES ('6@15', '6串15', '15', '6015');
INSERT INTO `lt_pass_type` VALUES ('6@20', '6串20', '20', '6020');
INSERT INTO `lt_pass_type` VALUES ('6@22', '6串22', '22', '6022');
INSERT INTO `lt_pass_type` VALUES ('6@35', '6串35', '35', '6035');
INSERT INTO `lt_pass_type` VALUES ('6@42', '6串42', '42', '6042');
INSERT INTO `lt_pass_type` VALUES ('6@50', '6串50', '50', '6050');
INSERT INTO `lt_pass_type` VALUES ('6@57', '6串57', '57', '6057');
INSERT INTO `lt_pass_type` VALUES ('6@6', '6串6', '6', '6006');
INSERT INTO `lt_pass_type` VALUES ('6@7', '6串7', '7', '6007');
INSERT INTO `lt_pass_type` VALUES ('7@1', '7串1', '1', '7001');
INSERT INTO `lt_pass_type` VALUES ('7@120', '7串120', '120', '7120');
INSERT INTO `lt_pass_type` VALUES ('7@21', '7串21', '21', '7021');
INSERT INTO `lt_pass_type` VALUES ('7@35', '7串35', '35', '7035');
INSERT INTO `lt_pass_type` VALUES ('7@7', '7串7', '7', '7007');
INSERT INTO `lt_pass_type` VALUES ('7@8', '7串8', '8', '7008');
INSERT INTO `lt_pass_type` VALUES ('8@1', '8串1', '1', '8001');
INSERT INTO `lt_pass_type` VALUES ('8@247', '8串247', '247', '8247');
INSERT INTO `lt_pass_type` VALUES ('8@28', '8串28', '28', '8028');
INSERT INTO `lt_pass_type` VALUES ('8@56', '8串56', '56', '8056');
INSERT INTO `lt_pass_type` VALUES ('8@70', '8串70', '70', '8070');
INSERT INTO `lt_pass_type` VALUES ('8@8', '8串8', '8', '8008');
INSERT INTO `lt_pass_type` VALUES ('8@9', '8串9', '9', '8009');

-- ----------------------------
-- Table structure for `lt_play`
-- ----------------------------
DROP TABLE IF EXISTS `lt_play`;
CREATE TABLE `lt_play` (
  `id` varchar(16) NOT NULL,
  `lottery_id` varchar(16) NOT NULL,
  `name` varchar(32) NOT NULL,
  `floor_ratio` smallint(5) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `lottery_id` (`lottery_id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of lt_play
-- ----------------------------
INSERT INTO `lt_play` VALUES ('01_ZC_1', 'JCZQ', '胜平负单关', '0');
INSERT INTO `lt_play` VALUES ('01_ZC_2', 'JCZQ', '胜平负过关', '0');
INSERT INTO `lt_play` VALUES ('02_ZC_1', 'JCZQ', '比分单关', '0');
INSERT INTO `lt_play` VALUES ('02_ZC_2', 'JCZQ', '比分过关', '0');
INSERT INTO `lt_play` VALUES ('03_ZC_1', 'JCZQ', '总进球数单关', '0');
INSERT INTO `lt_play` VALUES ('03_ZC_2', 'JCZQ', '总进球数过关', '0');
INSERT INTO `lt_play` VALUES ('04_ZC_1', 'JCZQ', '半全场胜平负单关', '0');
INSERT INTO `lt_play` VALUES ('04_ZC_2', 'JCZQ', '半全场胜平负过关', '0');
INSERT INTO `lt_play` VALUES ('06_LC_1', 'JCLQ', '让分胜负单关', '0');
INSERT INTO `lt_play` VALUES ('06_LC_2', 'JCLQ', '让分胜负过关', '0');
INSERT INTO `lt_play` VALUES ('07_LC_1', 'JCLQ', '胜负单关', '0');
INSERT INTO `lt_play` VALUES ('07_LC_2', 'JCLQ', '胜负过关', '0');
INSERT INTO `lt_play` VALUES ('08_LC_1', 'JCLQ', '胜分差单关', '0');
INSERT INTO `lt_play` VALUES ('08_LC_2', 'JCLQ', '胜分差过关', '0');
INSERT INTO `lt_play` VALUES ('09_LC_1', 'JCLQ', '大小分单关', '0');
INSERT INTO `lt_play` VALUES ('09_LC_2', 'JCLQ', '大小分过关', '0');

-- ----------------------------
-- Table structure for `lt_play_option`
-- ----------------------------
DROP TABLE IF EXISTS `lt_play_option`;
CREATE TABLE `lt_play_option` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `play_id` varchar(16) NOT NULL,
  `value` varchar(2) NOT NULL,
  `name` varchar(32) NOT NULL,
  `status` smallint(5) NOT NULL DEFAULT '0',
  `note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `play_id` (`play_id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=141 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of lt_play_option
-- ----------------------------
INSERT INTO `lt_play_option` VALUES ('1', '01_ZC_1', '3', '胜', '0', '');
INSERT INTO `lt_play_option` VALUES ('2', '01_ZC_1', '1', '平', '0', '');
INSERT INTO `lt_play_option` VALUES ('3', '01_ZC_1', '0', '负', '0', '');
INSERT INTO `lt_play_option` VALUES ('4', '01_ZC_2', '3', '胜', '0', '');
INSERT INTO `lt_play_option` VALUES ('5', '01_ZC_2', '1', '平', '0', '');
INSERT INTO `lt_play_option` VALUES ('6', '01_ZC_2', '0', '负', '0', '');
INSERT INTO `lt_play_option` VALUES ('8', '02_ZC_1', '10', '1:0', '0', '');
INSERT INTO `lt_play_option` VALUES ('9', '02_ZC_1', '20', '2:0', '0', '');
INSERT INTO `lt_play_option` VALUES ('10', '02_ZC_1', '21', '2:1', '0', '');
INSERT INTO `lt_play_option` VALUES ('11', '02_ZC_1', '30', '3:0', '0', '');
INSERT INTO `lt_play_option` VALUES ('12', '02_ZC_1', '31', '3:1', '0', '');
INSERT INTO `lt_play_option` VALUES ('13', '02_ZC_1', '32', '3:2', '0', '');
INSERT INTO `lt_play_option` VALUES ('14', '02_ZC_1', '40', '4:0', '0', '');
INSERT INTO `lt_play_option` VALUES ('15', '02_ZC_1', '41', '4:1', '0', '');
INSERT INTO `lt_play_option` VALUES ('16', '02_ZC_1', '42', '4:2', '0', '');
INSERT INTO `lt_play_option` VALUES ('17', '02_ZC_1', '50', '5:0', '0', '');
INSERT INTO `lt_play_option` VALUES ('18', '02_ZC_1', '51', '5:1', '0', '');
INSERT INTO `lt_play_option` VALUES ('19', '02_ZC_1', '52', '5:2', '0', '');
INSERT INTO `lt_play_option` VALUES ('20', '02_ZC_1', '90', '胜其他', '0', '');
INSERT INTO `lt_play_option` VALUES ('21', '02_ZC_1', '00', '0:0', '0', '');
INSERT INTO `lt_play_option` VALUES ('22', '02_ZC_1', '11', '1:1', '0', '');
INSERT INTO `lt_play_option` VALUES ('23', '02_ZC_1', '22', '2:2', '0', '');
INSERT INTO `lt_play_option` VALUES ('24', '02_ZC_1', '33', '3:3', '0', '');
INSERT INTO `lt_play_option` VALUES ('25', '02_ZC_1', '99', '平其他', '0', '');
INSERT INTO `lt_play_option` VALUES ('26', '02_ZC_1', '01', '0:1', '0', '');
INSERT INTO `lt_play_option` VALUES ('27', '02_ZC_1', '02', '0:2', '0', '');
INSERT INTO `lt_play_option` VALUES ('28', '02_ZC_1', '12', '1:2', '0', '');
INSERT INTO `lt_play_option` VALUES ('29', '02_ZC_1', '03', '0:3', '0', '');
INSERT INTO `lt_play_option` VALUES ('30', '02_ZC_1', '13', '1:3', '0', '');
INSERT INTO `lt_play_option` VALUES ('31', '02_ZC_1', '23', '2:3', '0', '');
INSERT INTO `lt_play_option` VALUES ('32', '02_ZC_1', '04', '0:4', '0', '');
INSERT INTO `lt_play_option` VALUES ('33', '02_ZC_1', '14', '1:4', '0', '');
INSERT INTO `lt_play_option` VALUES ('34', '02_ZC_1', '24', '2:4', '0', '');
INSERT INTO `lt_play_option` VALUES ('35', '02_ZC_1', '05', '0:5', '0', '');
INSERT INTO `lt_play_option` VALUES ('36', '02_ZC_1', '15', '1:5', '0', '');
INSERT INTO `lt_play_option` VALUES ('37', '02_ZC_1', '25', '2:5', '0', '');
INSERT INTO `lt_play_option` VALUES ('38', '02_ZC_1', '09', '负其他', '0', '');
INSERT INTO `lt_play_option` VALUES ('39', '02_ZC_2', '10', '1:0', '0', '');
INSERT INTO `lt_play_option` VALUES ('40', '02_ZC_2', '20', '2:0', '0', '');
INSERT INTO `lt_play_option` VALUES ('41', '02_ZC_2', '21', '2:1', '0', '');
INSERT INTO `lt_play_option` VALUES ('42', '02_ZC_2', '30', '3:0', '0', '');
INSERT INTO `lt_play_option` VALUES ('43', '02_ZC_2', '31', '3:1', '0', '');
INSERT INTO `lt_play_option` VALUES ('44', '02_ZC_2', '32', '3:2', '0', '');
INSERT INTO `lt_play_option` VALUES ('45', '02_ZC_2', '40', '4:0', '0', '');
INSERT INTO `lt_play_option` VALUES ('46', '02_ZC_2', '41', '4:1', '0', '');
INSERT INTO `lt_play_option` VALUES ('47', '02_ZC_2', '42', '4:2', '0', '');
INSERT INTO `lt_play_option` VALUES ('48', '02_ZC_2', '50', '5:0', '0', '');
INSERT INTO `lt_play_option` VALUES ('49', '02_ZC_2', '51', '5:1', '0', '');
INSERT INTO `lt_play_option` VALUES ('50', '02_ZC_2', '52', '5:2', '0', '');
INSERT INTO `lt_play_option` VALUES ('51', '02_ZC_2', '90', '胜其他', '0', '');
INSERT INTO `lt_play_option` VALUES ('52', '02_ZC_2', '00', '0:0', '0', '');
INSERT INTO `lt_play_option` VALUES ('53', '02_ZC_2', '11', '1:1', '0', '');
INSERT INTO `lt_play_option` VALUES ('54', '02_ZC_2', '22', '2:2', '0', '');
INSERT INTO `lt_play_option` VALUES ('55', '02_ZC_2', '33', '3:3', '0', '');
INSERT INTO `lt_play_option` VALUES ('56', '02_ZC_2', '99', '平其他', '0', '');
INSERT INTO `lt_play_option` VALUES ('57', '02_ZC_2', '01', '0:1', '0', '');
INSERT INTO `lt_play_option` VALUES ('58', '02_ZC_2', '02', '0:2', '0', '');
INSERT INTO `lt_play_option` VALUES ('59', '02_ZC_2', '12', '1:2', '0', '');
INSERT INTO `lt_play_option` VALUES ('60', '02_ZC_2', '03', '0:3', '0', '');
INSERT INTO `lt_play_option` VALUES ('61', '02_ZC_2', '13', '1:3', '0', '');
INSERT INTO `lt_play_option` VALUES ('62', '02_ZC_2', '23', '2:3', '0', '');
INSERT INTO `lt_play_option` VALUES ('63', '02_ZC_2', '04', '0:4', '0', '');
INSERT INTO `lt_play_option` VALUES ('64', '02_ZC_2', '14', '1:4', '0', '');
INSERT INTO `lt_play_option` VALUES ('65', '02_ZC_2', '24', '2:4', '0', '');
INSERT INTO `lt_play_option` VALUES ('66', '02_ZC_2', '05', '0:5', '0', '');
INSERT INTO `lt_play_option` VALUES ('67', '02_ZC_2', '15', '1:5', '0', '');
INSERT INTO `lt_play_option` VALUES ('68', '02_ZC_2', '25', '2:5', '0', '');
INSERT INTO `lt_play_option` VALUES ('69', '02_ZC_2', '09', '负其他', '0', '');
INSERT INTO `lt_play_option` VALUES ('70', '03_ZC_1', '0', '0', '0', '');
INSERT INTO `lt_play_option` VALUES ('71', '03_ZC_1', '1', '1', '0', '');
INSERT INTO `lt_play_option` VALUES ('72', '03_ZC_1', '2', '2', '0', '');
INSERT INTO `lt_play_option` VALUES ('73', '03_ZC_1', '3', '3', '0', '');
INSERT INTO `lt_play_option` VALUES ('74', '03_ZC_1', '4', '4', '0', '');
INSERT INTO `lt_play_option` VALUES ('75', '03_ZC_1', '5', '5', '0', '');
INSERT INTO `lt_play_option` VALUES ('76', '03_ZC_1', '6', '6', '0', '');
INSERT INTO `lt_play_option` VALUES ('77', '03_ZC_1', '7', '7+', '0', '');
INSERT INTO `lt_play_option` VALUES ('78', '03_ZC_2', '0', '0', '0', '');
INSERT INTO `lt_play_option` VALUES ('79', '03_ZC_2', '1', '1', '0', '');
INSERT INTO `lt_play_option` VALUES ('80', '03_ZC_2', '2', '2', '0', '');
INSERT INTO `lt_play_option` VALUES ('81', '03_ZC_2', '3', '3', '0', '');
INSERT INTO `lt_play_option` VALUES ('82', '03_ZC_2', '4', '4', '0', '');
INSERT INTO `lt_play_option` VALUES ('83', '03_ZC_2', '5', '5', '0', '');
INSERT INTO `lt_play_option` VALUES ('84', '03_ZC_2', '6', '6', '0', '');
INSERT INTO `lt_play_option` VALUES ('85', '03_ZC_2', '7', '7+', '0', '');
INSERT INTO `lt_play_option` VALUES ('86', '04_ZC_1', '33', '胜胜', '0', '');
INSERT INTO `lt_play_option` VALUES ('87', '04_ZC_1', '31', '胜平', '0', '');
INSERT INTO `lt_play_option` VALUES ('88', '04_ZC_1', '30', '胜负', '0', '');
INSERT INTO `lt_play_option` VALUES ('89', '04_ZC_1', '13', '平胜', '0', '');
INSERT INTO `lt_play_option` VALUES ('90', '04_ZC_1', '11', '平平', '0', '');
INSERT INTO `lt_play_option` VALUES ('91', '04_ZC_1', '10', '平负', '0', '');
INSERT INTO `lt_play_option` VALUES ('92', '04_ZC_1', '03', '负胜', '0', '');
INSERT INTO `lt_play_option` VALUES ('93', '04_ZC_1', '01', '负平', '0', '');
INSERT INTO `lt_play_option` VALUES ('94', '04_ZC_1', '00', '负负', '0', '');
INSERT INTO `lt_play_option` VALUES ('95', '04_ZC_2', '33', '胜胜', '0', '');
INSERT INTO `lt_play_option` VALUES ('96', '04_ZC_2', '31', '胜平', '0', '');
INSERT INTO `lt_play_option` VALUES ('97', '04_ZC_2', '30', '胜负', '0', '');
INSERT INTO `lt_play_option` VALUES ('98', '04_ZC_2', '13', '平胜', '0', '');
INSERT INTO `lt_play_option` VALUES ('99', '04_ZC_2', '11', '平平', '0', '');
INSERT INTO `lt_play_option` VALUES ('100', '04_ZC_2', '10', '平负', '0', '');
INSERT INTO `lt_play_option` VALUES ('101', '04_ZC_2', '03', '负胜', '0', '');
INSERT INTO `lt_play_option` VALUES ('102', '04_ZC_2', '01', '负平', '0', '');
INSERT INTO `lt_play_option` VALUES ('103', '04_ZC_2', '00', '负负', '0', '');
INSERT INTO `lt_play_option` VALUES ('104', '06_LC_1', '2', '主负', '0', '');
INSERT INTO `lt_play_option` VALUES ('105', '06_LC_1', '1', '主胜', '0', '');
INSERT INTO `lt_play_option` VALUES ('106', '06_LC_2', '2', '主负', '0', '');
INSERT INTO `lt_play_option` VALUES ('107', '06_LC_2', '1', '主胜', '0', '');
INSERT INTO `lt_play_option` VALUES ('108', '07_LC_1', '2', '主负', '0', '');
INSERT INTO `lt_play_option` VALUES ('109', '07_LC_1', '1', '主胜', '0', '');
INSERT INTO `lt_play_option` VALUES ('110', '07_LC_2', '2', '主负', '0', '');
INSERT INTO `lt_play_option` VALUES ('111', '07_LC_2', '1', '主胜', '0', '');
INSERT INTO `lt_play_option` VALUES ('112', '09_LC_1', '1', '大', '0', '');
INSERT INTO `lt_play_option` VALUES ('113', '09_LC_1', '2', '小', '0', '');
INSERT INTO `lt_play_option` VALUES ('114', '09_LC_2', '1', '大', '0', '');
INSERT INTO `lt_play_option` VALUES ('115', '09_LC_2', '2', '小', '0', '');
INSERT INTO `lt_play_option` VALUES ('116', '08_LC_1', '01', '主胜1-5', '0', '');
INSERT INTO `lt_play_option` VALUES ('117', '08_LC_1', '02', '主胜6-10', '0', '');
INSERT INTO `lt_play_option` VALUES ('118', '08_LC_1', '03', '主胜11-15', '0', '');
INSERT INTO `lt_play_option` VALUES ('119', '08_LC_1', '04', '主胜16-20', '0', '');
INSERT INTO `lt_play_option` VALUES ('120', '08_LC_1', '05', '主胜21-25', '0', '');
INSERT INTO `lt_play_option` VALUES ('121', '08_LC_1', '06', '主胜26+', '0', '');
INSERT INTO `lt_play_option` VALUES ('122', '08_LC_1', '11', '客胜1-5', '0', '');
INSERT INTO `lt_play_option` VALUES ('123', '08_LC_1', '12', '客胜6-10', '0', '');
INSERT INTO `lt_play_option` VALUES ('124', '08_LC_1', '13', '客胜11-15', '0', '');
INSERT INTO `lt_play_option` VALUES ('125', '08_LC_1', '14', '客胜16-20', '0', '');
INSERT INTO `lt_play_option` VALUES ('126', '08_LC_1', '15', '客胜21-25', '0', '');
INSERT INTO `lt_play_option` VALUES ('127', '08_LC_1', '16', '客胜26+', '0', '');
INSERT INTO `lt_play_option` VALUES ('129', '08_LC_2', '01', '主胜1-5', '0', '');
INSERT INTO `lt_play_option` VALUES ('130', '08_LC_2', '02', '主胜6-10', '0', '');
INSERT INTO `lt_play_option` VALUES ('131', '08_LC_2', '03', '主胜11-15', '0', '');
INSERT INTO `lt_play_option` VALUES ('132', '08_LC_2', '04', '主胜16-20', '0', '');
INSERT INTO `lt_play_option` VALUES ('133', '08_LC_2', '05', '主胜21-25', '0', '');
INSERT INTO `lt_play_option` VALUES ('134', '08_LC_2', '06', '主胜26+', '0', '');
INSERT INTO `lt_play_option` VALUES ('135', '08_LC_2', '11', '客胜1-5', '0', '');
INSERT INTO `lt_play_option` VALUES ('136', '08_LC_2', '12', '客胜6-10', '0', '');
INSERT INTO `lt_play_option` VALUES ('137', '08_LC_2', '13', '客胜11-15', '0', '');
INSERT INTO `lt_play_option` VALUES ('138', '08_LC_2', '14', '客胜16-20', '0', '');
INSERT INTO `lt_play_option` VALUES ('139', '08_LC_2', '15', '客胜21-25', '0', '');
INSERT INTO `lt_play_option` VALUES ('140', '08_LC_2', '16', '客胜26+', '0', '');

-- ----------------------------
-- Table structure for `lt_play_pass_type`
-- ----------------------------
DROP TABLE IF EXISTS `lt_play_pass_type`;
CREATE TABLE `lt_play_pass_type` (
  `play_id` varchar(16) NOT NULL,
  `pass_type_id` varchar(16) NOT NULL,
  PRIMARY KEY (`play_id`,`pass_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of lt_play_pass_type
-- ----------------------------
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_1', '1@1');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '2@1');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '3@1');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '3@3');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '3@4');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '4@1');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '4@11');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '4@4');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '4@5');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '4@6');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '5@1');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '5@10');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '5@16');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '5@20');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '5@26');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '5@5');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '5@6');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '6@1');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '6@15');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '6@20');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '6@22');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '6@35');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '6@42');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '6@50');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '6@57');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '6@6');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '6@7');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '7@1');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '7@120');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '7@21');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '7@35');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '7@7');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '7@8');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '8@1');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '8@247');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '8@28');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '8@56');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '8@70');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '8@8');
INSERT INTO `lt_play_pass_type` VALUES ('01_ZC_2', '8@9');
INSERT INTO `lt_play_pass_type` VALUES ('02_ZC_1', '1@1');
INSERT INTO `lt_play_pass_type` VALUES ('02_ZC_2', '2@1');
INSERT INTO `lt_play_pass_type` VALUES ('02_ZC_2', '3@1');
INSERT INTO `lt_play_pass_type` VALUES ('02_ZC_2', '3@3');
INSERT INTO `lt_play_pass_type` VALUES ('02_ZC_2', '3@4');
INSERT INTO `lt_play_pass_type` VALUES ('02_ZC_2', '4@1');
INSERT INTO `lt_play_pass_type` VALUES ('02_ZC_2', '4@11');
INSERT INTO `lt_play_pass_type` VALUES ('02_ZC_2', '4@4');
INSERT INTO `lt_play_pass_type` VALUES ('02_ZC_2', '4@5');
INSERT INTO `lt_play_pass_type` VALUES ('02_ZC_2', '4@6');
INSERT INTO `lt_play_pass_type` VALUES ('03_ZC_1', '1@1');
INSERT INTO `lt_play_pass_type` VALUES ('03_ZC_2', '2@1');
INSERT INTO `lt_play_pass_type` VALUES ('03_ZC_2', '3@1');
INSERT INTO `lt_play_pass_type` VALUES ('03_ZC_2', '3@3');
INSERT INTO `lt_play_pass_type` VALUES ('03_ZC_2', '3@4');
INSERT INTO `lt_play_pass_type` VALUES ('03_ZC_2', '4@1');
INSERT INTO `lt_play_pass_type` VALUES ('03_ZC_2', '4@11');
INSERT INTO `lt_play_pass_type` VALUES ('03_ZC_2', '4@4');
INSERT INTO `lt_play_pass_type` VALUES ('03_ZC_2', '4@5');
INSERT INTO `lt_play_pass_type` VALUES ('03_ZC_2', '4@6');
INSERT INTO `lt_play_pass_type` VALUES ('03_ZC_2', '5@1');
INSERT INTO `lt_play_pass_type` VALUES ('03_ZC_2', '5@10');
INSERT INTO `lt_play_pass_type` VALUES ('03_ZC_2', '5@16');
INSERT INTO `lt_play_pass_type` VALUES ('03_ZC_2', '5@20');
INSERT INTO `lt_play_pass_type` VALUES ('03_ZC_2', '5@26');
INSERT INTO `lt_play_pass_type` VALUES ('03_ZC_2', '5@5');
INSERT INTO `lt_play_pass_type` VALUES ('03_ZC_2', '5@6');
INSERT INTO `lt_play_pass_type` VALUES ('03_ZC_2', '6@1');
INSERT INTO `lt_play_pass_type` VALUES ('03_ZC_2', '6@15');
INSERT INTO `lt_play_pass_type` VALUES ('03_ZC_2', '6@20');
INSERT INTO `lt_play_pass_type` VALUES ('03_ZC_2', '6@22');
INSERT INTO `lt_play_pass_type` VALUES ('03_ZC_2', '6@35');
INSERT INTO `lt_play_pass_type` VALUES ('03_ZC_2', '6@42');
INSERT INTO `lt_play_pass_type` VALUES ('03_ZC_2', '6@50');
INSERT INTO `lt_play_pass_type` VALUES ('03_ZC_2', '6@57');
INSERT INTO `lt_play_pass_type` VALUES ('03_ZC_2', '6@6');
INSERT INTO `lt_play_pass_type` VALUES ('03_ZC_2', '6@7');
INSERT INTO `lt_play_pass_type` VALUES ('04_ZC_1', '1@1');
INSERT INTO `lt_play_pass_type` VALUES ('04_ZC_2', '2@1');
INSERT INTO `lt_play_pass_type` VALUES ('04_ZC_2', '3@1');
INSERT INTO `lt_play_pass_type` VALUES ('04_ZC_2', '3@3');
INSERT INTO `lt_play_pass_type` VALUES ('04_ZC_2', '3@4');
INSERT INTO `lt_play_pass_type` VALUES ('04_ZC_2', '4@1');
INSERT INTO `lt_play_pass_type` VALUES ('04_ZC_2', '4@11');
INSERT INTO `lt_play_pass_type` VALUES ('04_ZC_2', '4@4');
INSERT INTO `lt_play_pass_type` VALUES ('04_ZC_2', '4@5');
INSERT INTO `lt_play_pass_type` VALUES ('04_ZC_2', '4@6');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_1', '1@1');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '2@1');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '3@1');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '3@3');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '3@4');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '4@1');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '4@11');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '4@4');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '4@5');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '4@6');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '5@1');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '5@10');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '5@16');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '5@20');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '5@26');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '5@5');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '5@6');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '6@1');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '6@15');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '6@20');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '6@22');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '6@35');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '6@42');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '6@50');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '6@57');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '6@6');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '6@7');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '7@1');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '7@120');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '7@21');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '7@35');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '7@7');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '7@8');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '8@1');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '8@247');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '8@28');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '8@56');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '8@70');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '8@8');
INSERT INTO `lt_play_pass_type` VALUES ('06_LC_2', '8@9');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_1', '1@1');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '2@1');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '3@1');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '3@3');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '3@4');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '4@1');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '4@11');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '4@4');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '4@5');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '4@6');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '5@1');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '5@10');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '5@16');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '5@20');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '5@26');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '5@5');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '5@6');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '6@1');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '6@15');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '6@20');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '6@22');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '6@35');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '6@42');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '6@50');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '6@57');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '6@6');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '6@7');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '7@1');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '7@120');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '7@21');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '7@35');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '7@7');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '7@8');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '8@1');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '8@247');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '8@28');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '8@56');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '8@70');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '8@8');
INSERT INTO `lt_play_pass_type` VALUES ('07_LC_2', '8@9');
INSERT INTO `lt_play_pass_type` VALUES ('08_LC_1', '1@1');
INSERT INTO `lt_play_pass_type` VALUES ('08_LC_2', '2@1');
INSERT INTO `lt_play_pass_type` VALUES ('08_LC_2', '3@1');
INSERT INTO `lt_play_pass_type` VALUES ('08_LC_2', '3@3');
INSERT INTO `lt_play_pass_type` VALUES ('08_LC_2', '3@4');
INSERT INTO `lt_play_pass_type` VALUES ('08_LC_2', '4@1');
INSERT INTO `lt_play_pass_type` VALUES ('08_LC_2', '4@11');
INSERT INTO `lt_play_pass_type` VALUES ('08_LC_2', '4@4');
INSERT INTO `lt_play_pass_type` VALUES ('08_LC_2', '4@5');
INSERT INTO `lt_play_pass_type` VALUES ('08_LC_2', '4@6');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_1', '1@1');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '2@1');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '3@1');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '3@3');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '3@4');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '4@1');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '4@11');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '4@4');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '4@5');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '5@1');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '5@10');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '5@16');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '5@20');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '5@26');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '5@5');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '5@6');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '6@1');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '6@15');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '6@20');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '6@22');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '6@35');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '6@42');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '6@50');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '6@57');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '6@6');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '6@7');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '7@1');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '7@120');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '7@21');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '7@35');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '7@7');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '7@8');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '8@1');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '8@247');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '8@28');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '8@56');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '8@70');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '8@8');
INSERT INTO `lt_play_pass_type` VALUES ('09_LC_2', '8@9');

-- ----------------------------
-- Table structure for `lt_ticket`
-- ----------------------------
DROP TABLE IF EXISTS `lt_ticket`;
CREATE TABLE `lt_ticket` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `scheme_id` bigint(20) NOT NULL,
  `play_id` varchar(16) NOT NULL,
  `pass_type_id` varchar(16) NOT NULL,
  `code` varchar(512) NOT NULL,
  `odds` varchar(2048) DEFAULT NULL,
  `note` int(11) unsigned NOT NULL DEFAULT '1',
  `multiple` int(11) unsigned NOT NULL DEFAULT '1',
  `money` int(11) unsigned NOT NULL DEFAULT '0',
  `expect_bonus` decimal(12,2) DEFAULT NULL,
  `pre_tax_bonus` decimal(12,2) DEFAULT NULL,
  `after_tax_bonus` decimal(12,2) DEFAULT NULL,
  `issue` varchar(16) DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `number` varchar(32) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `status` smallint(5) unsigned NOT NULL DEFAULT '0',
  `version` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `scheme_id` (`scheme_id`),
  KEY `created_time` (`created_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1050 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `lt_user`
-- ----------------------------
DROP TABLE IF EXISTS `lt_user`;
CREATE TABLE `lt_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `real_name` varchar(32) NOT NULL,
  `id_number` varchar(18) DEFAULT NULL,
  `question` varchar(32) NOT NULL,
  `answer` varchar(16) NOT NULL,
  `email` varchar(64) DEFAULT NULL,
  `mobile` varchar(16) NOT NULL DEFAULT '0',
  `phone` varchar(16) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `province` varchar(16) DEFAULT NULL,
  `city` varchar(16) DEFAULT NULL,
  `verify_status` int(11) NOT NULL,
  `gender` smallint(5) unsigned NOT NULL DEFAULT '0',
  `status` smallint(5) unsigned NOT NULL DEFAULT '0',
  `birthday` date DEFAULT NULL,
  `ip` varchar(32) DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `last_login_time` datetime NOT NULL,
  `last_login_ip` varchar(32) DEFAULT NULL,
  `referer` varchar(32) DEFAULT NULL,
  `login_number` int(11) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `id_number` (`id_number`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `lt_win`
-- ----------------------------
DROP TABLE IF EXISTS `lt_win`;
CREATE TABLE `lt_win` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `scheme_id` bigint(20) DEFAULT NULL,
  `amount` decimal(12,2) DEFAULT NULL,
  `bonus` decimal(12,2) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `uc_idcard`
-- ----------------------------
DROP TABLE IF EXISTS `uc_idcard`;
CREATE TABLE `uc_idcard` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `username` varchar(32) NOT NULL,
  `realname` varchar(32) DEFAULT NULL,
  `idnumber` varchar(18) DEFAULT NULL,
  `picture` varchar(64) NOT NULL,
  `admin_id` bigint(20) DEFAULT NULL,
  `admin` varchar(32) DEFAULT NULL,
  `status` smallint(6) NOT NULL,
  `type` smallint(6) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `uc_message`
-- ----------------------------
DROP TABLE IF EXISTS `uc_message`;
CREATE TABLE `uc_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sys_message_id` bigint(20) DEFAULT NULL,
  `uid` bigint(20) NOT NULL,
  `user` varchar(32) NOT NULL,
  `type` smallint(5) NOT NULL,
  `isread` bit(2) NOT NULL,
  `subject` varchar(255) NOT NULL,
  `note` varchar(255) NOT NULL,
  `authorid` bigint(20) NOT NULL,
  `author` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of uc_message
-- ----------------------------

-- ----------------------------
-- Table structure for `uc_sysmessage`
-- ----------------------------
DROP TABLE IF EXISTS `uc_sysmessage`;
CREATE TABLE `uc_sysmessage` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` int(11) NOT NULL,
  `subject` varchar(255) NOT NULL,
  `note` varchar(255) NOT NULL,
  `authorid` bigint(20) NOT NULL,
  `author` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `expiry_date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of uc_sysmessage
-- ----------------------------

-- ----------------------------
-- Table structure for `uc_verify`
-- ----------------------------
DROP TABLE IF EXISTS `uc_verify`;
CREATE TABLE `uc_verify` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NOT NULL,
  `code` varchar(60) NOT NULL,
  `target` varchar(64) DEFAULT NULL,
  `created_time` datetime NOT NULL,
  `expiry_time` datetime NOT NULL,
  `status` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8;

-- ----------------------------
-- View structure for `view_zc_1`
-- ----------------------------
DROP VIEW IF EXISTS `view_zc_1`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `view_zc_1` AS select `m`.`playing_time` AS `playing_time`,`m`.`id` AS `id`,`m`.`name` AS `name`,`m`.`code` AS `code`,`m`.`offtime` AS `offtime`,`m`.`league_name` AS `league_name`,`m`.`home_team_name` AS `home_team_name`,`m`.`guest_team_name` AS `guest_team_name`,`m`.`concede_points` AS `concede_points`,`mp`.`options` AS `options`,`mp`.`prior_odds` AS `prior_odds`,`mp`.`odds` AS `odds`,`mp`.`status` AS `status`,`mp`.`play_id` AS `play_id` from (`fb_match` `m` join `fb_match_play` `mp`) where (`m`.`id` = `mp`.`match_id`) order by `mp`.`match_id` desc;
DELIMITER ;;
CREATE TRIGGER `trigger_fb_match_play_update` BEFORE UPDATE ON `fb_match_play` FOR EACH ROW BEGIN
SET NEW.prior_odds = OLD.odds;
END
;;
DELIMITER ;
