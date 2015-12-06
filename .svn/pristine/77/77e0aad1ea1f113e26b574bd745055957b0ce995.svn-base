CREATE TABLE `lt_match_support_play` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
	`lottery_id` VARCHAR(10) NOT NULL COMMENT '彩种ID',
	`match_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '赛事ID',
	`play_id` VARCHAR(16) NOT NULL COMMENT '玩法ID（对应lt_play的ID）',
	`created_time` DATETIME NOT NULL COMMENT '记录创建时间',
	PRIMARY KEY (`id`),
	INDEX `lottery_id_match_id` (`lottery_id`, `match_id`)
)
COMMENT='竞彩赛事所支持的单关玩法对应关系表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1

