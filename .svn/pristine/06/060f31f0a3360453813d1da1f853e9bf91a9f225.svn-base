--大V彩平台预先生成的赔率字符串
ALTER TABLE `lt_ticket`
	ADD COLUMN `davcai_odds` VARCHAR(2048) NULL DEFAULT NULL COMMENT '大V彩平台预先生成的赔率字符串' AFTER `platform_bet_code`;

--实体店出票配置
CREATE TABLE `lt_bet_switch` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	`channel` VARCHAR(50) NOT NULL COMMENT '投注渠道ID',
	`lottery_id` VARCHAR(50) NOT NULL COMMENT '彩种',
	`status` INT(10) NOT NULL DEFAULT '0' COMMENT '开启接收:0，停止接收:-1',
	`update_time` DATETIME NULL DEFAULT NULL COMMENT '更新时间',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `channel_lottery_id_status` (`channel`, `lottery_id`, `status`),
	INDEX `status` (`status`)
)
COMMENT='投注开关'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

INSERT INTO `lt_bet_switch` VALUES (1, 'mobile', 'JCZQ', 0, NULL);
INSERT INTO `lt_bet_switch` VALUES (2, 'mobile', 'JCLQ', 0, NULL);
INSERT INTO `lt_bet_switch` VALUES (3, 'mobile', 'CTZC', 0, NULL);
INSERT INTO `lt_bet_switch` VALUES (4, 'mobile', 'CQSS', 0, NULL);
INSERT INTO `lt_bet_switch` VALUES (5, 'mobile', 'JX11', 0, NULL);
INSERT INTO `lt_bet_switch` VALUES (6, 'mobile', 'SSQ', 0, NULL);



ALTER TABLE `lt_ticket_preset`
  CHANGE COLUMN `number` `number` VARCHAR(50) NULL DEFAULT NULL AFTER `created_time`;

  
ALTER TABLE `lt_bet_scheme_preset`
	CHANGE COLUMN `partner` `partner` VARCHAR(50) NOT NULL DEFAULT 'lai310www' COMMENT '合作伙伴\\n\\n' AFTER `channel`;
	
	
INSERT INTO `lt_return_status` (`system_key`, `status_code_for_client`, `desc_for_client`) VALUES ('bet_close', '6008', '投注已截止');



CREATE TABLE `lt_lottery_open_sale` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
	`lotteryId` VARCHAR(50) NOT NULL COMMENT '彩种id',
	`dayOfWeek` SMALLINT(6) NOT NULL COMMENT '周几，周日是1，周1是2，。。。周六是7',
	`openTime` TIME NOT NULL COMMENT '投注开始时间，格式为HH:mm:ss',
	`endTime` TIME NOT NULL COMMENT '投注截止时间，格式为HH:mm:ss',
	PRIMARY KEY (`id`)
)
COMMENT='各彩种每天投注开始和截止时间配置表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;



INSERT INTO `lt_lottery_open_sale` VALUES (1, 'JCZQ', 1, '09:00:00', '23:00:00');
INSERT INTO `lt_lottery_open_sale` VALUES (2, 'JCZQ', 2, '09:00:00', '22:00:00');
INSERT INTO `lt_lottery_open_sale` VALUES (3, 'JCZQ', 3, '09:00:00', '22:00:00');
INSERT INTO `lt_lottery_open_sale` VALUES (4, 'JCZQ', 4, '09:00:00', '22:00:00');
INSERT INTO `lt_lottery_open_sale` VALUES (5, 'JCZQ', 5, '09:00:00', '22:00:00');
INSERT INTO `lt_lottery_open_sale` VALUES (6, 'JCZQ', 6, '09:00:00', '22:00:00');
INSERT INTO `lt_lottery_open_sale` VALUES (7, 'JCZQ', 7, '09:00:00', '23:00:00');
INSERT INTO `lt_lottery_open_sale` VALUES (8, 'JCLQ', 1, '09:00:00', '23:00:00');
INSERT INTO `lt_lottery_open_sale` VALUES (9, 'JCLQ', 2, '09:00:00', '22:00:00');
INSERT INTO `lt_lottery_open_sale` VALUES (10, 'JCLQ', 3, '09:00:00', '22:00:00');
INSERT INTO `lt_lottery_open_sale` VALUES (11, 'JCLQ', 4, '07:30:00', '22:00:00');
INSERT INTO `lt_lottery_open_sale` VALUES (12, 'JCLQ', 5, '07:30:00', '22:00:00');
INSERT INTO `lt_lottery_open_sale` VALUES (13, 'JCLQ', 6, '09:00:00', '22:00:00');
INSERT INTO `lt_lottery_open_sale` VALUES (14, 'JCLQ', 7, '09:00:00', '23:00:00');


INSERT INTO `lt_system_conf` VALUES (7, 'before_close_minute', '120', '实体店出票时，提前截止时间，单位分钟');
