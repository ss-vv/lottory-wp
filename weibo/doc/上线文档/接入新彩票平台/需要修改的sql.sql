ALTER TABLE `lt_ticket`
	ADD COLUMN `lottery_platform_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '表示属于哪个彩票平台，zunao表示尊奥，anruizhiying表示安瑞智赢' AFTER `preset_award`;
ALTER TABLE `lt_ticket`
	ADD COLUMN `platform_bet_code` VARCHAR(2048) NULL COMMENT '平台投注格式串,使用英文半角分号分割' AFTER `lottery_platform_id`;
ALTER TABLE `lt_ticket`
	CHANGE COLUMN `actual_code` `actual_code` VARCHAR(512) NULL DEFAULT '' AFTER `code`;
ALTER TABLE `lt_ticket`
	CHANGE COLUMN `actual_status` `actual_status` SMALLINT(5) NOT NULL DEFAULT '0' AFTER `status`;
ALTER TABLE `lt_ticket`
	ADD INDEX `lottery_platform_id` (`lottery_platform_id`);

CREATE TABLE `lottery_platform_priority` (
	`id` INT NOT NULL AUTO_INCREMENT COMMENT '自增主键',
	`lottery_platform_id` VARCHAR(100) NOT NULL COMMENT '彩票平台id，zunao表示尊奥，zuruizhiying表示安瑞至赢',
	`interface_name` VARCHAR(100) NOT NULL COMMENT '接口名称',
	`lottery_id` VARCHAR(100) NULL DEFAULT NULL COMMENT '彩种id',
	`priority` INT UNSIGNED NOT NULL DEFAULT '0' COMMENT '权重，数字越大表示权重越高，值为0表示权重最低，一般不参与按权重分配的过程',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `lottery_platform_id_interface_name_lottery_id` (`lottery_platform_id`, `interface_name`, `lottery_id`)
)
COMMENT='彩票平台权重表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

INSERT INTO `lottery_platform_priority` VALUES (1, 'anruizhiying', 'orderTicket', 'JCZQ', 1);
INSERT INTO `lottery_platform_priority` VALUES (2, 'zunao', 'orderTicket', 'JCZQ', 1);
INSERT INTO `lottery_platform_priority` VALUES (3, 'zunao', 'queryMatch', NULL, 0);
INSERT INTO `lottery_platform_priority` VALUES (4, 'anruizhiying', 'queryMatch', NULL, 1);
INSERT INTO `lottery_platform_priority` VALUES (5, 'zunao', 'orderTicket', 'JCLQ', 1);
INSERT INTO `lottery_platform_priority` VALUES (6, 'anruizhiying', 'orderTicket', 'JCLQ', 1);
INSERT INTO `lottery_platform_priority` VALUES (7, 'zunao', 'orderTicket', 'CQSS', 1);
INSERT INTO `lottery_platform_priority` VALUES (9, 'zunao', 'orderTicket', 'CTZC', 1);
INSERT INTO `lottery_platform_priority` VALUES (10, 'zunao', 'orderTicket', 'JX11', 1);
INSERT INTO `lottery_platform_priority` VALUES (11, 'zunao', 'orderTicket', 'SSQ', 1);
INSERT INTO `lottery_platform_priority` VALUES (12, 'anruizhiying', 'queryMatchOdds', NULL, 1);
INSERT INTO `lottery_platform_priority` VALUES (13, 'zunao', 'queryMatchOdds', NULL, 0);
INSERT INTO `lottery_platform_priority` VALUES (14, 'zunao', 'orderTicket', 'BJDC', 1);
INSERT INTO `lottery_platform_priority` (`lottery_platform_id`, `interface_name`, `lottery_id`, `priority`) VALUES ('zunao', 'orderTicket', 'BDSF', 1);
INSERT INTO `lottery_platform_priority` (`lottery_platform_id`, `interface_name`, `lottery_id`, `priority`) VALUES ('zunao', 'orderTicket', 'FC3D', 1);
 


