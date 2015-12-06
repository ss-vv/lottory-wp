/*
Navicat MySQL Data Transfer

Source Server         : local test lottery
Source Server Version : 50165
Source Host           : localhost:3306
Source Database       : db_lottery

Target Server Type    : MYSQL
Target Server Version : 50165
File Encoding         : 65001

Date: 2012-11-21 18:21:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `lt_system_conf`
-- ----------------------------
DROP TABLE IF EXISTS `lt_system_conf`;
CREATE TABLE `lt_system_conf` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `conf_key` varchar(50) NOT NULL COMMENT '配置的键',
  `conf_value` varchar(50) NOT NULL COMMENT '配置的值',
  `desc` varchar(50) NOT NULL COMMENT '对于该配置的描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `conf_key` (`conf_key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of lt_system_conf
-- ----------------------------
INSERT INTO `lt_system_conf` VALUES ('1', 'interval_minute_for_zm_close_time', '2', '中民投票截止时间间隔（以分钟为单位）');
INSERT INTO `lt_system_conf` VALUES ('2', 'interval_minute_for_lc_stop_time', '1', '大V彩期截止时间间隔（以分钟为单位）');
INSERT INTO `lt_system_conf` VALUES ('3', 'issue_extend_second', '40', 'MC出票延长时间:单位秒，可以为负数');
INSERT INTO `lt_system_conf` VALUES ('4', 'ctzc_lc_ahead_second', '600', '大V彩传统足彩相对中民期截止提前时间(秒)');
INSERT INTO `lt_system_conf` VALUES ('5', 'groupbuy_default_ahead_second', '300', '合买默认提前截至时间，单位秒');