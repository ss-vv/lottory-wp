--大V彩赛事ID与各个彩票平台定义的赛事ID对应关系
CREATE TABLE `lt_match_platform` (
	`id` INT(12) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键标识',
	`platform_id` VARCHAR(50) NOT NULL COMMENT '平台ID',
	`lottery_id` VARCHAR(50) NOT NULL COMMENT '大V彩定义的彩种ID',
	`match_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '大V彩定义的赛事ID',
	`p_match_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '平台定义的赛事ID',
	`created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `uniq_plm` (`platform_id`, `lottery_id`, `match_id`)
)
COMMENT='大V彩赛事ID与各个彩票平台定义的赛事ID对应关系'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;