CREATE TABLE `lt_ssq_info` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`lottery_id` VARCHAR(50) NOT NULL DEFAULT 'SSQ' COMMENT '彩种ID',
	`issue_number` VARCHAR(50) NOT NULL DEFAULT '0' COMMENT '期号',
	`total_sales` DECIMAL(12,2) NOT NULL DEFAULT '0.00' COMMENT '总销售额',
	`ydj_bouns` DECIMAL(12,2) NOT NULL DEFAULT '0.00' COMMENT '一等奖奖金',
	`jackpot` DECIMAL(12,2) NOT NULL DEFAULT '0.00' COMMENT '奖池滚存',
	`used_ball_num` INT(11) NOT NULL DEFAULT '0' COMMENT '使用第几套球开奖',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `issue_number` (`issue_number`)
)
COMMENT='双色球往期信息'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;
