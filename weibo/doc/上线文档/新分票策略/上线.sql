ALTER TABLE `lottery_platform_priority`
	ADD COLUMN `priority_of_big_ticket` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '分配大额票的权重，数字越大表示权重越高，值为0表示权重最低' AFTER `priority`;

	INSERT INTO `db_lottery`.`lt_system_conf` (`conf_key`, `conf_value`, `desc`) VALUES ('should_arrange_big_ticket', '0', '是否需要对大额票进行分配。0为不需要，1为需要');
	INSERT INTO `db_lottery`.`lt_system_conf` (`conf_key`, `conf_value`, `desc`) VALUES ('big_ticket_money_threshold', '100', '大额票投注额阙值，单位元');